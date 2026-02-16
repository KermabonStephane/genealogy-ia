package com.genealogy.gedcom;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

/**
 * Root record for the GEDCOM Header structure.
 */
public record GedcomHeader(
        Source source,
        List<String> destinations,
        TransmissionDate transmissionDate,
        String submitterXref,
        String submissionXref,
        String fileName,
        String copyright,
        GedcomVersion gedcomVersion,
        String characterSet,
        String language,
        String placeStructure,
        String note) {

    public record Source(
            String systemId,
            String version,
            String name,
            Corporate business,
            Data data) {
    }

    public record Corporate(
            String name,
            Address address) {
    }

    public record Address(
            String fullAddress, // Simplified for now (CONT/CONC lines)
            String city,
            String state,
            String postalCode,
            String country,
            String phone,
            String email,
            String fax,
            String www) {
    }

    public record Data(
            String name,
            String copyright) {
    }

    public record TransmissionDate(
            LocalDate date,
            LocalTime time) {
    }

    public record GedcomVersion(
            String version,
            String form) {
    }

    // Builder pattern or mutable DTO might be easier for parsing,
    // but we stick to Record as requested. Parsing logic will need to accumulate
    // state.
}
