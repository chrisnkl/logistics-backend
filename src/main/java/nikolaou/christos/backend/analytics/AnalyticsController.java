package nikolaou.christos.backend.analytics;

import lib.response.BackendResponse;
import lombok.RequiredArgsConstructor;
import nikolaou.christos.backend.analytics.dto.AnalyticsResponse;
import nikolaou.christos.backend.analytics.service.AnalyticsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping
    public ResponseEntity<BackendResponse> getAnalytics() throws ExecutionException, InterruptedException {

        AnalyticsResponse response = analyticsService.getAnalytics().get();

        return ResponseEntity.ok(new BackendResponse(
                HttpStatus.OK.value(),
                "Analytics Retrieved",
                response
        ));
    }

}
