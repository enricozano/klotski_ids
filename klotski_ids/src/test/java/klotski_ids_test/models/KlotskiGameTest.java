package klotski_ids_test.models;

import klotski_ids.models.Component;

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

        List<Component> components = new ArrayList<>();
        components.add(new Component("component1", 0, 0,1,1, 50,50));
        components.add(new Component("component2", 1, 1,1,1,50,50));

        List<Component> copyList = KlotskiGame.copyComponentsList(components);

        // Assert that the copy list has the same number of elements
        assertEquals(components.size(), copyList.size());

        // Assert that the copied components match the original components
        for (int i = 0; i < components.size(); i++) {
            Component originalComponent = components.get(i);
            Component copiedComponent = copyList.get(i);

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
        List<Component> originalList = new ArrayList<>();

        // Invoke the method under test
        List<Component> copyList = KlotskiGame.copyComponentsList(originalList);

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

    @Test
    @DisplayName("Testing winCondition with winning components")
    public void testWinCondition() {
        // Create a list of components for testing
        List<Component> componentsList = new ArrayList<>();

        // Add a component that satisfies the win condition
        Component winComponent = new Component("1", 3, 1, 2, 2, 100, 100);
        componentsList.add(winComponent);

        // Add some other components
        Component component1 = new Component("2", 0, 0, 1, 1, 50, 50);
        Component component2 = new Component("3", 2, 2, 1, 1, 50, 50);
        componentsList.add(component1);
        componentsList.add(component2);

        // Create an instance of KlotskiGame
        KlotskiGame klotskiGame = new KlotskiGame();

        // Call the winCondition method and assert that it returns true
        assertTrue(klotskiGame.winCondition(componentsList));
    }

    @Test
    @DisplayName("Testing winCondition with non-winning components")
    public void testWinCondition_NoWin() {
        // Create a list of components for testing
        List<Component> componentsList = new ArrayList<>();

        // Add some components that do not satisfy the win condition
        Component component1 = new Component("1", 0, 0, 1, 1, 50, 50);
        Component component2 = new Component("2", 2, 2, 1, 1, 50, 50);
        componentsList.add(component1);
        componentsList.add(component2);

        // Create an instance of KlotskiGame
        KlotskiGame klotskiGame = new KlotskiGame();

        // Call the winCondition method and assert that it returns false
        assertFalse(klotskiGame.winCondition(componentsList));
    }


}