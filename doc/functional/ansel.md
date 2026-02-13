# ANSEL Character Set Support

## 🎯 Purpose
To support import and export of genealogical data (GEDCOM, MARC records) that uses the ANSEL character set (NISO Z39.47 / MARC-8).

## 📋 Features
-   **Full ASCII Compatibility**: Standard 7-bit ASCII is preserved.
-   **Extended Latin Support**: Handling of characters like Ł, Ø, Æ.
-   **Combining Diacritics**: Correct processing of predictive diacritics (e.g., Acute 'e' -> 'é').

## 🔍 Usage in Genealogy
ANSEL is the standard encoding for GEDCOM 5.5. Modern systems prefer UTF-8/Unicode. This module acts as a bridge, allowing the application to ingest legacy files and convert them to its internal Unicode format.

## ⚠️ Limitations
-   This implementation covers the standard genealogy subset (GEDCOM/MARC-8).
-   Rare control characters in the 0x88-0x9F range may not be fully mapped.
