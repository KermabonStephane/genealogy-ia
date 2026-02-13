package com.genealogy.ansel;

import java.util.HashMap;
import java.util.Map;

/**
 * Mapping definitions for ANSEL to Unicode conversion.
 * Based on Library of Congress ANSEL table and GEDCOM standards.
 */
public class AnselMapping {

    // ANSEL uses 0x88-0xC8 for special characters and 0xE0-0xFE for combining diacritics
    public static final char[] ANSEL_TO_UNICODE = new char[256];
    public static final Map<Character, Integer> UNICODE_TO_ANSEL = new HashMap<>();

    // Combining Diacritics Mapping (ANSEL -> Unicode Combining)
    // Note: In ANSEL, diacritics precede the letter. In Unicode, they follow.
    public static final Map<Integer, Character> COMBINING_DIACRITICS = new HashMap<>();

    static {
        // Initialize ASCII (0x00 - 0x7E)
        for (int i = 0; i <= 0x7E; i++) {
            ANSEL_TO_UNICODE[i] = (char) i;
            UNICODE_TO_ANSEL.put((char) i, i);
        }

        // Initialize Extended Characters (0xA1 - 0xC8)
        map(0xA1, '\u0141'); // Ł (L slash)
        map(0xA2, '\u00D8'); // Ø (O slash)
        map(0xA3, '\u0110'); // Đ (D with stroke)
        map(0xA4, '\u00DE'); // Þ (Thorn)
        map(0xA5, '\u00C6'); // Æ (AE)
        map(0xA6, '\u0152'); // Œ (OE)
        map(0xA7, '\u02B9'); // ʹ (Modifier letter prime - Soft sign?)
        map(0xA8, '\u00B7'); // · (Middle dot)
        map(0xA9, '\u266D'); // ♭ (Music flat sign)
        map(0xAA, '\u00AE'); // ® (Registered sign)
        map(0xAB, '\u00B1'); // ± (Plus-minus sign)
        map(0xAC, '\u01A0'); // Ơ (O with horn)
        map(0xAD, '\u01AF'); // Ư (U with horn)
        map(0xAE, '\u02BC'); // ʼ (Modifier letter apostrophe)
        
        // Lowercase extended
        map(0xB1, '\u0142'); // ł
        map(0xB2, '\u00F8'); // ø
        map(0xB3, '\u0111'); // đ
        map(0xB4, '\u00FE'); // þ
        map(0xB5, '\u00E6'); // æ
        map(0xB6, '\u0153'); // œ
        map(0xB7, '\u02BA'); // ʺ (Modifier letter double prime)
        map(0xB8, '\u0131'); // ı (Dotless i)
        map(0xB9, '\u00A3'); // £ (Pound sign)
        map(0xBA, '\u00F0'); // ð (Eth)
        map(0xBC, '\u01A1'); // ơ
        map(0xBD, '\u01B0'); // ư
        
        map(0xC0, '\u00B0'); // ° (Degree sign)
        map(0xC1, '\u2113'); // ℓ (Script l)
        map(0xC2, '\u2117'); // ℗ (Sound recording copyright)
        map(0xC3, '\u00A9'); // © (Copyright sign)
        map(0xC4, '\u266F'); // ♯ (Music sharp sign)
        map(0xC5, '\u00BF'); // ¿ (Inverted question mark)
        map(0xC6, '\u00A1'); // ¡ (Inverted exclamation mark)
        map(0xC7, '\u00DF'); // ß (Eszett) (Sometimes mapped purely)
        map(0xC8, '\u20AC'); // € (Euro) - Extended addition in some sets

        // Combining Diacritics (0xE0 - 0xFE)
        // These map to Unicode Combining Diacritical Marks (0x03xx)
        mapDiacritic(0xE0, '\u0309'); // Hook above (Alif?) -> 0309 (Combining Hook Above)
        mapDiacritic(0xE1, '\u0300'); // Grave accent
        mapDiacritic(0xE2, '\u0301'); // Acute accent
        mapDiacritic(0xE3, '\u0302'); // Circumflex accent
        mapDiacritic(0xE4, '\u0303'); // Tilde
        mapDiacritic(0xE5, '\u0304'); // Macron
        mapDiacritic(0xE6, '\u0306'); // Breve
        mapDiacritic(0xE7, '\u0307'); // Dot above
        mapDiacritic(0xE8, '\u0308'); // Umlaut (Diaeresis)
        mapDiacritic(0xE9, '\u030C'); // Caron (Hacek)
        mapDiacritic(0xEA, '\u030A'); // Ring above
        mapDiacritic(0xEB, '\uFE20'); // Ligature left half (Special handling needed typically, mapping to roughly close)
        mapDiacritic(0xEC, '\uFE21'); // Ligature right half
        mapDiacritic(0xED, '\u0315'); // Comma above right
        mapDiacritic(0xEE, '\u030B'); // Double acute accent
        mapDiacritic(0xEF, '\u0310'); // Candrabindu
        mapDiacritic(0xF0, '\u0327'); // Cedilla
        mapDiacritic(0xF1, '\u0328'); // Ogonek
        mapDiacritic(0xF2, '\u0323'); // Dot below
        mapDiacritic(0xF3, '\u0324'); // Diaeresis below
        mapDiacritic(0xF4, '\u0325'); // Ring below
        mapDiacritic(0xF5, '\u0333'); // Double underscore
        mapDiacritic(0xF6, '\u0332'); // Underscore
        mapDiacritic(0xF7, '\u0326'); // Comma below
        mapDiacritic(0xF8, '\u031C'); // Left half ring below
        mapDiacritic(0xF9, '\u032E'); // Breve below
        mapDiacritic(0xFA, '\u0361'); // Double tilde left half -> Generic Double Tilde
        mapDiacritic(0xFB, '\u0360'); // Double tilde right half
        mapDiacritic(0xFE, '\u0313'); // Comma above (High comma)
    }

    private static void map(int ansel, char unicode) {
        ANSEL_TO_UNICODE[ansel] = unicode;
        UNICODE_TO_ANSEL.put(unicode, ansel);
    }
    
    private static void mapDiacritic(int ansel, char unicodeCombining) {
        COMBINING_DIACRITICS.put(ansel, unicodeCombining);
        UNICODE_TO_ANSEL.put(unicodeCombining, ansel);
    }
}
