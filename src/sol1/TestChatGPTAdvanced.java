package src.sol1;

import static org.junit.Assert.*;
import java.util.*;
import org.junit.Test;

public class TestChatGPTAdvanced {

    private SubsequenceCombinerFactory factory;

    @org.junit.Before
    public void init() {
        this.factory = new SubsequenceCombinerFactoryImpl();
    }

    @Test
    public void testTripletsToSum() {
        SubsequenceCombiner<Integer, Integer> sc = this.factory.tripletsToSum();
        // Si isolano triple, e se ne fornisce la somma: una parte finale di 1 o 2 elementi è comunque sommata
        assertEquals(List.of(30, 300, 3000, 30),
                sc.combine(List.of(10, 10, 10, 100, 100, 100, 1000, 1000, 1000, 10, 20))
        );
        assertEquals(List.of(18, 300),
                sc.combine(List.of(5, 6, 7, 100, 100, 100))
        );
        // Aggiunto un caso con meno di 3 elementi
        assertEquals(List.of(15),
                sc.combine(List.of(5, 5, 5))
        );
    }

    @Test
    public void testTripletsToList() {
        SubsequenceCombiner<Integer, List<Integer>> sc = this.factory.tripletsToList();
        // Come nel caso precedente, ma si formino liste
        assertEquals(List.of(List.of(10, 10, 10), List.of(100, 100, 100), List.of(1000, 1000, 1000), List.of(10, 20)),
                sc.combine(List.of(10, 10, 10, 100, 100, 100, 1000, 1000, 1000, 10, 20))
        );
        assertEquals(List.of(List.of(10, 10, 10), List.of(100, 100, 100)),
                sc.combine(List.of(10, 10, 10, 100, 100, 100))
        );
        // Caso con meno di 3 elementi
        assertEquals(List.of(List.of(1, 2)),
                sc.combine(List.of(1, 2))
        );
    }

    @Test
    public void testCountUntilZero() {
        SubsequenceCombiner<Integer, Integer> sc = this.factory.countUntilZero();
        // Trovato uno zero (o fine lista), si produca il numero di elementi fin qui
        assertEquals(List.of(3, 2, 4, 2),
                sc.combine(List.of(1, 1, 1, 0, 2, 2, 0, 3, 3, 3, 3, 0, 5, 6))
        );
        assertEquals(List.of(3, 2),
                sc.combine(List.of(10, 10, 10, 0, 2, 3, 0))
        );
        // Caso senza zero
        assertEquals(List.of(3),
                sc.combine(List.of(1, 2, 3))
        );
    }

    @Test
    public void testSingleReplacer() {
        // La combine in questo caso è come la map degli stream
        SubsequenceCombiner<String, String> sc = this.factory.singleReplacer(s -> s + s);
        assertEquals(List.of("aa", "bb", "cc"),
                sc.combine(List.of("a", "b", "c"))
        );
        SubsequenceCombiner<String, Integer> sc2 = this.factory.singleReplacer(s -> s.length());
        assertEquals(List.of(1, 3, 2),
                sc2.combine(List.of("a", "bbb", "cc"))
        );
        // Caso con stringhe vuote
        assertEquals(List.of(0, 0, 1),
                sc2.combine(List.of("", "", "a"))
        );
    }

    @Test
    public void testCumulativeToList() {
        SubsequenceCombiner<Integer, List<Integer>> sc = this.factory.cumulateToList(100);
        // Soglia 100: non appena la somma degli elementi trovati diventa >=100 (o c'è fine lista)
        // la sottosequenza viene data in uscita
        assertEquals(List.of(List.of(10, 50, 70), List.of(80, 20), List.of(30, 30, 39, 30), List.of(40)),
                sc.combine(List.of(10, 50, 70, 80, 20, 30, 30, 39, 30, 40))
        );
        // Caso in cui la somma esatta raggiunge la soglia
        assertEquals(List.of(List.of(100), List.of(50, 50), List.of(40)),
                sc.combine(List.of(100, 50, 50, 40))
        );
        // Caso con somma inferiore alla soglia
        assertEquals(List.of(List.of(10, 20, 30)),
                sc.combine(List.of(10, 20, 30))
        );
    }
}

