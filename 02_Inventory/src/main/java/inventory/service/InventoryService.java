package inventory.service;

import inventory.model.*;
import inventory.repository.IInventoryRepository;
import inventory.repository.InventoryRepository;
import javafx.collections.ObservableList;

import java.security.InvalidParameterException;
import java.util.List;

public class InventoryService {

    private IInventoryRepository repo;
    public InventoryService(IInventoryRepository repo){
        this.repo =repo;
    }

    public void addInhousePart(String name, double price, int inStock, int min, int  max, int partDynamicValue){
        String errorMessageProduct=Part.isValidPart(name, price, inStock, min, max,"");
        if (errorMessageProduct.length()>0)
            throw new InvalidParameterException(errorMessageProduct);
        InhousePart inhousePart = new InhousePart(repo.getAutoPartId(), name, price, inStock, min, max, partDynamicValue);

        repo.addPart(inhousePart);
    }
    public void addOutsourcePart(String name, double price, int inStock, int min, int  max, String partDynamicValue){
        String errorMessageProduct=Part.isValidPart(name, price, inStock, min, max,"");
        if (errorMessageProduct.length()>0)
            throw new InvalidParameterException(errorMessageProduct);

        OutsourcedPart outsourcedPart = new OutsourcedPart(repo.getAutoPartId(), name, price, inStock, min, max, partDynamicValue);
        repo.addPart(outsourcedPart);
    }

    public void addProduct(String name, double price, int inStock, int min, int  max, ObservableList<Part> addParts){
        String errorMessageProduct=Product.isValidProduct(name, price, inStock, min, max,addParts,"");
        if (errorMessageProduct.length()>0)
            throw new InvalidParameterException(errorMessageProduct);
        Product product = new Product(repo.getAutoProductId(), name, price, inStock, min, max, addParts);
        repo.addProduct(product);
    }

    public ObservableList<Part> getAllParts() {
        return repo.getAllParts();
    }

    public ObservableList<Product> getAllProducts() {
        return repo.getProducts();
    }

    public Part lookupPart(String search) {
        return repo.lookupPart(search);
    }

    public Product lookupProduct(String search) {
        return repo.lookupProduct(search);
    }

    public void updateInhousePart(int partIndex, int partId, String name, double price, int inStock, int min, int max, int partDynamicValue){
        InhousePart inhousePart = new InhousePart(partId, name, price, inStock, min, max, partDynamicValue);
        repo.updatePart(partIndex, inhousePart);
    }

    public void updateOutsourcedPart(int partIndex, int partId, String name, double price, int inStock, int min, int max, String partDynamicValue){
        OutsourcedPart outsourcedPart = new OutsourcedPart(partId, name, price, inStock, min, max, partDynamicValue);
        repo.updatePart(partIndex, outsourcedPart);
    }

    public void updateProduct(int productIndex, int productId, String name, double price, int inStock, int min, int max, ObservableList<Part> addParts){
        Product product = new Product(productId, name, price, inStock, min, max, addParts);
        repo.updateProduct(productIndex, product);
    }

    public void deletePart(Part part){
        //verific daca exista obiecte care au doar partea respectiva
        List<Product> products= getAllProducts();
        for(Product product:products)
        {
            if(product.getAssociatedParts().size()==1 && product.getAssociatedParts().get(0).getPartId()==part.getPartId())
                {
                    throw new InvalidParameterException("You cannot delete a part if it is the only part a product is composed of!");
                }


        }
        repo.removePart(part);

    }

    public void deleteProduct(Product product){
        repo.removeProduct(product);
    }

}