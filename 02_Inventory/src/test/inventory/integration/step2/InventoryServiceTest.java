package inventory.integration.step2;

import inventory.model.*;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
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
        var inHousePartMock = mock(InhousePart.class);

        when(inHousePartMock.getName()).thenReturn("PartX");
        when(inHousePartMock.getPrice()).thenReturn(45.0);
        when(inHousePartMock.getInStock()).thenReturn(200);
        when(inHousePartMock.getMin()).thenReturn(100);
        when(inHousePartMock.getMax()).thenReturn(250);
        when(inHousePartMock.getMachineId()).thenReturn(123);

        inventoryService.addInhousePart(
                inHousePartMock.getName(),
                inHousePartMock.getPrice(),
                inHousePartMock.getInStock(),
                inHousePartMock.getMin(),
                inHousePartMock.getMax(),
                inHousePartMock.getMachineId()
        );

        ObservableList<Part> result = inventoryService.getAllParts();
        assertEquals(1, result.size());
    }

    @Test
    void testAddOutsourcePart_Successful() {
        var outsourcedPartMock = mock(OutsourcedPart.class);

        when(outsourcedPartMock.getName()).thenReturn("PartX");
        when(outsourcedPartMock.getPrice()).thenReturn(45.0);
        when(outsourcedPartMock.getInStock()).thenReturn(200);
        when(outsourcedPartMock.getMin()).thenReturn(100);
        when(outsourcedPartMock.getMax()).thenReturn(250);
        when(outsourcedPartMock.getCompanyName()).thenReturn("CompanyX");

        inventoryService.addOutsourcePart(
                outsourcedPartMock.getName(),
                outsourcedPartMock.getPrice(),
                outsourcedPartMock.getInStock(),
                outsourcedPartMock.getMin(),
                outsourcedPartMock.getMax(),
                outsourcedPartMock.getCompanyName()
        );

        ObservableList<Part> result = inventoryService.getAllParts();
        assertEquals(1, result.size());
    }

    @Test
    void testLookupPart_CallsRepository() {
        var inHousePartMock = mock(InhousePart.class);
        when(inHousePartMock.getName()).thenReturn("PartX");

        inventoryRepository.addPart(inHousePartMock);

        var result = inventoryService.lookupPart("PartX");

        assertEquals(inHousePartMock, result);
    }

    @Test
    void testGetAllParts_CallsRepository() {
        var outsourcedPartMock = mock(OutsourcedPart.class);

        inventoryRepository.addPart(outsourcedPartMock);

        var result = inventoryService.getAllParts();
        assertEquals(1, result.size());
    }

    @Test
    void testDeletePart_WhenPartIsOnlyPartInProduct_ShouldThrowException() {
        var inHousePartMock = mock(InhousePart.class);
        when(inHousePartMock.getPartId()).thenReturn(1);

        var productMock = mock(Product.class);
        when(productMock.getAssociatedParts()).thenReturn(FXCollections.observableArrayList(inHousePartMock));

        inventoryRepository.addPart(inHousePartMock);
        inventoryRepository.addProduct(productMock);

        InvalidParameterException ex = assertThrows(InvalidParameterException.class, () -> inventoryService.deletePart(inHousePartMock));
        assertEquals("You cannot delete a part if it is the only part a product is composed of!", ex.getMessage());
    }

    @Test
    void testDeletePart_WhenPartIsNotTheOnlyPart_ShouldDeleteSuccessfully() {
        var inHousePartMock = mock(InhousePart.class);
        var anotherInHousePartMock = mock(InhousePart.class);

        var productMock = mock(Product.class);
        when(productMock.getAssociatedParts()).thenReturn(FXCollections.observableArrayList(inHousePartMock, anotherInHousePartMock));
        inventoryRepository.addPart(inHousePartMock);
        inventoryRepository.addPart(anotherInHousePartMock);
        inventoryRepository.addProduct(productMock);

        inventoryService.deletePart(inHousePartMock);

        assertEquals(1, inventoryRepository.getAllParts().size());
    }

    @Test
    void testUpdateInhousePart_Successful() {
        var inHousePartMock = mock(InhousePart.class);

        when(inHousePartMock.getPartId()).thenReturn(1);
        when(inHousePartMock.getName()).thenReturn("PartX");
        when(inHousePartMock.getPrice()).thenReturn(45.0);
        when(inHousePartMock.getInStock()).thenReturn(200);
        when(inHousePartMock.getMin()).thenReturn(100);
        when(inHousePartMock.getMax()).thenReturn(250);
        when(inHousePartMock.getMachineId()).thenReturn(123);

        inventoryRepository.addPart(inHousePartMock);

        inventoryService.updateInhousePart(
                0,
                inHousePartMock.getPartId(),
                inHousePartMock.getName() + "Updated",
                inHousePartMock.getPrice(),
                inHousePartMock.getInStock(),
                inHousePartMock.getMin(),
                inHousePartMock.getMax(),
                inHousePartMock.getMachineId()
        );

        Part updatedPart = inventoryRepository.lookupPart(inHousePartMock.getName());
        assertEquals(inHousePartMock.getName() + "Updated", updatedPart.getName());
    }

    @Test
    void testUpdateOutsourcedPart_Successful() {
        var outsourcedPartMock = mock(OutsourcedPart.class);

        when(outsourcedPartMock.getName()).thenReturn("PartX");
        when(outsourcedPartMock.getPrice()).thenReturn(45.0);
        when(outsourcedPartMock.getInStock()).thenReturn(200);
        when(outsourcedPartMock.getMin()).thenReturn(100);
        when(outsourcedPartMock.getMax()).thenReturn(250);
        when(outsourcedPartMock.getCompanyName()).thenReturn("CompanyX");

        inventoryRepository.addPart(outsourcedPartMock);

        inventoryService.updateOutsourcedPart(
                0,
                outsourcedPartMock.getPartId(),
                outsourcedPartMock.getName() + "Updated",
                outsourcedPartMock.getPrice(),
                outsourcedPartMock.getInStock(),
                outsourcedPartMock.getMin(),
                outsourcedPartMock.getMax(),
                outsourcedPartMock.getCompanyName()
        );

        Part updatedPart = inventoryRepository.lookupPart(outsourcedPartMock.getName());
        assertEquals(outsourcedPartMock.getName() + "Updated", updatedPart.getName());
    }
}
