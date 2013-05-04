/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xwget;

import java.io.IOException;
import java.util.ArrayList;
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
    
    
    Project project;
    JTextArea log;
    JTextField urlsLeft;
    JTextField urlsProcessed;
    String mainHost;
    
    
    Engine(Project projecto, JTextArea log, JTextField urlsLeft, JTextField urlsProcessed) throws IOException{
        this.project = projecto;
        this.log = log;
        this.urlsLeft = urlsLeft;
        this.urlsProcessed = urlsProcessed;
        this.mainHost = project.getHost();
        
        log.append("Engine created\n");
        
        project.queue.add(this.project.mainUrl);
        try{
            run();
        } catch (Exception e){
            
        }
    }
    
    private void run() throws IOException, Exception{
        System.setProperty ("jsse.enableSNIExtension", "false");
        Utils u = new Utils();
        MyWebClient cl = new MyWebClient();
        String url = this.project.mainUrl;
        System.out.println(project.getHost());
        SaveManager sm = new SaveManager(mainHost, project.savePath);
        sm.saveIndex(url);
        int tmp = Integer.parseInt(urlsLeft.getText());
        while (true) {
            url = project.queue.poll().toString();
            tmp -= 1;
            urlsLeft.setText(String.valueOf(tmp));
            if(url == null){
                log.append("\nQueue empty\n");
                break;
            }
            
            log.append("Checking URL " + url);
            if (!url.startsWith("http:")) {
                log.append(" rejected\n");
                continue;
            }
            
            if (!url.startsWith(mainHost)) {
                log.append (" rejected\n");
                continue;
            }
            
            if (project.visited.contains(url)) {
                log.append (" rejected\n");
                continue;
            }
            
            log.append(" approved\n");
            
            project.visited.add(url);
            
            switch(cl.getType(url)){
                case "text/html":
                    List<String> links = Utils.extractLinks(url);
                    for (String link : links){
                        if(!project.visited.contains(link)){
                            project.queue.add(link);
                            tmp += 1;
                        }
                    }

                    urlsProcessed.setText(String.valueOf(project.visited.size()));
                    sm.saveHtml(url);
                    break;
                default:
                    sm.saveFile(url);
                    break;
            }
        }
    }
}