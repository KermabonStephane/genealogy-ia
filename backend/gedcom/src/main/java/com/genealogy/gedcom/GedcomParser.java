package com.genealogy.gedcom;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public class GedcomParser {


    public GedcomIndividual parseIndividual(Reader reader) throws IOException {
        BufferedReader bufferedReader = (reader instanceof BufferedReader) ? (BufferedReader) reader : new BufferedReader(reader);
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

        String currentEventType = null;
        String currentEventDate = null;
        String currentEventPlace = null;
        
        while ((lineStr = bufferedReader.readLine()) != null) {
            line = GedcomLine.parse(lineStr);
            if (line == null) continue;

            if (line.level() == 0) {
                if (xref == null) {
                   if ("INDI".equals(line.tag())) {
                        xref = line.xref();
                   }
                   // If tag is not INDI, we might be skipping? For now, if we call parseIndividual, assume first record is INDI.
                } else {
                    break;
                }
            }

            if (line.level() == 1) {
                if (currentNameValue != null) {
                    names.add(new GedcomName(currentNameValue, currentGiven, currentSurname, null, null));
                    currentNameValue = null; currentGiven = null; currentSurname = null;
                }
                if (currentEventType != null) {
                     events.add(new GedcomEvent(currentEventType, currentEventDate != null ? new GedcomDate(currentEventDate) : null, currentEventPlace, null));
                     currentEventType = null; currentEventDate = null; currentEventPlace = null;
                }

                switch (line.tag()) {
                    case "NAME" -> currentNameValue = line.value();
                    case "SEX" -> sex = line.value();
                    case "BIRT", "DEAT", "CHR", "BURI" -> currentEventType = line.tag();
                    case "FAMS" -> fams.add(line.value());
                    case "FAMC" -> famc.add(line.value());
                    case "CHAN" -> {} 
                }
            } else if (line.level() == 2) {
                if (currentNameValue != null) {
                    if ("SURN".equals(line.tag())) currentSurname = line.value();
                    if ("GIVN".equals(line.tag())) currentGiven = line.value();
                }
                if (currentEventType != null) {
                    if ("DATE".equals(line.tag())) currentEventDate = line.value();
                    if ("PLAC".equals(line.tag())) currentEventPlace = line.value();
                }
            }
        }
        
        if (currentNameValue != null) {
             names.add(new GedcomName(currentNameValue, currentGiven, currentSurname, null, null));
        }
        if (currentEventType != null) {
             events.add(new GedcomEvent(currentEventType, currentEventDate != null ? new GedcomDate(currentEventDate) : null, currentEventPlace, null));
        }

        return new GedcomIndividual(xref, names, sex, events, fams, famc, changeDate);
    }

    public GedcomHeader parseHeader(Reader reader) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(reader);
        String lineStr;

        String sourceSystemId = null;
        String sourceVersion = null;
        String sourceName = null;
        String dest = null;
        String charSet = null;
        String gedcomVer = null;
        String gedcomForm = null;
        String fileName = null;
        String submXref = null;
        String dateStr = null;
        String timeStr = null;

        GedcomLine line;
        // Simple context tracking: the last Level 1 tag seen
        String lastLevel1Tag = null;
        String lastLevel2Tag = null;

        while ((lineStr = bufferedReader.readLine()) != null) {
            line = GedcomLine.parse(lineStr);
            if (line == null) continue;

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
                    case "DEST" -> dest = line.value();
                    case "FILE" -> fileName = line.value();
                    case "CHAR" -> charSet = line.value();
                    case "SUBM" -> submXref = line.value();
                    case "DATE" -> dateStr = line.value();
                }
            } else if (line.level() == 2) {
                lastLevel2Tag = line.tag();
                if ("GEDC".equals(lastLevel1Tag)) {
                    if ("VERS".equals(line.tag())) gedcomVer = line.value();
                    if ("FORM".equals(line.tag())) gedcomForm = line.value();
                } else if ("SOUR".equals(lastLevel1Tag)) {
                    if ("VERS".equals(line.tag())) sourceVersion = line.value();
                    if ("NAME".equals(line.tag())) sourceName = line.value();
                } else if ("DATE".equals(lastLevel1Tag)) {
                    if ("TIME".equals(line.tag())) timeStr = line.value();
                }
            }
        }

        GedcomHeader.GedcomVersion gv = new GedcomHeader.GedcomVersion(gedcomVer, gedcomForm);
        GedcomHeader.Source src = new GedcomHeader.Source(sourceSystemId, sourceVersion, sourceName, null, null);
        
        GedcomHeader.TransmissionDate transmissionDate = null;
        if (dateStr != null) {
            // Basic time parsing if needed, but for now passing null or implementing simple parse
            java.time.LocalTime time = null;
            if (timeStr != null) {
                try {
                     time = java.time.LocalTime.parse(timeStr);
                } catch (Exception e) {
                    // Ignore or log
                }
            }
            transmissionDate = new GedcomHeader.TransmissionDate(dateStr, time);
        }

        return new GedcomHeader(
            src,
            dest,
            transmissionDate, 
            submXref,
            null,
            fileName,
            null,
            gv,
            charSet,
            null,
            null,
            null
        );
    }
}
