package cc.maids.librarymanagement.patron.service;

import cc.maids.librarymanagement.borrowingrecord.service.BorrowingRecordService;
import cc.maids.librarymanagement.patron.entity.Patron;
import cc.maids.librarymanagement.patron.exception.PatronCannotBeDeletedException;
import cc.maids.librarymanagement.patron.exception.PatronNotFoundException;
import cc.maids.librarymanagement.patron.repository.PatronRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PatronServiceImpl implements PatronService {

    private final PatronRepository patronRepository;
    private final BorrowingRecordService borrowingRecordService;

    @Override
    public List<Patron> getPatrons() {
        return patronRepository.findAll();
    }

    @Override
    public Patron getPatron(Long id) {
        return patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found with id: " + id));
    }

    @Override
    @Transactional
    public Patron createPatron(Patron patron) {
        return patronRepository.save(patron);
    }

    @Override
    @Transactional
    public Patron updatePatron(Long id, Patron updatedPatron) {
        Patron existingPatron = patronRepository.findById(id)
                .orElseThrow(() -> new PatronNotFoundException("Patron not found with id: " + id));

        existingPatron.setName(updatedPatron.getName());
        existingPatron.setEmail(updatedPatron.getEmail());
        existingPatron.setPhoneNumber(updatedPatron.getPhoneNumber());

        return patronRepository.save(existingPatron);
    }

    @Override
    @Transactional
    public void deletePatron(Long id) {
        if (borrowingRecordService.isPatronCurrentlyBorrowing(id)) {
            throw new PatronCannotBeDeletedException("Patron with id: " + id + " cannot be deleted because they currently have borrowed books");
        }
        patronRepository.deleteById(id);
    }


}
