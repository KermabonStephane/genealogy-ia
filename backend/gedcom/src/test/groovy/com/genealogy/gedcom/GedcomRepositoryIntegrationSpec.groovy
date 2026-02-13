package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader
import java.io.StringWriter

class GedcomRepositoryIntegrationSpec extends Specification {

    def "should read a repository record, write it and result should match initial value"() {
        given: "A properly ordered GEDCOM repository string"
        String initialGedcom = """0 @R1@ REPO
1 NAME National Archives
1 ADDR 123 Main St
1 PHON +1-555-1234
1 EMAIL info@archives.gov
1 WWW http://archives.gov
"""
        def parser = new com.genealogy.gedcom.GedcomParser()
        def writer = new com.genealogy.gedcom.GedcomWriter()
        
        when: "Parsing the repository record"
        def reader = new StringReader(initialGedcom)
        def repo = parser.parseRepository(reader)
        
        and: "Writing the repository record back"
        def stringWriter = new StringWriter()
        writer.writeRepository(repo, stringWriter)
        def resultGedcom = stringWriter.toString()

        then: "The output should be identical to the input"
        resultGedcom == initialGedcom
    }
}
