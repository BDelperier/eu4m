package com.belperier.eu4m.exporter.parser;

import com.belperier.eu4m.exporter.utils.StringUtils;
import com.belperier.eu4m.exporter.utils.TxtFileUtils;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

@Slf4j
public class TxtParser {
    public static String toJson(String resourceFilePath) {
        List<String> content = TxtFileUtils.read(resourceFilePath);

        int computedIndent = 0;
        boolean previousLineEndWithOpenBracket = false;

        for (int i = 0; i < content.size(); i++) {
            StringUtils.count("\t\t\t\tNOT = {\t", '\t');
            int lineNumber = i + 1;
            String rawLine = removeComment(content.get(i));

            if (shouldSkipLine(rawLine, lineNumber)) {
                continue;
            }

            if (previousLineEndWithOpenBracket) {
                computedIndent = computedIndent + 1;
                previousLineEndWithOpenBracket = false;
            }

            if (rawLine.matches(".*\\{[ \t]*")) {
                log.debug("The line {} ends with a {.", lineNumber);
                previousLineEndWithOpenBracket = true;
            }

            if (rawLine.matches(".*}[ \t]*") && !rawLine.contains("{")) {
                log.debug("The line {} ends with a }.", lineNumber);
                computedIndent = computedIndent - 1;
            }

            long indentNumber = StringUtils.count(rawLine, '\t');

            if (indentNumber != computedIndent) {
                log.debug("indent do not match on line {}.", lineNumber);
            }

            if (!rawLine.contains("=") && !rawLine.matches("[ \t]*}[ \t]*")) {
                log.info("A not skipped line {} do not contains equals : {}.", lineNumber, rawLine);
            }
        }

        return String.join("", content);
    }

    private static String removeComment(String line) {
        if (line.contains("#")) {
            return line.substring(0, line.indexOf("#"));
        }
        return line;
    }

    private static boolean shouldSkipLine(String rawLine, int lineNumber) {
        if (StringUtils.isBlank(rawLine)) {
            log.debug("line {} is blank", lineNumber);
            return true;
        }

        if (rawLine.startsWith("#")) {
            log.debug("line {} is a comment", lineNumber);
            return true;
        }

        return false;
    }
}
