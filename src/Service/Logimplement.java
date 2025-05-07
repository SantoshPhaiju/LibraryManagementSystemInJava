package Service;

public interface Logimplement {
    public void deleteLog(String bookName);
    public void AddLog(String UserName,String bookName);
    public void issueLog(String bookName,String userName,String LibraryEmployeeName);
    public void returnLog(String userName,String bookName);
    public void  DisplayAllBookLog(String bookName) ;
    public void updateLog(String oldBookName,String bookName,int bookIs);

    public void displayIssuedBookLog();

    public void showLog();
}