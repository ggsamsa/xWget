/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xwget;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

/**
 *
 * @author erase
 */
public class SaveManager {
    
    String mainHost;
    String savePath;
    
    SaveManager(String mainHost, String savePath){
        this.mainHost = mainHost;
        this.savePath = savePath;
    }
    
    public void saveHtml(String url) throws Exception{
        MyWebClient cl = new MyWebClient();
        String page = cl.getPage(url);
        String convPage = page.replaceAll(mainHost, "");
        String pathDir = savePath + "/" + getDirectory(url);
        System.out.println(pathDir);
        File d = new File(pathDir);
        
        d.mkdirs();
        d.createNewFile();
        
        String pathFile = savePath + "/" + getDirectory(url) + "/" + getFilename(url);
        System.out.println(pathFile);
        File f = new File(pathFile);
        f.createNewFile();
        try{
            FileWriter fstream = new FileWriter(pathFile);
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(page);
            out.close();
        }
        catch (Exception e) {
            System.out.println("Error saving file: " + pathFile);
        }
    }
    
    public void saveFile(String url) throws MalformedURLException, FileNotFoundException, IOException{
        String path = savePath + "/" + getDirectory(url) + "/" + getFilename(url);
        System.out.println(path);
        File d = new File(savePath + "/" + getDirectory(url));
        d.mkdirs();
        d.createNewFile();
        URL website = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(path);
        fos.getChannel().transferFrom(rbc, 0, 1 << 24);
    }
    
    public void saveIndex(String url) throws MalformedURLException, FileNotFoundException, IOException{
        String path = savePath + "/" + getDirectory(url) + "/" + "index.html";
        System.out.println(path);
        File d = new File(savePath + "/" + getDirectory(url));
        d.mkdirs();
        d.createNewFile();
        URL website = new URL(url);
        ReadableByteChannel rbc = Channels.newChannel(website.openStream());
        FileOutputStream fos = new FileOutputStream(path);
        fos.getChannel().transferFrom(rbc, 0, 1 << 24);
    }
    
    private String getDirectory(String url){
        String tmp = url.replaceAll(mainHost, "");
        int j = tmp.lastIndexOf("/");
        return tmp.substring(0, j);
    }
    
    private String getFilename(String url){
        int j = url.lastIndexOf("/");
        String dirty = url.substring(j);
        String clean;
        clean = dirty.replaceAll("\\?", "_");
        clean = clean.replaceAll("\\s", "_");
        return clean;
    }
}