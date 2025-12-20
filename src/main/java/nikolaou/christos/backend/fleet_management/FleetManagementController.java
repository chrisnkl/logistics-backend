package nikolaou.christos.backend.fleet_management;

import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/vehicles")
public class FleetManagementController {

    //TODO: Implement fleet management endpoints
    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public String getVehicles() {
        return "List of vehicles";
    }

}
