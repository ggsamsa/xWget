/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xwget;

import java.io.File;

/**
 *
 * @author erase
 */
public class Project {
    String mainUrl;
    String savePath;
    int depth;
    int threadNumber;
    
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
