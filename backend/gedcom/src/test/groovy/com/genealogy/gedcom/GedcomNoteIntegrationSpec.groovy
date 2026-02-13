package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader
import java.io.StringWriter

class GedcomNoteIntegrationSpec extends Specification {

    def "should read a note, write it and result should match initial value"() {
        given: "A properly ordered GEDCOM note string"
        String initialGedcom = """0 @N1@ NOTE Line 1
1 CONT Line 2
"""
        def parser = new com.genealogy.gedcom.GedcomParser()
        def writer = new com.genealogy.gedcom.GedcomWriter()
        
        when: "Parsing the note"
        def reader = new StringReader(initialGedcom)
        def note = parser.parseNote(reader)
        
        and: "Writing the note back"
        def stringWriter = new StringWriter()
        writer.writeNote(note, stringWriter)
        def resultGedcom = stringWriter.toString()

        then: "The output should be identical to the input"
        resultGedcom == initialGedcom
    }
}
