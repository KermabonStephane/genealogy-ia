package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader
import java.io.StringWriter

class GedcomSubmissionIntegrationSpec extends Specification {

    def "should read a submission record, write it and result should match initial value"() {
        given: "A properly ordered GEDCOM submission string"
        String initialGedcom = """0 @SUB1@ SUBN
1 SUBM @U1@
1 FAMF MyFamily.ged
1 TEMP SLAKE
1 ANCE 3
1 DESC 2
1 ORDI yes
"""
        def parser = new com.genealogy.gedcom.GedcomParser()
        def writer = new com.genealogy.gedcom.GedcomWriter()
        
        when: "Parsing the submission record"
        def reader = new StringReader(initialGedcom)
        def subn = parser.parseSubmission(reader)
        
        and: "Writing the submission record back"
        def stringWriter = new StringWriter()
        writer.writeSubmission(subn, stringWriter)
        def resultGedcom = stringWriter.toString()

        then: "The output should be identical to the input"
        resultGedcom == initialGedcom
    }
}
