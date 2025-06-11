package dao;

import entities.User;

import java.sql.SQLException;

public interface UserDAO {
    public boolean createUser(User user) throws SQLException;
    public void updateUser(User user);
    public void deleteUser(User user);
    public User findUserByEmail(String email);
}
