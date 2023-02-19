package fr.schouvey.william.treasuremap.web.mapper;

import fr.schouvey.william.treasuremap.exception.InvalidFileNameException;
import fr.schouvey.william.treasuremap.exception.InvalidFileTypeException;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

public class FileWebMapper {

    private static final String ACCEPTED_EXTENSION = "txt";
    private static final String RESULT_FILE_NAME = "result";

    private FileWebMapper() {}

    public static List<String> mapFileToListString(MultipartFile multipartFile) throws IOException {
        File file = new File(File.separator + "tmp" + File.separator + Objects.requireNonNull(multipartFile.getOriginalFilename()));
        multipartFile.transferTo(file);
        file.deleteOnExit();
        checkFileExtension(file.getName());
        return readFile(file);
    }

    private static List<String> readFile(File file) throws IOException {
        List<String> lines = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line = reader.readLine();
            while (line != null) {
                lines.add(line);
                line = reader.readLine();
            }
        }
        return lines;
    }

    private static void checkFileExtension(String filename) {
        var extension = Optional.ofNullable(filename)
                .filter(f -> f.contains("."))
                .map(f -> f.substring(filename.lastIndexOf(".") + 1))
                .orElseThrow(() -> new InvalidFileNameException(filename));
        if (!ACCEPTED_EXTENSION.equalsIgnoreCase(extension)) {
            throw new InvalidFileTypeException(filename);
        }
    }

    public static File mapListStringToFile(List<String> lines) throws IOException {
        File file = new File(File.separator + "tmp" + File.separator + Objects.requireNonNull(RESULT_FILE_NAME) + "." + ACCEPTED_EXTENSION);
        file.deleteOnExit();
        writeLinesToFile(file, lines);
        return file;
    }

    private static void writeLinesToFile(File file, List<String> lines) throws IOException {
        try (PrintWriter printWriter = new PrintWriter(new FileWriter(file))) {
            lines.forEach(printWriter::println);
        }
    }

}
