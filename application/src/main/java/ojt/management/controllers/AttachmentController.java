package ojt.management.controllers;

import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import ojt.management.business.services.AttachmentService;
import ojt.management.common.exceptions.AccountIdNotExistedException;
import ojt.management.common.exceptions.ApplicationNotExistedException;
import ojt.management.common.exceptions.AttachmentAlreadyExistedException;
import ojt.management.common.exceptions.AttachmentNotExistedException;
import ojt.management.common.payload.dto.AttachmentDTO;
import ojt.management.common.payload.request.AttachmentRequest;
import ojt.management.configuration.security.services.UserDetailsImpl;
import ojt.management.mappers.AttachmentMapper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/attachments")
@SecurityRequirement(name = "bearerAuth")
public class AttachmentController {

    private final AttachmentService attachmentService;
    private final AttachmentMapper attachmentMapper;

    public AttachmentController(AttachmentService attachmentService,
                                AttachmentMapper attachmentMapper){
        this.attachmentMapper = attachmentMapper;
        this.attachmentService = attachmentService;
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'COMPANY_REPRESENTATIVE', 'STUDENT')")
    @GetMapping("/{id}")
    public AttachmentDTO getAttachmentByApplicationId(@PathVariable String key,
                                                      Authentication authentication) 
            throws AttachmentNotExistedException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return attachmentMapper.attachmentToAttachDTO(attachmentService.getAttachmentByKey(key, accountId));
    }

    @PreAuthorize("hasAnyAuthority('SYS_ADMIN', 'COMPANY_REPRESENTATIVE', 'STUDENT')")
    @PostMapping("/{id}")
    public AttachmentDTO createAttachment(@Valid @RequestBody AttachmentRequest attachmentRequest,
                                          Authentication authentication)
            throws AttachmentAlreadyExistedException, ApplicationNotExistedException, AccountIdNotExistedException {
        Long accountId = ((UserDetailsImpl) authentication.getPrincipal()).getId();
        return attachmentMapper.attachmentToAttachDTO(attachmentService.createAttachment(attachmentRequest, accountId));
    }
}
