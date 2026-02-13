package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringWriter

class GedcomWriterIndividualSpec extends Specification {

    def "should write individual record"() {
        given:
        def writer = new StringWriter()
        def serializer = new GedcomWriter()
        
        def name = new GedcomName("John /Doe/", "John", "Doe", null, null)
        def birth = new GedcomEvent("BIRT", new GedcomDate("1 JAN 1980"), "Paris", null)
        def ind = new GedcomIndividual(
            "@I1@",
            [name],
            "M",
            [birth],
            ["@F1@"],
            ["@F2@"],
            null // changeDate
        )
        
        when:
        serializer.writeIndividual(ind, writer)
        def output = writer.toString()
        
        then:
        output.contains("0 @I1@ INDI")
        output.contains("1 NAME John /Doe/")
        output.contains("2 GIVN John")
        output.contains("2 SURN Doe")
        output.contains("1 SEX M")
        output.contains("1 BIRT")
        output.contains("2 DATE 1 JAN 1980")
        output.contains("2 PLAC Paris")
        output.contains("1 FAMS @F1@")
        output.contains("1 FAMC @F2@")
    }
}
