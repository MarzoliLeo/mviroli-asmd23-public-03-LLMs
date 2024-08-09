package src.e2;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

public class LogicsImpl implements Logics {
    private final int size;
    private final Set<Pair<Integer, Integer>> mines = new HashSet<>();
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
    public void click(Pair<Integer, Integer> cell) {
        if (isMine(cell)) {
            gameOver = true;
        }
    }

    @Override
    public boolean isGameOver() {
        return gameOver;
    }
}
