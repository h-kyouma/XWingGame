/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x.wing.game;

import java.io.*;

/**
 *
 * @author Kacper
 */
public class writer {

    private String a;
    
    public void write(String input) throws IOException
    {   
        a = input;
        
        FileWriter fw = new FileWriter("score.txt");
        BufferedWriter bw = new BufferedWriter (fw);
        PrintWriter pw = new PrintWriter (bw);
        
        pw.print(a);
        pw.close();
    }
}