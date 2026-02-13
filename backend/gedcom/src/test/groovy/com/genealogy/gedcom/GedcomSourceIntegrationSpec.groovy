package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader
import java.io.StringWriter

class GedcomSourceIntegrationSpec extends Specification {

    def "should read a source record, write it and result should match initial value"() {
        given: "A properly ordered GEDCOM source string"
        String initialGedcom = """0 @S1@ SOUR
1 TITL Census of 1900
1 AUTH National Archives
1 PUBL Government Printing Office
1 ABBR 1900 Census
1 REPO @R1@
1 TEXT Population schedule
"""
        def parser = new com.genealogy.gedcom.GedcomParser()
        def writer = new com.genealogy.gedcom.GedcomWriter()
        
        when: "Parsing the source record"
        def reader = new StringReader(initialGedcom)
        def source = parser.parseSource(reader)
        
        and: "Writing the source record back"
        def stringWriter = new StringWriter()
        writer.writeSource(source, stringWriter)
        def resultGedcom = stringWriter.toString()

        then: "The output should be identical to the input"
        resultGedcom == initialGedcom
    }
}
