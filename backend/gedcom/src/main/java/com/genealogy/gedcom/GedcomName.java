package com.genealogy.gedcom;

public record GedcomName(
    String value,
    String givenName,
    String surname,
    String prefix,
    String suffix
) {}
