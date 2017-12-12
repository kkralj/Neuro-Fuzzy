package hr.fer.zemris.fuzzy.zad5;

import hr.fer.zemris.fuzzy.zad5.gui.training.TrainingFrame;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class Main {
    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            new TrainingFrame().setVisible(true);
        });

    }
}
