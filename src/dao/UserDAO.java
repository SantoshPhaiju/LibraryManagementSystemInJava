package dao;

import entities.User;

import java.sql.SQLException;
import java.util.List;

public interface UserDAO {
    public boolean createUser(User user) throws SQLException;
    public void updateUser(User user);
//    public void deleteUser(int id);
    public User findUserByEmail(String email);
    public List<User> getAllUsers();
}
