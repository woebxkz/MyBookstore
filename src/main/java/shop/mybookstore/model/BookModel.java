package shop.mybookstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BookModel {

    private String title;
    private String author;
    private double price;
    private Integer stock;
    private String category;
    private LocalDate publishedDate;
    private String publisher;

}
