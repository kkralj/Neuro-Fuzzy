package hr.fer.zemris.fuzzy.zad5.demo;

import hr.fer.zemris.fuzzy.zad5.gui.training.TrainingFrame;

import javax.swing.*;
import java.lang.reflect.InvocationTargetException;

public class TrainingDemo {

    public static final int M = 30;

    public static void main(String[] args) throws InvocationTargetException, InterruptedException {
        SwingUtilities.invokeAndWait(() -> {
            new TrainingFrame(M).setVisible(true);
        });

    }
}
