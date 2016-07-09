///*
// * To change this license header, choose License Headers in Project Properties.
// * To change this template file, choose Tools | Templates
// * and open the template in the editor.
// */
//package org.pig.net;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.DataOutputStream;
//import java.io.File;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.net.Socket;
//import java.net.URL;
//import java.util.Properties;
//import java.util.logging.Level;
//import java.util.logging.Logger;
//
///**
// *
// * @author martin
// */
//public final class PigOld {
//
//    private Properties p;
//    private File fileHost;
//    
//    public PigOld() {
//        fileHost = new File("host.txt");
//        
//        if(!fileHost.exists()) try {
//            fileHost.createNewFile();
//            if (isConected()) generateHost();
//            
//            else throw new IOException("No hay conexion a internet, por tanto \n"
//                        + "no se puede rescatar la IP publica del router");
//            
//        } catch (IOException ex) {
//            Logger.getLogger(Pig.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        
//        generateHost();
//    }
//
//    public void generateHost(){
//        
//        try {
//            
//            URL url = new URL("http://icanhazip.com");
//            BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
//            host = br.readLine().trim();
//        } catch (IOException ex) {
//            Logger.getLogger(Pig.class.getName()).log(Level.SEVERE, null, ex);
//        }        
//    }
//    
//    public void saveHost(){
//        
//        BufferedWriter bw = null;
//        try {
//            fileHost = new File("host.txt");
//            bw = new BufferedWriter(new FileWriter(fileHost));
//            bw.write(host);
//            bw.flush();
//            bw.close();
//            
//        } catch (IOException ex) {
//            Logger.getLogger(Pig.class.getName()).log(Level.SEVERE, null, ex);
//        } finally {
//            try {
//                bw.close();
//            } catch (IOException ex) {
//                Logger.getLogger(Pig.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//        
//    }
//    
//    public File getFileHost() {
//        return fileHost;
//    }
//
//    public String getHost() {
//        return host;
//    }
//    
//    public static boolean isConected() throws IOException{
//        
//        return new Socket("8.8.8.8", 80).isConnected();
//    }
//    
//    public void uploadHost(URL url) throws IOException{
//        
//        DataOutputStream dos = new DataOutputStream(url.openConnection().getOutputStream());
//        dos.writeUTF(host);
//        
//    }
//    
//    public void updateHost(URL url) throws IOException{
//    
//        BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
//        String hostCloud = br.readLine().trim();
//        
//        if (!host.equalsIgnoreCase(hostCloud)) {
//            
//            uploadHost(url);
//        }
//        
//    }
//    
//    private void loadFile(){
//        
//    }
//}
//
