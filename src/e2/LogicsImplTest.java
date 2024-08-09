package src.e2;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class LogicsImplTest {
    @Test
    public void testPlaceMines() {
        Logics logics = new LogicsImpl(10);
        logics.placeMines(20);
        int mineCount = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (logics.isMine(new Pair<>(i, j))) {
                    mineCount++;
                }
            }
        }
        assertEquals(20, mineCount);
    }
}
