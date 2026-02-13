package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader

class GedcomNoteSpec extends Specification {

    def "should parse simple note"() {
        given:
        def gedcom = """0 @N1@ NOTE This is a note
1 CONC  continued on same line
1 CONT And this is a new line
"""
        def reader = new StringReader(gedcom)
        def parser = new com.genealogy.gedcom.GedcomParser()

        when:
        def note = parser.parseNote(reader)

        then:
        note.xref() == "@N1@"
        // Note: my parser logic appends \n for CONT.
        note.value() == "This is a note continued on same line\nAnd this is a new line"
    }
}
