package src;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class MyTestLLM extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private final List<JButton> cells = new ArrayList<>();
    private final List<Pair<Integer, Integer>> selectedCells = new ArrayList<>(); // To store selected cells

    public MyTestLLM(int size) {
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100 * size, 100 * size);

        JPanel panel = new JPanel(new GridLayout(size, size));
        this.getContentPane().add(panel);

        ActionListener al = e -> {
            var jb = (JButton) e.getSource();
            handleCellClick(jb);
        };

        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                var pos = new Pair<>(j, i);
                final JButton jb = new JButton(pos.toString());
                this.cells.add(jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    // Method to handle cell click
    private void handleCellClick(JButton clickedButton) {
        int index = cells.indexOf(clickedButton);
        Pair<Integer, Integer> clickedPos = new Pair<>(index % 10, index / 10);

        // Check if the clicked cell is adjacent to a numbered cell
        if (isAdjacentToNumberedCell(clickedPos)) {
            moveNumberedCells(); // Move all numbered cells to the top-right
        }

        // Update the selected cells and number them
        selectedCells.add(clickedPos);
        updateCellNumbers();

        // Check if the selected cells exceed the grid boundaries
        if (!areCellsWithinBounds()) {
            JOptionPane.showMessageDialog(this, "Game Over");
            System.exit(0);
        }
    }

    // Method to check if the clicked cell is adjacent to a numbered cell
    private boolean isAdjacentToNumberedCell(Pair<Integer, Integer> clickedPos) {
        for (Pair<Integer, Integer> pos : selectedCells) {
            if (Math.abs(pos.getX() - clickedPos.getX()) <= 1 && Math.abs(pos.getY() - clickedPos.getY()) <= 1) {
                return true;
            }
        }
        return false;
    }

    // Method to move all numbered cells to the top-right
    private void moveNumberedCells() {
        for (JButton cell : cells) {
            int index = cells.indexOf(cell);
            Pair<Integer, Integer> pos = new Pair<>(index % 10, index / 10);
            if (selectedCells.contains(pos)) {
                cell.setText(""); // Clear the text of the numbered cells
            }
        }
        selectedCells.clear(); // Clear the selected cells list
    }

    // Method to update cell numbers
    private void updateCellNumbers() {
        int count = 0;
        for (Pair<Integer, Integer> pos : selectedCells) {
            int index = pos.getX() + pos.getY() * 10;
            cells.get(index).setText(String.valueOf(count));
            count++;
        }
    }

    // Method to check if selected cells are within grid boundaries
    private boolean areCellsWithinBounds() {
        for (Pair<Integer, Integer> pos : selectedCells) {
            if (pos.getX() >= 10 || pos.getY() >= 10) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) throws java.io.IOException {
        new MyTestLLM(10);
    }
}

