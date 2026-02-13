package com.genealogy.gedcom;

public record GedcomRepository(
    String xref,
    String name,      // NAME
    String address,   // ADDR (simplified - could be multi-line)
    String phone,     // PHON
    String email,     // EMAIL
    String www,       // WWW
    String changeDate
) {}
