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
    
    MyRunnable(Project project, JTextArea log, JTextField urlsLeft) {
        this.project = project;
        this.log = log;
        this.urlsLeft = urlsLeft;
    }

    @Override
    public void run() {
        try {
            Engine engine = new Engine(project, log, urlsLeft);
        } catch (IOException ex) {
            Logger.getLogger(MainWindow.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

