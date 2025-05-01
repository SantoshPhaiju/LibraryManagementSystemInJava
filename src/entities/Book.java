package entities;

import java.util.List;
import java.util.Map;

public class Book {
    private String title;
    private String author;
    private int bookId;
    private int quantity;
    private Map<String, List<String>> issuedList;
    private boolean b;


    public Book() {

    }

    public Book(String title, String author, int bookId, int quantity) {
        this.title = title;
        this.author = author;
        this.bookId = bookId;
        this.quantity = quantity;
    }

    public Map<String, List<String>> getIssuedList() {
        return issuedList;
    }

    public void setIssuedList(Map<String, List<String>> issuedList) {
        this.issuedList = issuedList;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getBookId() {
        return bookId;
    }

    public void setBookId(int bookId) {
        this.bookId = bookId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public boolean isIssued() {
        return true;
    }

    public void setIssued(boolean b) {
        this.b = b;
    }
}
