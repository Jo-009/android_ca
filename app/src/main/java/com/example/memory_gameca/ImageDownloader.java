package com.example.memory_gameca;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

public class ImageDownloader {
    protected static boolean downloadImage(String imgURL, File destFile) {
        //try catch as URLConnection may throw exception
        //e.g. when server or file not found
        try {
            URL url = new URL(imgURL);
            //opens connection to server to retrieve file
            URLConnection conn = url.openConnection();
            //acquires INPUT stream to read data with
            InputStream in = conn.getInputStream();
            //acquires OUTPUT stream to write received data into based on given file obj
            FileOutputStream out = new FileOutputStream(destFile);

            byte[] buf = new byte[1024];
            int bytesRead = -1;
            //blocking operation -read()
            //hence above code should be excuted within background thread
            while((bytesRead = in.read(buf)) != -1) {
                out.write(buf, 0, bytesRead);
                //writes incoming data into output stream acquired
                // from "FileOutputStream out" line
            }

            out.close();
            in.close();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
