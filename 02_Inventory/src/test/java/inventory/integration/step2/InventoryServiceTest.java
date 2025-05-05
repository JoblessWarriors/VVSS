package inventory.integration.step2;

import inventory.model.InhousePart;
import inventory.model.Inventory;
import inventory.model.OutsourcedPart;
import inventory.model.Part;
import inventory.repository.IInventoryRepository;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class InventoryServiceTest {
    IInventoryRepository inventoryRepository;
    InventoryService inventoryService = null;

    @BeforeEach
    void setUp() {
        inventory = mock(Inventory.class);
        when(inventory.getAllParts()).thenReturn(FXCollections.observableArrayList());
        inventoryRepository = new InventoryRepository();
        inventoryService = new InventoryService(inventoryRepository);
    }

    @AfterEach
    void tearDown() {
        inventoryRepository.getAllParts().clear();
        inventoryRepository.writeAll();
    }

    @Test
    void testGetAllParts() {
        ObservableList<Part> result = inventoryService.getAllParts();
        assertEquals(0, result.size());
    }

    @Test
    void testAddInhousePart_Successful() {
        var inHousePartMock = mock(InhousePart.class);
        when(inventory.getAllParts()).thenReturn(FXCollections.observableArrayList(inHousePartMock));

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
        var outsourcedPart = mock(OutsourcedPart.class);
        when(inventory.getAllParts()).thenReturn(FXCollections.observableArrayList(outsourcedPart));

        when(outsourcedPart.getName()).thenReturn("PartX");
        when(outsourcedPart.getPrice()).thenReturn(45.0);
        when(outsourcedPart.getInStock()).thenReturn(200);
        when(outsourcedPart.getMin()).thenReturn(100);
        when(outsourcedPart.getMax()).thenReturn(250);
        when(outsourcedPart.getCompanyName()).thenReturn("CompanyX");

        inventoryService.addOutsourcePart(
                outsourcedPart.getName(),
                outsourcedPart.getPrice(),
                outsourcedPart.getInStock(),
                outsourcedPart.getMin(),
                outsourcedPart.getMax(),
                outsourcedPart.getCompanyName()
        );

        ObservableList<Part> result = inventoryService.getAllParts();
        assertEquals(1, result.size());
    }
}
