package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringWriter

class GedcomWriterMultimediaSpec extends Specification {

    def "should write multimedia record"() {
        given:
        def writer = new StringWriter()
        def serializer = new GedcomWriter()
        
        def media = new GedcomMultimedia(
            "@M1@",
            "jpeg",
            "My Photo",
            "photo.jpg",
            null
        )
        
        when:
        serializer.writeMultimedia(media, writer)
        def output = writer.toString()
        
        then:
        output.contains("0 @M1@ OBJE")
        output.contains("1 FORM jpeg")
        output.contains("1 TITL My Photo")
        output.contains("1 FILE photo.jpg")
    }
}
