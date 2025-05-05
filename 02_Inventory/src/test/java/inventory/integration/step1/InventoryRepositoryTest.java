package inventory.integration.step1;

import inventory.model.Inventory;
import inventory.model.Part;
import inventory.model.InhousePart;
import inventory.repository.InventoryRepository;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryRepositoryTest {

    @Mock
    private Inventory mockInventory;

    @InjectMocks
    private InventoryRepository repository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddPart_CallsInventoryAddPartOnce() {
        // Setup
        Part part = new InhousePart(1, "Gear", 10.0, 5, 1, 10, 123);

        // Define mock behavior explicitly
        doNothing().when(mockInventory).addPart(part);

        // Exercise
        repository.addPart(part);

        // Verify interaction with mock object
        verify(mockInventory, times(1)).addPart(part);
    }

    @Test
    void testDeletePart_CallsInventoryDeletePartOnce() {
        // Setup
        Part part = new InhousePart(2, "Bolt", 5.0, 10, 1, 15, 321);

        // Define mock behavior explicitly
        doNothing().when(mockInventory).deletePart(part);

        // Exercise
        repository.deletePart(part);

        // Verify interaction with mock object
        verify(mockInventory, times(1)).deletePart(part);
    }

    @Test
    void testGetAllParts_ReturnsExpectedPartsList() {
        // Setup
        ObservableList<Part> expectedParts = FXCollections.observableArrayList(
                new InhousePart(3, "Nut", 0.50, 100, 10, 500, 456)
        );

        // Define mock behavior
        when(mockInventory.getAllParts()).thenReturn(expectedParts);

        // Exercise
        ObservableList<Part> actualParts = repository.getAllParts();

        // Verify interaction with mock object
        verify(mockInventory, times(1)).getAllParts();

        // Verify the state of returned data
        assertEquals(expectedParts, actualParts);
        assertEquals(1, actualParts.size());
        assertEquals("Nut", actualParts.get(0).getName());
    }
}
