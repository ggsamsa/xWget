/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package xwget;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * @author erase
 */
public class MyWebClient {

    public String getType(String url) {
        HttpURLConnection uc = null;
        try {
            URL iurl = new URL(url);
            uc = (HttpURLConnection) iurl.openConnection();
            uc.connect();
            int i = uc.getContentType().indexOf(';');
            if (i > 0) {
                return uc.getContentType().substring(0, i);
            }
        } catch (Exception e) {
        }
        String s = "unknown";
        try{
            s = uc.getContentType();
            
        } catch(Exception e) {
            
        }
        return s;
    }
    
    public int getSize(String url){
        HttpURLConnection uc = null;
        try {
            URL iurl = new URL(url);
            uc = (HttpURLConnection) iurl.openConnection();
            uc.connect();

        } catch (Exception e) {
        }
        return uc.getContentLength();
    }

    public String getPage(String url) throws Exception{
        URL page = new URL(url);
        StringBuilder text = new StringBuilder();
        HttpURLConnection conn = (HttpURLConnection) page.openConnection();
        conn.connect();
        InputStreamReader in = new InputStreamReader((InputStream) conn.getContent());
        BufferedReader buff = new BufferedReader(in);
        String line;
        do {
            line = buff.readLine();
            text.append(line).append("\n");
        } while (line != null);
        return text.toString();
    }
}
