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
2 TIME 13:15:30.12
1 FILE mytree.ged
1 GEDC
2 VERS 5.5
2 FORM LINEAGE-LINKED
1 CHAR ANSEL
1 SUBM @U1@
1 NOTE This is a test GEDCOM file.
2 CONT another line 
2 CONT a third line
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
        header.destinations() == ["ANSTFILE"]
        header.fileName() == "mytree.ged"
        header.gedcomVersion().version() == "5.5"
        header.gedcomVersion().form() == "LINEAGE-LINKED"
        header.characterSet() == "ANSEL"
        header.transmissionDate().date() == java.time.LocalDate.of(1998, 1, 1)
        header.transmissionDate().time() == java.time.LocalTime.of(13, 15, 30, 120_000_000)
        header.note() == "This is a test GEDCOM file. another line a third line"
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
    def "should parse header with multiple destinations"() {
        given:
        def gedcom = """0 HEAD
1 SOUR MYAPP
1 DEST DEST1
1 DEST DEST2
1 CHAR UTF-8
"""
        def reader = new StringReader(gedcom)
        def parser = new com.genealogy.gedcom.GedcomParser()

        when:
        def header = parser.parseHeader(reader)

        then:
        header.destinations() == ["DEST1", "DEST2"]
    }
}
