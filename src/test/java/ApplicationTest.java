import messages.ExceptionMessages;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertIterableEquals;

class ApplicationTest {
    private static final String firstProblem = "1\n4\n0 1 2\n";
    private static final String secondProblem = "1\n8\n0 1 2 0 0 3 3\n";
    private static final String thirdProblem = "1\n9\n0 1 1 1 1 0 2 2\n";
    private static final String happyPathProblems = "3\n4\n0 1 2\n8\n0 1 2 0 0 3 3\n9\n0 1 1 1 1 0 2 2\n";

    @Test
    void firstProblem() {
        ByteArrayInputStream in = new ByteArrayInputStream(firstProblem.getBytes());
        System.setIn(in);

        List<Integer> expected = Stream.of(2).collect(Collectors.toList());
        List<Integer> actual = Application.compute();

        assertEquals(actual.size(), expected.size());
        assertIterableEquals(actual, expected);
    }

    @Test
    void secondProblem() {
        ByteArrayInputStream in = new ByteArrayInputStream(secondProblem.getBytes());
        System.setIn(in);

        List<Integer> expected = Stream.of(4).collect(Collectors.toList());
        List<Integer> actual = Application.compute();

        assertEquals(actual.size(), expected.size());
        assertIterableEquals(actual, expected);
    }

    @Test
    void thirdProblem() {
        ByteArrayInputStream in = new ByteArrayInputStream(thirdProblem.getBytes());
        System.setIn(in);

        List<Integer> expected = Stream.of(5).collect(Collectors.toList());
        List<Integer> actual = Application.compute();

        assertEquals(actual.size(), expected.size());
        assertIterableEquals(actual, expected);
    }

    @Test
    void happyPathProblems() {
        ByteArrayInputStream in = new ByteArrayInputStream(happyPathProblems.getBytes());
        System.setIn(in);

        List<Integer> expected = Stream.of(2,4,5).collect(Collectors.toList());
        List<Integer> actual = Application.compute();

        assertEquals(actual.size(), expected.size());
        assertIterableEquals(actual, expected);
    }

    private void errorNumOfCities(String input){
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));

        List<Integer> actual = Application.compute();
        assertEquals(actual.size(), 0);
        assertEquals(ExceptionMessages.NUM_OF_CITIES_RANGE_ERR, err.toString());
    }

    @Test
    void errorLessThanMinNumOfCities(){
        errorNumOfCities("1\n1\n");
    }

    @Test
    void errorLessThanMinNumOfCities2(){
        errorNumOfCities("1\n-1\n");
    }

    @Test
    void errorGreaterThanMaxNumOfCities(){
        errorNumOfCities("1\n601\n");
    }

    private void errorNumOfRoadsTest(String input){
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));

        List<Integer> actual = Application.compute();
        assertEquals(actual.size(), 0);
        assertEquals(ExceptionMessages.NUM_OF_ROADS_RANGE_ERR, err.toString());
    }

    @Test
    void errorNumOfRoads(){
        errorNumOfRoadsTest("1\n3\n0 1 2\n");
    }

    @Test
    void errorNumOfRoads2(){
        errorNumOfRoadsTest("1\n4\n0 1 2 0\n");
    }

    private void errorRoadPathOutOfRange(String input){
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));

        List<Integer> actual = Application.compute();
        assertEquals(actual.size(), 0);
        assertEquals(ExceptionMessages.ROAD_PATH_OUT_OF_RANGE_ERR, err.toString());
    }

    @Test
    void errorRoadPathOutOfRange(){
        errorRoadPathOutOfRange("1\n3\n0 8\n");
    }

    @Test
    void errorRoadPathOutOfRange2(){
        errorRoadPathOutOfRange("1\n3\n-1 0\n");
    }

    private void errorGraphIsNotAcyclicTest(String input){
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));

        List<Integer> actual = Application.compute();
        assertEquals(actual.size(), 0);
        assertEquals(ExceptionMessages.GRAPH_IS_NOT_ACYCLIC, err.toString());
    }

    @Test
    void errorGraphIsNotAcyclic(){
        errorGraphIsNotAcyclicTest("1\n3\n1 1\n");
    }

    @Test
    void errorGraphIsNotAcyclic2(){
        errorGraphIsNotAcyclicTest("1\n4\n2 3 1\n");
    }

    private void errorNumberFormatTest(String input){
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));

        List<Integer> actual = Application.compute();
        assertEquals(actual.size(), 0);
        assertEquals(ExceptionMessages.NUMBER_FORMAT_ERR, err.toString());
    }

    @Test
    void errorNumberFormat(){
        errorNumberFormatTest("1,\n");
    }

    @Test
    void errorNumberFormat2(){
        errorNumberFormatTest("1\n4,\n");
    }

    @Test
    void errorNumberFormat3(){
        errorNumberFormatTest("1\n4\n0,1,2\n");
    }

    @Test
    void errorNumberFormat4(){
        errorNumberFormatTest("1\n4\n0-1 2\n");
    }

    private void errorNumOfProblemsTest(String input){
        ByteArrayInputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);
        ByteArrayOutputStream err = new ByteArrayOutputStream();
        System.setErr(new PrintStream(err));

        List<Integer> actual = Application.compute();
        assertEquals(actual.size(), 0);
        assertEquals(ExceptionMessages.NUM_OF_PROBLEMS_RANGE_ERR, err.toString());
    }

    @Test
    void errorNumOfProblems(){
        errorNumOfProblemsTest("0\n");
    }

    @Test
    void errorNumOfProblems2(){
        errorNumOfProblemsTest("1001\n");
    }

}