/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package javaapplication2;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.List;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//import static javaapplication2.TestRead.generalFetcher;

/**
 *
 * @author foufou
 */
class TestRead_CR {

    TestRead_CR(String Classpath, String Testpath) throws FileNotFoundException, UnsupportedEncodingException, IOException {
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    System.out.println("class: "+Classpath+"\n test: "+Testpath);
    main(Classpath,Testpath);
    
    }

    public static void main(String Classpath, String Testpath) throws FileNotFoundException, UnsupportedEncodingException, IOException {
//        Pattern patternQuestion;
//        Matcher matcherQuestion;
//        patternQuestion = Pattern.compile("^%frage=");
//        matcherQuestion = patternQuestion.matcher(strLine);
//        if (matcherQuestion.find()) { // findet %frage: ....
//        }


// Read the Java Class File 
        FileInputStream fstreamC = new FileInputStream(Classpath);
        DataInputStream inC = new DataInputStream(fstreamC);
        BufferedReader brC = new BufferedReader(new InputStreamReader(inC, "ISO-8859-15"));
        String strLineC;
        System.out.println("########        Begin Answer Class     ########\n");
        while ((strLineC = brC.readLine()) != null) {
            System.out.println(strLineC + "\n");
        }

// Read the Java Class File 
        FileInputStream fstreamT = new FileInputStream(Testpath);
        DataInputStream inT = new DataInputStream(fstreamT);
        BufferedReader brT = new BufferedReader(new InputStreamReader(inT, "ISO-8859-15"));
        String strLineT;
        System.out.println("########        Begin Test Class     ########\n");
        String buffer="";
        while ((strLineT = brT.readLine()) != null) {
            System.out.println(strLineT + "\n");
            buffer+=strLineT.trim();
                    
        } 
        String buffer1 = buffer.trim().replace("}@Test", "$@Test").replace("}/**", "$/**").replace("}}", "$}");
        //(@Test[^{]*)([^$]*)
        System.out.println(buffer1 + "\n");

    }
    
}
        
