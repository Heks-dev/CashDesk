package ua.org.goservice.cashdesk.model.util.loader;

import ua.org.goservice.cashdesk.model.exception.ActionDeniedException;

import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class FileLoader {

    private static final String ERROR = "Ошибка чтения файла";

    public static String readFile(String location) {
        try {
            Path path = Paths.get(Class.class.getResource(location).toURI());
            return new String(Files.readAllBytes(path), "UTF-8");
        } catch (IOException | URISyntaxException e) {
            e.printStackTrace();
            throw new ActionDeniedException(ERROR);
        }
    }
}
