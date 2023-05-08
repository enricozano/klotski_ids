package klotski_ids.controllers.util;

public class Components {
    private String id;
    private int width;
    private int height;
    private int row;
    private int col;
    private int colSpan;
    private int rowSpan;

    public int getHeight() {
        return height;
    }

    public String getId() {
        return id;
    }

    public int getWidth() {
        return width;
    }

    public int getRow(){
        return row;
    }

    public int getCol(){
        return col;
    }

    public int getColSpan() {
        return colSpan;
    }

    public int getRowSpan() {
        return rowSpan;
    }

    public void setColSpan(int colSpan) {
        this.colSpan = colSpan;
    }

    public void setRowSpan(int rowSpan) {
        this.rowSpan = rowSpan;
    }


    public void setCol(int col) {
        this.col = col;
    }

    public void setHeight(int height) {
        this.height = height;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public void setWidth(int width) {
        this.width = width;
    }

    public void setId(String id) {
        this.id = id;
    }

}