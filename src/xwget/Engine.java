/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xwget;

import java.io.IOException;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import javax.swing.JTextArea;
import javax.swing.JTextField;

/**
 *
 * @author erase
 */
public class Engine {
    
    Queue<String> queue;
    Project project;
    JTextArea log;
    JTextField urlsLeft;
    String mainHost;
    
    Engine(Project projecto, JTextArea log, JTextField urlsLeft) throws IOException{
        this.queue = new LinkedList<>();
        this.project = projecto;
        this.log = log;
        this.urlsLeft = urlsLeft;
        
        log.append("Engine created\n");
        
        queue.add(this.project.mainUrl);
        try{
            run();
        } catch (Exception e){
            
        }
    }
    
    private void run() throws IOException, Exception{
        System.setProperty ("jsse.enableSNIExtension", "false");
        Utils u = new Utils();
        MyWebClient cl = new MyWebClient();
        String url = "";
        System.out.println(project.getHost());
    
        int tmp = Integer.parseInt(urlsLeft.getText());
        while (true) {
            url = queue.poll().toString();
            if(url == null){
                log.append("\nQueue empty\n");
                break;
            }
            
            log.append("Checking URL " + url);
            if (!url.startsWith("http:") || !"text/html".equals(cl.getType(url))) {
                log.append(" rejected\n");
                continue;
            }
            
            log.append(" approved\n");
            List<String> links = Utils.extractLinks(url);
            for (String link : links) {
                queue.add(link);
                tmp += 1;
                urlsLeft.setText(String.valueOf(tmp));
            }
        }
    }
}
