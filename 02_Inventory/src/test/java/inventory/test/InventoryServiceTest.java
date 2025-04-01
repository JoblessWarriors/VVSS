package inventory.test;

import inventory.repository.InventoryRepository;
import inventory.service.InventoryService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.*;
@DisplayName("Inventory Service Test")
class InventoryServiceTest {
    private InventoryRepository inventoryRepo;
    private InventoryService inventoryService;


    @BeforeEach
    void setUp() {
        inventoryRepo = new InventoryRepository();
        inventoryService = new InventoryService(inventoryRepo);
    }

    @AfterEach
    void tearDown() {
        inventoryRepo = null;
        inventoryService = null;
    }

    @Test
    @DisplayName("Test Case 1 - ECP: Add Inhouse Part")
    void tc1_ecp_addInhousePart() {
        String name="PartX";
        double price=45;
        int inStock=200;
        int min=100;
        int max=250;
        String partDynamicValue="MCH-23";
        int size=inventoryRepo.getAllParts().size();
        //act
       assertDoesNotThrow(()-> inventoryService.addInhousePart(name, price, inStock, min, max, 23));
       //assert new
        assertEquals(size+1,inventoryRepo.getAllParts().size());

    }
    @Test
    @DisplayName("TC2_ECP – Invalid: empty name")
    void tc2_ecp_emptyName() {
        int size=inventoryRepo.getAllParts().size();
        //act
        assertThrows(IllegalArgumentException.class, () ->
                inventoryService.addInhousePart("", 45, 200, 100, 250, 23)
        );
        //assert
        assertEquals(size,inventoryRepo.getAllParts().size());

    }

    @ParameterizedTest(name = "TC{index}_BVA – Valid min={0}, max={1}")
    @CsvSource({
            "45,46"
    })
    @DisplayName("Valid BVA Cases")
    void tc6_bva_addInhouseBVA(int min, int max) {
        // Arrange
        int before = inventoryRepo.getAllParts().size();

        // Act
        assertDoesNotThrow(() ->
                inventoryService.addInhousePart("ValidName", 45, 100, min, max, 23)
        );

        // Assert
        assertEquals(before + 1, inventoryRepo.getAllParts().size());
    }

    @ParameterizedTest(name = "TC{index}_BVA – Invalid min={0}, max={1}")
    @CsvSource({
            "50,50",
            "50,49"
    })
    @DisplayName("Invalid BVA Cases")
    void testInvalidBVA(int min, int max) {
        // Act & Assert
        assertThrows(IllegalArgumentException.class, () ->
                inventoryService.addInhousePart("PartX", 45, 100, min, max, 23)
        );
    }


}