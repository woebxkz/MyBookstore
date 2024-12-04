package shop.mybookstore.book;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "categories")
public class Category {

    @Id
    private int id;
    private String name;

    public Category() {
    }

    public Category(int id, String name) {
    }


    public int getId() {
        return id;
    }
}
