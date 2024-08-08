package src.sol1;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class TestChatGPT {

    private SubsequenceCombinerFactory factory;

    @org.junit.Before
    public void init() {
        this.factory = new SubsequenceCombinerFactoryImpl();
    }

    @Test
    public void testTripletsToSum() {
        SubsequenceCombiner<Integer, Integer> combiner = factory.tripletsToSum();
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<Integer> expected = Arrays.asList(6, 15, 15);
        assertEquals(expected, combiner.combine(input));
    }

    @Test
    public void testTripletsToList() {
        SubsequenceCombiner<Integer, List<Integer>> combiner = factory.tripletsToList();
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<List<Integer>> expected = Arrays.asList(
                Arrays.asList(1, 2, 3),
                Arrays.asList(4, 5, 6),
                Arrays.asList(7, 8)
        );
        assertEquals(expected, combiner.combine(input));
    }

    @Test
    public void testCountUntilZero() {
        SubsequenceCombiner<Integer, Integer> combiner = factory.countUntilZero();
        List<Integer> input = Arrays.asList(1, 2, 0, 4, 5, 6, 0, 7, 8, 9, 0);
        List<Integer> expected = Arrays.asList(2, 3, 3);
        assertEquals(expected, combiner.combine(input));
    }

    @Test
    public void testSingleReplacer() {
        SubsequenceCombiner<Integer, String> combiner = factory.singleReplacer(Object::toString);
        List<Integer> input = Arrays.asList(1, 2, 3);
        List<String> expected = Arrays.asList("1", "2", "3");
        assertEquals(expected, combiner.combine(input));
    }

    @Test
    public void testCumulateToList() {
        SubsequenceCombiner<Integer, List<Integer>> combiner = factory.cumulateToList(10);
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8);
        List<List<Integer>> expected = Arrays.asList(
                Arrays.asList(1, 2, 3, 4),
                Arrays.asList(5, 6),
                Arrays.asList(7, 8)
        );
        assertEquals(expected, combiner.combine(input));
    }
}

