package ru.timestop.generator;

import java.io.Closeable;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * @author t.i.m.e.s.t.o.p
 * @version 1.0.0
 * @since 24.08.2018
 */
public class IOUtilites {

    /**
     * @param path
     * @param consumer
     */
    public static void load(String path, Consumer<String> consumer) {
        File tasksFile = new File(path);
        try (Stream<String> stream = Files.lines(tasksFile.toPath())) {
            stream.filter(itm -> !itm.isEmpty() && !itm.trim().startsWith("#")).forEach(consumer);
        } catch (IOException e) {
            throw new RuntimeException("File " + path + " reading error!", e);
        }
    }

    /**
     * close object without exception
     *
     * @param closeable any closeable object
     */
    public static void closeQuiet(AutoCloseable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            //DO_NOTHING
        }
    }


    /**
     * close object without exception
     *
     * @param closeable any closeable object
     */
    public static void closeQuiet(Closeable closeable) {
        try {
            closeable.close();
        } catch (Exception e) {
            //DO_NOTHING
        }
    }
}
