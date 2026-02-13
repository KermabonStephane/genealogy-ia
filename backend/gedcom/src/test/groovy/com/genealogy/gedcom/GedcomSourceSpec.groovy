package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader

class GedcomSourceSpec extends Specification {

    def "should parse source record"() {
        given:
        def gedcom = """0 @S1@ SOUR
1 TITL Census of 1900
1 AUTH National Archives
1 PUBL Government Printing Office
1 ABBR 1900 Census
1 REPO @R1@
1 TEXT Population schedule
"""
        def reader = new StringReader(gedcom)
        def parser = new com.genealogy.gedcom.GedcomParser()

        when:
        def source = parser.parseSource(reader)

        then:
        source.xref() == "@S1@"
        source.title() == "Census of 1900"
        source.author() == "National Archives"
        source.publication() == "Government Printing Office"
        source.abbreviation() == "1900 Census"
        source.repositoryXref() == "@R1@"
        source.text() == "Population schedule"
    }
}
