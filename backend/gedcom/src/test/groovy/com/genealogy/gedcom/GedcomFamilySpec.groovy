package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader

class GedcomFamilySpec extends Specification {

    def "should parse simple family"() {
        given:
        def gedcom = """0 @F1@ FAM
1 HUSB @I1@
1 WIFE @I2@
1 CHIL @I3@
1 CHIL @I4@
1 MARR
2 DATE 10 JAN 2000
2 PLAC Paris
"""
        def reader = new StringReader(gedcom)
        def parser = new com.genealogy.gedcom.GedcomParser()

        when:
        def fam = parser.parseFamily(reader)

        then:
        fam.xref() == "@F1@"
        fam.husbandXref() == "@I1@"
        fam.wifeXref() == "@I2@"
        fam.childrenXrefs() == ["@I3@", "@I4@"]
        fam.events().size() == 1
        fam.events()[0].type() == "MARR"
        fam.events()[0].date().date() == "10 JAN 2000"
        fam.events()[0].place() == "Paris"
    }
}
