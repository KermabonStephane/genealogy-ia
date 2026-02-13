# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

### Added
-   **#2**: Created `ansel` Maven module for reading/writing ANSEL charset files.
    -   Implemented `java.nio.charset.Charset` provider for "ANSEL".
    -   Added mapping for standard extended Latin characters and combining diacritics.
    -   Added Spock unit tests for encoding/decoding.
    -   Documentation added in `doc/functional/ansel.md` and `doc/technical/ansel-module.md`.

### Added (GEDCOM Header)
-   **#4**: Created `gedcom` Maven module.
    -   Implemented `GedcomHeader` record and nested records.
    -   Implemented `GedcomParser` (Header only).
    -   Implemented `GedcomWriter` (Header only).
    -   Added Spock unit tests.

### Added (GEDCOM Individual)
-   **#5**: Support for parsing and writing Individual (`INDI`) records.
    -   Implemented `GedcomIndividual`, `GedcomName`, `GedcomEvent`, `GedcomDate` records.
    -   Extended `GedcomParser` with `parseIndividual`.
    -   Extended `GedcomWriter` with `writeIndividual`.
    -   Added unit and integration tests.

### Added (GEDCOM Family)
-   **#6**: Support for parsing and writing Family (`FAM`) records.
    -   Implemented `GedcomFamily` record.
    -   Extended `GedcomParser` with `parseFamily`.
    -   Extended `GedcomWriter` with `writeFamily`.
    -   Added unit and integration tests.

### Added (GEDCOM Note)
-   **#7**: Support for parsing and writing Note (`NOTE`) records.
    -   Implemented `GedcomNote` record.
    -   Extended `GedcomParser` with `parseNote` (supports CONT/CONC).
    -   Extended `GedcomWriter` with `writeNote`.
    -   Added unit and integration tests.

### Added (GEDCOM Multimedia)
-   **#8**: Support for parsing and writing Multimedia (`OBJE`) records.
    -   Implemented `GedcomMultimedia` record.
    -   Extended `GedcomParser` with `parseMultimedia`.
    -   Extended `GedcomWriter` with `writeMultimedia`.
    -   Added unit and integration tests.

### Added (GEDCOM Repository)
-   **#9**: Support for parsing and writing Repository (`REPO`) records.
    -   Implemented `GedcomRepository` record.
    -   Extended `GedcomParser` with `parseRepository`.
    -   Extended `GedcomWriter` with `writeRepository`.
    -   Added unit and integration tests.

### Added (GEDCOM Source)
-   **#10**: Support for parsing and writing Source (`SOUR`) records.
    -   Implemented `GedcomSource` record.
    -   Extended `GedcomParser` with `parseSource`.
    -   Extended `GedcomWriter` with `writeSource`.
    -   Added unit and integration tests.

