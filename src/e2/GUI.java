package src.e2;

import javax.swing.*;
import javax.swing.event.MouseInputAdapter;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.util.HashMap;
import java.util.Map;

public class GUI extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private final Map<JButton,Pair<Integer,Integer>> buttons = new HashMap<>();
    private final Logics logics;

    public GUI(int size, int numberOfMines) {
        this.logics = new LogicsImpl(size);
        this.logics.placeMines(numberOfMines);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setSize(100*size, 100*size);

        JPanel panel = new JPanel(new GridLayout(size,size));
        this.getContentPane().add(BorderLayout.CENTER,panel);

        for (int i=0; i<size; i++){
            for (int j=0; j<size; j++){
                final JButton jb = new JButton(" ");
                jb.addActionListener(e -> {
                    final Pair<Integer,Integer> pos = buttons.get(jb);
                    int adjacentMines = logics.click(pos);
                    if (logics.isGameOver()) {
                        quitGame();
                        JOptionPane.showMessageDialog(this, "You lost!!");
                    } else {
                        jb.setText(adjacentMines > 0 ? String.valueOf(adjacentMines) : "");
                        jb.setEnabled(false);
                        if (logics.isGameOver()) {
                            quitGame();
                            JOptionPane.showMessageDialog(this, "You won!!");
                        }
                    }
                });
                jb.addMouseListener(new MouseInputAdapter() {
                    @Override
                    public void mouseClicked(MouseEvent e) {
                        if (e.getButton() == MouseEvent.BUTTON3) {
                            final Pair<Integer,Integer> pos = buttons.get(jb);
                            logics.placeFlag(pos);
                            jb.setText(logics.isFlagged(pos) ? "F" : "");
                        }
                    }
                });
                this.buttons.put(jb,new Pair<>(i,j));
                panel.add(jb);
            }
        }
        this.setVisible(true);
    }

    private void quitGame() {
        for (var entry: this.buttons.entrySet()) {
            JButton button = entry.getKey();
            Pair<Integer, Integer> pos = entry.getValue();
            if (logics.isMine(pos)) {
                button.setText("*");
            }
            button.setEnabled(false);
        }
    }
}
