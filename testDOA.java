import com.smartstock.model.Product;

import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        ProductDAO productDAO = new ProductDAO();

        Product product = new Product(0, "Keyboard", 8, 1800.00);
        boolean inserted = productDAO.addProduct(product);
        System.out.println("Inserted: " + inserted);

        List<Product> products = productDAO.getAllProducts();
        for (Product p : products) {
            p.displayInfo();
        }

        boolean updated = productDAO.updateStock(1, 0);
        System.out.println("Updated: " + updated);
    }
}
