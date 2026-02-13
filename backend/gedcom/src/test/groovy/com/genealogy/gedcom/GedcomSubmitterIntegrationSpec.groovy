package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader
import java.io.StringWriter

class GedcomSubmitterIntegrationSpec extends Specification {

    def "should read a submitter record, write it and result should match initial value"() {
        given: "A properly ordered GEDCOM submitter string"
        String initialGedcom = """0 @U1@ SUBM
1 NAME John Smith
1 ADDR 456 Oak Ave
1 PHON +1-555-5678
1 EMAIL john@example.com
1 WWW http://example.com
1 LANG English
"""
        def parser = new com.genealogy.gedcom.GedcomParser()
        def writer = new com.genealogy.gedcom.GedcomWriter()
        
        when: "Parsing the submitter record"
        def reader = new StringReader(initialGedcom)
        def subm = parser.parseSubmitter(reader)
        
        and: "Writing the submitter record back"
        def stringWriter = new StringWriter()
        writer.writeSubmitter(subm, stringWriter)
        def resultGedcom = stringWriter.toString()

        then: "The output should be identical to the input"
        resultGedcom == initialGedcom
    }
}
