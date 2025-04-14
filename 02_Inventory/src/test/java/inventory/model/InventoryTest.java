package inventory.model;

import inventory.repository.InventoryRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.NoSuchElementException;

import static org.junit.jupiter.api.Assertions.*;

class InventoryTest {
    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @AfterEach
    void tearDown() {
        inventory = null;
    }

    @Test
    @DisplayName("F02a_TC01-WBT-Valid-Lookup Part")
    void f02_tc01_lookupPart() {
        //Arrange
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
        var searchItem = "1part";
        var expectedResult = inventory.getAllParts().get(0);

        //Act
        var actualResult = inventory.lookupPart(searchItem);

        //Assert
        assertEquals(actualResult, expectedResult);
    }

    @Test
    @DisplayName("F02a_TC02-WBT-Valid-Lookup Part")
    void f02_tc02_lookupPart() {
        //Arrange
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
        var searchItem = "2";
        var expectedResult = inventory.getAllParts().get(1);

        //Act
        var actualResult = inventory.lookupPart(searchItem);

        //Assert
        assertEquals(actualResult, expectedResult);
    }

    @Test
    @DisplayName("F02a_TC03-WBT-Valid-Lookup Part")
    void f02_tc03_lookupPart() {
        //Arrange
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
        var searchItem = "pa";
        var expectedResult = inventory.getAllParts().get(2);

        //Act
        var actualResult = inventory.lookupPart(searchItem);

        //Assert
        assertEquals(actualResult, expectedResult);
    }

    @Test
    @DisplayName("F02a_TC04-WBT-Not valid-Lookup Part")
    void f02_tc04_lookupPart() {
        //Arrange
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
        var searchItem = "aa";

        //Act & Assert
        assertThrows(NoSuchElementException.class, () ->
                inventory.lookupPart(searchItem)
        );
    }

    @Test
    @DisplayName("F02a_TC05-WBT-Not valid-Lookup Part")
    void f02_tc05_lookupPart() {
        //Arrange
        var searchItem = "qwfqew";

        //Act & Assert
        assertThrows(NoSuchElementException.class, () ->
                inventory.lookupPart(searchItem)
        );
    }
}