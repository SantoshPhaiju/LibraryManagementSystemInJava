import Service.LibraryService;
import Service.LibraryServiceImpl;
import entities.Book;

import java.util.Scanner;

public class LibraryClass {
    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        System.out.println("""
                ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                |                                                                                                    |   
                ================================ Welcome to my Library Management System =============================
                |                                                                                                    |   
                ++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++++
                """);

        boolean quit = true;
        LibraryService newLibrary = new LibraryServiceImpl();
;
        while (quit) {
            System.out.println("""
                    Enter your choice:
                    1. Add a new book.
                    2. Show all books.
                    3. Show all issued books.
                    4. Delete a book.
                    5. Exit the program.
                    """);
            int choice = scanner.nextInt();
            switch (choice) {
                case 1:
                    System.out.println("Enter bookId: ");
                    int bookId = scanner.nextInt();
                    System.out.println("Enter book name: ");
                    String title = scanner.nextLine();
                    System.out.println("Enter author: ");
                    String author = scanner.next();
                    System.out.println("Enter quantity of the book: ");
                    int quantity = scanner.nextInt();
                    scanner.nextLine();

                    Book book = new Book(title, author, bookId, quantity);


            }
        }


    }
}
