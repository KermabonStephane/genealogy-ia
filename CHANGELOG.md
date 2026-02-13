# Changelog

All notable changes to this project will be documented in this file.

## [Unreleased]

### Added
-   **#2**: Created `ansel` Maven module for reading/writing ANSEL charset files.
    -   Implemented `java.nio.charset.Charset` provider for "ANSEL".
    -   Added mapping for standard extended Latin characters and combining diacritics.
    -   Added Spock unit tests for encoding/decoding.
    -   Documentation added in `doc/functional/ansel.md` and `doc/technical/ansel-module.md`.

### Added (GEDCOM)
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

