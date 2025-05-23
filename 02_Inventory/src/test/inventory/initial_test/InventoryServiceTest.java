package inventory.initial_test;

import inventory.model.Part;
import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.Nested; // ✅ correct import
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

@DisplayName("Inventory Service Test – F01")
class InventoryServiceTest {

    private InventoryRepository inventoryRepo;
    private InventoryService inventoryService;
    private static String fileName = "data/test_items.txt";
    @BeforeEach
    void setUp() {
        // create file
        File file = new File(fileName);
        if(!file.exists()) {
            try {
                file.createNewFile();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        inventoryRepo = new InventoryRepository(fileName);
        inventoryService = new InventoryService(inventoryRepo);
    }

    @AfterEach
    void tearDown() {
        //delete the file with data
        new File(fileName).delete();

        inventoryRepo = null;
        inventoryService = null;
    }

    // ecp
    @Nested
    @DisplayName("ECP Tests")
    @Tag("ECP")
    class ECPTests {

        @Test
        @DisplayName("TC1a_ECP – Valid input")
        void tc1_validAddInhousePart() {
            // Arrange
            String name = "PartX";
            double price = 45;
            int inStock = 200;
            int min = 100;
            int max = 250;
            int before = inventoryRepo.getAllParts().size();

            // Act & Assert
            assertDoesNotThrow(() ->
                    inventoryService.addOutsourcePart(name, price, inStock, min, max, "MCH-23")
            );
            assertEquals(before + 1, inventoryRepo.getAllParts().size());
        }

        @Test
        @DisplayName("TC2a_ECP – Invalid: empty name")
        void tc2_invalidEmptyName() {
            int before = inventoryRepo.getAllParts().size();
            assertThrows(IllegalArgumentException.class, () ->
                    inventoryService.addOutsourcePart("", 45, 200, 100, 250,"MCH-23" )
            );
            assertEquals(before, inventoryRepo.getAllParts().size());
        }

        @Test
        @DisplayName("TC3a_ECP – Invalid: negative price")
        void tc3_invalidNegativePrice() {
            int before = inventoryRepo.getAllParts().size();
            assertThrows(Exception.class, () ->
                    inventoryService.addOutsourcePart("PartX", -10, 200, 100, 250, "MCH-23")
            );
            assertEquals(before, inventoryRepo.getAllParts().size());
        }
        @Test
        @DisplayName("TC4_ECP – Invalid: inStock < min")
        void tc4_invalidInStockLessThanMin() {
            int before = inventoryRepo.getAllParts().size();
            assertThrows(IllegalArgumentException.class, () ->
                    inventoryService.addOutsourcePart("PartX", 45, 50, 100, 250, "MCH-23")
            );
            assertEquals(before, inventoryRepo.getAllParts().size());
        }
    }
    //bva
    @Nested
    @DisplayName("BVA Tests")
    @Tag("BVA")
    class BvaTests {

        @ParameterizedTest
        @CsvSource({
                "15,16", // min < max
                "0,1"    // lower boundary
        })
        @DisplayName("TC8a_TC7a_BVA – Valid min/max values")
        void testValidBVA(int min, int max) {
            int before = inventoryRepo.getAllParts().size();
            assertDoesNotThrow(() ->
                    inventoryService.addOutsourcePart("ValidPart", 45, max, min, max, "MCH-23")
            );
            assertEquals(before + 1, inventoryRepo.getAllParts().size());
        }

        @ParameterizedTest
        @CsvSource({
                "50,50", // min == max
                "51,50"  // min > max
        })
        @DisplayName("TC6a_TC9a_BVA – Invalid min/max values")
        void testInvalidBVA(int min, int max) {
            int before = inventoryRepo.getAllParts().size();
            assertThrows(IllegalArgumentException.class, () ->
                    inventoryService.addOutsourcePart("InvalidPart", 45, 100, min, max, "MCH-23")
            );
            assertEquals(before, inventoryRepo.getAllParts().size());
        }

        @Test
        @DisplayName("TC2b_BVA – Valid price")
        void testValidPriceProduct() {
            int before = inventoryRepo.getAllParts().size();
            double product_price=0.1000000000002;
            double part_price=0.1000000000001;
            assertDoesNotThrow(() ->
                    inventoryService.addOutsourcePart("Battery2", part_price, 100, 50, 150, "MCH-23")

            );
            Part part= inventoryService.lookupPart("Battery2");
            ObservableList<Part> parts = FXCollections.observableArrayList(part);
            assertDoesNotThrow(() ->
                    inventoryService.addProduct("A",product_price,10,5,15,parts)
            );
            assertEquals(before + 1, inventoryRepo.getAllParts().size());
        }

        @Test
        @DisplayName("TC3b_BVA – Invalid price")
        void testInvalidValidPriceProduct() {
            int before = inventoryRepo.getAllParts().size();
            double product_price=0.0;
            double part_price=0.1;
            assertDoesNotThrow(() ->
                    inventoryService.addOutsourcePart("Battery2", part_price, 100, 50, 150, "MCH-23")

            );

            Part part= inventoryService.lookupPart("Battery2");
            ObservableList<Part> parts = FXCollections.observableArrayList(part);

            assertThrows(IllegalArgumentException.class, () ->
                    inventoryService.addProduct("Phone",product_price,10,5,15,parts)
            );
            assertEquals(before + 1, inventoryRepo.getAllParts().size());
        }
    }
}
