package ojt.management.business.services;

import ojt.management.common.exceptions.SemesterAlreadyExistedException;
import ojt.management.common.exceptions.SemesterNotExistedException;
import ojt.management.common.payload.request.SemesterRequest;
import ojt.management.common.payload.request.SemesterUpdateRequest;
import ojt.management.data.entities.Semester;

import java.util.Date;
import java.util.List;

public interface SemesterService {
    Semester getById(Long id) throws SemesterNotExistedException;

    List<Semester> searchSemesters(String name, Date startDate, Date endDate);

    Semester updateSemester(SemesterUpdateRequest semesterUpdateRequest) throws SemesterAlreadyExistedException, SemesterNotExistedException;

    boolean deleteSemester(Long id) throws SemesterNotExistedException;

    Semester createSemester(SemesterRequest semesterRequest) throws SemesterAlreadyExistedException;
}
