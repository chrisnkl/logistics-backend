package nikolaou.christos.backend.order_processing.exception;

import lombok.Getter;

@Getter
public class BackendException extends RuntimeException {

    public BackendException(String message) {
        super(message);
    };
}

