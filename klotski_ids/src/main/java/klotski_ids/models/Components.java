package klotski_ids.models;

/**
 This class represents a component in a grid, with its size and position information.
 */
public class Components {
    private String id;
    private int width;
    private int height;
    private int row;
    private int col;
    private int colSpan;
    private int rowSpan;

    /**
     Returns the height of this component.
     @return the height of this component
     */
    public int getHeight() {
        return height;
    }

    /**
     Returns the id of this component.
     @return the id of this component
     */
    public String getId() {
        return id;
    }

    /**
     Returns the width of this component.
     @return the width of this component
     */
    public int getWidth() {
        return width;
    }

    /**
     Returns the row index of the upper left corner of this component.
     @return the row index of the upper left corner of this component
     */
    public int getRow(){
        return row;
    }

    /**
     Returns the column index of the upper left corner of this component.
     @return the column index of the upper left corner of this component
     */
    public int getCol(){
        return col;
    }

    /**
     Returns the number of columns occupied by this component.
     @return the number of columns occupied by this component
     */
    public int getColSpan() {
        return colSpan;
    }

    /**
     Returns the number of rows occupied by this component.
     @return the number of rows occupied by this component
     */
    public int getRowSpan() {
        return rowSpan;
    }

    /**
     Sets the number of columns occupied by this component.
     @param colSpan the number of columns occupied by this component
     */
    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    /**
     Sets the number of rows occupied by this component.
     @param rowSpan the number of rows occupied by this component
     */
    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }

    /**
     Sets the column index of the upper left corner of this component.
     @param col the column index of the upper left corner of this component
     */
    public void setCol(int col) {
        this.col = col;
    }

    /**
     Sets the height of this component.
     @param height the height of this component
     */
    public void setHeight(int height) {
        this.height = height;
    }

    /**
     Sets the row index of the upper left corner of this component.
     @param row the row index of the upper left corner of this component
     */
    public void setRow(int row) {
        this.row = row;
    }

    /**
     Sets the width of this component.
     @param width the width of this component
     */
    public void setWidth(int width) {
        this.width = width;
    }

    /**
     Sets the id of this component.
     @param id the id of this component
     */
    public void setId(String id) {
        this.id = id;
    }

}