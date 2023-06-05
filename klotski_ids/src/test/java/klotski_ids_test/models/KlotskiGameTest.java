package klotski_ids_test.models;

import klotski_ids.models.Components;

import klotski_ids.models.KlotskiGame;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class KlotskiGameTest {

    @Test
    @DisplayName("Copy of the component list test")
    public void testCopyComponentsList() {

        List<Components> components = new ArrayList<>();
        components.add(new Components("component1", 0, 0,1,1, 50,50));
        components.add(new Components("component2", 1, 1,1,1,50,50));

        List<Components> copyList = KlotskiGame.copyComponentsList(components);

        // Assert that the copy list has the same number of elements
        assertEquals(components.size(), copyList.size());

        // Assert that the copied components match the original components
        for (int i = 0; i < components.size(); i++) {
            Components originalComponent = components.get(i);
            Components copiedComponent = copyList.get(i);

            assertEquals(originalComponent.getId(), copiedComponent.getId());
            assertEquals(originalComponent.getRow(), copiedComponent.getRow());
            assertEquals(originalComponent.getCol(), copiedComponent.getCol());
            assertEquals(originalComponent.getColSpan(), copiedComponent.getColSpan());
            assertEquals(originalComponent.getRowSpan(), copiedComponent.getRowSpan());
            assertEquals(originalComponent.getWidth(), copiedComponent.getWidth());
            assertEquals(originalComponent.getHeight(), copiedComponent.getHeight());
        }
    }

    @Test
    @DisplayName("Empty component list can be copied")
    public void testCopyComponentsListWithEmptyList() {
        // Create an empty original list of components
        List<Components> originalList = new ArrayList<>();

        // Invoke the method under test
        List<Components> copyList = KlotskiGame.copyComponentsList(originalList);

        // Assert that the copy list is also empty
        assertTrue(copyList.isEmpty());
    }

    @Test
    @DisplayName("Cant copy null component list")
    public void testCopyComponentsListWithNullList() {
        // Invoke the method under test with null original list
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> KlotskiGame.copyComponentsList(null));

        // Assert that the exception message is correct
        assertEquals("The original list cannot be null.", exception.getMessage());
    }

}