package ui.gui;

import javax.swing.*;

// Graphical template
public abstract class GTemplate extends JFrame {

    protected static final int WIDTH = 1280;
    protected static final int HEIGHT = 720;
//    protected boolean running = true;

    public GTemplate(String title) {
        super(title);
        initializeFields();
        initializeGraphics();
        initializeAction();
    }

    protected abstract void initializeFields();

    protected abstract void initializeGraphics();

    protected abstract void initializeAction();

    // MODIFIES: this
    // EFFECT: close current window
    protected void close() {
        setDefaultLookAndFeelDecorated(false);
        dispose();
    }

}
