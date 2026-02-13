package com.genealogy.ansel;

import java.nio.charset.Charset;
import java.nio.charset.spi.CharsetProvider;
import java.util.Collections;
import java.util.Iterator;

public class AnselCharsetProvider extends CharsetProvider {

    private static final Charset ANSEL = new AnselCharset();

    @Override
    public Iterator<Charset> charsets() {
        return Collections.singleton(ANSEL).iterator();
    }

    @Override
    public Charset charsetForName(String charsetName) {
        if ("ANSEL".equalsIgnoreCase(charsetName) || 
            "MARC-8-ANSEL".equalsIgnoreCase(charsetName) || 
            "Z39.47".equalsIgnoreCase(charsetName)) {
            return ANSEL;
        }
        return null;
    }
}
