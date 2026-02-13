package com.genealogy.gedcom;

public record GedcomSubmitter(
    String xref,
    String name,      // NAME
    String address,   // ADDR (simplified)
    String phone,     // PHON
    String email,     // EMAIL
    String www,       // WWW
    String language,  // LANG
    String changeDate
) {}
