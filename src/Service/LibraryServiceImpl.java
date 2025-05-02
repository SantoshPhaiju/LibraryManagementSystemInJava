package Service;

import entities.Book;
import entities.User;

import java.sql.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LibraryServiceImpl implements LibraryService {

    List<Book> books = new ArrayList<>();

    @Override
    public void addBook(Book book) {
        books.add(book);
    }

    @Override
    public Book updateBook(int index, Book book) {
        books.set(index, book);
        return book;
    }

    @Override
    public void deleteBook(String bookName) {
        Book bookToRemove = null;
        for (Book book : books) {
            if (book.getTitle().equals(bookName)) {
                bookToRemove = book;
                break;
            }
        }
        if (bookToRemove != null) {
            books.remove(bookToRemove);
            System.out.println("Book removed: " + bookName);
        } else {
            System.out.println("Book not found: " + bookName);
        }
    }


    @Override
    public Book getBook(String bookName) {
        Book book = null;
        for (Book book1 : books) {
            if (book1.getTitle().equals(bookName)) {
                book = book1;
            }
        }
        return book;
    }

    @Override
    public List<Book> fetchAllBooks() {
        return books;
    }

    @Override
    public boolean issueBook(String bookName, User user, String email) {
        Book newBook = null;
        for (Book book : books) {
            if (book.getTitle().equals(bookName)) {
                newBook = book;
                break;
            }
        }

        if (newBook == null) {
            System.out.println("Book not found: " + bookName);
            return false;
        } else {
            Map<String, List<String>> issuedList = newBook.getIssuedList();
            if (issuedList == null) {
                issuedList = new HashMap<>();
            }

            List<String> issuedToUsers = issuedList.getOrDefault(bookName, new ArrayList<>());

            if (issuedToUsers.contains(user.getUsername())) {
                System.out.println("User already issued this book.");
                return false;
            }

            issuedToUsers.add(user.getUsername());
//            issuedToUsers.add(user.getEmail());
            issuedList.put(bookName, issuedToUsers);
            newBook.setIssuedList(issuedList);

            System.out.println("Book Issued: " + newBook.getTitle() + " to user: " + user.getUsername() + " User Email: " + user.getEmail());
            return true;
        }
    }


    @Override
    public boolean returnBook(String bookName, User user) {
        for (Book book : books) {
            if (book.getTitle().equals(bookName)) {
                Map<String, List<String>> issuedList = book.getIssuedList();
                if (issuedList != null && issuedList.containsKey(bookName)) {
                    List<String> issuedUsers = issuedList.get(bookName);
                    if (issuedUsers.contains(user.getUsername())) {
                        issuedUsers.remove(user.getUsername());

                        if (issuedUsers.isEmpty()) {
                            issuedList.remove(bookName);
                        } else {
                            issuedList.put(bookName, issuedUsers);
                        }

                        book.setIssuedList(issuedList);
                        return true;
                    }
                }
                break;
            }
        }
        return false;
    }

    @Override
    public void displayAllBooks() {
        System.out.println("Books List: ");
        for (Book book : books) {
            System.out.println("BookId: " + book.getBookId() + " Title: " + book.getTitle() + " Quantity: " + book.getQuantity() + " Author: " + book.getAuthor());
        }
    }

    public void displayIssuedBook() {
        boolean anyIssued = false;
        System.out.println("Issued books list:");

        for (Book book : books) {
            Map<String, List<String>> issuedList = book.getIssuedList();
            if (issuedList != null && !issuedList.isEmpty()) {
                anyIssued = true;
                for (Map.Entry<String, List<String>> entry : issuedList.entrySet()) {
                    String bookName = entry.getKey();
                    List<String> issueDetails = entry.getValue();
                    List<String> emailsUsers = entry.getValue();
                    System.out.println("Books Name:-" + bookName + " UserName:-" + issueDetails + "User Email:-" + emailsUsers);
                }
            }
        }

        if (!anyIssued) {
            System.out.println("No books have been issued.");
        }
    }

    @Override
    public List<Book> deleteBooks(String bookName) {
        boolean found = false;

        for (int i = 0; i < books.size(); i++) {
            if (books.get(i).getTitle().equalsIgnoreCase(bookName)) {
                Book removedBook = books.remove(i);
                System.out.println("Removed book: " + removedBook);
                found = true;
                break;
            }
        }

        if (!found) {
            System.out.println("Book not found!");
        }

        return books;
    }

}

