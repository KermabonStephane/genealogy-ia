package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader
import java.io.StringWriter

class GedcomIndividualIntegrationSpec extends Specification {

    def "should read an individual, write it and result should match initial value"() {
        given: "A properly ordered GEDCOM individual string"
        String initialGedcom = """0 @I1@ INDI
1 NAME John /Doe/
2 GIVN John
2 SURN Doe
1 SEX M
1 BIRT
2 DATE 10 JAN 1980
2 PLAC New York
1 DEAT
2 DATE 10 JAN 2080
1 FAMS @F1@
1 FAMC @F2@
"""
        def parser = new com.genealogy.gedcom.GedcomParser()
        def writer = new com.genealogy.gedcom.GedcomWriter()
        
        when: "Parsing the individual"
        def reader = new StringReader(initialGedcom)
        def ind = parser.parseIndividual(reader)
        
        and: "Writing the individual back"
        def stringWriter = new StringWriter()
        writer.writeIndividual(ind, stringWriter)
        def resultGedcom = stringWriter.toString()

        then: "The output should be identical to the input"
        resultGedcom == initialGedcom
    }
}
