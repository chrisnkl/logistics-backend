package nikolaou.christos.backend.order_processing.exception;

import lib.exception.BackendException;

public class FailedCostCalculationException extends BackendException {

    public FailedCostCalculationException(String message) {
        super(message);
    }
}
