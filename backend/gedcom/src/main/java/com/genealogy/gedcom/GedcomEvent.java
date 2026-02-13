package com.genealogy.gedcom;

import java.util.List;

public record GedcomEvent(
    String type,
    GedcomDate date,
    String place,
    String note
) {}
