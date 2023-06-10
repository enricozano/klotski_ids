package klotski_ids_test.models;

import klotski_ids.models.Components;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class ComponentsTest {

    @Test
    @DisplayName("Constructor and Getters")
    public void testConstructorAndGetters() {
        Components component = new Components("id", 1, 2, 3, 4, 5, 6);

        Assertions.assertEquals("id", component.getId());
        Assertions.assertEquals(1, component.getRow());
        Assertions.assertEquals(2, component.getCol());
        Assertions.assertEquals(3, component.getColSpan());
        Assertions.assertEquals(4, component.getRowSpan());
        Assertions.assertEquals(5, component.getWidth());
        Assertions.assertEquals(6, component.getHeight());
    }

    @Test
    @DisplayName("Setters")
    public void testSetters() {
        Components component = new Components();

        component.setId("id");
        component.setRow(1);
        component.setCol(2);
        component.setColSpan(3);
        component.setRowSpan(4);
        component.setWidth(5);
        component.setHeight(6);

        Assertions.assertEquals("id", component.getId());
        Assertions.assertEquals(1, component.getRow());
        Assertions.assertEquals(2, component.getCol());
        Assertions.assertEquals(3, component.getColSpan());
        Assertions.assertEquals(4, component.getRowSpan());
        Assertions.assertEquals(5, component.getWidth());
        Assertions.assertEquals(6, component.getHeight());
    }

    @Test
    @DisplayName("Equals")
    public void testEquals() {
        Components component1 = new Components("id", 1, 2, 3, 4, 5, 6);
        Components component2 = new Components("id", 1, 2, 3, 4, 5, 6);
        Components component3 = new Components("id2", 1, 2, 3, 4, 5, 6);

        Assertions.assertEquals(component1, component2);
        Assertions.assertNotEquals(component1, component3);
    }
}