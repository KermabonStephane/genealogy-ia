package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringWriter

class GedcomWriterSubmitterSpec extends Specification {

    def "should write submitter record"() {
        given:
        def writer = new StringWriter()
        def serializer = new GedcomWriter()
        
        def subm = new GedcomSubmitter(
            "@U1@",
            "John Smith",
            "456 Oak Ave",
            "+1-555-5678",
            "john@example.com",
            "http://example.com",
            "English",
            null
        )
        
        when:
        serializer.writeSubmitter(subm, writer)
        def output = writer.toString()
        
        then:
        output.contains("0 @U1@ SUBM")
        output.contains("1 NAME John Smith")
        output.contains("1 ADDR 456 Oak Ave")
        output.contains("1 PHON +1-555-5678")
        output.contains("1 EMAIL john@example.com")
        output.contains("1 WWW http://example.com")
        output.contains("1 LANG English")
    }
}
