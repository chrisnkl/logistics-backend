package nikolaou.christos.backend.fleet_management.utils;

import nikolaou.christos.backend.fleet_management.exception.UnsupportedVehicleTypeException;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

@Component
public class VehicleTypeConverter implements Converter<String, VehicleType> {

    @Override
    public VehicleType convert(String source) {
        try {
            return VehicleType.valueOf(source.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new UnsupportedVehicleTypeException("Vehicle type " + source + " not found.");
        }
    }
}
