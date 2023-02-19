package fr.schouvey.william.treasuremap.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidMovementException extends RuntimeException {

    public InvalidMovementException(String code) {
        super("The given movement code: " + code + " is invalid");
    }


}
