# ANSEL Module Documentation

## 🏗️ Architecture
The `ansel` module is a standalone library within the backend monolith. It provides a Java SPI (Service Provider Interface) implementation for `java.nio.charset.Charset`.

### Component Diagram
```
[Application] --> [Java NIO Charset API]
                          ^
                          | SPI
                  [AnselCharsetProvider]
                          |
                  [AnselCharset] --> [AnselMapping]
```

## 🛠️ Implementation Details

### Charset Provider
The module registers `com.genealogy.ansel.AnselCharsetProvider` in `META-INF/services/java.nio.charset.spi.CharsetProvider`. This allows any part of the application (or external libraries on the classpath) to use:
```java
Charset.forName("ANSEL");
```

### Decoding (ANSEL to Unicode)
-   Reads bytes from the input buffer.
-   If a **Combining Diacritic** (0xE0-0xFE) is encountered:
    -   It is buffered.
    -   The next byte (Base Character) is read.
    -   The sequence is output in Unicode Normalization Form D (NFD): `[Base] [Diacritic]`.
    -   *Note: Standard ANSEL stores diacritics before the base character.*

### Encoding (Unicode to ANSEL)
-   Input text is normalized to **NFD** to separate diacritics from base characters.
-   The encoder then:
    1.  Writes the Diacritic ANSEL byte first.
    2.  Writes the Base Character ANSEL byte second.
-   Characters without direct ANSEL equivalents are replaced with '?' or `\uFFFD`.

## 🧪 Testing
Unit tests are written in **Spock Framework** (Groovy).
-   **Location**: `src/test/groovy/com/genealogy/ansel/AnselCharsetSpec.groovy`
-   **Coverage**: ASCII, Special Chars, Combining Diacritics.

## 📚 References
-   NISO Z39.47
-   MARC-8 ANSEL Character Set
-   GEDCOM 5.5 Specification
