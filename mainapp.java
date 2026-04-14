package com.smartstock.ui;

import com.smartstock.dao.ProductDAO;
import com.smartstock.model.Product;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class MainApp extends Application {

    private final ProductDAO productDAO = new ProductDAO();
    private final TableView<Product> tableView = new TableView<>();
    private final ObservableList<Product> productList = FXCollections.observableArrayList();

    @Override
    public void start(Stage stage) {
        Label titleLabel = new Label("SmartStock Inventory Management System");

        TextField nameField = new TextField();
        nameField.setPromptText("Product Name");

        TextField quantityField = new TextField();
        quantityField.setPromptText("Quantity");

        TextField priceField = new TextField();
        priceField.setPromptText("Price");

        Button addButton = new Button("Add Product");
        Button viewButton = new Button("View Products");

        TextField updateIdField = new TextField();
        updateIdField.setPromptText("Product ID");

        TextField updateQuantityField = new TextField();
        updateQuantityField.setPromptText("New Quantity");

        Button updateButton = new Button("Update Stock");

        TextField deleteIdField = new TextField();
        deleteIdField.setPromptText("Product ID to Delete");

        Button deleteButton = new Button("Delete Product");

        TableColumn<Product, Integer> idColumn = new TableColumn<>("ID");
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Product, String> nameColumn = new TableColumn<>("Product Name");
        nameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));

        TableColumn<Product, Integer> quantityColumn = new TableColumn<>("Quantity");
        quantityColumn.setCellValueFactory(new PropertyValueFactory<>("quantity"));

        TableColumn<Product, Double> priceColumn = new TableColumn<>("Price");
        priceColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        tableView.getColumns().addAll(idColumn, nameColumn, quantityColumn, priceColumn);
        tableView.setItems(productList);
        tableView.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY_ALL_COLUMNS);

        tableView.setRowFactory(tv -> new TableRow<>() {
            @Override
            protected void updateItem(Product product, boolean empty) {
                super.updateItem(product, empty);

                if (empty || product == null) {
                    setStyle("");
                } else if (product.getQuantity() == 0) {
                    setStyle("-fx-background-color: #ffdddd;");
                } else {
                    setStyle("");
                }
            }
        });

        addButton.setOnAction(e -> {
            try {
                String name = nameField.getText().trim();
                int quantity = Integer.parseInt(quantityField.getText().trim());
                double price = Double.parseDouble(priceField.getText().trim());

                if (name.isEmpty()) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Product name cannot be empty.");
                    return;
                }

                if (quantity < 0) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Quantity cannot be negative.");
                    return;
                }

                if (price < 0) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Price cannot be negative.");
                    return;
                }

                Product product = new Product(0, name, quantity, price);
                boolean success = productDAO.addProduct(product);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Product added successfully.");
                    clearFields(nameField, quantityField, priceField);
                    loadProducts();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Database Error", "Failed to add product.");
                }

            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "Quantity must be a whole number and price must be numeric.");
            }
        });

        viewButton.setOnAction(e -> loadProducts());

        updateButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(updateIdField.getText().trim());
                int newQuantity = Integer.parseInt(updateQuantityField.getText().trim());

                if (newQuantity < 0) {
                    showAlert(Alert.AlertType.ERROR, "Validation Error", "Quantity cannot be negative.");
                    return;
                }

                boolean success = productDAO.updateStock(id, newQuantity);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Stock updated successfully.");
                    clearFields(updateIdField, updateQuantityField);
                    loadProducts();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Update Error", "No product found with that ID.");
                }

            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "ID and quantity must be whole numbers.");
            }
        });
        deleteButton.setOnAction(e -> {
            try {
                int id = Integer.parseInt(deleteIdField.getText().trim());

                boolean success = productDAO.deleteProduct(id);

                if (success) {
                    showAlert(Alert.AlertType.INFORMATION, "Success", "Product deleted successfully.");
                    clearFields(deleteIdField);
                    loadProducts();
                } else {
                    showAlert(Alert.AlertType.ERROR, "Delete Error", "No product found with that ID.");
                }

            } catch (NumberFormatException ex) {
                showAlert(Alert.AlertType.ERROR, "Input Error", "ID must be a number.");
            }
        });

        VBox addSection = new VBox(10,
                new Label("Add Product"),
                nameField,
                quantityField,
                priceField,
                addButton
        );

        VBox updateSection = new VBox(10,
                new Label("Update Stock"),
                updateIdField,
                updateQuantityField,
                updateButton
        );
        VBox deleteSection = new VBox(10,
                new Label("Delete Product"),
                deleteIdField,
                deleteButton
        );

        HBox formSection = new HBox(20, addSection, updateSection, deleteSection);

        VBox root = new VBox(15, titleLabel, formSection, viewButton, tableView);

        root.setPadding(new Insets(15));

        loadProducts();

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/style.css").toExternalForm());
        stage.setTitle("SmartStock");
        stage.setScene(scene);
        stage.show();
    }

    private void loadProducts() {
        productList.clear();
        productList.addAll(productDAO.getAllProducts());
    }

    private void clearFields(TextField... fields) {
        for (TextField field : fields) {
            field.clear();
        }
    }

    private void showAlert(Alert.AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    public static void main(String[] args) {
        launch();
    }
}
