package lib.exception;

import lib.response.BackendResponse;
import nikolaou.christos.backend.fleet_management.exception.UnsupportedVehicleTypeException;
import nikolaou.christos.backend.fleet_management.exception.VehicleNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<BackendResponse> handleVehicleNotFoundException(VehicleNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new BackendResponse(HttpStatus.NOT_FOUND.value(), e.getMessage()));
    }

    @ExceptionHandler(UnsupportedVehicleTypeException.class)
    public ResponseEntity<BackendResponse> handleUnsupportedVehicleTypeException(UnsupportedVehicleTypeException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BackendResponse(HttpStatus.BAD_REQUEST.value(), e.getMessage()));
    }

    @ExceptionHandler
    public ResponseEntity<BackendResponse> handleMethodArgumentTypeMismatchException(MethodArgumentTypeMismatchException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new BackendResponse(HttpStatus.BAD_REQUEST.value(), "Invalid enum value provided."));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<BackendResponse> handleException(Exception e) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new BackendResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(), e.getMessage()));
    }

}
