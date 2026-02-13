package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringWriter

class GedcomWriterFamilySpec extends Specification {

    def "should write family record"() {
        given:
        def writer = new StringWriter()
        def serializer = new GedcomWriter()
        
        def marriage = new GedcomEvent("MARR", new GedcomDate("10 JAN 2000"), "Paris", null)
        def fam = new GedcomFamily(
            "@F1@",
            "@I1@",
            "@I2@",
            ["@I3@", "@I4@"],
            [marriage],
            null
        )
        
        when:
        serializer.writeFamily(fam, writer)
        def output = writer.toString()
        
        then:
        output.contains("0 @F1@ FAM")
        output.contains("1 HUSB @I1@")
        output.contains("1 WIFE @I2@")
        output.contains("1 CHIL @I3@")
        output.contains("1 CHIL @I4@")
        output.contains("1 MARR")
        output.contains("2 DATE 10 JAN 2000")
        output.contains("2 PLAC Paris")
    }
}
