package Service;

import entities.Book;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class LogImplementImp implements Logimplement {
    List<Book> books = new ArrayList<>() ;
    @Override
    public void deleteLog(String bookName) {
        boolean removeVar = false;
        for(int i =0;i< books.size();i++){
            if(books.get(i).getTitle().equalsIgnoreCase(bookName)){
                books.remove(i);
                removeVar = true;
                break;
            }
        }
        if(!removeVar) {
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = date.format(formatter);
            String datalog = new String("Delete book Name " + bookName.toUpperCase() + " at " + timestamp);
            files.WriteLog(datalog);
        }else {
            System.out.println("Book is not found!!");
        }
    }

    FileHandlingServices files = new FileHandlingServices();
    @Override
    public void AddLog(String userName,String bookName){
//        LocalDateTime data = LocalDateTime.now();
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String timestamp = data.format(formatter);
//        String logData = new String(userName.toUpperCase() + " added the book: "+bookName+" at "+timestamp);
//        files.WriteLog(logData);

        boolean remove = false;
        for(int i =0;i< books.size();i++){
            if(books.get(i).getTitle().equalsIgnoreCase(bookName)){
                books.remove(i);
                remove = true;
                break;
            }
        }
        if(!remove) {
            LocalDateTime date = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String timestamp = date.format(formatter);
            String datalog = new String("Delete book Name " + bookName.toUpperCase() + " at " + timestamp);
            files.WriteLog(datalog);
        }else {
            System.out.println("Book is not found!!");
        }
    }

    @Override
    public void issueLog(String bookName,String userName,String LibraryEmployeeName) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = date.format(formatter);
        String logData = new String(LibraryEmployeeName+ " issued the book: "+bookName+ " to "+userName+ " at "+timestamp);
        files.WriteLog(logData);
    }

    @Override
    public void returnLog(String userName,String bookName) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyy-MM-dd HH:mm:ss");
        String timestamp = date.format(formatter);
        String logData = new String(userName+" returned the book "+bookName+" at "+timestamp);
        files.WriteLog(logData);
    }

    @Override
    public void DisplayAllBookLog(String username) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = date.format(formatter);
        String logdata = username + " viewed all books" + " at " + timestamp;
        files.WriteLog(logdata);
    }

    @Override
    public void updateLog(String oldBookName, String newBookName, int bookId) {
        LocalDateTime date = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String timestamp = date.format(formatter);
        String logData = "Updated book from \"" + oldBookName.toUpperCase() + "\" to \"" + newBookName.toUpperCase() + "\" (Book ID: " + bookId + ") at " + timestamp;
        files.WriteLog(logData);
    }

    @Override
    public void displayIssuedBookLog() {
        try(BufferedReader br = new BufferedReader(new FileReader("log.data"))){
            boolean found = false;
            String line;
            while ((line = br.readLine()) != null){
                if(line.toUpperCase().contains("Issued")){
                    System.out.println(line);
                    found = true;
                }
            }
            if(!found){
                System.out.println("Issued Book is not found!!");
            }
            br.close();
        }catch (IOException e){
            System.out.println("Message:- " + e.getMessage());
        }
    }

    @Override
    public void showLog() {
        files.ReadLog();
    }
}