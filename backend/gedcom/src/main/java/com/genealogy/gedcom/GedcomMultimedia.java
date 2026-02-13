package com.genealogy.gedcom;

import java.util.List;

public record GedcomMultimedia(
    String xref,
    String format,    // FORM
    String title,     // TITL
    String fileMode,  // FILE
    String changeDate
) {}
