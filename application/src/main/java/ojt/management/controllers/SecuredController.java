package ojt.management.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@PreAuthorize("hasAnyAuthority('STUDENT')")
@SecurityRequirement(name = "bearerAuth")
@RequestMapping("/api/secure")
public class SecuredController {

    @GetMapping
    public ResponseEntity<String> reachSecureEndpoint() {

        return new ResponseEntity<>("If your are reading this you reached a secure endpoint", HttpStatus.OK);
    }

    @GetMapping("/abc")
    public ResponseEntity<String> abc() {

        return new ResponseEntity<>("If your are reading this you reached a secure endpoint", HttpStatus.OK);
    }
}
