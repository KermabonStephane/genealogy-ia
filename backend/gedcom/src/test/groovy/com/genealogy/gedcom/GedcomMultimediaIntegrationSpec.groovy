package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader
import java.io.StringWriter

class GedcomMultimediaIntegrationSpec extends Specification {

    def "should read a multimedia record, write it and result should match initial value"() {
        given: "A properly ordered GEDCOM multimedia string"
        String initialGedcom = """0 @M1@ OBJE
1 FORM jpeg
1 TITL My Photo
1 FILE photo.jpg
"""
        def parser = new com.genealogy.gedcom.GedcomParser()
        def writer = new com.genealogy.gedcom.GedcomWriter()
        
        when: "Parsing the multimedia record"
        def reader = new StringReader(initialGedcom)
        def media = parser.parseMultimedia(reader)
        
        and: "Writing the multimedia record back"
        def stringWriter = new StringWriter()
        writer.writeMultimedia(media, stringWriter)
        def resultGedcom = stringWriter.toString()

        then: "The output should be identical to the input"
        resultGedcom == initialGedcom
    }
}
