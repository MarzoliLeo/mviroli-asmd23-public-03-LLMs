package src.e2;

public interface Logics {
    void placeMines(int numberOfMines);
    boolean isMine(Pair<Integer, Integer> cell);
    void click(Pair<Integer, Integer> cell);
    boolean isGameOver();
}
