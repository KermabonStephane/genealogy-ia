package com.genealogy.gedcom;

import java.io.IOException;
import java.io.Writer;

public class GedcomWriter {

    public void writeHeader(GedcomHeader header, Writer writer) throws IOException {
        writeLine(writer, new GedcomLine(0, null, "HEAD", null));
        
        if (header.source() != null) {
            writeLine(writer, new GedcomLine(1, null, "SOUR", header.source().systemId()));
            if (header.source().version() != null) {
                writeLine(writer, new GedcomLine(2, null, "VERS", header.source().version()));
            }
            if (header.source().name() != null) {
                writeLine(writer, new GedcomLine(2, null, "NAME", header.source().name()));
            }
        }
        
        if (header.destination() != null) {
            writeLine(writer, new GedcomLine(1, null, "DEST", header.destination()));
        }
        
        if (header.date() != null) {
            writeLine(writer, new GedcomLine(1, null, "DATE", header.date().date()));
            if (header.date().time() != null) {
                writeLine(writer, new GedcomLine(2, null, "TIME", header.date().time().toString()));
            }
        }

        if (header.submitterXref() != null) {
            writeLine(writer, new GedcomLine(1, null, "SUBM", header.submitterXref()));
        }
        
        if (header.submissionXref() != null) {
             writeLine(writer, new GedcomLine(1, null, "SUBN", header.submissionXref()));
        }

        if (header.fileName() != null) {
            writeLine(writer, new GedcomLine(1, null, "FILE", header.fileName()));
        }
        
        if (header.copyright() != null) {
            writeLine(writer, new GedcomLine(1, null, "COPR", header.copyright()));
        }
        
        if (header.gedcomVersion() != null) {
            writeLine(writer, new GedcomLine(1, null, "GEDC", null));
            writeLine(writer, new GedcomLine(2, null, "VERS", header.gedcomVersion().version()));
            writeLine(writer, new GedcomLine(2, null, "FORM", header.gedcomVersion().form()));
        }
        
        if (header.characterSet() != null) {
            writeLine(writer, new GedcomLine(1, null, "CHAR", header.characterSet()));
        }
        
        if (header.language() != null) {
             writeLine(writer, new GedcomLine(1, null, "LANG", header.language()));
        }
        
        if (header.placeStructure() != null) {
             writeLine(writer, new GedcomLine(1, null, "PLAC", null));
             writeLine(writer, new GedcomLine(2, null, "FORM", header.placeStructure()));
        }
        
        if (header.note() != null) {
             writeLine(writer, new GedcomLine(1, null, "NOTE", header.note()));
        }
    }

    public void writeIndividual(GedcomIndividual ind, Writer writer) throws IOException {
        writeLine(writer, new GedcomLine(0, ind.xref(), "INDI", null));
        
        for (GedcomName name : ind.names()) {
            writeLine(writer, new GedcomLine(1, null, "NAME", name.value()));
            if (name.givenName() != null) writeLine(writer, new GedcomLine(2, null, "GIVN", name.givenName()));
            if (name.surname() != null) writeLine(writer, new GedcomLine(2, null, "SURN", name.surname()));
            if (name.prefix() != null) writeLine(writer, new GedcomLine(2, null, "NPFX", name.prefix()));
            if (name.suffix() != null) writeLine(writer, new GedcomLine(2, null, "NSFX", name.suffix()));
        }
        
        if (ind.sex() != null) {
            writeLine(writer, new GedcomLine(1, null, "SEX", ind.sex()));
        }
        
        for (GedcomEvent event : ind.events()) {
            writeLine(writer, new GedcomLine(1, null, event.type(), null));
            if (event.date() != null) writeLine(writer, new GedcomLine(2, null, "DATE", event.date().date()));
            if (event.place() != null) writeLine(writer, new GedcomLine(2, null, "PLAC", event.place()));
            if (event.note() != null) writeLine(writer, new GedcomLine(2, null, "NOTE", event.note()));
        }
        
        for (String fams : ind.spouseFamilyLinks()) {
            writeLine(writer, new GedcomLine(1, null, "FAMS", fams));
        }
        
        for (String famc : ind.childFamilyLinks()) {
            writeLine(writer, new GedcomLine(1, null, "FAMC", famc));
        }
        
        if (ind.changeDate() != null) {
            writeLine(writer, new GedcomLine(1, null, "CHAN", null));
            writeLine(writer, new GedcomLine(2, null, "DATE", ind.changeDate()));
        }
    }
    
    private void writeLine(Writer writer, GedcomLine line) throws IOException {
        writer.write(line.toString());
        writer.write("\n");
    }
}
