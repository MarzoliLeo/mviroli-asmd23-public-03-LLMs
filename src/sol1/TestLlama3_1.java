package src.sol1;


import java.util.*;
import java.util.function.Function;

import static org.junit.Assert.*;
import org.junit.Test;

public class TestLlama3_1 {

    private SubsequenceCombinerFactory factory;

    @org.junit.Before
    public void init() {
        this.factory = new SubsequenceCombinerFactoryImpl();
    }

    @Test
    public void testTripletsToSum() {
        // Test case for tripletsToSum()
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        SubsequenceCombiner<Integer, Integer> combiner = factory.tripletsToSum();
        List<Integer> result = combiner.combine(input);
        assertEquals(Arrays.asList(6, 9), result);

        input = Arrays.asList(10, 20, 30, 40, 50);
        combiner = factory.tripletsToSum();
        result = combiner.combine(input);
        assertEquals(Arrays.asList(60, 120), result);
    }

    @Test
    public void testTripletsToList() {
        // Test case for tripletsToList()
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        SubsequenceCombiner<Integer, List<Integer>> combiner = factory.tripletsToList();
        List<List<Integer>> result = combiner.combine(input);
        assertEquals(Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5)), result);

        input = Arrays.asList(10, 20, 30, 40, 50);
        combiner = factory.tripletsToList();
        result = combiner.combine(input);
        assertEquals(Arrays.asList(Arrays.asList(10, 20, 30), Arrays.asList(40, 50)), result);
    }

    @Test
    public void testCountUntilZero() {
        // Test case for countUntilZero()
        List<Integer> input = Arrays.asList(1, 2, 0, 4, 5, 0, 7, 8, 9);
        SubsequenceCombiner<Integer, Integer> combiner = factory.countUntilZero();
        List<Integer> result = combiner.combine(input);
        assertEquals(Arrays.asList(2, 2), result);

        input = Arrays.asList(1, 2, 3, 4, 5, 6, 0, 7, 8, 9);
        combiner = factory.countUntilZero();
        result = combiner.combine(input);
        assertEquals(Arrays.asList(6, 3), result);
    }

    @Test
    public void testSingleReplacer() {
        // Test case for singleReplacer()
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5);
        Function<Integer, Integer> function = x -> x * 2;
        SubsequenceCombiner<Integer, Integer> combiner = factory.singleReplacer(function);
        List<Integer> result = combiner.combine(input);
        assertEquals(Arrays.asList(2, 4, 6, 8, 10), result);

        input = Arrays.asList(10, 20, 30, 40, 50);
        function = x -> x + 1;
        combiner = factory.singleReplacer(function);
        result = combiner.combine(input);
        assertEquals(Arrays.asList(11, 21, 31, 41, 51), result);
    }

    @Test
    public void testCumulateToList() {
        // Test case for cumulateToList()
        List<Integer> input = Arrays.asList(1, 2, 3, 4, 5, 6);
        SubsequenceCombiner<Integer, List<Integer>> combiner = factory.cumulateToList(7);
        List<List<Integer>> result = combiner.combine(input);
        assertEquals(Arrays.asList(Arrays.asList(1, 2, 3), Arrays.asList(4, 5, 6)), result);

        input = Arrays.asList(10, 20, 30, 40, 50);
        combiner = factory.cumulateToList(80);
        result = combiner.combine(input);
        assertEquals(Arrays.asList(Arrays.asList(10, 20, 30), Arrays.asList(40, 50)), result);
    }
}