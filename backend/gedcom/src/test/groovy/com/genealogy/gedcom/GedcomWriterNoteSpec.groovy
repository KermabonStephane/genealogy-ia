package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringWriter

class GedcomWriterNoteSpec extends Specification {

    def "should write note record with continuation"() {
        given:
        def writer = new StringWriter()
        def serializer = new GedcomWriter()
        
        def note = new GedcomNote(
            "@N1@",
            "Line 1\nLine 2",
            null
        )
        
        when:
        serializer.writeNote(note, writer)
        def output = writer.toString()
        
        then:
        output.contains("0 @N1@ NOTE Line 1")
        output.contains("1 CONT Line 2")
    }
}
