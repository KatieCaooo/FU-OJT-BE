package ojt.management.remote.business;

import org.springframework.web.multipart.MultipartFile;

public interface AmazonClientService {
    String uploadFile(MultipartFile multipartFile);
}
