package ojt.management.remote.business;

import com.amazonaws.services.s3.model.S3Object;
import ojt.management.data.entities.Attachment;
import org.springframework.web.multipart.MultipartFile;

public interface AmazonClientService {
    Attachment uploadFile(MultipartFile multipartFile, Long accountId);
    S3Object downloadFile(String key);
}
