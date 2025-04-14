package inventory.model;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
        inventory.addPart(
                new Part(
                        1,
                        "1part",
                        10.0,
                        5,
                        1,
                        10));
        inventory.addPart(
                new Part(
                        2,
                        "2",
                        10.0,
                        5,
                        1,
                        10));
        inventory.addPart(
                new Part(
                        3,
                        "3part",
                        10.0,
                        5,
                        1,
                        10));
    }

    @AfterEach
    void tearDown() {
        inventory = null;
    }

    @Test
    @DisplayName("F02a_TC01-WBT-Valid-Lookup Part")
    void f02_tc01_lookupPart() {
        //Arrange
        var searchItem = "1part";

        //Act
        var part = inventory.lookupPart(searchItem);

        //Assert
        assertEquals(part, inventory.getAllParts().get(0));
    }

    @Test
    @DisplayName("F02a_TC01-WBT-Valid-Lookup Part")
    void f02_tc02_lookupPart() {
        //Arrange
        var searchItem = "1part";

        //Act
        var part = inventory.lookupPart(searchItem);

        //Assert
        assertEquals(part, inventory.getAllParts().get(0));
    }
}