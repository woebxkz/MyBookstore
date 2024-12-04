package shop.mybookstore.book;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "publishers")
public class Publisher {

    @Id
    private int id;
    private String name;


    public Publisher() {
    }

    public Publisher(int id, String name) {
    }

    public int getId() {
        return id;
    }
}
