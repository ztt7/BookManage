package book.manage.entity;

import lombok.Data;

@Data
public class Book {
    int bid;
    String title;
    String desc;
    double price;

    public Book() {
    }

    public Book(String title, String desc, double price) {
        this.title = title;
        this.desc = desc;
        this.price = price;
    }

}
