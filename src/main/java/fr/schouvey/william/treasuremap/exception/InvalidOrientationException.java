package fr.schouvey.william.treasuremap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST)
public class InvalidOrientationException extends RuntimeException {

    public InvalidOrientationException(String code) {
        super("The given orientation: " + code + " is invalid");
    }

}
