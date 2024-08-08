package cc.maids.librarymanagement.patron.mapper;

import cc.maids.librarymanagement.patron.entity.Patron;
import cc.maids.librarymanagement.patron.request.CreatePatronRequest;
import cc.maids.librarymanagement.patron.request.UpdatePatronRequest;
import cc.maids.librarymanagement.patron.response.CreatePatronResponse;
import cc.maids.librarymanagement.patron.response.GetPatronResponse;
import cc.maids.librarymanagement.patron.response.UpdatePatronResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface PatronMapper {

    CreatePatronResponse patronToCreatePatronResponse(Patron patron);

    List<CreatePatronResponse> patronToCreatePatronResponse(List<Patron> patron);

    GetPatronResponse patronToGetPatronResponse(Patron patron);

    List<GetPatronResponse> patronToGetPatronResponse(List<Patron> patron);

    UpdatePatronResponse patronToUpdatePatronResponse(Patron patron);

    Patron createPatronRequestToPatron(CreatePatronRequest createPatronRequest);

    Patron updatePatronRequestToPatron(UpdatePatronRequest updatePatronRequest);


}
