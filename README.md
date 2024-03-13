# **Lab 03 - Application of LLM is Software Engineering. **
## Leggenda:
Per facilitare la lettura si userà la seguente nomenclatura per i vari LLMs affrontati: 
* GTP: ChatGPT 3.5
* C: Copilot
* L: LangChain
* O: Ollama

Quando viene riportata la domanda fatta da me nel prompt dei vari LLM questa viene evidenziata tramite una sezione apposita e appare nel seguente formato, per esempio:
> Che tempo fa oggi?

GPT: Bello. \
C: Brutto. \
L: Nuvoloso. \
O: Pioverà.

## **Task 1: CODE GENERATION **
**Goal**: evaluate the effectiveness of various LLMs in generating solutions for OOP exam from previous years.
**Task**: utilize multiple LLMs (such as ChatGPT, GitHub Copilot, and Codex) to attempt solving past OOP exam questions. Assess which models deliver accurate solutions and document
any modifications you apply to enhance the models’ responses.
**Additional Task**: experiment with different prompting strategies (e.g., zero-shot, few-shot) to understand their impact on solutions

## **Task 1: Implementazione. **
Come caso d'uso ho voluto prendere la soluzione `a01a.e2` al seguente [repo](https://bitbucket.org/mviroli/workspace/repositories/) che ha la seguente specifica:
```txt
Scopo di questo esercizio è realizzare una GUI con l'aspetto mostrato nell'immagine fig1.png, fornita, 
che realizza la possibilità di selezionare un insieme di celle non contigue, per poi farle traslare
tutte insieme in alto-destra:
1 - l'utente clicka su una cella qualunque della griglia, e questa si numera incrementalmente
2 - può continuare a selezionare più celle, a patto che non ne selezioni una adiacente a una già numerata (fig 1)
3 - alla prima pressione su una cella *adiacente a una numerata* (orizzontale/verticale/diagonale), allora tutte le celle numerate si spostano in alto-destra di una posizione (fig 2)
4 - ad ogni altra successiva pressione di qualunque cella, tutte le celle numerate si spostano ulteriormente in alto-destra di una posizione (fig 3)
5 - non appena una pressione causerebbe l'uscita dalla griglia di una cella numerata (fig 3), a quel punto l'applicazione si chiuda

Sono considerati opzionali ai fini della possibilità di correggere l'esercizio, ma concorrono comunque 
al raggiungimento della totalità del punteggio:
- scorporamento via delegazione di tutti gli aspetti che non sono di view in una interfaccia+classe esterna
- gestione della fine del gioco
 
La classe GUI fornita, da modificare, include codice che potrebbe essere utile per la soluzione.

Indicazioni di punteggio:
- correttezza della parte obbligatoria: 10 punti
- qualità della parte opzionale: 5 punti
- correttezza della parte opzionale: 2 punti

```

Dunque, come primo passo ho fornito questo contesto ai vari LLM, altrimenti non avrebbero potuto rispondere alle domande in maniera sensata. Cosa si intende con contesto? Ciascuno di essi (in diversi modi) ha bisogno di conoscere la specifica intera del laboratorio e il codice delle classi implementate, che essa sia fornita tramite prompt per chi ne ha uno (come ChatGPT) o tramite qualsiasi altro mezzo, per esempio Copilot abilita un pulsante per visualizzare il contenuto delle classi prima di fargli una domanda.
> [!NOTE]  
> Poiché in ogni caso gli LLM capaci di tradurre in altre lingue effettuano internamente una conversione in inglese, per questioni di performance e riduzione di ambiguità delle parole si è scelto di scrivere direttamente in questa lingua.

A questo punto, la prima domanda è stata: 

> Given the context I've provided you, can you implement every point?

GPT: ``` To implement the requirements, we need to make several modifications to the provided code. Below is the modified code with comments explaining the changes: package a01a.e2;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class GUI extends JFrame {

    private static final long serialVersionUID = -6218820567019985015L;
    private final List<JButton> cells = new ArrayList<>();
    private final List<Pair<Integer, Integer>> selectedCells = new ArrayList<>(); // To store selected cells

    public GUI(int size) {
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
        new GUI(10);
    }
}
```

Ciò che sale subito all'occhio nella soluzione fornita è il fatto che GPT predilige sempre a unificare la soluzione ad un unico file evitando quelli che sono i buon principi di mantenimento del codice. Questo può essere ovviato chiedendo esplicitamente la suddivisione in classi. La soluzione fornita non è ottimale in quanto presenta diversi problemi evidenti:
1. La GUI non è come dovrebbe essere, infatti questo è l'aspetto che essa assume facendo eseguire il codice generato.
[!image_of_the_grid](./img/grid.png)
2. Posso numerare infinite celle purché queste siano di distanza adeguata, ma quando ne numero una adiacente azzera quelle precedenti (simulando un Game Over) e inserisce uno zero nella nuova cella selezionata e le celle non si muovono mai perciò non rispetta interamente i requirements funzionali richiesti.
[!image_of_the_grid_playing](./img/grid_playing.png)
[!image_of_the_grid_playing](./img/grid_game_over.png)

C: Brutto. \
L: Nuvoloso. \
O: Pioverà.

