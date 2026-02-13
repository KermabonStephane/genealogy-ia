package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringWriter

class GedcomWriterSpec extends Specification {

    def "should write basic header"() {
        given:
        def writer = new StringWriter()
        def serializer = new GedcomWriter()
        
        def version = new GedcomHeader.GedcomVersion("5.5.1", "LINEAGE-LINKED")
        def source = new GedcomHeader.Source("MYAPP", "2.0", "My App", null, null)
        
        def header = new GedcomHeader(
            source,
            "ANY",
            null,
            null,
            null,
            "test.ged",
            null,
            version,
            "UTF-8",
            "English",
            null,
            null
        )
        
        when:
        serializer.writeHeader(header, writer)
        def output = writer.toString()
        
        then:
        output.contains("0 HEAD")
        output.contains("1 SOUR MYAPP")
        output.contains("2 VERS 2.0")
        output.contains("2 NAME My App")
        output.contains("1 DEST ANY")
        output.contains("1 GEDC")
        output.contains("2 VERS 5.5.1")
        output.contains("2 FORM LINEAGE-LINKED")
        output.contains("1 CHAR UTF-8")
        output.contains("1 FILE test.ged")
    }
}
