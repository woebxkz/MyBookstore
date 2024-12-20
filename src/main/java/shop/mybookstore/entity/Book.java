package shop.mybookstore.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "books")
public class Book {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private String author;

    @Column(nullable = false)
    private double price;

    @Column(nullable = false)
    private Integer stock;

    @Column(nullable = false)
    private String category;

    @Column(name = "published_date", nullable = false)
    private LocalDate publishedDate;


    @Column(nullable = false)
    private String publisher;

    public Book(){}

    public Book(Long id,
                String title,
                String author,
                double price,
                Integer stock,
                String category,
                LocalDate publishedDate,
                String publisher) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.price = price;
        this.stock = stock;
        this.category = category;
        this.publishedDate = publishedDate;
        this.publisher = publisher;
    }

    @Override
    public String toString() {
        String newLine = System.lineSeparator();
        return "Book " + this.id + ":" + newLine +
                "  Title: " + this.title + newLine +
                "  Author: " + this.author + newLine +
                "  Price: " + this.price + newLine +
                "  Stock: " + this.stock + newLine +
                "  Category: " + this.category + newLine +
                "  Published Date: " + this.publishedDate + newLine +
                "  Publisher: " + this.publisher;
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public LocalDate getPublishedDate() {
        return publishedDate;
    }

    public void setPublishedDate(LocalDate publishedDate) {
        this.publishedDate = publishedDate;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
}
