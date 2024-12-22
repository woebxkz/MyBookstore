package shop.mybookstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
public class BookModel {

    private String title;
    private String author;
    private BigDecimal price;
    private Integer stock;
    private String category;
    private LocalDate publishedDate;
    private String publisher;

}
