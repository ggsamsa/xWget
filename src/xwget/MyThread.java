/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xwget;

/**
 *
 * @author erase
 */
public class MyThread extends Thread{
    
    boolean pleaseWait = false;

    MyThread(Runnable task) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    // Este método é chamando quando a thread é iniciada
    public void run() {
        while (true) {
            // processamento...

            // Verifica se a thread deve esperar
            synchronized (this) {
                while (pleaseWait) {
                    try {
                        wait();
                    } catch (Exception e) {
                    }
                }
            }
            // processamento...
        }
    }
}
