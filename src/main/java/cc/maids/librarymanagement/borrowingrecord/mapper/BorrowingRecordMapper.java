package cc.maids.librarymanagement.borrowingrecord.mapper;

import cc.maids.librarymanagement.borrowingrecord.entity.BorrowingRecord;
import cc.maids.librarymanagement.borrowingrecord.request.BorrowBookRequest;
import cc.maids.librarymanagement.borrowingrecord.request.ReturnBookRequest;
import cc.maids.librarymanagement.borrowingrecord.response.BorrowBookResponse;
import cc.maids.librarymanagement.borrowingrecord.response.ReturnBookResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BorrowingRecordMapper {

    BorrowingRecord borrowBookRequestToBorrowingRecord(BorrowBookRequest borrowBookRequest);

    BorrowingRecord returnBookRequestToBorrowingRecord(ReturnBookRequest returnBookRequest);

    BorrowBookResponse borrowingRecordToBorrowBookResponse(BorrowingRecord borrowingRecord);

    ReturnBookResponse borrowingRecordToReturnBookResponse(BorrowingRecord borrowingRecord);
}
