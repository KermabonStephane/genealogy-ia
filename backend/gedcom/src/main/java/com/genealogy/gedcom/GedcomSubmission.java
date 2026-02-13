package com.genealogy.gedcom;

public record GedcomSubmission(
    String xref,
    String submitterXref, // SUBM (link to submitter)
    String familyFileName, // FAMF
    String templeCode,     // TEMP
    String ancestorsCount, // ANCE
    String descendantsCount, // DESC
    String ordinanceFlag,  // ORDI
    String changeDate
) {}
