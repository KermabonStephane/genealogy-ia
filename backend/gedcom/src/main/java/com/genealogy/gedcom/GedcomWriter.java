package com.genealogy.gedcom;

import java.io.IOException;
import java.io.Writer;

public class GedcomWriter {

    public void writeSource(GedcomSource source, Writer writer) throws IOException {
        writeLine(writer, new GedcomLine(0, source.xref(), "SOUR", null));
        
        if (source.title() != null) writeLine(writer, new GedcomLine(1, null, "TITL", source.title()));
        if (source.author() != null) writeLine(writer, new GedcomLine(1, null, "AUTH", source.author()));
        if (source.publication() != null) writeLine(writer, new GedcomLine(1, null, "PUBL", source.publication()));
        if (source.abbreviation() != null) writeLine(writer, new GedcomLine(1, null, "ABBR", source.abbreviation()));
        if (source.repositoryXref() != null) writeLine(writer, new GedcomLine(1, null, "REPO", source.repositoryXref()));
        if (source.text() != null) writeLine(writer, new GedcomLine(1, null, "TEXT", source.text()));

        if (source.changeDate() != null) {
             writeLine(writer, new GedcomLine(1, null, "CHAN", null));
             writeLine(writer, new GedcomLine(2, null, "DATE", source.changeDate()));
        }
    }

    public void writeRepository(GedcomRepository repo, Writer writer) throws IOException {
        writeLine(writer, new GedcomLine(0, repo.xref(), "REPO", null));
        
        if (repo.name() != null) writeLine(writer, new GedcomLine(1, null, "NAME", repo.name()));
        if (repo.address() != null) writeLine(writer, new GedcomLine(1, null, "ADDR", repo.address()));
        if (repo.phone() != null) writeLine(writer, new GedcomLine(1, null, "PHON", repo.phone()));
        if (repo.email() != null) writeLine(writer, new GedcomLine(1, null, "EMAIL", repo.email()));
        if (repo.www() != null) writeLine(writer, new GedcomLine(1, null, "WWW", repo.www()));

        if (repo.changeDate() != null) {
             writeLine(writer, new GedcomLine(1, null, "CHAN", null));
             writeLine(writer, new GedcomLine(2, null, "DATE", repo.changeDate()));
        }
    }

    public void writeMultimedia(GedcomMultimedia media, Writer writer) throws IOException {
        writeLine(writer, new GedcomLine(0, media.xref(), "OBJE", null));
        
        if (media.format() != null) writeLine(writer, new GedcomLine(1, null, "FORM", media.format()));
        if (media.title() != null) writeLine(writer, new GedcomLine(1, null, "TITL", media.title()));
        if (media.fileMode() != null) writeLine(writer, new GedcomLine(1, null, "FILE", media.fileMode()));

        if (media.changeDate() != null) {
             writeLine(writer, new GedcomLine(1, null, "CHAN", null));
             writeLine(writer, new GedcomLine(2, null, "DATE", media.changeDate()));
        }
    }

    public void writeNote(GedcomNote note, Writer writer) throws IOException {
        String firstLine = null;
        String remaining = null;
        
        if (note.value() != null) {
             // Simple splitting for CONC/CONT not fully implemented yet, just dumping first line?
             // Or assuming value doesn't contain newlines implies simple value.
             // If value contains newlines, we need to handle CONT.
             String[] lines = note.value().split("\n", -1); // -1 to keep empty lines?
             firstLine = lines.length > 0 ? lines[0] : null;
             // Logic for remaining lines...
             
             writeLine(writer, new GedcomLine(0, note.xref(), "NOTE", firstLine));
             
             for (int i = 1; i < lines.length; i++) {
                 writeLine(writer, new GedcomLine(1, null, "CONT", lines[i]));
             }
        } else {
             writeLine(writer, new GedcomLine(0, note.xref(), "NOTE", null));
        }

        if (note.changeDate() != null) {
             writeLine(writer, new GedcomLine(1, null, "CHAN", null));
             writeLine(writer, new GedcomLine(2, null, "DATE", note.changeDate()));
        }
    }

    public void writeFamily(GedcomFamily fam, Writer writer) throws IOException {
        writeLine(writer, new GedcomLine(0, fam.xref(), "FAM", null));
        
        if (fam.husbandXref() != null) writeLine(writer, new GedcomLine(1, null, "HUSB", fam.husbandXref()));
        if (fam.wifeXref() != null) writeLine(writer, new GedcomLine(1, null, "WIFE", fam.wifeXref()));
        
        for (String chil : fam.childrenXrefs()) {
            writeLine(writer, new GedcomLine(1, null, "CHIL", chil));
        }
        
        for (GedcomEvent event : fam.events()) {
            writeLine(writer, new GedcomLine(1, null, event.type(), null));
            if (event.date() != null) writeLine(writer, new GedcomLine(2, null, "DATE", event.date().date()));
            if (event.place() != null) writeLine(writer, new GedcomLine(2, null, "PLAC", event.place()));
            if (event.note() != null) writeLine(writer, new GedcomLine(2, null, "NOTE", event.note()));
        }
        
        if (fam.changeDate() != null) {
             writeLine(writer, new GedcomLine(1, null, "CHAN", null));
             writeLine(writer, new GedcomLine(2, null, "DATE", fam.changeDate()));
        }
    }

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
