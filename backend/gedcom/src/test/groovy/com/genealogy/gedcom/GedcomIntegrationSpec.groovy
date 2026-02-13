package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader
import java.io.StringWriter

class GedcomIntegrationSpec extends Specification {

    def "should read a header, write it and result should match initial value"() {
        given: "A properly ordered GEDCOM header string"
        // The order must match GedcomWriter's output order for strict string comparison
        String initialGedcom = """0 HEAD
1 SOUR MYAPP
2 VERS 1.0
2 NAME MyGenealogyApp
1 DEST ANSTFILE
1 DATE 1 JAN 1998
2 TIME 13:00
1 SUBM @U1@
1 FILE mytree.ged
1 GEDC
2 VERS 5.5
2 FORM LINEAGE-LINKED
1 CHAR ANSEL
"""
        // Note: Java Reader might convert line endings. StringWriter uses '\n'.
        // My GedcomWriter uses '\n'.
        // The multiline string uses '\n' in Groovy usually.
        
        def parser = new com.genealogy.gedcom.GedcomParser()
        def writer = new com.genealogy.gedcom.GedcomWriter()
        
        when: "Parsing the header"
        def reader = new StringReader(initialGedcom)
        def header = parser.parseHeader(reader)
        
        and: "Writing the header back"
        def stringWriter = new StringWriter()
        writer.writeHeader(header, stringWriter)
        def resultGedcom = stringWriter.toString()

        then: "The output should be identical to the input"
        resultGedcom == initialGedcom
    }
}
