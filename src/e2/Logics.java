package src.e2;

public interface Logics {
    void placeMines(int numberOfMines);
    boolean isMine(Pair<Integer, Integer> cell);
    int click(Pair<Integer, Integer> cell);
    boolean isGameOver();
    boolean isDisabled(Pair<Integer, Integer> cell);
    int getAdjacentMines(Pair<Integer, Integer> cell);
    void autoClick(Pair<Integer, Integer> cell);
    void placeFlag(Pair<Integer, Integer> cell);
    boolean isFlagged(Pair<Integer, Integer> cell);
}
