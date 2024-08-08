package cc.maids.librarymanagement.book.mapper;

import cc.maids.librarymanagement.book.entity.Book;
import cc.maids.librarymanagement.book.request.CreateBookRequest;
import cc.maids.librarymanagement.book.request.UpdateBookRequest;
import cc.maids.librarymanagement.book.response.CreateBookResponse;
import cc.maids.librarymanagement.book.response.GetBookResponse;
import cc.maids.librarymanagement.book.response.UpdateBookResponse;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface BookMapper {

    CreateBookResponse bookToCreateBookResponse(Book book);

    List<CreateBookResponse> bookToCreateBookResponse(List<Book> book);

    GetBookResponse bookToGetBookResponse(Book book);

    List<GetBookResponse> bookToGetBookResponse(List<Book> book);

    Book createBookRequestToBook(CreateBookRequest createBookRequest);

    Book updateBookRequestToBook(UpdateBookRequest updateBookRequest);

    UpdateBookResponse bookToUpdateBookResponse(Book book);

}
