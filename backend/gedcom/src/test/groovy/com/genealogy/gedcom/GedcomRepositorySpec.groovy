package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader

class GedcomRepositorySpec extends Specification {

    def "should parse repository record"() {
        given:
        def gedcom = """0 @R1@ REPO
1 NAME National Archives
1 ADDR 123 Main St
1 PHON +1-555-1234
1 EMAIL info@archives.gov
1 WWW http://archives.gov
"""
        def reader = new StringReader(gedcom)
        def parser = new com.genealogy.gedcom.GedcomParser()

        when:
        def repo = parser.parseRepository(reader)

        then:
        repo.xref() == "@R1@"
        repo.name() == "National Archives"
        repo.address() == "123 Main St"
        repo.phone() == "+1-555-1234"
        repo.email() == "info@archives.gov"
        repo.www() == "http://archives.gov"
    }
}
