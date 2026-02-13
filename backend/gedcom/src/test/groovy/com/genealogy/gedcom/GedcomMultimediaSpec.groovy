package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader

class GedcomMultimediaSpec extends Specification {

    def "should parse multimedia record"() {
        given:
        def gedcom = """0 @M1@ OBJE
1 FORM jpeg
1 TITL My Photo
1 FILE photo.jpg
"""
        def reader = new StringReader(gedcom)
        def parser = new com.genealogy.gedcom.GedcomParser()

        when:
        def media = parser.parseMultimedia(reader)

        then:
        media.xref() == "@M1@"
        media.format() == "jpeg"
        media.title() == "My Photo"
        media.fileMode() == "photo.jpg"
    }
}
