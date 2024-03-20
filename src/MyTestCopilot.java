package src;

import javax.swing.*;
import java.awt.*;
import java.util.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

import static javax.swing.WindowConstants.EXIT_ON_CLOSE;

public class MyTestCopilot {
    // Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, * che realizza la possibilità di selezionare un insieme di celle non contigue, per poi farle traslare * tutte insieme in alto-destra: * 1 - l'utente clicka su una cella qualunque della griglia, e questa si numera incrementalmente * 2 - può continuare a selezionare più celle, a patto che non ne selezioni una adiacente a una già numerata (fig 1) * 3 - alla prima pressione su una cella adiacente a una numerata (orizzontale/verticale/diagonale), allora tutte le celle numerate si spostano in alto-destra di una posizione (fig 2) * 4 - ad ogni altra successiva pressione di qualunque cella, tutte le celle numerate si spostano ulteriormente in alto-destra di una posizione (fig 3) * 5 - non appena una pressione causerebbe l'uscita dalla griglia di una cella numerata (fig 3), a quel punto l'applicazione si chiuda * * Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque * al raggiungimento della totalità del punteggio: * - scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna * - gestione della fine del gioco

    public static void main(String[] args) {
        new MyTestCopilot(10);
    }

    private MyTestCopilot(int size) {
        var frame = new JFrame();
        frame.setDefaultCloseOperation(EXIT_ON_CLOSE);
        frame.setSize(100 * size, 100 * size);
        var panel = new JPanel(new GridLayout(size, size));
        frame.getContentPane().add(panel);
        var cells = new ArrayList<JButton>();
        var selectedCells = new ArrayList<Pair<Integer, Integer>>();
        ActionListener al = e -> {
            var jb = (JButton) e.getSource();
            handleCellClick(jb, cells, selectedCells, size);
        };
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                var pos = new Pair<>(j, i);
                final JButton jb = new JButton(pos.toString());
                cells.add(jb);
                jb.addActionListener(al);
                panel.add(jb);
            }
        }
        frame.setVisible(true);
    }

    private void handleCellClick(JButton clickedButton, List<JButton> cells, List<Pair<Integer, Integer>> selectedCells, int size) {
        int index = cells.indexOf(clickedButton);
        Pair<Integer, Integer> clickedPos = new Pair<>(index % 10, index / 10);
        if (isAdjacentToNumberedCell(clickedPos, selectedCells)) {
            moveNumberedCells(cells, selectedCells, size);
        }
        selectedCells.add(clickedPos);
        updateCellNumbers(cells, selectedCells);
        if (!areCellsWithinBounds(selectedCells, size)) {
            JOptionPane.showMessageDialog(null, "Game Over");
            System.exit(0);
        }
    }

    private boolean isAdjacentToNumberedCell(Pair<Integer, Integer> clickedPos, List<Pair<Integer, Integer>> selectedCells) {
        for (Pair<Integer, Integer> cell : selectedCells) {
            int x = cell.getX();
            int y = cell.getY();
            if ((Math.abs(clickedPos.getX() - x) <= 1) && (Math.abs(clickedPos.getY() - y) <= 1)) {
                return true;
            }
        }
        return false;
    }

    private void moveNumberedCells(List<JButton> cells, List<Pair<Integer, Integer>> selectedCells, int size) {
        for (Pair<Integer, Integer> cell : selectedCells) {
            int x = cell.getX();
            int y = cell.getY();
            if (x < size - 1 && y > 0) {
                cell.setX(x + 1);
                cell.setY(y - 1);
            }
        }
    }


    private void updateCellNumbers(List<JButton> cells, List<Pair<Integer, Integer>> selectedCells) {
        for (Pair<Integer, Integer> cell : selectedCells) {
            int index = cell.getY() * 10 + cell.getX();
            JButton jb = cells.get(index);
            jb.setText(String.valueOf(selectedCells.indexOf(cell) + 1));
        }
    }

    private boolean areCellsWithinBounds(List<Pair<Integer, Integer>> selectedCells, int size) {
        for (Pair<Integer, Integer> cell : selectedCells) {
            int x = cell.getX();
            int y = cell.getY();
            if (x >= size || y < 0) {
                return false;
            }
        }
        return true;
    }
}

