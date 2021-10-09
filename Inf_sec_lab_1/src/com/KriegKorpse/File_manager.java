package com.KriegKorpse;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Scanner;

public class File_manager {
    protected String read(String file_name){
        String outString;
        Scanner in = null;
        try {
            in = new Scanner(new FileReader(file_name)).useDelimiter("\\Z");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        while(in.hasNext()) {
            sb.append(in.next() + " ");
        }
        in.close();
        return sb.toString();
    }

    protected String read_line(String file_name){
        String outString;
        Scanner in = null;
        try {
            in = new Scanner(new FileReader(file_name)).useDelimiter("\\Z");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        StringBuilder sb = new StringBuilder();
        //while(in.hasNext() && in.) {
            sb.append(in.nextLine());
        //}
        in.close();
        return sb.toString();
    }

    public void write(String text, String file_name, boolean append){
        String outString;

        try {
            FileWriter out = new FileWriter(file_name, append);//(file_name);
            out.write(text);
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void write(String text, String file_name, boolean append, String title_text){
        String outString;

        try {
            FileWriter out = new FileWriter(file_name, append);//(file_name);
            out.write(title_text + "\n");
            out.write(text);
            out.write("\n");
            out.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
