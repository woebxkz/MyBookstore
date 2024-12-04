package shop.mybookstore.book;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "authors")
public class Author {

    @Id
    private int id;
    private String name;

    public Author(){
    }


    public Author(int id, String name) {
    }


    public int getId() {
        return id;
    }
}
