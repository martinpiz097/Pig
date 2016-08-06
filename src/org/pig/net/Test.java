/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pig.net;

import java.io.IOException;

/**
 *
 * @author martin
 */
public class Test {

    /**
     * @param args the command line arguments
     * @throws java.io.IOException
     */
    
    public static void main(String[] args) throws IOException {

        Pig p = Pig.getPig();
        
        long ti, tf;
        
        while (true) {
            ti = start();
            p.getHostInBytes();
            tf = finish(ti);
            System.out.println("Tiempo: " + (tf));
        }

//        long ti, tf;
//
//        
//        int i = 0;
//        long acuTime = 0;
//        
//        for (i = 0; i < 10; i++) {
//            ti = start();
//            p.reload();
//            tf = finish(ti);
//            acuTime += tf;
//            System.out.println("Tiempo total reload: " + tf);
//            System.out.println(p.hasHost());
//        }
//        
//        System.out.println("---------------------");
//        System.out.println("Promedio de tiempos: " + ((double) acuTime / 10));
//        System.out.println("---------------------");
    }
    
    public static long start(){
        return System.nanoTime();
        //return System.currentTimeMillis();
    }
    
    public static long finish(long ti){
        return System.nanoTime() - ti;
        //return System.currentTimeMillis() - ti;
    }
    
}
