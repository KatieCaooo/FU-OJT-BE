package ojt.management.business.services;

import ojt.management.common.exceptions.SemesterAlreadyExistedException;
import ojt.management.common.exceptions.SemesterNotExistedException;
import ojt.management.data.entities.Semester;
import ojt.management.data.repositories.SemesterRepository;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Service
public class SemesterServiceImpl implements SemesterService{

    private final SemesterRepository semesterRepository;

    public SemesterServiceImpl(SemesterRepository semesterRepository) { this.semesterRepository = semesterRepository;}

    @Override
    public Semester getById(Long id) throws SemesterNotExistedException {
        if (Boolean.FALSE.equals(semesterRepository.existsById(id))) {
            throw new SemesterNotExistedException();
        } else
            return semesterRepository.getById(id);
    }

    @Override
    public List<Semester> searchSemesters(String name, Date startDate, Date endDate) {
        if (name == null & startDate == null & endDate == null) {
            return semesterRepository.findAll();
        }
        return  semesterRepository.searchSemester(name, startDate, endDate);
    }

    @Override
    public Semester updateSemester(Long id, String name, Date startDate, Date endDate) throws SemesterAlreadyExistedException, SemesterNotExistedException {
        if (Boolean.FALSE.equals(semesterRepository.existsById(id))) {
            throw new SemesterNotExistedException();
        } else if (Boolean.TRUE.equals(semesterRepository.existsByName(name)) || Boolean.TRUE.equals(semesterRepository.existsByStartDateAndEndDate(startDate, endDate))) {
            throw new SemesterAlreadyExistedException();
        } else {
            Semester semester = semesterRepository.getById(id);
            if (semester.isDisabled() == true) {
                throw new SemesterNotExistedException();
            } else {
                if (name != null) {
                    semester.setName(name);
                }
                if (startDate != null) {
                    semester.setStartDate(startDate);
                }
                if (endDate != null) {
                    semester.setEndDate(endDate);
                }
                semesterRepository.save(semester);
                return semesterRepository.getById(id);
            }
        }
    }

    @Override
    public boolean deleteSemester(Long id) throws SemesterNotExistedException{
        if (Boolean.FALSE.equals(semesterRepository.existsById(id))) {
            throw new SemesterNotExistedException();
        } else {
            Semester semester = semesterRepository.getById(id);
            boolean response = false;
            if (semester != null & semester.isDisabled() == false) {
                semester.setDisabled(true);
                semesterRepository.save(semester);
                response = true;
                return response;
            }
            return response;
        }
    }

    @Override
    public Semester createSemester(String name, Date startDate, Date endDate) throws SemesterAlreadyExistedException{
        if (Boolean.TRUE.equals(semesterRepository.existsByName(name)) || Boolean.TRUE.equals(semesterRepository.existsByStartDateAndEndDate(startDate, endDate))) {
            throw new SemesterAlreadyExistedException();
        } else {
            Semester semester = new Semester(name, startDate, endDate);
            semesterRepository.save(semester);
            return semesterRepository.getById(semester.getId());
        }

    }
}
