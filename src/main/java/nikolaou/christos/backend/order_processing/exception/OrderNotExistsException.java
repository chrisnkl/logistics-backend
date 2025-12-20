package nikolaou.christos.backend.order_processing.exception;

import lib.exception.BackendException;

public class OrderNotExistsException extends BackendException {

    public OrderNotExistsException(String message) {
        super(message);
    }

}
