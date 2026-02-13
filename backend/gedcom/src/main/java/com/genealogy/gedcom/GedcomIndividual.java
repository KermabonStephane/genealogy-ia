package com.genealogy.gedcom;

import java.util.List;

public record GedcomIndividual(
    String xref,
    List<GedcomName> names,
    String sex,
    List<GedcomEvent> events,
    List<String> spouseFamilyLinks, // FAMS
    List<String> childFamilyLinks,  // FAMC
    String changeDate
) {}
