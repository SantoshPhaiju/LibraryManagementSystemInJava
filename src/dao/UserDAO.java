package dao;

import entities.User;

public interface UserDAO {
    public void createUser(User user);
    public void updateUser(User user);
    public void deleteUser(User user);
    public User findUserByEmail(String email);
}
