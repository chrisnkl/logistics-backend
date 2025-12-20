package nikolaou.christos.backend.fleet_management.exception;

import lib.exception.BackendException;

public class VehicleNotFoundException extends BackendException {

    public VehicleNotFoundException(String message) {
        super(message);
    }
}
