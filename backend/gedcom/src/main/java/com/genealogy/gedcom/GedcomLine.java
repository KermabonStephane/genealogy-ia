package com.genealogy.gedcom;

public record GedcomLine(int level, String xref, String tag, String value) {
    public static final String DELIMITER = " ";

    /**
     * Parses a raw GEDCOM line.
     * Format: Level + [Delim] + [Optional Xref] + [Delim] + Tag + [Delim] + [Optional Value]
     */
    public static GedcomLine parse(String line) {
        if (line == null || line.isBlank()) {
            return null;
        }

        String content = line.trim();
        int spaceIndex = content.indexOf(DELIMITER);
        
        if (spaceIndex == -1) {
             // Only Level? Invalid but handle gracefully or single token?
             // Maybe "0 HEAD" -> spaceIndex=1.
             // If "0", technically valid line but no tag? Invalid.
             // Let's assume content is correctly formed or throw exception later.
             return new GedcomLine(Integer.parseInt(content), null, null, null);
        }

        int level = Integer.parseInt(content.substring(0, spaceIndex));
        String rest = content.substring(spaceIndex + 1).trim();

        String xref = null;
        String tag = null;
        String value = null;

        if (rest.startsWith("@")) {
            int xrefEnd = rest.indexOf("@", 1);
            if (xrefEnd != -1) {
                xref = rest.substring(0, xrefEnd + 1);
                rest = rest.substring(xrefEnd + 1).trim();
            }
        }

        int valueStart = rest.indexOf(DELIMITER);
        if (valueStart != -1) {
            tag = rest.substring(0, valueStart);
            value = rest.substring(valueStart + 1);
        } else {
            tag = rest;
        }

        return new GedcomLine(level, xref, tag, value);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(level);
        if (xref != null) {
            sb.append(DELIMITER).append(xref);
        }
        if (tag != null) {
            sb.append(DELIMITER).append(tag);
        }
        if (value != null) {
            sb.append(DELIMITER).append(value);
        }
        return sb.toString();
    }
}
