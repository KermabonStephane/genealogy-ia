package com.genealogy.gedcom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class GedcomParser {

    private static final DateTimeFormatter HEADER_DATE_FORMATTER = new DateTimeFormatterBuilder()
            .parseCaseInsensitive()
            .appendPattern("d MMM yyyy")
            .toFormatter(Locale.ENGLISH);

    public GedcomSubmission parseSubmission(Reader reader) throws IOException {
        BufferedReader bufferedReader = (reader instanceof BufferedReader) ? (BufferedReader) reader
                : new BufferedReader(reader);
        String lineStr;
        GedcomLine line;

        String xref = null;
        String submitterXref = null;
        String familyFileName = null;
        String templeCode = null;
        String ancestorsCount = null;
        String descendantsCount = null;
        String ordinanceFlag = null;
        String changeDate = null;

        while ((lineStr = bufferedReader.readLine()) != null) {
            line = GedcomLine.parse(lineStr);
            if (line == null)
                continue;

            if (line.level() == 0) {
                if (xref == null) {
                    if ("SUBN".equals(line.tag())) {
                        xref = line.xref();
                    }
                } else {
                    break;
                }
            }

            if (line.level() == 1) {
                switch (line.tag()) {
                    case "SUBM" -> submitterXref = line.value();
                    case "FAMF" -> familyFileName = line.value();
                    case "TEMP" -> templeCode = line.value();
                    case "ANCE" -> ancestorsCount = line.value();
                    case "DESC" -> descendantsCount = line.value();
                    case "ORDI" -> ordinanceFlag = line.value();
                    case "CHAN" -> {
                    }
                }
            } else if (line.level() == 2) {
                // CHAN.DATE
            }
        }

        return new GedcomSubmission(xref, submitterXref, familyFileName, templeCode, ancestorsCount, descendantsCount,
                ordinanceFlag, changeDate);
    }

    public GedcomSubmitter parseSubmitter(Reader reader) throws IOException {
        BufferedReader bufferedReader = (reader instanceof BufferedReader) ? (BufferedReader) reader
                : new BufferedReader(reader);
        String lineStr;
        GedcomLine line;

        String xref = null;
        String name = null;
        String address = null;
        String phone = null;
        String email = null;
        String www = null;
        String language = null;
        String changeDate = null;

        while ((lineStr = bufferedReader.readLine()) != null) {
            line = GedcomLine.parse(lineStr);
            if (line == null)
                continue;

            if (line.level() == 0) {
                if (xref == null) {
                    if ("SUBM".equals(line.tag())) {
                        xref = line.xref();
                    }
                } else {
                    break;
                }
            }

            if (line.level() == 1) {
                switch (line.tag()) {
                    case "NAME" -> name = line.value();
                    case "ADDR" -> address = line.value();
                    case "PHON" -> phone = line.value();
                    case "EMAIL" -> email = line.value();
                    case "WWW" -> www = line.value();
                    case "LANG" -> language = line.value();
                    case "CHAN" -> {
                    }
                }
            } else if (line.level() == 2) {
                // CHAN.DATE or ADDR continuation
            }
        }

        return new GedcomSubmitter(xref, name, address, phone, email, www, language, changeDate);
    }

    public GedcomSource parseSource(Reader reader) throws IOException {
        BufferedReader bufferedReader = (reader instanceof BufferedReader) ? (BufferedReader) reader
                : new BufferedReader(reader);
        String lineStr;
        GedcomLine line;

        String xref = null;
        String title = null;
        String author = null;
        String publication = null;
        String abbreviation = null;
        String repositoryXref = null;
        String text = null;
        String changeDate = null;

        while ((lineStr = bufferedReader.readLine()) != null) {
            line = GedcomLine.parse(lineStr);
            if (line == null)
                continue;

            if (line.level() == 0) {
                if (xref == null) {
                    if ("SOUR".equals(line.tag())) {
                        xref = line.xref();
                    }
                } else {
                    break;
                }
            }

            if (line.level() == 1) {
                switch (line.tag()) {
                    case "TITL" -> title = line.value();
                    case "AUTH" -> author = line.value();
                    case "PUBL" -> publication = line.value();
                    case "ABBR" -> abbreviation = line.value();
                    case "REPO" -> repositoryXref = line.value();
                    case "TEXT" -> text = line.value(); // Could be multi-line with CONT
                    case "CHAN" -> {
                    }
                }
            } else if (line.level() == 2) {
                // CHAN.DATE or TEXT continuation
            }
        }

        return new GedcomSource(xref, title, author, publication, abbreviation, repositoryXref, text, changeDate);
    }

    public GedcomRepository parseRepository(Reader reader) throws IOException {
        BufferedReader bufferedReader = (reader instanceof BufferedReader) ? (BufferedReader) reader
                : new BufferedReader(reader);
        String lineStr;
        GedcomLine line;

        String xref = null;
        String name = null;
        String address = null;
        String phone = null;
        String email = null;
        String www = null;
        String changeDate = null;

        while ((lineStr = bufferedReader.readLine()) != null) {
            line = GedcomLine.parse(lineStr);
            if (line == null)
                continue;

            if (line.level() == 0) {
                if (xref == null) {
                    if ("REPO".equals(line.tag())) {
                        xref = line.xref();
                    }
                } else {
                    break;
                }
            }

            if (line.level() == 1) {
                switch (line.tag()) {
                    case "NAME" -> name = line.value();
                    case "ADDR" -> address = line.value(); // Simplified - ADDR can be multi-line
                    case "PHON" -> phone = line.value();
                    case "EMAIL" -> email = line.value();
                    case "WWW" -> www = line.value();
                    case "CHAN" -> {
                    }
                }
            } else if (line.level() == 2) {
                // CHAN.DATE or ADDR continuation
            }
        }

        return new GedcomRepository(xref, name, address, phone, email, www, changeDate);
    }

    public GedcomMultimedia parseMultimedia(Reader reader) throws IOException {
        BufferedReader bufferedReader = (reader instanceof BufferedReader) ? (BufferedReader) reader
                : new BufferedReader(reader);
        String lineStr;
        GedcomLine line;

        String xref = null;
        String format = null;
        String title = null;
        String fileMode = null; // Normally stored in FILE tag or sub-record in 5.5.1
        // In 5.5.1, OBJE has FILE sub-record. In 5.5 OBJE has FORM, TITL, FILE.
        // Let's assume a simplified structure where FILE contains the path/mode.

        String changeDate = null;

        while ((lineStr = bufferedReader.readLine()) != null) {
            line = GedcomLine.parse(lineStr);
            if (line == null)
                continue;

            if (line.level() == 0) {
                if (xref == null) {
                    if ("OBJE".equals(line.tag())) {
                        xref = line.xref();
                    }
                } else {
                    break;
                }
            }

            if (line.level() == 1) {
                switch (line.tag()) {
                    case "FORM" -> format = line.value();
                    case "TITL" -> title = line.value();
                    case "FILE" -> fileMode = line.value(); // In 5.5.1 this is likely the filename
                    case "CHAN" -> {
                    }
                }
            } else if (line.level() == 2) {
                // CHAN.DATE
            }
        }

        return new GedcomMultimedia(xref, format, title, fileMode, changeDate);
    }

    public GedcomNote parseNote(Reader reader) throws IOException {
        BufferedReader bufferedReader = (reader instanceof BufferedReader) ? (BufferedReader) reader
                : new BufferedReader(reader);
        String lineStr;
        GedcomLine line;

        String xref = null;
        String value = null;
        String changeDate = null;

        StringBuilder noteBuilder = new StringBuilder();

        while ((lineStr = bufferedReader.readLine()) != null) {
            line = GedcomLine.parse(lineStr);
            if (line == null)
                continue;

            if (line.level() == 0) {
                if (xref == null) {
                    if ("NOTE".equals(line.tag())) {
                        xref = line.xref();
                        if (line.value() != null) {
                            noteBuilder.append(line.value());
                        }
                    }
                } else {
                    break;
                }
            }

            if (line.level() == 1) {
                switch (line.tag()) {
                    case "CONC" -> {
                        if (line.value() != null)
                            noteBuilder.append(line.value());
                    }
                    case "CONT" -> {
                        noteBuilder.append("\n");
                        if (line.value() != null)
                            noteBuilder.append(line.value());
                    }
                    case "CHAN" -> {
                    }
                }
            } else if (line.level() == 2) {
                // CHAN.DATE
            }
        }

        value = noteBuilder.toString();
        if (value.isEmpty())
            value = null;

        return new GedcomNote(xref, value, changeDate);
    }

    public GedcomFamily parseFamily(Reader reader) throws IOException {
        BufferedReader bufferedReader = (reader instanceof BufferedReader) ? (BufferedReader) reader
                : new BufferedReader(reader);
        String lineStr;
        GedcomLine line;

        String xref = null;
        String husbandXref = null;
        String wifeXref = null;
        List<String> childrenXrefs = new ArrayList<>();
        List<GedcomEvent> events = new ArrayList<>();
        String changeDate = null;

        String currentEventType = null;
        String currentEventDate = null;
        String currentEventPlace = null;
        String currentEventNote = null;

        while ((lineStr = bufferedReader.readLine()) != null) {
            line = GedcomLine.parse(lineStr);
            if (line == null)
                continue;

            if (line.level() == 0) {
                if (xref == null) {
                    if ("FAM".equals(line.tag())) {
                        xref = line.xref();
                    }
                } else {
                    break;
                }
            }

            if (line.level() == 1) {
                if (currentEventType != null) {
                    events.add(new GedcomEvent(currentEventType,
                            currentEventDate != null ? new GedcomDate(currentEventDate) : null, currentEventPlace,
                            currentEventNote));
                    currentEventType = null;
                    currentEventDate = null;
                    currentEventPlace = null;
                    currentEventNote = null;
                }

                switch (line.tag()) {
                    case "HUSB" -> husbandXref = line.value();
                    case "WIFE" -> wifeXref = line.value();
                    case "CHIL" -> childrenXrefs.add(line.value());
                    case "MARR", "DIV" -> currentEventType = line.tag();
                    case "CHAN" -> {
                    }
                }
            } else if (line.level() == 2) {
                if (currentEventType != null) {
                    if ("DATE".equals(line.tag()))
                        currentEventDate = line.value();
                    if ("PLAC".equals(line.tag()))
                        currentEventPlace = line.value();
                    if ("NOTE".equals(line.tag()))
                        currentEventNote = line.value();
                }
            }
        }

        if (currentEventType != null) {
            events.add(new GedcomEvent(currentEventType,
                    currentEventDate != null ? new GedcomDate(currentEventDate) : null, currentEventPlace,
                    currentEventNote));
        }

        return new GedcomFamily(xref, husbandXref, wifeXref, childrenXrefs, events, changeDate);
    }

    public GedcomIndividual parseIndividual(Reader reader) throws IOException {
        BufferedReader bufferedReader = (reader instanceof BufferedReader) ? (BufferedReader) reader
                : new BufferedReader(reader);
        String lineStr;
        GedcomLine line;

        String xref = null;
        List<GedcomName> names = new ArrayList<>();
        String sex = null;
        List<GedcomEvent> events = new ArrayList<>();
        List<String> fams = new ArrayList<>();
        List<String> famc = new ArrayList<>();
        String changeDate = null;

        String currentNameValue = null;
        String currentGiven = null;
        String currentSurname = null;
        String currentNickname = null;
        String currentPrefix = null;
        String currentSurnamePrefix = null;
        String currentSuffix = null;

        String currentEventType = null;
        String currentEventDate = null;
        String currentEventPlace = null;

        while ((lineStr = bufferedReader.readLine()) != null) {
            line = GedcomLine.parse(lineStr);
            if (line == null)
                continue;

            if (line.level() == 0) {
                if (xref == null) {
                    if ("INDI".equals(line.tag())) {
                        xref = line.xref();
                    }
                    // If tag is not INDI, we might be skipping? For now, if we call
                    // parseIndividual, assume first record is INDI.
                } else {
                    break;
                }
            }

            if (line.level() == 1) {
                if (currentNameValue != null) {
                    names.add(new GedcomName(currentNameValue, currentGiven, currentSurname, currentNickname,
                            currentPrefix, currentSurnamePrefix, currentSuffix));
                    currentNameValue = null;
                    currentGiven = null;
                    currentSurname = null;
                    currentNickname = null;
                    currentPrefix = null;
                    currentSurnamePrefix = null;
                    currentSuffix = null;
                }
                if (currentEventType != null) {
                    events.add(new GedcomEvent(currentEventType,
                            currentEventDate != null ? new GedcomDate(currentEventDate) : null, currentEventPlace,
                            null));
                    currentEventType = null;
                    currentEventDate = null;
                    currentEventPlace = null;
                }

                switch (line.tag()) {
                    case "NAME" -> currentNameValue = line.value();
                    case "SEX" -> sex = line.value();
                    case "BIRT", "DEAT", "CHR", "BURI" -> currentEventType = line.tag();
                    case "FAMS" -> fams.add(line.value());
                    case "FAMC" -> famc.add(line.value());
                    case "CHAN" -> {
                    }
                }
            } else if (line.level() == 2) {
                if (currentNameValue != null) {
                    switch (line.tag()) {
                        case "GIVN" -> currentGiven = line.value();
                        case "SURN" -> currentSurname = line.value();
                        case "NICK" -> currentNickname = line.value();
                        case "NPFX" -> currentPrefix = line.value();
                        case "SPFX" -> currentSurnamePrefix = line.value();
                        case "NSFX" -> currentSuffix = line.value();
                    }
                }
                if (currentEventType != null) {
                    if ("DATE".equals(line.tag()))
                        currentEventDate = line.value();
                    if ("PLAC".equals(line.tag()))
                        currentEventPlace = line.value();
                }
            }
        }

        if (currentNameValue != null) {
            names.add(new GedcomName(currentNameValue, currentGiven, currentSurname, currentNickname, currentPrefix,
                    currentSurnamePrefix, currentSuffix));
        }
        if (currentEventType != null) {
            events.add(new GedcomEvent(currentEventType,
                    currentEventDate != null ? new GedcomDate(currentEventDate) : null, currentEventPlace, null));
        }

        return new GedcomIndividual(xref, names, sex, events, fams, famc, changeDate);
    }

    public GedcomHeader parseHeader(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String lineStr;

        String sourceSystemId = null;
        String sourceVersion = null;
        String sourceName = null;
        List<String> destinations = new ArrayList<>();
        String charSet = null;
        String gedcomVer = null;
        String gedcomForm = null;
        String fileName = null;
        String submXref = null;
        String dateStr = null;
        String timeStr = null;
        StringBuilder noteBuilder = null;

        GedcomLine line;
        // Simple context tracking: the last Level 1 tag seen
        String lastLevel1Tag = null;

        while ((lineStr = bufferedReader.readLine()) != null) {
            line = GedcomLine.parse(lineStr);
            if (line == null)
                continue;

            if (line.level() == 0) {
                if ("HEAD".equals(line.tag())) {
                    continue;
                } else {
                    // Non-HEAD level 0 record means header is done
                    break;
                }
            }

            if (line.level() == 1) {
                lastLevel1Tag = line.tag();
                switch (line.tag()) {
                    case "SOUR" -> sourceSystemId = line.value();
                    case "DEST" -> destinations.add(line.value());
                    case "FILE" -> fileName = line.value();
                    case "CHAR" -> charSet = line.value();
                    case "SUBM" -> submXref = line.value();
                    case "DATE" -> dateStr = line.value();
                    case "NOTE" -> {
                        noteBuilder = new StringBuilder();
                        if (line.value() != null)
                            noteBuilder.append(line.value());
                    }
                }
            } else if (line.level() == 2) {
                if ("NOTE".equals(lastLevel1Tag) && noteBuilder != null) {
                    if ("CONT".equals(line.tag())) {
                        noteBuilder.append(" ");
                        if (line.value() != null)
                            noteBuilder.append(line.value());
                    } else if ("CONC".equals(line.tag())) {
                        if (line.value() != null)
                            noteBuilder.append(line.value());
                    }
                } else if ("GEDC".equals(lastLevel1Tag)) {
                    if ("VERS".equals(line.tag()))
                        gedcomVer = line.value();
                    if ("FORM".equals(line.tag()))
                        gedcomForm = line.value();
                } else if ("SOUR".equals(lastLevel1Tag)) {
                    if ("VERS".equals(line.tag()))
                        sourceVersion = line.value();
                    if ("NAME".equals(line.tag()))
                        sourceName = line.value();
                } else if ("DATE".equals(lastLevel1Tag)) {
                    if ("TIME".equals(line.tag()))
                        timeStr = line.value();
                }
            }
        }

        GedcomHeader.GedcomVersion gv = new GedcomHeader.GedcomVersion(gedcomVer, gedcomForm);
        GedcomHeader.Source src = new GedcomHeader.Source(sourceSystemId, sourceVersion, sourceName, null, null);

        GedcomHeader.TransmissionDate transmissionDate = null;
        if (dateStr != null) {
            LocalDate date = null;
            try {
                date = LocalDate.parse(dateStr, HEADER_DATE_FORMATTER);
            } catch (Exception e) {
                // Basic fallback or error handling could be added here
            }

            LocalTime time = null;
            if (timeStr != null) {
                try {
                    time = LocalTime.parse(timeStr);
                } catch (Exception e) {
                    // Ignore or log
                }
            }
            transmissionDate = new GedcomHeader.TransmissionDate(date, time);
        }

        return new GedcomHeader(
                src,
                destinations,
                transmissionDate,
                submXref,
                null,
                fileName,
                null,
                gv,
                charSet,
                null,
                null,
                noteBuilder != null ? noteBuilder.toString() : null);
    }
}
