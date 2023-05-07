package klotski_ids.controllers.panes;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.geometry.Point2D;
import javafx.scene.layout.AnchorPane;
import javafx.scene.shape.Rectangle;

import java.util.Arrays;
import java.util.EventListener;
import java.util.List;

public class levelController {
    public Rectangle rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9, rect10, empty1, empty2;
    public AnchorPane mainAnchorPane;
    private List<Rectangle> rectangles;

    @FXML
    private void initialize() {
        rectangles = Arrays.asList(rect1, rect2, rect3, rect4, rect5, rect6, rect7, rect8, rect9, rect10, empty1, empty2);

        for (Rectangle rect : rectangles) {
            rect.setOnMousePressed(event -> {
                // Salva la posizione iniziale del rettangolo
                rect.setUserData(new Point2D(event.getSceneX(), event.getSceneY()));
                event.consume();
            });

            rect.setOnMouseDragged(event -> {
                Point2D delta = new Point2D(event.getSceneX(), event.getSceneY()).subtract((Point2D) rect.getUserData());

                // Controlla se il rettangolo può essere spostato e imposta la nuova posizione
                if (canMove(rect, delta.getX(), delta.getY())) {
                    double newX = Math.min(Math.max(rect.getX() + delta.getX(), 0), mainAnchorPane.getWidth() - rect.getWidth());
                    double newY = Math.min(Math.max(rect.getY() + delta.getY(), 0), mainAnchorPane.getHeight() - rect.getHeight());
                    rect.setX(newX);
                    rect.setY(newY);

                    // Controlla se il rettangolo è stato spostato sopra empty1 o empty2
                    if ((empty1.getBoundsInParent().intersects(rect.getBoundsInParent()) && !empty1.equals(rect)) ||
                            (empty2.getBoundsInParent().intersects(rect.getBoundsInParent()) && !empty2.equals(rect))) {
                        // Scambia le posizioni del rettangolo e di empty1 o empty2
                        Point2D temp = new Point2D(empty1.getX(), empty1.getY());
                        empty1.setX(empty2.getX());
                        empty1.setY(empty2.getY());
                        empty2.setX(temp.getX());
                        empty2.setY(temp.getY());
                    }
                }

                // Salva la posizione attuale del rettangolo
                rect.setUserData(new Point2D(event.getSceneX(), event.getSceneY()));
                event.consume();
            });

        }
    }
    private boolean canMove(Rectangle rect, double deltaX, double deltaY) {
        // Calcola la nuova posizione del rettangolo
        double newX = Math.min(Math.max(rect.getX() + deltaX, 0), mainAnchorPane.getWidth() - rect.getWidth());
        double newY = Math.min(Math.max(rect.getY() + deltaY, 0), mainAnchorPane.getHeight() - rect.getHeight());

        // Controlla se il rettangolo si sovrappone ad altri rettangoli
        for (Rectangle r : rectangles) {
            if (r == rect) {
                continue;
            }

            if ((r == empty1 || r == empty2) && newX + rect.getWidth() > r.getX() && newX < r.getX() + r.getWidth() &&
                    newY + rect.getHeight() > r.getY() && newY < r.getY() + r.getHeight()) {
                // Scambia la posizione di empty1 o empty2 con il rettangolo che gli si piazza sopra
                if (r == empty1) {
                    empty1.setX(rect.getX());
                    empty1.setY(rect.getY());
                } else {
                    empty2.setX(rect.getX());
                    empty2.setY(rect.getY());
                }
                return true;
            } else if (newX + rect.getWidth() > r.getX() && newX < r.getX() + r.getWidth() &&
                    newY + rect.getHeight() > r.getY() && newY < r.getY() + r.getHeight()) {
                return false;
            }
        }

        return true;
    }

}
