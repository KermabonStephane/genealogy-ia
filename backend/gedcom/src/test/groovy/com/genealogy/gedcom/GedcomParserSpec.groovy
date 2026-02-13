package com.genealogy.gedcom

import spock.lang.Specification
import java.io.StringReader

class GedcomParserSpec extends Specification {

    def "should parse minimal header"() {
        given:
        def gedcom = """0 HEAD
1 SOUR MYAPP
2 VERS 1.0
2 NAME MyGenealogyApp
1 DEST ANSTFILE
1 DATE 1 JAN 1998
1 FILE mytree.ged
1 GEDC
2 VERS 5.5
2 FORM LINEAGE-LINKED
1 CHAR ANSEL
1 SUBM @U1@
0 @U1@ SUBM
1 NAME Stephane
"""
        def reader = new StringReader(gedcom)
        def parser = new com.genealogy.gedcom.GedcomParser()
        
        when:
        def header = parser.parseHeader(reader)
        
        then:
        header.source().systemId() == "MYAPP"
        header.source().version() == "1.0"
        header.source().name() == "MyGenealogyApp"
        header.destination() == "ANSTFILE"
        header.fileName() == "mytree.ged"
        header.gedcomVersion().version() == "5.5"
        header.gedcomVersion().form() == "LINEAGE-LINKED"
        header.characterSet() == "ANSEL"
        header.date().date() == "1 JAN 1998"
    }

    def "should handle missing optional fields"() {
        given:
        def gedcom = """0 HEAD
1 SOUR SIMPLE
1 CHAR UTF-8
"""
        def reader = new StringReader(gedcom)
        def parser = new com.genealogy.gedcom.GedcomParser()

        when:
        def header = parser.parseHeader(reader)

        then:
        header.source().systemId() == "SIMPLE"
        header.characterSet() == "UTF-8"
        header.gedcomVersion() != null
        header.gedcomVersion().version() == null
    }
}
