package entities;

import java.util.List;
import java.util.Map;

public class Book {
    private String title;
    private String author;
    private int bookId;
    private int quantity;
    private Map<String, List<String>> issuedList;
    private boolean isIssued;


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
        return this.isIssued;
    }

    public void setIssued(boolean b) {
        this.isIssued = b;
    }

    @Override
    public String toString() {
        return title + " by " + author + (isIssued ? " (Issued)" : " (Available)");
    }

    public String toFileString() {
        return bookId + "," + title + "," + author + "," + quantity;
    }

    public static Book fromFileString(String line) {
        String[] parts = line.trim().split("\\s*,\\s*");
        return new Book(parts[1], parts[2], Integer.parseInt(parts[0]), Integer.parseInt(parts[3]));
    }

    public void displayBookDetails() {
        System.out.println("BookId: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        System.out.println("Quantity: " + quantity);
        System.out.println("Issued: " + isIssued);
    }
}
