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
    Boolean pleaseWait = false;
    int depth;
    
    Engine(Project projecto, int depth, JTextArea log, JTextField urlsLeft, JTextField urlsProcessed) throws IOException{
        this.project = projecto;
        this.log = log;
        this.urlsLeft = urlsLeft;
        this.urlsProcessed = urlsProcessed;
        this.mainHost = project.getHost();
        this.depth = depth;
        
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
        while (true) {
            url = project.queue.poll().toString();
            urlsLeft.setText(String.valueOf(project.queue.size()));
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
                log.append (" rejected [already processed]\n");
                continue;
            }
            String cType = cl.getType(url);
            
            log.append(" approved\n");
            
            project.visited.add(url);
            
            switch(cType){
                case "text/html":
                    if( (depth == -2) || (depth >= 0)  ){
                        List<String> links = Utils.extractLinks(url);
                        for (String link : links) {
                            if (!project.visited.contains(link)) {
                                project.queue.add(link);
                            }
                        }
                        if (!(depth == -2)){
                            depth -= 1;
                        }
                    }

                    urlsProcessed.setText(String.valueOf(project.visited.size()));
                    if(project.filetype.equals("All") || project.filetype.equals("HTML")){
                        sm.saveHtml(url);
                    }
                    break;
                default:
                    urlsProcessed.setText(String.valueOf(project.visited.size()));
                    switch (project.filetype) {
                        case "PDF":
                            if ("application/pdf".equals(cType)) {
                                sm.saveFile(url);
                            }
                            else{
                                log.append(" rejected [not PDF]\n");
                            }
                            break;
                        case "Images":
                            if (cType.startsWith("image")) {
                                sm.saveFile(url);
                            }
                            else{
                                log.append(" rejected [not an image]\n");
                            }
                            break;
                        case "All":
                            sm.saveFile(url);
                            break;
                    }
                    break;
            }
        }
    }
}