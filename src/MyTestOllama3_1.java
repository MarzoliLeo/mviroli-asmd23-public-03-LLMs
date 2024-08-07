package src;


import javax.swing.*;
import java.awt.*;

public class MyTestOllama3_1 {
    private int selectedCellCount = 0;
    private int[] selectedRows = new int[10];
    private int[] selectedCols = new int[10];
    private JTable table; // Aggiunta della variabile di istanza
    public static void main(String[] args) {
        // Create the frame (window)
        JFrame frame = new JFrame("Grid Translation GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        MyTestOllama3_1 gui = new MyTestOllama3_1();
        gui.createAndShowGUI(10, 10);
    }

    private void createAndShowGUI(int gridSizeX, int gridSizeY) {
        // Create a table with checkboxes for cell selection
        this.table = createGridTable(gridSizeX, gridSizeY); // Modifica qui
        JScrollPane scrollPane = new JScrollPane(this.table); // Modifica qui

        // Add buttons to translate selected cells
        JPanel buttonPanel = createButtonPanel();

        // Create the top-right panel (status bar)
        JPanel statusBarPanel = new JPanel();
        statusBarPanel.setLayout(new FlowLayout());
        JLabel statusLabel = new JLabel("Status: ");
        JButton copyButton = new JButton("Copy");

        statusBarPanel.add(statusLabel);
        statusBarPanel.add(copyButton);

        // Add components to the frame and make it visible
        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        JFrame frame = new JFrame("Grid Translation GUI");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().add(mainPanel, BorderLayout.NORTH);
        frame.getContentPane().add(statusBarPanel, BorderLayout.EAST);

        // Add cell click listener
        table.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                int row = table.getSelectedRow();
                int col = table.getSelectedColumn();

                if (row >= 0 && col >= 0) {
                    MyTestOllama3_1.this.getCellClickEvent(row, col);
                }
            }
        });

        frame.pack();
        frame.setVisible(true);
    }

    private void getCellClickEvent(int row, int col) {
        // Check if cell is adjacent to already selected cells
        boolean isAdjacent = false;
        for (int i = 0; i < selectedCellCount; i++) {
            int r = selectedRows[i];
            int c = selectedCols[i];

            if ((row == r && Math.abs(col - c) == 1)) {
                // Cell clicked on is adjacent to already selected cell
                moveSelectedCellsUpRight();
                return;
            }
        }

        if (!isAdjacent) {
            addSelectedCell(row, col);
            updateGUI();
        } else {
            JOptionPane.showMessageDialog(null, "Already selected this cell. Cannot select adjacent cells.");
        }
    }

    private void moveSelectedCellsUpRight() {
        // Move all the numbered cells up-right by one position
        for (int i = 0; i < selectedCellCount; i++) {
            int row = selectedRows[i];
            int col = selectedCols[i];

            String value = table.getValueAt(row, col).toString();

            // Move the cell up-right by one position
            int newRow = Math.max(0, row - 1);
            int newCol = Math.max(0, col + 1);

            // Remove the old cell from the table
            table.setValueAt("", row, col);

            // Insert the new cell at its new position
            table.setValueAt(value, newRow, newCol);
        }

        selectedCellCount = 0;
    }

    private void addSelectedCell(int row, int col) {
        selectedRows[selectedCellCount] = row;
        selectedCols[selectedCellCount] = col;
        selectedCellCount++;
    }

    private void updateGUI() {
        // Update the table's selected cell
        for (int i = 0; i < selectedCellCount; i++) {
            int row = selectedRows[i];
            int col = selectedCols[i];

            // Set the cell to be highlighted
            this.table.setRowSelectionInterval(row, row); // Modifica qui
            this.table.setColumnSelectionInterval(col, col); // Modifica qui

            // Add incrementing number to cell
            String value = "" + (i + 1) + "";
            this.table.setValueAt(value, row, col); // Modifica qui
        }
    }

    private JTable createGridTable(int gridSizeX, int gridSizeY) {
        return new JTable(new Object[gridSizeY][gridSizeX], new String[gridSizeY][gridSizeX]);
    }

    private JPanel createButtonPanel() {
        // Add buttons to translate selected cells
        JButton button1 = new JButton("Translate");
        JButton button2 = new JButton("Reset");

        JPanel panel = new JPanel();
        panel.add(button1);
        panel.add(button2);

        return panel;
    }

}
