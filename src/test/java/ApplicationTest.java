import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class ApplicationTest {
    private Application app;
    private static final String firstProblem = "1\n4\n0 1 2\n";
    private static final String secondProblem = "1\n8\n0 1 2 0 0 3 3\n";
    private static final String thirdProblem = "1\n9\n0 1 1 1 1 0 2 2\n";
    private static final String happyPathProblems = "3\n4\n0 1 2\n8\n0 1 2 0 0 3 3\n9\n0 1 1 1 1 0 2 2\n";

    @BeforeEach
    void setUp() {
        app = new Application();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void firstProblem() {
        ByteArrayInputStream in = new ByteArrayInputStream(firstProblem.getBytes());
        System.setIn(in);

        List<Integer> expected = Stream.of(2).collect(Collectors.toList());
        List<Integer> actual = app.compute();

        assertEquals(actual.size(), expected.size());
        assertIterableEquals(actual, expected);
    }

    @Test
    void secondProblem() {
        ByteArrayInputStream in = new ByteArrayInputStream(secondProblem.getBytes());
        System.setIn(in);

        List<Integer> expected = Stream.of(4).collect(Collectors.toList());
        List<Integer> actual = app.compute();

        assertEquals(actual.size(), expected.size());
        assertIterableEquals(actual, expected);
    }

    @Test
    void thirdProblem() {
        ByteArrayInputStream in = new ByteArrayInputStream(thirdProblem.getBytes());
        System.setIn(in);

        List<Integer> expected = Stream.of(5).collect(Collectors.toList());
        List<Integer> actual = app.compute();

        assertEquals(actual.size(), expected.size());
        assertIterableEquals(actual, expected);
    }

    @Test
    void happyPathProblems() {
        ByteArrayInputStream in = new ByteArrayInputStream(happyPathProblems.getBytes());
        System.setIn(in);

        List<Integer> expected = Stream.of(2,4,5).collect(Collectors.toList());
        List<Integer> actual = app.compute();

        assertEquals(actual.size(), expected.size());
        assertIterableEquals(actual, expected);
    }

    @Test
    void errorLessThanMinNumOfCities(){
        ByteArrayInputStream in = new ByteArrayInputStream("1\n1\n".getBytes());
        System.setIn(in);

        List<Integer> actual = app.compute();
        assertEquals(actual.size(), 0);
    }

    @Test
    void errorGreaterThanMaxNumOfCities(){
        ByteArrayInputStream in = new ByteArrayInputStream("1\n601\n".getBytes());
        System.setIn(in);

        List<Integer> actual = app.compute();
        assertEquals(actual.size(), 0);
    }

    @Test
    void errorNumOfRoads(){
        ByteArrayInputStream in = new ByteArrayInputStream("1\n3\n0 1 2\n".getBytes());
        System.setIn(in);

        List<Integer> actual = app.compute();
        assertEquals(actual.size(), 0);
    }

    @Test
    void errorNumOfRoadss(){
        ByteArrayInputStream in = new ByteArrayInputStream("1\n3\n0 8\n".getBytes());
        System.setIn(in);

        List<Integer> actual = app.compute();
        assertEquals(actual.size(), 0);
    }
}