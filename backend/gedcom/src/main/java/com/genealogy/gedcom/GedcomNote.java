package com.genealogy.gedcom;

import java.util.List;

public record GedcomNote(
    String xref,
    String value,
    String changeDate
) {}
