package shop.mybookstore.model;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@Data
public class BookModel {

    private Long id;
    private String title;
    private String author;
    private Double price;
    private Integer stock;
    private String category;
    private LocalDate publishedDate;
    private String publisher;


    public BookModel(String title, String author, Double price, Integer stock, String category, LocalDate publishedDate, String publisher) {
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
    }
}
