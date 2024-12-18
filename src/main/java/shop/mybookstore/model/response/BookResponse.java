package shop.mybookstore.model.response;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class BookResponse {

    private Long id;
    private String title;
    private String category;
    private double price;
    private Integer stock;
    private LocalDate publishedDate;
    private String publisher;

    public BookResponse(Long id,
                        String title,
                        String category,
                        double price,
                        Integer stock,
                        LocalDate publishedDate,
                        String publisher) {
        this.id = id;
        this.title = title;
        this.category = category;
        this.price = price;
        this.stock = stock;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
    }
}
