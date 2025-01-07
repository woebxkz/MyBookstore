package shop.mybookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "book_id")
    @JsonIgnore
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(nullable = false)
    @JsonIgnore
    private Integer stock;

    @Column(nullable = false)
    private String category;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;

    @Column(nullable = false)
    private String publisher;

    public Book(String title, String author, BigDecimal price, String category, LocalDate publishedDate, String publisher){
        this.title = title;
        this.author = author;
        this.price = price;
        this.category = category;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
    }

}
