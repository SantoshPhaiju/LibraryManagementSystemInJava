package dao;

import Database.DatabaseConnection;
import entities.User;

import javax.xml.crypto.Data;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDaoImpl implements UserDAO {
    @Override
    public boolean createUser(User user) {
        try (Connection connection = DatabaseConnection.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement("INSERT INTO users (username, email) VALUES (?,?)")) {

            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());
            int rowsAffected = preparedStatement.executeUpdate();

            if (rowsAffected > 0) {
                System.out.println("User created successfully");
                return true;
            } else {
                System.out.println("User creation failed");
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return false;
    }

    @Override
    public void updateUser(User user) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement("UPDATE users SET username = ?, email = ? WHERE id = ?");
            preparedStatement.setString(1, user.getUsername());
            preparedStatement.setString(2, user.getEmail());

            int rowsAffected = preparedStatement.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("User updated successfully");
            } else {
                System.out.println("User update failed");
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public void deleteUser(User user) {

        try (Connection connection = DatabaseConnection.getConnection();) {
            String query = "DELETE FROM users WHERE id = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setInt(1, user.getId());
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    @Override
    public List<User> getAllUsers() {
        try (Connection connection = DatabaseConnection.getConnection();){
            String query = "SELECT * FROM users";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            ResultSet resultSet = preparedStatement.executeQuery();
            List<User> users = new ArrayList<>();
            while (resultSet.next()) {
                User newuser = new User(resultSet.getString("username"));
                newuser.setEmail(resultSet.getString("email"));
                newuser.setTimestamp(resultSet.getString("timestamp"));
                newuser.setId(resultSet.getInt("id"));
                users.add(newuser);
            }
            resultSet.close();
            preparedStatement.close();
            return users;
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

    @Override
    public User findUserByEmail(String email) {
        try (Connection connection = DatabaseConnection.getConnection()) {
            String query = "SELECT * FROM users WHERE email = ?";
            PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next()) {
                User user = new User(resultSet.getString("username")); // constructor with username
                user.setId(resultSet.getInt("id"));
                user.setEmail(resultSet.getString("email"));
                user.setTimestamp(resultSet.getString("timestamp"));
                return user;
            }
        } catch (ClassNotFoundException | SQLException e) {
            System.out.println(e.getMessage());
        }
        return null;
    }

}
