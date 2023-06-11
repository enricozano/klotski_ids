package klotski_ids_test.models;

import javafx.scene.shape.Rectangle;
import klotski_ids.models.Component;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
public class ComponentTest {

    @Test
    @DisplayName("Constructor and Getters")
    public void testConstructorAndGetters() {
        Component component = new Component("id", 1, 2, 3, 4, 5, 6);

        assertEquals("id", component.getId());
        assertEquals(1, component.getRow());
        assertEquals(2, component.getCol());
        assertEquals(3, component.getColSpan());
        assertEquals(4, component.getRowSpan());
        assertEquals(5, component.getWidth());
        assertEquals(6, component.getHeight());
    }

    @Test
    @DisplayName("Setters")
    public void testSetters() {
        Component component = new Component();

        component.setId("id");
        component.setRow(1);
        component.setCol(2);
        component.setColSpan(3);
        component.setRowSpan(4);
        component.setWidth(5);
        component.setHeight(6);

       assertEquals("id", component.getId());
        assertEquals(1, component.getRow());
        assertEquals(2, component.getCol());
        assertEquals(3, component.getColSpan());
        assertEquals(4, component.getRowSpan());
        assertEquals(5, component.getWidth());
        assertEquals(6, component.getHeight());
    }


    @Test
    @DisplayName("Test the solver format string function")
    public void testToSolverFormatString() {
        Component component = new Component("id", 1, 2, 1, 1, 50, 50);
        var actual = component.toSolverFormatString();
        var expected = String.format("%s%n%s","S","2 1\n");
        assertEquals(expected,actual);
    }

    @Test
    @DisplayName("Convert component to rectangle")
    public void testToRectangle() {
        Component component = new Component("id", 1, 2, 1, 1, 50, 50);
        var actual = component.toRectangle();
        Rectangle expected = new Rectangle(50,50);
        expected.setId("id");

        assertEquals(expected.getWidth(),actual.getWidth());
        assertEquals(expected.getHeight(),actual.getHeight());
        assertEquals(expected.getId(),actual.getId());

    }

    @Test
    @DisplayName("Covert a component to JsonObject")
    public void testToJsonObject(){
        Component component = new Component("id", 1, 2, 1, 1, 50,50);
        String actual = component.toJsonObject().toString();
        String expected = "{\"rowSpan\":1,\"col\":2,\"colSpan\":1,\"width\":50,\"id\":\"id\",\"row\":1,\"height\":50}";
        Assertions.assertEquals(expected,actual);
    }

    @Test
    @DisplayName("Test equals method")
    public void testEquals() {
        Component component1 = new Component("rect1", 0, 0,1,1,50,50);
        Component component2 = new Component("rect1", 0, 0,1,1,50,50);
        Component component3 = new Component("rect3", 1, 2,3,4,100,100);

        // Test equality with the same object
        assertEquals(component1, component1);

        // Test equality with another object of the same class
        assertEquals(component1, component2);
        assertEquals(component2, component1);

        // Test inequality with a different object of the same class
        assertNotEquals(component1, component3);
        assertNotEquals(component3, component1);

        // Test inequality with a different type of object
        assertNotEquals("rect1", component1);

        // Test inequality with null
        assertNotEquals(null, component1);
    }
}