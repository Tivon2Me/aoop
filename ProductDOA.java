package com.smartstock.dao;

import com.smartstock.database.DBConnection;
import com.smartstock.model.Product;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ProductDAO {

    public boolean addProduct(Product product) {
        String sql = "INSERT INTO products (name, quantity, price) VALUES (?, ?, ?)";

        try (Connection connection = DBConnection.getConnection();
             PreparedStatement preparedStatement = connection.prepareStatement(sql)) {

            preparedStatement.setString(1, product.getName());
            preparedStatement.setInt(2, product.getQuantity());
            preparedStatement.setDouble(3, product.getPrice());

            int rowsAffected = preparedStatement.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

 
