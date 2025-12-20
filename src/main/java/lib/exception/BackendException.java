package lib.exception;

import lombok.Getter;

@Getter
public class BackendException extends RuntimeException {

    public BackendException(String message) {
        super(message);
    };
}

