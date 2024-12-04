package shop.mybookstore.book;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "books")
public class Book {

    @Id
    private Long id;
    private int isbn;
    private String title;
    private String author;
    private double price;
    private int stock;


    public Long getId() {
        return id;
    }
}
