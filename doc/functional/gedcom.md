# GEDCOM 5.5.1 Support

## 🎯 Purpose
To read and write standard GEDCOM 5.5 and 5.5.1 files, which are the industry standard for genealogical data exchange.

## 📋 Features
-   **Header Parsing**: Full support for reading the `HEAD` record.
-   **Header Writing**: Ability to generate a compliant `HEAD` record.
-   **Individual Parsing**: Support for reading `INDI` records including names, sex, events, and family links.
-   **Individual Writing**: Ability to generate `INDI` records.
-   **Family Parsing**: Support for reading `FAM` records including husband, wife, children, and events.
-   **Family Writing**: Ability to generate `FAM` records.
-   **Note Parsing**: Support for reading `NOTE` records including continuation lines (`CONT`/`CONC`).
-   **Note Writing**: Ability to generate `NOTE` records.
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

### Family
```java
Reader reader = ...; // Reader positioned at family record
GedcomFamily fam = parser.parseFamily(reader);
```

### Note
```java
Reader reader = ...; // Reader positioned at note record
GedcomNote note = parser.parseNote(reader);
```
