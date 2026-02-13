package com.genealogy.ansel

import java.nio.charset.Charset
import spock.lang.Specification
import spock.lang.Unroll
import java.text.Normalizer

class AnselCharsetSpec extends Specification {

    def "Should handle basic ASCII characters"() {
        given:
        def charset = Charset.forName("ANSEL")
        def input = "Hello World"

        when:
        def encoded = input.getBytes(charset)
        def decoded = new String(encoded, charset)

        then:
        decoded == input
        encoded.length == input.length()
    }

    @Unroll
    def "Should handle special character #name (#unicodeChar)"(String name, String unicodeChar, int anselCode) {
        given:
        def charset = Charset.forName("ANSEL")

        when:
        // Encoding
        byte[] bytes = unicodeChar.getBytes(charset)
        // Decoding
        String result = new String([(byte)anselCode] as byte[], charset)

        then:
        bytes.length == 1
        (bytes[0] & 0xFF) == anselCode
        result == unicodeChar

        where:
        name     | unicodeChar | anselCode
        "L slash"| "\u0141"    | 0xA1
        "O slash"| "\u00D8"    | 0xA2
        "Eth"    | "\u00F0"    | 0xBA
    }

    @Unroll
    def "Should handle combining diacritic #name"() {
        given:
        def charset = Charset.forName("ANSEL")
        // Unicode: 'e' + acute (NFD: e + \u0301) -> NFC: é (\u00E9)
        // ANSEL: Acute (0xE2) + 'e' (0x65)
        def inputChar = 'é' 
        def expectedAnsel = [(byte)0xE2, (byte)0x65] as byte[]

        when:
        // Encode (Java String is likely NFC 'é')
        byte[] encoded = "é".getBytes(charset)
        
        // Decode
        String decoded = new String(expectedAnsel, charset)

        then:
        encoded == expectedAnsel
        encoded == expectedAnsel
        Normalizer.normalize(decoded, Normalizer.Form.NFC) == "é" // Normalize to NFC for comparison
        
        where:
        name = "acute e"
    }

    def "Should decode complex sequence"() {
        given:
        def charset = Charset.forName("ANSEL")
        // ANSEL Bytes: [0xE2] [0x65] [0x6E] (Acute+e, n) -> én
        byte[] anselBytes = [(byte)0xE2, (byte)0x65, (byte)0x6E] as byte[]

        when:
        String result = new String(anselBytes, charset)

        then:
        Normalizer.normalize(result, Normalizer.Form.NFC) == "én"
    }
}
