package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader
import java.io.StringWriter

class GedcomFamilyIntegrationSpec extends Specification {

    def "should read a family, write it and result should match initial value"() {
        given: "A properly ordered GEDCOM family string"
        String initialGedcom = """0 @F1@ FAM
1 HUSB @I1@
1 WIFE @I2@
1 CHIL @I3@
1 CHIL @I4@
1 MARR
2 DATE 10 JAN 2000
2 PLAC Paris
"""
        def parser = new com.genealogy.gedcom.GedcomParser()
        def writer = new com.genealogy.gedcom.GedcomWriter()
        
        when: "Parsing the family"
        def reader = new StringReader(initialGedcom)
        def fam = parser.parseFamily(reader)
        
        and: "Writing the family back"
        def stringWriter = new StringWriter()
        writer.writeFamily(fam, stringWriter)
        def resultGedcom = stringWriter.toString()

        then: "The output should be identical to the input"
        resultGedcom == initialGedcom
    }
}
