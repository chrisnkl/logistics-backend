package nikolaou.christos.backend.fleet_management.exception;

import lib.exception.BackendException;

public class UnsupportedVehicleTypeException extends BackendException {

    public UnsupportedVehicleTypeException(String message) {
        super(message);
    }
}
