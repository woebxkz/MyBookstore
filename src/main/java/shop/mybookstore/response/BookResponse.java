package shop.mybookstore.response;

import lombok.*;
import shop.mybookstore.model.BookModel;

import java.util.List;

@Getter
@Setter
@Builder
public class BookResponse {

    private List<BookModel> books;

    public BookResponse(List<BookModel> books) {
        this.books = books;
    }

}
