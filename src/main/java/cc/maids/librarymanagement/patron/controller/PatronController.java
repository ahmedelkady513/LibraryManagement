package cc.maids.librarymanagement.patron.controller;

import cc.maids.librarymanagement.patron.entity.Patron;
import cc.maids.librarymanagement.patron.mapper.PatronMapper;
import cc.maids.librarymanagement.patron.request.CreatePatronRequest;
import cc.maids.librarymanagement.patron.request.UpdatePatronRequest;
import cc.maids.librarymanagement.patron.response.CreatePatronResponse;
import cc.maids.librarymanagement.patron.response.GetPatronResponse;
import cc.maids.librarymanagement.patron.response.UpdatePatronResponse;
import cc.maids.librarymanagement.patron.service.PatronService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/patrons")
@RequiredArgsConstructor
public class PatronController {

    private final PatronService patronService;
    private final PatronMapper patronMapper;

    @GetMapping
    public ResponseEntity<List<GetPatronResponse>> getPatrons() {
        List<GetPatronResponse> createPatronResponse = patronMapper.patronToGetPatronResponse(patronService.getPatrons());
        return ResponseEntity.status(HttpStatus.OK).body(createPatronResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<GetPatronResponse> getPatron(@PathVariable Long id) {
        GetPatronResponse createPatronResponse = patronMapper.patronToGetPatronResponse(patronService.getPatron(id));
        return ResponseEntity.status(HttpStatus.OK).body(createPatronResponse);
    }

    @PostMapping
    public ResponseEntity<CreatePatronResponse> addPatron(@Valid @RequestBody CreatePatronRequest createPatronRequest) {
        Patron patron = patronService.createPatron(patronMapper.createPatronRequestToPatron(createPatronRequest));
        CreatePatronResponse createPatronResponse = patronMapper.patronToCreatePatronResponse(patron);
        return ResponseEntity.status(HttpStatus.CREATED).body(createPatronResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<UpdatePatronResponse> updatePatron(@PathVariable Long id, @Valid @RequestBody UpdatePatronRequest updatePatronRequest) {
        Patron updatePatron = patronService.updatePatron(id, patronMapper.updatePatronRequestToPatron(updatePatronRequest));
        UpdatePatronResponse createPatronResponse = patronMapper.patronToUpdatePatronResponse(updatePatron);
        return ResponseEntity.status(HttpStatus.OK).body(createPatronResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePatron(@PathVariable Long id) {
        patronService.deletePatron(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).body(null);
    }

}
