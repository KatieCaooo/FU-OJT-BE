package ojt.management.business.services;

import ojt.management.common.exceptions.MajorNameAlreadyExistedException;
import ojt.management.common.exceptions.MajorNotExistedException;
import ojt.management.data.entities.Major;
import ojt.management.data.repositories.MajorRepository;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
public class MajorServiceImpl implements MajorService{

    private final MajorRepository majorRepository;

    public MajorServiceImpl(MajorRepository majorRepository) {this.majorRepository = majorRepository;}

    @Override
    public Major getMajorById (Long id) throws MajorNotExistedException {
        if (Boolean.FALSE.equals(majorRepository.existsById(id))) {
            throw new MajorNotExistedException();
        } else
            return majorRepository.getById(id);
    }

    @Override
    public List<Major> searchMajor (String name) {
        if (name == null) {
            return majorRepository.findAll();
        }
        return  majorRepository.searchMajor(name);
    }

    @Override
    public Major updateMajor (Long id, String name) throws MajorNotExistedException, MajorNameAlreadyExistedException{
        if (Boolean.FALSE.equals(majorRepository.existsById(id))) {
            throw new MajorNotExistedException();
        } else if (Boolean.TRUE.equals(majorRepository.existsByName(name))) {
            throw new MajorNameAlreadyExistedException();
        } else {
            Major major = majorRepository.getById(id);
            if (major.isDisabled() == true) {
                throw new MajorNotExistedException();
            } else {
                if (name != null) {
                    major.setName(name);
                }
                majorRepository.save(major);
                return majorRepository.getById(id);
            }
        }
    }

    @Override
    public boolean deleteMajor (Long id) throws MajorNotExistedException {
        if (Boolean.FALSE.equals(majorRepository.existsById(id))) {
            throw new MajorNotExistedException();
        } else {
            Major major = majorRepository.getById(id);
            boolean response = false;
            if (major != null && major.isDisabled() == false) {
                major.setDisabled(true);
                majorRepository.save(major);
                response = true;
                return response;
            } else
                return response;
        }
    }

    @Override
    public Major createMajor (String name) throws MajorNameAlreadyExistedException {
        if (Boolean.TRUE.equals(majorRepository.existsByName(name))) {
            throw new MajorNameAlreadyExistedException();
        } else {
            Major major = new Major(name);
            majorRepository.save(major);
            return majorRepository.getById(major.getId());
        }
    }
}
