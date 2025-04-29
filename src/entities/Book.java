package entities;

public class Book {
    private String title;
    private String author;
    private int bookId;
    private int quantity;

    public Book() {

    }

    public Book(String title, String author, int bookId, int quantity) {
        this.title = title;
        this.author = author;
        this.bookId = bookId;
        this.quantity = quantity;
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
}
