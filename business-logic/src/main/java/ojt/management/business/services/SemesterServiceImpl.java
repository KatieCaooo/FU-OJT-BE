package ojt.management.business.services;

import ojt.management.common.exceptions.SemesterAlreadyExistedException;
import ojt.management.common.exceptions.SemesterNotExistedException;
import ojt.management.common.payload.request.SemesterRequest;
import ojt.management.common.payload.request.SemesterUpdateRequest;
import ojt.management.data.entities.Semester;
import ojt.management.data.repositories.SemesterRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SemesterServiceImpl implements SemesterService {

    private final SemesterRepository semesterRepository;

    public SemesterServiceImpl(SemesterRepository semesterRepository) {
        this.semesterRepository = semesterRepository;
    }

    @Override
    public Semester getById(Long id) throws SemesterNotExistedException {
        if (Boolean.FALSE.equals(semesterRepository.existsById(id))) {
            throw new SemesterNotExistedException();
        } else
            return semesterRepository.getById(id);
    }

    @Override
    public List<Semester> searchSemesters(String name, Date startDate, Date endDate) {
        if (name == null && startDate == null && endDate == null) {
            return semesterRepository.findAll();
        }
        return semesterRepository.searchSemester(name, startDate, endDate);
    }

    @Override
    public Semester updateSemester(SemesterUpdateRequest semesterUpdateRequest) throws SemesterAlreadyExistedException, SemesterNotExistedException {
        if (Boolean.FALSE.equals(semesterRepository.existsById(semesterUpdateRequest.getId()))) {
            throw new SemesterNotExistedException();
        } else if (Boolean.TRUE.equals(semesterRepository.existsByName(semesterUpdateRequest.getName()))
                || Boolean.TRUE.equals(semesterRepository.existsByStartDateAndEndDate(
                semesterUpdateRequest.getStartDate(),
                semesterUpdateRequest.getEndDate()))) {
            throw new SemesterAlreadyExistedException();
        } else {
            Semester semester = semesterRepository.getById(semesterUpdateRequest.getId());
            if (semester.isDisabled()) {
                throw new SemesterNotExistedException();
            } else {
                semester.setName(semesterUpdateRequest.getName());
                semester.setStartDate(semesterUpdateRequest.getStartDate());
                semester.setEndDate(semesterUpdateRequest.getEndDate());
                return semesterRepository.save(semester);
            }
        }
    }

    @Override
    public boolean deleteSemester(Long id) throws SemesterNotExistedException {
        if (Boolean.FALSE.equals(semesterRepository.existsById(id))) {
            throw new SemesterNotExistedException();
        } else {
            Semester semester = semesterRepository.getById(id);
            if (!semester.isDisabled()) {
                semester.setDisabled(true);
                semesterRepository.save(semester);
            }
            return true;
        }
    }

    @Override
    public Semester createSemester(SemesterRequest semesterRequest) throws SemesterAlreadyExistedException {
        if (Boolean.TRUE.equals(semesterRepository.existsByName(semesterRequest.getName()))
                || Boolean.TRUE.equals(semesterRepository.existsByStartDateAndEndDate(
                semesterRequest.getStartDate(),
                semesterRequest.getEndDate()))) {
            throw new SemesterAlreadyExistedException();
        } else {
            Semester semester = new Semester(semesterRequest.getName(),
                    semesterRequest.getStartDate(),
                    semesterRequest.getEndDate());
            return semesterRepository.save(semester);
        }
    }
}
