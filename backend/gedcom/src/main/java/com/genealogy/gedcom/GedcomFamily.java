package com.genealogy.gedcom;

import java.util.List;

public record GedcomFamily(
    String xref,
    String husbandXref, // HUSB
    String wifeXref,    // WIFE
    List<String> childrenXrefs, // CHIL
    List<GedcomEvent> events,   // MARR, DIV, etc.
    String changeDate
) {}
