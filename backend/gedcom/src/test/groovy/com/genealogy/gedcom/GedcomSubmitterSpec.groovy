package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader

class GedcomSubmitterSpec extends Specification {

    def "should parse submitter record"() {
        given:
        def gedcom = """0 @U1@ SUBM
1 NAME John Smith
1 ADDR 456 Oak Ave
1 PHON +1-555-5678
1 EMAIL john@example.com
1 WWW http://example.com
1 LANG English
"""
        def reader = new StringReader(gedcom)
        def parser = new com.genealogy.gedcom.GedcomParser()

        when:
        def subm = parser.parseSubmitter(reader)

        then:
        subm.xref() == "@U1@"
        subm.name() == "John Smith"
        subm.address() == "456 Oak Ave"
        subm.phone() == "+1-555-5678"
        subm.email() == "john@example.com"
        subm.www() == "http://example.com"
        subm.language() == "English"
    }
}
