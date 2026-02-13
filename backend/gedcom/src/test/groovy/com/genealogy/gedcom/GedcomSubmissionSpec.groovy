package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader

class GedcomSubmissionSpec extends Specification {

    def "should parse submission record"() {
        given:
        def gedcom = """0 @SUB1@ SUBN
1 SUBM @U1@
1 FAMF MyFamily.ged
1 TEMP SLAKE
1 ANCE 3
1 DESC 2
1 ORDI yes
"""
        def reader = new StringReader(gedcom)
        def parser = new com.genealogy.gedcom.GedcomParser()

        when:
        def subn = parser.parseSubmission(reader)

        then:
        subn.xref() == "@SUB1@"
        subn.submitterXref() == "@U1@"
        subn.familyFileName() == "MyFamily.ged"
        subn.templeCode() == "SLAKE"
        subn.ancestorsCount() == "3"
        subn.descendantsCount() == "2"
        subn.ordinanceFlag() == "yes"
    }
}
