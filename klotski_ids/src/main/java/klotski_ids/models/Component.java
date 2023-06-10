package klotski_ids.models;

import javafx.scene.shape.Rectangle;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.LinkedHashMap;

/**
 * This class represents a component in a grid, with its properties such as ID, position, size, and span.
 */

//TODO add validation to setter
public class Component {
    /**
     * The ID of the element
     */
    private String id;
    /**
     * The width of the element
     */
    private int width;
    /**
     * The heigth of the element
     */
    private int height;
    /**
     * The row index of the element
     */
    private int row;
    /**
     * The column index of the element
     */
    private int col;
    /**
     * The column span of the element
     */
    private int colSpan;
    /**
     * The row span of the element
     */
    private int rowSpan;

    /**
     * Constructs a new Components object with the default properties.
     */
    public Component() {
        this.id = "";
        this.row = 0;
        this.col = 0;
        this.colSpan = 0;
        this.rowSpan = 0;
        this.width = 0;
        this.height = 0;
    }


    /**
     * Constructs a new Components object with the specified properties.
     *
     * @param id      the ID of the component
     * @param row     the row position of the component
     * @param col     the column position of the component
     * @param colSpan the column span of the component
     * @param rowSpan the row span of the component
     * @param width   the width of the component
     * @param height  the height of the component
     */
    public Component(String id, int row, int col, int colSpan, int rowSpan, int width, int height) {
        this.id = id;
        this.row = row;
        this.col = col;
        this.colSpan = colSpan;
        this.rowSpan = rowSpan;
        this.width = width;
        this.height = height;
    }

    /**
     * Returns the height of this component.
     *
     * @return the height of this component
     */
    public int getHeight() {
        return height;
    }

    /**
     * Returns the id of this component.
     *
     * @return the id of this component
     */
    public String getId() {
        return id;
    }

    /**
     * Returns the width of this component.
     *
     * @return the width of this component
     */
    public int getWidth() {
        return width;
    }

    /**
     * Returns the row index of the upper left corner of this component.
     *
     * @return the row index of the upper left corner of this component
     */
    public int getRow() {
        return row;
    }

    /**
     * Returns the column index of the upper left corner of this component.
     *
     * @return the column index of the upper left corner of this component
     */
    public int getCol() {
        return col;
    }

    /**
     * Returns the number of columns occupied by this component.
     *
     * @return the number of columns occupied by this component
     */
    public int getColSpan() {
        return colSpan;
    }

    /**
     * Returns the number of rows occupied by this component.
     *
     * @return the number of rows occupied by this component
     */
    public int getRowSpan() {
        return rowSpan;
    }

    /**
     * Sets the number of columns occupied by this component.
     *
     * @param colSpan the number of columns occupied by this component
     */
    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    /**
     * Sets the number of rows occupied by this component.
     *
     * @param rowSpan the number of rows occupied by this component
     */
    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    /**
     * Sets the column index of the upper left corner of this component.
     *
     * @param col the column index of the upper left corner of this component
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     * Sets the height of this component.
     *
     * @param height the height of this component
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     * Sets the row index of the upper left corner of this component.
     *
     * @param row the row index of the upper left corner of this component
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     * Sets the width of this component.
     *
     * @param width the width of this component
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     * Sets the id of this component.
     *
     * @param id the id of this component
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * Converts the Component to a string representation in the solver format.
     * The solver format represents the shape and coordinates of the Component.
     *
     * @return the string representation of the Component in the solver format
     */
    public String toSolverFormatString() {
        int rectangleAreaDimension = (width * height) / 100;

        String shape;
        if (rectangleAreaDimension == 100) {
            shape = "B";
        } else if (rectangleAreaDimension == 50 && width == 100) {
            shape = "H";
        } else if (rectangleAreaDimension == 50 && height == 100) {
            shape = "V";
        } else {
            shape = "S";
        }

        return String.format("%s%n%s", shape, coordsToString());
    }

    /**
     * Converts the row and column coordinates of the Component to a string representation.
     *
     * @return the string representation of the coordinates in the format "X Y\n"
     */
    private String coordsToString() {
        String X = Integer.toString(col);
        String Y = Integer.toString(row);
        return X + " " + Y + "\n";
    }

    /**
     * Converts the Component object to a Rectangle object.
     *
     * @return the Rectangle object representing the Component
     */
    public Rectangle toRectangle() {
        Rectangle rectangle = new Rectangle(width, height);
        rectangle.setId(id);

        return rectangle;
    }

    /**
     * Converts the Component object to a JSONObject representation.
     *
     * @return the JSONObject representing the Component object
     * @throws JSONException if an error occurs while creating the JSONObject
     */
    public JSONObject toJsonObject() throws JSONException {
        JSONObject rectangleJson = new JSONObject(new LinkedHashMap<>());
        rectangleJson.put("id", id);
        rectangleJson.put("width", width);
        rectangleJson.put("height", height);
        rectangleJson.put("row", row);
        rectangleJson.put("col", col);
        rectangleJson.put("colSpan", colSpan);
        rectangleJson.put("rowSpan", rowSpan);
        return rectangleJson;
    }

    /**
     * Checks if this Component object is equal to another object.
     *
     * @param obj the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Component component = (Component) obj;

        return row == component.getRow()
                && col == component.getCol()
                && id.equals(component.getId());
    }
}