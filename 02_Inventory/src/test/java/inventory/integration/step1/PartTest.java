package inventory.integration.step1;

import inventory.model.Part;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PartTest {

    @Test
    void testIsValidPart_AllValidInputs() {
        // Arrange
        String name = "Bolt";
        double price = 10.0;
        int inStock = 5;
        int min = 1;
        int max = 10;

        // Act
        String result = Part.isValidPart(name, price, inStock, min, max, "");

        // Assert
        assertEquals("", result, "Expected no error message for valid inputs.");
    }

    @Test
    void testIsValidPart_EmptyName() {
        // Arrange
        String name = "";
        double price = 10.0;
        int inStock = 5;
        int min = 1;
        int max = 10;

        // Act
        String result = Part.isValidPart(name, price, inStock, min, max, "");

        // Assert
        assertTrue(result.contains("A name has not been entered."),
                "Expected an error message for empty name.");
    }

    @Test
    void testIsValidPart_InvalidPriceAndInventory() {
        // Arrange
        String name = "Screw";
        double price = 0.0;
        int inStock = 2;
        int min = 5;
        int max = 3;

        // Act
        String result = Part.isValidPart(name, price, inStock, min, max, "");

        // Assert (all possible error messages should be present)
        assertTrue(result.contains("The price must be greater than 0."),
                "Expected error for invalid price.");
        assertTrue(result.contains("Inventory level is lower than minimum value."),
                "Expected error for inventory lower than min.");
        assertTrue(result.contains("The Min value must be less than the Max value."),
                "Expected error for min greater than max.");
    }
}