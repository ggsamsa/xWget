/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xwget;

import java.awt.TextField;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author erase
 */
public class MyRunnable implements Runnable {

    Project project;
    JTextArea log;
    JTextField urlsLeft;
    JTextField urlsProcessed;
    int depth;
    
    MyRunnable(Project project, int depth, JTextArea log, JTextField urlsLeft, JTextField urlsProcessed) {
        this.project = project;
        this.log = log;
        this.urlsLeft = urlsLeft;
        this.urlsProcessed = urlsProcessed;
        this.depth = depth;
    }

    @Override
    public void run() {
        try {
            Engine engine = new Engine(project, depth, log, urlsLeft, urlsProcessed);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

