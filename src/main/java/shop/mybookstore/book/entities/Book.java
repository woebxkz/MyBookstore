package shop.mybookstore.book.entities;

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

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }
}
