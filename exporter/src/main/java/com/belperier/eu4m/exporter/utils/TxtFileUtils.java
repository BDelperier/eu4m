package com.belperier.eu4m.exporter.utils;

import com.belperier.eu4m.exporter.parser.TxtParser;
import lombok.experimental.UtilityClass;
import lombok.extern.slf4j.Slf4j;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

@UtilityClass
@Slf4j
public class TxtFileUtils {

    public static List<String> read(String resourceFilePath) {
        try {
            if (!resourceFilePath.startsWith("/")) {
                resourceFilePath = "/" + resourceFilePath;
            }

            URL resource = TxtParser.class.getResource(resourceFilePath);
            if (resource == null) {
                log.error("The resource {} was not found.", resourceFilePath);
                throw new IllegalStateException("The resource " + resourceFilePath + " was not found.");
            }

            return Files.readAllLines(Path.of(resource.toURI()));
        } catch (Exception e) {
            log.error("error trying to read file {}", resourceFilePath);
            throw new IllegalStateException("error trying to read file " + resourceFilePath, e);
        }
    }
}

