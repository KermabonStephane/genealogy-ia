# GEDCOM 5.5.1 Support

## 🎯 Purpose
To read and write standard GEDCOM 5.5 and 5.5.1 files, which are the industry standard for genealogical data exchange.

## 📋 Features
-   **Header Parsing**: Full support for reading the `HEAD` record.
-   **Header Writing**: Ability to generate a compliant `HEAD` record.
-   **Individual Parsing**: Support for reading `INDI` records including names, sex, events, and family links.
-   **Individual Writing**: Ability to generate `INDI` records.
-   **Charset Support**: Integrates with the `ansel` module for character set handling.

## 🔍 Usage
The module provides a `GedcomParser` and `GedcomWriter`.

### Header
```java
GedcomParser parser = new GedcomParser();
GedcomHeader header = parser.parseHeader(new FileReader("tree.ged"));
```

### Individual
```java
Reader reader = ...; // Reader positioned at individual record
GedcomIndividual ind = parser.parseIndividual(reader);
```
