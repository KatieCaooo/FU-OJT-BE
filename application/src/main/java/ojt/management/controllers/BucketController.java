package ojt.management.controllers;

import com.amazonaws.services.s3.model.S3Object;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.common.payload.dto.AttachmentDTO;
import ojt.management.configuration.security.services.UserDetailsImpl;
import ojt.management.mappers.AttachmentMapper;
import ojt.management.remote.business.AmazonClientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.Map;

@RestController
@RequestMapping("/storage/")
@SecurityRequirement(name = "bearerAuth")
public class BucketController {

    private final AmazonClientService amazonClientService;
    private final AttachmentMapper attachmentMapper;

    @Autowired
    BucketController(AmazonClientService amazonClientService, AttachmentMapper attachmentMapper) {
        this.amazonClientService = amazonClientService;
        this.attachmentMapper = attachmentMapper;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AttachmentDTO uploadFile(@RequestParam(value = "file") MultipartFile file, Authentication authentication) {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return attachmentMapper.attachmentToAttachmentDTO(amazonClientService.uploadFile(file, accountId));
    }

    @GetMapping("/{key}")
    public ResponseEntity<?> downloadFile(@PathVariable("key") String key){
        S3Object object = amazonClientService.downloadFile(key);
        Map<String, String> metadata = object.getObjectMetadata().getUserMetadata();
        InputStream s3InputStream = object.getObjectContent();
        InputStreamResource resource = new InputStreamResource(s3InputStream);

        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setContentDispositionFormData("attachment", metadata.get("fileName").toString());
        return new ResponseEntity<>(resource, responseHeaders, HttpStatus.OK);
    }
}