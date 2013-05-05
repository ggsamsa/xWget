/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xwget;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

/**
 *
 * @author erase
 */
public class Project {
    String mainUrl;
    String savePath;
    String filetype;
    int depth;
    int threadNumber;
    
    Queue<String> queue = new LinkedList<>();
    List<String> visited = new ArrayList<>();
    
    public boolean validateMainUrl(){
        MyWebClient cl = new MyWebClient();
        
        int size = cl.getSize(mainUrl);
        
        if(size >= 0){
            return true;
        } else {
            return false;
        }
    }
    
    public boolean validateSavePath(){
        File dir = new File(savePath);
        if(dir.canWrite()){
            return true;
        } else {
            return false;
        }
    }
    
    public String getHost(){
        String host = mainUrl;
        int i = mainUrl.indexOf("/", 9);
        if (i>0) {
            host = mainUrl.substring(0, i);
        }
        return host;
    }
}
