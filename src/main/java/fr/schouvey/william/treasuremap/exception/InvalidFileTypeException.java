package fr.schouvey.william.treasuremap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidFileTypeException extends RuntimeException {

    public InvalidFileTypeException(String fileName) {
        super("File type is invalid: " + fileName);
    }

}
