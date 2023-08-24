package com.cos.core.util.format;

import com.cos.core.config.cp.ConnectionPullC3P0Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.file.Path;
import java.nio.file.Paths;

public class FileFormatter implements IFileFormatter {

    private static final Logger LOG = LoggerFactory.getLogger(ConnectionPullC3P0Configuration.class);


    @Override
    public FileFormat getFormat(String fileName) {
        Path path = Paths.get(fileName);
        String fileNameWithExtension = path.getFileName().toString();
        String fileExtension = fileNameWithExtension.substring(fileNameWithExtension.lastIndexOf(".") + 1);

        LOG.info("File format is {}", fileExtension);
        if (FileFormat.XML.getValue().equals(fileExtension)) {
            return FileFormat.XML;
        }
        if (FileFormat.PROPERTIES.getValue().equals(fileExtension)) {
            return FileFormat.PROPERTIES;
        }
        throw new RuntimeException("File format isn't xml or properties");
    }
}
