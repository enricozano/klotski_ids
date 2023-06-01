package klotski_ids.models;

/**
 * This class represents a component in a grid, with its properties such as ID, position, size, and span.
 */
public class Components {
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
    public Components(String id, int row, int col, int colSpan, int rowSpan, int width, int height) {
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
     * Checks if this Components object is equal to another object.
     *
     * @param obj the object to compare to
     * @return true if the objects are equal, false otherwise
     */
    public boolean equals(Object obj) {
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }

        Components component = (Components) obj;

        return row == component.getRow()
                && col == component.getCol()
                && id.equals(component.getId());
    }
}