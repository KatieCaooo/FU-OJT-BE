package ojt.management.business.services;

import ojt.management.common.exceptions.SemesterAlreadyExistedException;
import ojt.management.common.exceptions.SemesterDisabledException;
import ojt.management.common.exceptions.SemesterNotExistedException;
import ojt.management.common.payload.request.SemesterRequest;
import ojt.management.data.entities.Semester;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

public interface SemesterService {
    Semester getById(Long id) throws SemesterNotExistedException;

    Page<Semester> searchSemester(Specification<Semester> specification, Pageable pageable);

    Semester updateSemester(Long id, SemesterRequest semesterUpdateRequest)
            throws SemesterAlreadyExistedException, SemesterNotExistedException;

    boolean deleteSemester(Long id) throws SemesterNotExistedException, SemesterDisabledException;

    Semester createSemester(SemesterRequest semesterRequest) throws SemesterAlreadyExistedException;
}
