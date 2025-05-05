package inventory.integration.step1;

import inventory.model.Inventory;
import inventory.model.Part;
import inventory.model.InhousePart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class InventoryRepositoryTest {

    private Inventory inventory;

    @BeforeEach
    void setUp() {
        inventory = new Inventory();
    }

    @Test
    void testAddPart_AddsPartToList() {
        Part part = mock(Part.class);
        inventory.addPart(part);

        assertEquals(1, inventory.getAllParts().size());
        assertSame(part, inventory.getAllParts().get(0));
    }

    @Test
    void testRemovePart_RemovesPartFromList() {
        Part part = mock(Part.class);
        inventory.addPart(part);

        inventory.removePart(part);

        assertTrue(inventory.getAllParts().isEmpty());
    }

    @Test
    void testUpdatePart_ReplacesPartAtIndex() {
        Part original = mock(Part.class);
        Part updated = mock(Part.class);

        inventory.addPart(original);
        inventory.updatePart(0, updated);

        assertEquals(1, inventory.getAllParts().size());
        assertSame(updated, inventory.getAllParts().get(0));
    }

    @Test
    void testLookupPart_FindsByName() {
        Part part = new InhousePart(1, "Bolt", 1.0, 1, 1, 1, 123);
        inventory.addPart(part);

        Part found = inventory.lookupPart("Bolt");

        assertSame(part, found);
    }

    @Test
    void testGetAutoPartId_IncrementsProperly() {
        int id1 = inventory.getAutoPartId();
        int id2 = inventory.getAutoPartId();

        assertEquals(id1 + 1, id2);
    }
}
