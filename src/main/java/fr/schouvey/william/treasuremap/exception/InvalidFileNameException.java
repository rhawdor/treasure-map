package fr.schouvey.william.treasuremap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFileNameException extends RuntimeException {

    public InvalidFileNameException(String fileName) {
        super("File name is invalid: " + fileName);
    }

}
