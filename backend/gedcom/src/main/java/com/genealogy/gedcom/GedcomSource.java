package com.genealogy.gedcom;

public record GedcomSource(
    String xref,
    String title,      // TITL
    String author,     // AUTH
    String publication, // PUBL
    String abbreviation, // ABBR
    String repositoryXref, // REPO (link to repository)
    String text,       // TEXT (source text)
    String changeDate
) {}
