package ojt.management.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.remote.business.AmazonClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/storage/")
@SecurityRequirement(name = "bearerAuth")
public class BucketController {

    private final AmazonClientService amazonClientService;

    @Autowired
    BucketController(AmazonClientService amazonClientService) {
        this.amazonClientService = amazonClientService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public String uploadFile(@RequestParam(value = "file") MultipartFile file) {
        return this.amazonClientService.uploadFile(file);
    }
}