package com.genealogy.gedcom

import spock.lang.Specification
import java.io.BufferedReader
import java.io.InputStreamReader

/**
 * Integration test that reads the real kermabon.ged file
 * and validates the count of each record type.
 */
class GedcomFileReadSpec extends Specification {

    def "should read kermabon.ged file and count all record types"() {
        given: "The kermabon.ged file from test resources"
        def inputStream = getClass().getResourceAsStream("/kermabon.ged")
        assert inputStream != null : "kermabon.ged file not found in test resources"
        
        def reader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"))
        
        when: "Counting all level-0 records by type"
        def counts = [:]
        String line
        while ((line = reader.readLine()) != null) {
            if (line.startsWith("0 ")) {
                // Parse level-0 records
                // Format can be:
                // 0 HEAD
                // 0 @I0001@ INDI
                // 0 TRLR
                def trimmed = line.substring(2).trim() // Remove "0 "
                String tag = null
                
                if (trimmed.startsWith("@")) {
                    // Has xref: @I0001@ INDI or @NOTE1@ NOTE ...
                    def endXref = trimmed.indexOf('@', 1)
                    if (endXref > 0 && trimmed.length() > endXref + 1) {
                        def afterXref = trimmed.substring(endXref + 1).trim()
                        def spacePos = afterXref.indexOf(' ')
                        tag = spacePos > 0 ? afterXref.substring(0, spacePos) : afterXref
                    }
                } else {
                    // No xref: HEAD, TRLR, etc.
                    def spacePos = trimmed.indexOf(' ')
                    tag = spacePos > 0 ? trimmed.substring(0, spacePos) : trimmed
                }
                
                if (tag) {
                    counts[tag] = (counts[tag] ?: 0) + 1
                }
            }
        }
        reader.close()
        
        then: "The file should contain the expected record counts"
        counts['HEAD'] == 1          // Header record
        counts['SUBM'] == 1          // Submitter record
        counts['INDI'] == 643        // Individual records
        counts['FAM'] == 242         // Family records
        counts['SOUR'] == 3          // Source records
        (counts['REPO'] ?: 0) == 0   // Repository records (none in this file)
        (counts['NOTE'] ?: 0) == 2   // Note records
        (counts['OBJE'] ?: 0) == 13  // Multimedia records
        (counts['SUBN'] ?: 0) == 0   // Submission records (none in this file)
        counts['TRLR'] == 1          // Trailer record
        
        and: "Print the actual counts for documentation"
        println "GEDCOM Record Counts from kermabon.ged:"
        println "  HEAD (Header):      ${counts['HEAD'] ?: 0}"
        println "  SUBM (Submitter):   ${counts['SUBM'] ?: 0}"
        println "  SUBN (Submission):  ${counts['SUBN'] ?: 0}"
        println "  INDI (Individual):  ${counts['INDI'] ?: 0}"
        println "  FAM (Family):       ${counts['FAM'] ?: 0}"
        println "  SOUR (Source):      ${counts['SOUR'] ?: 0}"
        println "  REPO (Repository):  ${counts['REPO'] ?: 0}"
        println "  NOTE (Note):        ${counts['NOTE'] ?: 0}"
        println "  OBJE (Multimedia):  ${counts['OBJE'] ?: 0}"
        println "  TRLR (Trailer):     ${counts['TRLR'] ?: 0}"
        println "  Total records:      ${counts.values().sum()}"
    }
    
    def "should successfully parse header from kermabon.ged"() {
        given: "The kermabon.ged file"
        def inputStream = getClass().getResourceAsStream("/kermabon.ged")
        assert inputStream != null
        def reader = new InputStreamReader(inputStream, "UTF-8")
        def parser = new GedcomParser()
        
        when: "Parsing the header"
        def header = parser.parseHeader(reader)
        
        then: "Header should be parsed successfully"
        header != null
        header.source() != null
        header.source().systemId() == "Gramps"
        header.source().version() == "6.0.6"
        header.characterSet() == "UTF-8"
        
        cleanup:
        reader?.close()
    }
}
