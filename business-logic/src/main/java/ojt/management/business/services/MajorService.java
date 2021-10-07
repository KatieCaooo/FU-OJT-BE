package ojt.management.business.services;

import ojt.management.common.exceptions.MajorNameAlreadyExistedException;
import ojt.management.common.exceptions.MajorNotExistedException;
import ojt.management.data.entities.Major;

import java.util.List;

public interface MajorService {
    Major getMajorById(Long id) throws MajorNotExistedException;

    List<Major> searchMajor(String name);

    Major updateMajor(Long id, String name) throws MajorNotExistedException, MajorNameAlreadyExistedException;

    boolean deleteMajor(Long id) throws MajorNotExistedException;

    Major createMajor(String name) throws MajorNameAlreadyExistedException;
}
