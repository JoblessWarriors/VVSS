package inventory.integration.step1;

import inventory.model.*;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.security.InvalidParameterException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryServiceTest {

    @Mock
    private InventoryRepository mockRepo;

    @InjectMocks
    private InventoryService service;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddInhousePart_Successful() {
        when(mockRepo.getAutoPartId()).thenReturn(1);

        service.addInhousePart("Gear", 10.0, 5, 1, 10, 123);

        verify(mockRepo).addPart(any(InhousePart.class));
    }

    @Test
    void testAddOutsourcePart_Successful() {
        when(mockRepo.getAutoPartId()).thenReturn(2);

        service.addOutsourcePart("Bolt", 5.0, 10, 1, 15, "CompanyX");

        verify(mockRepo).addPart(any(OutsourcedPart.class));
    }

    @Test
    void testDeletePart_WhenPartIsOnlyPartInProduct_ShouldThrowException() {
        Part part = new InhousePart(1, "Screw", 1.0, 10, 1, 20, 100);
        ObservableList<Part> associatedParts = FXCollections.observableArrayList(part);
        Product product = new Product(1, "Widget", 20.0, 5, 1, 10, associatedParts);

        when(mockRepo.getAllProducts()).thenReturn(FXCollections.observableArrayList(product));

        InvalidParameterException ex = assertThrows(InvalidParameterException.class, () -> service.deletePart(part));
        assertEquals("You cannot delete a part if it is the only part a product is composed of!", ex.getMessage());

        verify(mockRepo, never()).deletePart(any());
    }

    @Test
    void testDeletePart_WhenPartIsNotTheOnlyPart_ShouldDeleteSuccessfully() {
        Part part = new InhousePart(1, "Screw", 1.0, 10, 1, 20, 100);
        Part anotherPart = new InhousePart(2, "Bolt", 2.0, 5, 1, 15, 101);
        ObservableList<Part> associatedParts = FXCollections.observableArrayList(part, anotherPart);
        Product product = new Product(1, "Widget", 20.0, 5, 1, 10, associatedParts);

        when(mockRepo.getAllProducts()).thenReturn(FXCollections.observableArrayList(product));

        service.deletePart(part);

        verify(mockRepo).deletePart(part);
    }

    @Test
    void testUpdateInhousePart_Successful() {
        service.updateInhousePart(0, 1, "Gear", 10.0, 5, 1, 10, 123);

        verify(mockRepo).updatePart(eq(0), any(InhousePart.class));
    }

    @Test
    void testUpdateOutsourcedPart_Successful() {
        service.updateOutsourcedPart(0, 2, "Bolt", 5.0, 10, 1, 15, "CompanyX");

        verify(mockRepo).updatePart(eq(0), any(OutsourcedPart.class));
    }

    @Test
    void testLookupPart_CallsRepository() {
        Part expectedPart = new InhousePart(1, "Gear", 10.0, 5, 1, 10, 123);
        when(mockRepo.lookupPart("Gear")).thenReturn(expectedPart);

        Part actualPart = service.lookupPart("Gear");

        verify(mockRepo).lookupPart("Gear");
        assertEquals(expectedPart, actualPart);
    }

    @Test
    void testGetAllParts_CallsRepository() {
        ObservableList<Part> expectedParts = FXCollections.observableArrayList();
        when(mockRepo.getAllParts()).thenReturn(expectedParts);

        ObservableList<Part> actualParts = service.getAllParts();

        verify(mockRepo).getAllParts();
        assertEquals(expectedParts, actualParts);
    }
}