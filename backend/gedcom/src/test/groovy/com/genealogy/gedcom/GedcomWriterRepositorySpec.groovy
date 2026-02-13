package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringWriter

class GedcomWriterRepositorySpec extends Specification {

    def "should write repository record"() {
        given:
        def writer = new StringWriter()
        def serializer = new GedcomWriter()
        
        def repo = new GedcomRepository(
            "@R1@",
            "National Archives",
            "123 Main St",
            "+1-555-1234",
            "info@archives.gov",
            "http://archives.gov",
            null
        )
        
        when:
        serializer.writeRepository(repo, writer)
        def output = writer.toString()
        
        then:
        output.contains("0 @R1@ REPO")
        output.contains("1 NAME National Archives")
        output.contains("1 ADDR 123 Main St")
        output.contains("1 PHON +1-555-1234")
        output.contains("1 EMAIL info@archives.gov")
        output.contains("1 WWW http://archives.gov")
    }
}
