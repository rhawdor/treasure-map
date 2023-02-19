package fr.schouvey.william.treasuremap.exception;

public class InvalidInputException extends RuntimeException {

    public InvalidInputException(String line, Exception e) {
        super("Input line: " + line + " is invalid and cannot be processed", e);
    }

}
