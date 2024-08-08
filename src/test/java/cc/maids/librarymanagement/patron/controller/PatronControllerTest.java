package cc.maids.librarymanagement.patron.controller;

import cc.maids.librarymanagement.patron.entity.Patron;
import cc.maids.librarymanagement.patron.exception.PatronNotFoundException;
import cc.maids.librarymanagement.patron.mapper.PatronMapper;
import cc.maids.librarymanagement.patron.request.CreatePatronRequest;
import cc.maids.librarymanagement.patron.request.UpdatePatronRequest;
import cc.maids.librarymanagement.patron.response.CreatePatronResponse;
import cc.maids.librarymanagement.patron.response.GetPatronResponse;
import cc.maids.librarymanagement.patron.response.UpdatePatronResponse;
import cc.maids.librarymanagement.patron.service.PatronService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(PatronController.class)
@AutoConfigureMockMvc(addFilters = false)
class PatronControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PatronService patronService;

    @MockBean
    private PatronMapper patronMapper;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void getPatrons_ShouldReturnListOfGetPatronResponses() throws Exception {
        List<Patron> patrons = Arrays.asList(
                new Patron(1L, "John Doe", "john@example.com", "+1234567890"),
                new Patron(2L, "Jane Doe", "jane@example.com", "+0987654321")
        );
        List<GetPatronResponse> getPatronResponses = Arrays.asList(
                new GetPatronResponse(1L, "John Doe", "john@example.com", "+1234567890"),
                new GetPatronResponse(2L, "Jane Doe", "jane@example.com", "+0987654321")
        );

        when(patronService.getPatrons()).thenReturn(patrons);
        when(patronMapper.patronToGetPatronResponse(patrons)).thenReturn(getPatronResponses);

        mockMvc.perform(get("/patrons"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.length()").value(2)) // Check array length
                .andExpect(jsonPath("$[0].id").value(1))
                .andExpect(jsonPath("$[0].name").value("John Doe"))
                .andExpect(jsonPath("$[0].email").value("john@example.com"))
                .andExpect(jsonPath("$[0].phoneNumber").value("+1234567890"))
                .andExpect(jsonPath("$[1].id").value(2))
                .andExpect(jsonPath("$[1].name").value("Jane Doe"))
                .andExpect(jsonPath("$[1].email").value("jane@example.com"))
                .andExpect(jsonPath("$[1].phoneNumber").value("+0987654321"));

        verify(patronService).getPatrons();
        verify(patronMapper).patronToGetPatronResponse(patrons);
    }

    @Test
    void getPatron_ShouldReturnGetPatronResponse() throws Exception {
        Long id = 1L;
        Patron patron = new Patron(id, "John Doe", "john@example.com", "+1234567890");
        GetPatronResponse getPatronResponse = new GetPatronResponse(id, "John Doe", "john@example.com", "+1234567890");

        when(patronService.getPatron(id)).thenReturn(patron);
        when(patronMapper.patronToGetPatronResponse(patron)).thenReturn(getPatronResponse);

        mockMvc.perform(get("/patrons/{id}", id))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("+1234567890"));

        verify(patronService).getPatron(id);
        verify(patronMapper).patronToGetPatronResponse(patron);
    }

    @Test
    void addPatron_ShouldCreateAndReturnCreatedPatronResponse() throws Exception {
        CreatePatronRequest createPatronRequest = new CreatePatronRequest("John Doe", "john@example.com", "+1234567890");
        Patron patron = new Patron(null, "John Doe", "john@example.com", "+1234567890");
        Patron createdPatron = new Patron(1L, "John Doe", "john@example.com", "+1234567890");
        CreatePatronResponse createPatronResponse = new CreatePatronResponse(1L, "John Doe", "john@example.com", "+1234567890");

        when(patronMapper.createPatronRequestToPatron(any(CreatePatronRequest.class))).thenReturn(patron);
        when(patronService.createPatron(patron)).thenReturn(createdPatron);
        when(patronMapper.patronToCreatePatronResponse(createdPatron)).thenReturn(createPatronResponse);

        mockMvc.perform(post("/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createPatronRequest)))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Doe"))
                .andExpect(jsonPath("$.email").value("john@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("+1234567890"));

        verify(patronMapper).createPatronRequestToPatron(any(CreatePatronRequest.class));
        verify(patronService).createPatron(patron);
        verify(patronMapper).patronToCreatePatronResponse(createdPatron);
    }

    @Test
    void addPatron_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        CreatePatronRequest request = new CreatePatronRequest("", "invalid-email", "123");

        mockMvc.perform(post("/patrons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }
    @Test
    void updatePatron_ShouldUpdateAndReturnUpdatePatronResponse() throws Exception {
        Long id = 1L;
        UpdatePatronRequest updatePatronRequest = new UpdatePatronRequest("John Updated", "john.updated@example.com", "+9876543210");
        Patron patronUpdateData = new Patron(null, "John Doe", "john@example.com", "+1234567890");
        Patron updatedPatron = new Patron(id, "John Updated", "john.updated@example.com", "+9876543210");
        UpdatePatronResponse updatePatronResponse = new UpdatePatronResponse(id, "John Updated", "john.updated@example.com", "+9876543210");

        when(patronMapper.updatePatronRequestToPatron(any(UpdatePatronRequest.class))).thenReturn(patronUpdateData);
        when(patronService.updatePatron(id, patronUpdateData)).thenReturn(updatedPatron);
        when(patronMapper.patronToUpdatePatronResponse(updatedPatron)).thenReturn(updatePatronResponse);

        mockMvc.perform(put("/patrons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePatronRequest)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John Updated"))
                .andExpect(jsonPath("$.email").value("john.updated@example.com"))
                .andExpect(jsonPath("$.phoneNumber").value("+9876543210"));

        verify(patronMapper).updatePatronRequestToPatron(any(UpdatePatronRequest.class));
        verify(patronService).updatePatron(id, patronUpdateData);
        verify(patronMapper).patronToUpdatePatronResponse(updatedPatron);
    }

    @Test
    void updatePatron_whenPatronNotFound_shouldReturnNotFound() throws Exception {
        Long id = 1L;
        UpdatePatronRequest updatePatronRequest = new UpdatePatronRequest("John Updated", "john.updated@example.com", "+9876543210");
        Patron patronUpdateData = new Patron(null, "John Doe", "john@example.com", "+1234567890");

        when(patronMapper.updatePatronRequestToPatron(any(UpdatePatronRequest.class))).thenReturn(patronUpdateData);
        when(patronService.updatePatron(id, patronUpdateData)).thenThrow(new PatronNotFoundException("Patron not found with id: " + id));

        mockMvc.perform(put("/patrons/{id}", id)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updatePatronRequest)))
                .andExpect(status().isNotFound());

        verify(patronMapper).updatePatronRequestToPatron(any(UpdatePatronRequest.class));
        verify(patronService).updatePatron(id, patronUpdateData);
    }

    @Test
    void updatePatron_WithInvalidData_ShouldReturnBadRequest() throws Exception {
        UpdatePatronRequest request = new UpdatePatronRequest("", "invalid-email", "123");

        mockMvc.perform(put("/patrons/{id}", 1L)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(request)))
                .andExpect(status().isBadRequest());

    }

    @Test
    void deletePatron_ShouldReturnOkStatus() throws Exception {
        Long id = 1L;

        mockMvc.perform(delete("/patrons/{id}", id))
                .andExpect(status().isNoContent());

        verify(patronService).deletePatron(id);
    }


}
