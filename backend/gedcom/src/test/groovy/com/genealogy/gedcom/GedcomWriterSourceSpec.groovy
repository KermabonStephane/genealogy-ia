package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringWriter

class GedcomWriterSourceSpec extends Specification {

    def "should write source record"() {
        given:
        def writer = new StringWriter()
        def serializer = new GedcomWriter()
        
        def source = new GedcomSource(
            "@S1@",
            "Census of 1900",
            "National Archives",
            "Government Printing Office",
            "1900 Census",
            "@R1@",
            "Population schedule",
            null
        )
        
        when:
        serializer.writeSource(source, writer)
        def output = writer.toString()
        
        then:
        output.contains("0 @S1@ SOUR")
        output.contains("1 TITL Census of 1900")
        output.contains("1 AUTH National Archives")
        output.contains("1 PUBL Government Printing Office")
        output.contains("1 ABBR 1900 Census")
        output.contains("1 REPO @R1@")
        output.contains("1 TEXT Population schedule")
    }
}
