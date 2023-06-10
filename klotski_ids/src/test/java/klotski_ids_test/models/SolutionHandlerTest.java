package klotski_ids_test.models;

import javafx.util.Pair;
import klotski_ids.models.SolutionHandler;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SolutionHandlerTest {

    @Test
    public void testReadSolutions() {
        String levelName = "Level 1";
        List<Pair<Integer,String>> actual = SolutionHandler.readSolutions(levelName);
        List<Pair<Integer, String>> expected = new ArrayList<>();
        expected.add(new Pair<>(6, "DOWN"));
        expected.add(new Pair<>(9, "LEFT"));
        expected.add(new Pair<>(5, "DOWN"));
        assertEquals(expected.get(0), actual.get(0));
        assertEquals(expected.get(1), actual.get(1));
        assertEquals(expected.get(2), actual.get(2));
    }
}