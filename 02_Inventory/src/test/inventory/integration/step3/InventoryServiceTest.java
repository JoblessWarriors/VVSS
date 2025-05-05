package inventory.integration.step3;

import inventory.model.*;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;

public class InventoryServiceTest {
    Inventory inventoryRepository;
    InventoryService inventoryService = null;

    @BeforeEach
    void setUp() {
        inventoryRepository = new Inventory();
        inventoryService = new InventoryService(inventoryRepository);
    }

    @AfterEach
    void tearDown() {
        inventoryRepository.getAllParts().clear();
    }

    @Test
    void testGetAllParts() {
        ObservableList<Part> result = inventoryService.getAllParts();
        assertEquals(0, result.size());
    }

    @Test
    void testAddInhousePart_Successful() {
        inventoryService.addInhousePart(
                "PartX",
                45.0,
                200,
                100,
                250,
                123
        );

        ObservableList<Part> result = inventoryService.getAllParts();
        assertEquals(1, result.size());
    }

    @Test
    void testAddOutsourcePart_Successful() {

        inventoryService.addOutsourcePart(
                "PartX",
                45.0,
                200,
                100,
                250,
                "CompanyX"
        );

        ObservableList<Part> result = inventoryService.getAllParts();
        assertEquals(1, result.size());
    }

    @Test
    void testLookupPart_CallsRepository() {
        var expected = new InhousePart(
                1,
                "PartX",
                45.0,
                200,
                100,
                250,
                123
        );
        inventoryService.addInhousePart(
                "PartX",
                45.0,
                200,
                100,
                250,
                123
        );

        var result = inventoryService.lookupPart("PartX");

        assertEquals(expected.getPartId(), result.getPartId());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getPrice(), result.getPrice());
        assertEquals(expected.getInStock(), result.getInStock());
        assertEquals(expected.getMin(), result.getMin());
        assertEquals(expected.getMax(), result.getMax());
    }


    @Test
    void testGetAllParts_CallsRepository() {
        inventoryService.addOutsourcePart(
                "PartX",
                45.0,
                200,
                100,
                250,
                "CompanyX"
        );

        var result = inventoryService.getAllParts();
        assertEquals(1, result.size());
    }


    @Test
    void testDeletePart_WhenPartIsOnlyPartInProduct_ShouldThrowException() {
        var inHousePart = new InhousePart(
                1,
                "PartX",
                45.0,
                200,
                100,
                250,
                123
        );
        inventoryRepository.addPart(inHousePart);
        var product = new Product(
                1,
                "ProductX",
                100.0,
                10,
                5,
                15,
                FXCollections.observableArrayList(inHousePart)
        );
        inventoryRepository.addProduct(product);

        InvalidParameterException ex = assertThrows(InvalidParameterException.class, () -> inventoryService.deletePart(inHousePart));
        assertEquals("You cannot delete a part if it is the only part a product is composed of!", ex.getMessage());
    }

    @Test
    void testDeletePart_WhenPartIsNotTheOnlyPart_ShouldDeleteSuccessfully() {
        var inHousePart = new InhousePart(
                1,
                "PartX",
                45.0,
                200,
                100,
                250,
                123
        );
        inventoryRepository.addPart(inHousePart);
        var anotherInHousePart = new InhousePart(
                2,
                "PartX",
                45.0,
                200,
                100,
                250,
                123
        );
        inventoryRepository.addPart(anotherInHousePart);
        var product = new Product(
                1,
                "ProductX",
                100.0,
                10,
                5,
                15,
                FXCollections.observableArrayList(inHousePart, anotherInHousePart)
        );
        inventoryRepository.addProduct(product);

        inventoryService.deletePart(inHousePart);

        assertEquals(1, inventoryRepository.getAllParts().size());
    }

    @Test
    void testUpdateInhousePart_Successful() {
        var inHousePart = new InhousePart(
                1,
                "PartX",
                45.0,
                200,
                100,
                250,
                123
        );
        inventoryService.addInhousePart(
                "PartX",
                45.0,
                200,
                100,
                250,
                123
        );

        inventoryService.updateInhousePart(
                0,
                inHousePart.getPartId(),
                inHousePart.getName() + "Updated",
                inHousePart.getPrice(),
                inHousePart.getInStock(),
                inHousePart.getMin(),
                inHousePart.getMax(),
                inHousePart.getMachineId()
        );

        Part updatedPart = inventoryRepository.lookupPart(inHousePart.getName());
        assertEquals(inHousePart.getName() + "Updated", updatedPart.getName());
    }

    @Test
    void testUpdateOutsourcedPart_Successful() {
        var outsourcedPart = new OutsourcedPart(
                1,
                "PartX",
                45.0,
                200,
                100,
                250,
                "CompanyX"
        );

        inventoryService.addOutsourcePart(
                "PartX",
                45.0,
                200,
                100,
                250,
                "CompanyX"
        );

        inventoryService.updateOutsourcedPart(
                0,
                outsourcedPart.getPartId(),
                outsourcedPart.getName() + "Updated",
                outsourcedPart.getPrice(),
                outsourcedPart.getInStock(),
                outsourcedPart.getMin(),
                outsourcedPart.getMax(),
                outsourcedPart.getCompanyName()
        );

        Part updatedPart = inventoryRepository.lookupPart(outsourcedPart.getName());
        assertEquals(outsourcedPart.getName() + "Updated", updatedPart.getName());
    }
}
