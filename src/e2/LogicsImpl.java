package src.e2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LogicsImpl implements Logics {
    private final int size;
    private final Set<Pair<Integer, Integer>> mines = new HashSet<>();
    private final Set<Pair<Integer, Integer>> disabledCells = new HashSet<>();
    private final Set<Pair<Integer, Integer>> flaggedCells = new HashSet<>();
    private boolean gameOver = false;

    public LogicsImpl(int size) {
        this.size = size;
    }

    @Override
    public void placeMines(int numberOfMines) {
        Random random = new Random();
        while (mines.size() < numberOfMines) {
            int x = random.nextInt(size);
            int y = random.nextInt(size);
            mines.add(new Pair<>(x, y));
        }
    }

    @Override
    public boolean isMine(Pair<Integer, Integer> cell) {
        return mines.contains(cell);
    }

    @Override
    public int click(Pair<Integer, Integer> cell) {
        if (isMine(cell)) {
            gameOver = true;
            return -1;
        } else {
            disabledCells.add(cell);
            if (getAdjacentMines(cell) == 0) {
                autoClick(cell);
            }
            return getAdjacentMines(cell);
        }
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }

    @Override
    public boolean isDisabled(Pair<Integer, Integer> cell) {
        return disabledCells.contains(cell);
    }

    @Override
    public int getAdjacentMines(Pair<Integer, Integer> cell) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int x = cell.getX() + dx;
                int y = cell.getY() + dy;
                if (x >= 0 && x < size && y >= 0 && y < size && isMine(new Pair<>(x, y))) {
                    count++;
                }
            }
        }
        return count;
    }

    @Override
    public void autoClick(Pair<Integer, Integer> cell) {
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue;
                int x = cell.getX() + dx;
                int y = cell.getY() + dy;
                Pair<Integer, Integer> adjacentCell = new Pair<>(x, y);
                if (x >= 0 && x < size && y >= 0 && y < size && !isMine(adjacentCell) && !isDisabled(adjacentCell)) {
                    click(adjacentCell);
                }
            }
        }
    }

    @Override
    public void placeFlag(Pair<Integer, Integer> cell) {
        if (!isDisabled(cell)) {
            if (flaggedCells.contains(cell)) {
                flaggedCells.remove(cell);
            } else {
                flaggedCells.add(cell);
            }
        }
    }

    @Override
    public boolean isFlagged(Pair<Integer, Integer> cell) {
        return flaggedCells.contains(cell);
    }
}