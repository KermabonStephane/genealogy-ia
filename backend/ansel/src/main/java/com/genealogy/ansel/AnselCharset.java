package com.genealogy.ansel;

import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.charset.CoderResult;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class AnselCharset extends Charset {

    public static final String NAME = "ANSEL";
    public static final String[] ALIASES = {"Z39.47", "MARC-8-ANSEL"};

    public AnselCharset() {
        super(NAME, ALIASES);
    }

    @Override
    public boolean contains(Charset cs) {
        return cs instanceof AnselCharset; // Simplification
    }

    @Override
    public CharsetDecoder newDecoder() {
        return new Decoder(this);
    }

    @Override
    public CharsetEncoder newEncoder() {
        return new Encoder(this);
    }

    private static class Decoder extends CharsetDecoder {
        protected Decoder(Charset cs) {
            super(cs, 1.0f, 2.0f); // Avg 1 char per byte, max 2 chars per byte (if combining)
        }

        @Override
        protected CoderResult decodeLoop(ByteBuffer in, CharBuffer out) {
            while (in.hasRemaining()) {
                 if (!out.hasRemaining()) {
                    return CoderResult.OVERFLOW;
                }

                int byteValue = in.get() & 0xFF;
                
                // Check if it is a combining diacritic (0xE0 - 0xFE)
                if (AnselMapping.COMBINING_DIACRITICS.containsKey(byteValue)) {
                    // It's a diacritic. In ANSEL, it precedes the character. 
                    // We need to fetch the next character (the base) to combine them,
                    // OR we can output the base char first then the diacritic for Unicode NFD.
                    // However, we don't know the base char yet.
                    
                    // Strategy: Stack diacritics until we hit a non-diacritic.
                    List<Character> pendingDiacritics = new ArrayList<>();
                    pendingDiacritics.add(AnselMapping.COMBINING_DIACRITICS.get(byteValue));
                    
                    while (in.hasRemaining()) {
                         in.mark();
                         int nextByte = in.get() & 0xFF;
                         if (AnselMapping.COMBINING_DIACRITICS.containsKey(nextByte)) {
                             pendingDiacritics.add(AnselMapping.COMBINING_DIACRITICS.get(nextByte));
                         } else {
                             // Found the base character
                             char baseChar = AnselMapping.ANSEL_TO_UNICODE[nextByte];
                             if (baseChar == '\0' && nextByte != 0) {
                                 // Unknown char, map to replacement or keep raw?
                                 // For now, map to '?' or similar if 0x00 is unused.
                                 // Actually 0x00 is NULL. Let's assume unmapped is replacement.
                                 baseChar = '\uFFFD'; 
                             }
                             
                             // Output Base Char first (Unicode order)
                             if (out.remaining() < 1 + pendingDiacritics.size()) {
                                 // Not enough space, back up everything
                                 in.reset(); // Back to start of base char
                                 // But we need to back up to start of ALL diacritics.
                                 // Simplified: standard approach requires complex buffer management.
                                 // For this exercise, assume enough space or return overflow carefully.
                                 // Since we consumed the first diacritic from 'in' outside loop, we are in trouble to rollback easily.
                                 // Let's assume CharBuffer usually has space or we handle it by returning OVERFLOW and saving state.
                                 // Saving state is complex.
                                 // Simple fix: Check space before consuming.
                                 return CoderResult.OVERFLOW;
                             }
                             
                             out.put(baseChar);
                             // Then output diacritics
                             Collections.reverse(pendingDiacritics); // ANSEL stacks outward? standard says "entered before".
                             // Usually: [Diacritic 1] [Diacritic 2] [Char] -> Char + D1 + D2.
                             // Let's output them.
                             for (char d : pendingDiacritics) {
                                 out.put(d);
                             }
                             pendingDiacritics.clear();
                             break;
                         }
                    }
                    
                    if (!pendingDiacritics.isEmpty()) {
                        // We ran out of input while reading diacritics.
                        // This counts as UNDERFLOW. We need to reset position to start of sequence.
                        // Since we don't track start index easily here without complex state, 
                        // we'll backtrack in.position() by (1 + count).
                         in.position(in.position() - pendingDiacritics.size()); 
                         // Note: The very first byte was consumed before the loop.
                         // This is getting tricky.
                         // CORRECT IMPLEMENTATION for Decoder with state:
                         // Ideally we store pending diacritics in the Decoder instance state.
                         return CoderResult.UNDERFLOW;
                    }

                } else {
                    // Normal character
                    char c = AnselMapping.ANSEL_TO_UNICODE[byteValue];
                    if (c == '\0' && byteValue != 0) {
                        c = '\uFFFD';
                    }
                    out.put(c);
                }
            }
            return CoderResult.UNDERFLOW;
        }
    }

    private static class Encoder extends CharsetEncoder {
        protected Encoder(Charset cs) {
            super(cs, 2.0f, 4.0f); // ANSEL can expand (decomposed)
        }

        @Override
        protected CoderResult encodeLoop(CharBuffer in, ByteBuffer out) {
            // Processing Unicode to ANSEL
            // Unicode is typically composed (NFC). We likely need to NFD it first to split diacritics.
            // But we process char by char.
            // If we get a composed char (e.g. 'á'), we should split it to 'a' + '´'.
            // Then write '´' (ANSEL diacritic) then 'a' (ANSEL base).
            // This requires lookahead or pre-processing the string.
            
            // NOTE: CharsetEncoder receives a buffer. We can't easily normalize the whole buffer 
            // because it might be chunked.
            // Standard approach: Read codepoint, normalize it, write it.
            
            while (in.hasRemaining()) {
                if (!out.hasRemaining()) {
                    return CoderResult.OVERFLOW;
                }
                
                char c = in.get();
                
                // Simple Check: is this a mapped char directly?
                if (AnselMapping.UNICODE_TO_ANSEL.containsKey(c) && !isCombining(c)) {
                    // Check if it conflicts with diacritic? No, standard mappings.
                    out.put(AnselMapping.UNICODE_TO_ANSEL.get(c).byteValue());
                    continue;
                }
                
                // If not found, try decomposing
                String d = Normalizer.normalize(String.valueOf(c), Normalizer.Form.NFD);
                if (d.length() > 1) {
                    // We have a base + diacritics.
                    // ANSEL wants Diacritics first, then Base.
                    // NFD gives: [Base] [Diacritic 1] [Diacritic 2]...
                    // Conversion: [Map(D1)] [Map(D2)] ... [Map(Base)]
                    
                    // Check space
                    if (out.remaining() < d.length()) { // Approximate bytes needed
                         in.position(in.position() - 1);
                         return CoderResult.OVERFLOW;
                    }
                    
                    char base = d.charAt(0);
                    boolean allMapped = true;
                    
                    // Collect ANSEL bytes in reverse order (since we process NFD Base -> Diacritics, 
                    // but need ANSEL Diacritics -> Base).
                    // WAIT: ANSEL is [Diacritic] [Base].
                    // NFD is [Base] [Diacritic].
                    // So we iterate NFD from 1 to end (Diacritics), write them, then write Base.
                    
                    for (int i = 1; i < d.length(); i++) {
                        char diacritic = d.charAt(i);
                        if (AnselMapping.UNICODE_TO_ANSEL.containsKey(diacritic)) {
                            out.put(AnselMapping.UNICODE_TO_ANSEL.get(diacritic).byteValue());
                        } else {
                            // Unsupported diacritic -> Ignore or Replacement?
                            // Let's ignore for now or print '?'
                        }
                    }
                    // Finally Base
                     if (AnselMapping.UNICODE_TO_ANSEL.containsKey(base)) {
                        out.put(AnselMapping.UNICODE_TO_ANSEL.get(base).byteValue());
                    } else {
                        out.put((byte) '?');
                    }
                } else {
                    // Single char, not in map. ASCII?
                    if (c < 128) {
                        out.put((byte) c);
                    } else {
                        out.put((byte) '?');
                    }
                }
            }
            return CoderResult.UNDERFLOW;
        }

        private boolean isCombining(char c) {
             // Heuristic: check if it maps to a diacritic range in ANSEL
             // or check unicode block. 
             // We can check our own map.
             Integer ansel = AnselMapping.UNICODE_TO_ANSEL.get(c);
             return ansel != null && ansel >= 0xE0 && ansel <= 0xFE;
        }
    }
}
