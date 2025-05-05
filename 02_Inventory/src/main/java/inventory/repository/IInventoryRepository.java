package inventory.repository;

import inventory.model.Part;
import inventory.model.Product;
import javafx.collections.ObservableList;

public interface IInventoryRepository {
    // Product-related methods
    void addProduct(Product product);
    void removeProduct(Product product);
    Product lookupProduct(String searchItem);
    void updateProduct(int index, Product product);
    ObservableList<Product> getProducts();
    void setProducts(ObservableList<Product> list);

    // Part-related methods
    void addPart(Part part);
    void removePart(Part part);
    Part lookupPart(String searchItem);
    void updatePart(int index, Part part);
    ObservableList<Part> getAllParts();
    void setAllParts(ObservableList<Part> list);

    // ID generation methods
    int getAutoPartId();
    int getAutoProductId();
    void setAutoPartId(int id);
    void setAutoProductId(int id);
}
