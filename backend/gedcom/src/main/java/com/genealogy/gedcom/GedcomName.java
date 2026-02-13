package com.genealogy.gedcom;

public record GedcomName(
    String value,
    String givenName,
    String surname,
    String nickname,
    String prefix,
    String surnamePrefix,
    String suffix
) {}
