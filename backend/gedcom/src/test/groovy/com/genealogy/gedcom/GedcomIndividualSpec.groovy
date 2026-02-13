package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader

class GedcomIndividualSpec extends Specification {

    def "should parse simple individual"() {
        given:
        def gedcom = """0 @I1@ INDI
1 NAME John /Doe/
2 GIVN John
2 SURN Doe
1 SEX M
1 BIRT
2 DATE 10 JAN 1980
2 PLAC New York
1 DEAT
2 DATE 10 JAN 2080
1 FAMS @F1@
1 FAMC @F2@
"""
        def reader = new StringReader(gedcom)
        def parser = new com.genealogy.gedcom.GedcomParser()

        when:
        def ind = parser.parseIndividual(reader)

        then:
        ind.xref() == "@I1@"
        ind.names().size() == 1
        ind.names()[0].value() == "John /Doe/"
        ind.names()[0].givenName() == "John"
        ind.names()[0].surname() == "Doe"
        ind.sex() == "M"
        ind.events().size() == 2
        ind.events()[0].type() == "BIRT"
        ind.events()[0].date().date() == "10 JAN 1980"
        ind.events()[0].place() == "New York"
        ind.events()[1].type() == "DEAT"
        ind.events()[1].date().date() == "10 JAN 2080"
        ind.spouseFamilyLinks() == ["@F1@"]
        ind.childFamilyLinks() == ["@F2@"]
    }
    def "should parse individual with full name parts"() {
        given:
        def gedcom = """0 @I2@ INDI
1 NAME Stéphane /de Kermabon/
2 GIVN Stéphane
2 SURN Kermabon
2 SPFX de
2 NICK Steph
2 NPFX Sir
2 NSFX III
"""
        def reader = new StringReader(gedcom)
        def parser = new com.genealogy.gedcom.GedcomParser()

        when:
        def ind = parser.parseIndividual(reader)

        then:
        ind.xref() == "@I2@"
        ind.names().size() == 1
        with(ind.names()[0]) {
            value() == "Stéphane /de Kermabon/"
            givenName() == "Stéphane"
            surname() == "Kermabon"
            surnamePrefix() == "de"
            nickname() == "Steph"
            prefix() == "Sir"
            suffix() == "III"
        }
    }
}
