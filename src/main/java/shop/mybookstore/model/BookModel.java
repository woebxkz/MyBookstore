package shop.mybookstore.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookModel {

    private Long id;
    private String title;
    private String author;
    private String category;
    private BigDecimal price;
    private Integer stock;
    private LocalDate publishedDate;
    private String publisher;

}
