# GEDCOM Module Documentation

## 🏗️ Architecture
The `gedcom` module handles the logic of parsing and generating GEDCOM files. It depends on the `ansel` module for legacy character encoding support.

### Data Model
The core data structure is defined using Java Records for immutability and conciseness.
-   `GedcomHeader`: Root record for the header.
-   `GedcomIndividual`: Record for Individual (`INDI`) data.
-   `GedcomFamily`: Record for Family (`FAM`) data.
-   `GedcomNote`: Record for Note (`NOTE`) data.
-   `GedcomMultimedia`: Record for Multimedia (`OBJE`) data.
-   `GedcomRepository`: Record for Repository (`REPO`) data.
-   `GedcomSource`: Record for Source (`SOUR`) data.
-   `GedcomSubmitter`: Record for Submitter (`SUBM`) data.
-   `GedcomLine`: Represents a raw line (Level, Tag, Value).
-   Nested records: `GedcomName`, `GedcomEvent`, `GedcomDate`.

### Parsing Strategy
-   **Line-by-Line**: The parser reads the input stream line by line.
-   **State Tracking**: Basic context implementation tracks the current parent tag (Level 1) to correctly assign Level 2 children (e.g., `SOUR.VERS` vs `GEDC.VERS`).

### Writing Strategy
-   The `GedcomWriter` serializes the `GedcomHeader` record back into the valid line-based format.

## 🧪 Testing
Unit tests use **Spock Framework**.
-   `GedcomParserSpec`: Tests parsing of valid and partial headers.
-   `GedcomWriterSpec`: Tests generation of headers.
-   `GedcomIndividualSpec`: Tests parsing of individual records.
-   `GedcomWriterIndividualSpec`: Tests generation of individual records.
-   `GedcomFamilySpec`: Tests parsing of family records.
-   `GedcomWriterFamilySpec`: Tests generation of family records.
-   `GedcomNoteSpec`: Tests parsing of note records.
-   `GedcomWriterNoteSpec`: Tests generation of note records.
-   `GedcomMultimediaSpec`: Tests parsing of multimedia records.
-   `GedcomWriterMultimediaSpec`: Tests generation of multimedia records.
-   `GedcomRepositorySpec`: Tests parsing of repository records.
-   `GedcomWriterRepositorySpec`: Tests generation of repository records.
-   `GedcomSourceSpec`: Tests parsing of source records.
-   `GedcomWriterSourceSpec`: Tests generation of source records.
-   `GedcomSubmitterSpec`: Tests parsing of submitter records.
-   `GedcomWriterSubmitterSpec`: Tests generation of submitter records.
-   `GedcomIntegrationSpec`: Integration tests for Header.
-   `GedcomIndividualIntegrationSpec`: Integration tests for Individual.
-   `GedcomFamilyIntegrationSpec`: Integration tests for Family.
-   `GedcomNoteIntegrationSpec`: Integration tests for Note.
-   `GedcomMultimediaIntegrationSpec`: Integration tests for Multimedia.
-   `GedcomRepositoryIntegrationSpec`: Integration tests for Repository.
-   `GedcomSourceIntegrationSpec`: Integration tests for Source.
-   `GedcomSubmitterIntegrationSpec`: Integration tests for Submitter.
