package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringWriter

class GedcomWriterSubmissionSpec extends Specification {

    def "should write submission record"() {
        given:
        def writer = new StringWriter()
        def serializer = new GedcomWriter()
        
        def subn = new GedcomSubmission(
            "@SUB1@",
            "@U1@",
            "MyFamily.ged",
            "SLAKE",
            "3",
            "2",
            "yes",
            null
        )
        
        when:
        serializer.writeSubmission(subn, writer)
        def output = writer.toString()
        
        then:
        output.contains("0 @SUB1@ SUBN")
        output.contains("1 SUBM @U1@")
        output.contains("1 FAMF MyFamily.ged")
        output.contains("1 TEMP SLAKE")
        output.contains("1 ANCE 3")
        output.contains("1 DESC 2")
        output.contains("1 ORDI yes")
    }
}
