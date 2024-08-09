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

    @Test
    public void testClickMine() {
        Logics logics = new LogicsImpl(10);
        logics.placeMines(20);
        Pair<Integer, Integer> mineCell = null;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                if (logics.isMine(new Pair<>(i, j))) {
                    mineCell = new Pair<>(i, j);
                    break;
                }
            }
            if (mineCell != null) {
                break;
            }
        }
        logics.click(mineCell);
        assertTrue(logics.isGameOver());
    }

    @Test
    public void testClickEmptyCell() {
        Logics logics = new LogicsImpl(10);
        logics.placeMines(20);
        Pair<Integer, Integer> emptyCell = null;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Pair<Integer, Integer> cell = new Pair<>(i, j);
                if (!logics.isMine(cell)) {
                    emptyCell = cell;
                    break;
                }
            }
            if (emptyCell != null) {
                break;
            }
        }
        logics.click(emptyCell);
        assertTrue(logics.isDisabled(emptyCell));
    }

    @Test
    public void testClickEmptyCellWithAdjacentMines() {
        Logics logics = new LogicsImpl(10);
        logics.placeMines(20);
        Pair<Integer, Integer> emptyCell = null;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Pair<Integer, Integer> cell = new Pair<>(i, j);
                if (!logics.isMine(cell)) {
                    emptyCell = cell;
                    break;
                }
            }
            if (emptyCell != null) {
                break;
            }
        }
        int adjacentMines = logics.click(emptyCell);
        assertEquals(logics.getAdjacentMines(emptyCell), adjacentMines);
    }

    @Test
    public void testAutoClick() {
        Logics logics = new LogicsImpl(10);
        logics.placeMines(20);
        Pair<Integer, Integer> emptyCell = null;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 10; j++) {
                Pair<Integer, Integer> cell = new Pair<>(i, j);
                if (!logics.isMine(cell) && logics.getAdjacentMines(cell) == 0) {
                    emptyCell = cell;
                    break;
                }
            }
            if (emptyCell != null) {
                break;
            }
        }
        logics.click(emptyCell);
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int x = emptyCell.getX() + dx;
                int y = emptyCell.getY() + dy;
                Pair<Integer, Integer> adjacentCell = new Pair<>(x, y);
                if (x >= 0 && x < 10 && y >= 0 && y < 10 && !logics.isMine(adjacentCell)) {
                    assertTrue(logics.isDisabled(adjacentCell));
                }
            }
        }
    }
}
