package cc.maids.librarymanagement.patron.service;

import cc.maids.librarymanagement.patron.entity.Patron;
import cc.maids.librarymanagement.patron.exception.PatronCannotBeDeletedException;
import cc.maids.librarymanagement.patron.exception.PatronNotFoundException;

import java.util.List;

/**
 * Service interface for managing patrons.
 * <p>
 * This interface defines methods for creating, retrieving, updating, and deleting patron records.
 */
public interface PatronService {

    /**
     * Creates a new patron in the system.
     *
     * @param patron The Patron object containing the details of the patron to be created.
     * @return The created  Patron object with an assigned ID.
     */
    Patron createPatron(Patron patron);

    /**
     * Retrieves a list of all patrons in the system.
     *
     * @return A list of all patrons in the system.
     */
    List<Patron> getPatrons();

    /**
     * Retrieves a specific patron by ID.
     *
     * @param id The ID of the patron to retrieve.
     * @return The Patron object with the specified ID.
     * @throws PatronNotFoundException If no patron with the specified ID exists in the system.
     */
    Patron getPatron(Long id);

    /**
     * Updates the details of a patron in the system.
     *
     * @param id     The ID of the patron to update.
     * @param patron The Patron object containing the new details for the patron.
     * @return The updated Patron object.
     * @throws PatronNotFoundException If no patron with the specified ID exists in the system.
     */
    Patron updatePatron(Long id, Patron patron);

    /**
     * Deletes a patron from the system.
     *
     * @param id The ID of the patron to delete.
     * @throws PatronCannotBeDeletedException If the patron cannot be deleted (e.g., because they currently have borrowed books).
     */
    void deletePatron(Long id);

}
