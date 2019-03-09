/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package x.wing.game;

import java.io.*;

public class reader {
    
    private String a;
    
    public String read() throws IOException
    {
     
        FileReader fr = new FileReader ("score.txt");
        BufferedReader br = new BufferedReader(fr);
        StringBuffer stringBuffer = new StringBuffer();
        String b;
        
        while ((b=br.readLine())!=null)
        {
            stringBuffer.append(b);
            stringBuffer.append("\n");
        }
        br.close();
        
        a = stringBuffer.toString();
        return a;
    }
}