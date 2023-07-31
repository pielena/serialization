package com.github.pielena.serialization.util;

import java.io.File;

public class FilePathStringCreator {

    private static final String SEPARATOR = File.separator;
    private static final String TARGET_DIRECTORY = "output";

    public static String createPathString(String fileName) {
        return ClassLoader.getSystemResource(TARGET_DIRECTORY).getPath() + SEPARATOR + fileName;
    }
}
