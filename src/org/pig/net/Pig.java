/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pig.net;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @version 1.0
 * @author martin
 */
public final class Pig {

    private final Properties saver; 
    private final URL urlHost;
    private final File fileHost;
    private BufferedReader reader;
    private FileOutputStream os;
    private FileInputStream is;
    private static Pig pig;
    
    /**
     * Método singleton de la clase Pig.
     * @return un objeto Pig estático instanciado a partir
     * del metodo
     */
    
    public static Pig getPig(){
        if(pig == null) try {
            pig = new Pig();
        } catch (IOException ex) {
            Logger.getLogger(Pig.class.getName()).log(Level.SEVERE, null, ex);
        }
        return pig;
    }
    
    /**
     * Constructor de Pig: Public Ip's Generator
     * Obtenemos la ip pública de nuestra red y ésta se guardará en un archivo
     * llamado host.txt.
     * @throws IOException En caso de no existir el archivo utilizado o al
     * existir errores de conexión
     */
    
    public Pig() throws IOException {
        this.urlHost = new URL("http://icanhazip.com");
        saver = new Properties();
        fileHost = new File("host.txt");
        if(!fileHost.exists()) saveFile();
        else loadFile();
        loadPublicHost();
    }

    /**
     * Rescata nuevamente la ip pública de la red y la almacena en el archivo.
     * @throws IOException En caso de existir errores de conexión o en la utilización del
     * archivo
     */
    
    public void reload() throws IOException{
        loadPublicHost();
    }
    
    /**
     * Rescata la ip pública de la red actual y la almacena en el archivo.
     * @throws IOException En caso de existir errores en la utilización del archivo
     * en donde se escribira la ip capturada, si es así la ip se almancenará como "unknown"
     */
    
    private void loadPublicHost() throws IOException {
        try {
            /*
            isReader = new InputStreamReader(urlHost.openStream()); 
            String host = "";
            char[] array = new char[1024];

            int writen = 0;
            while (isReader.read() != -1){ 
                isReader.read(array);
                writen++;
            }
            for (int i = 0; i < writen; i++) 
                host.concat(String.valueOf(array[i]));
            this.setHost(host);
            */
            reader = new BufferedReader(new InputStreamReader(urlHost.openStream()));
            this.setHost(reader.lines().findFirst().orElse("unknown"));
            reader.close();
        } catch (IOException ex) {
            this.setHost("unknown");
        }

        // La ip del archivo sera la encontrada en la url, si no existe
        // se escribirá "unknown"
    }

    /**
     * Retorna la ip pública convertida en un arreglo numérico.
     * @return un arreglo short con los datos de la ip
     */

    public Short[] getHostInBytes(){

        char[] charArray = getHost().toCharArray();
        int cont = 0;
        for (char c1 : charArray)
            if (c1 != '.') 
                cont++;
        
        Short[] arrayNumberIp = new Short[cont];

        cont = 0;
        for (char c2 : charArray) 
            if (c2 != '.') 
                arrayNumberIp[cont] = Short.parseShort(c2 + "");
        
        
        return arrayNumberIp;
    }
    
    /**
     * Retorna una lista con los valores de la ip pública separados por puntos.
     * @return Lista de valores separados por puntos de la ip pública
     */
    
    public LinkedList<Short> getHostInList(){
        LinkedList<String> listHost = new LinkedList<>();
        char[] charArray = getHost().toCharArray();
        int currentIndex = 0;
        listHost.add("");
        
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] != '.') 
                listHost.set(currentIndex, listHost.get(currentIndex) + charArray[i]);
            else{
                currentIndex++;
                listHost.add("");
            }
        }
        
        LinkedList<Short> listIpValues = new LinkedList<>();
        listHost.stream().forEach((value) -> listIpValues.add(Short.parseShort(value)));
        return listIpValues;
    }
    
    /**
     * Retorna la ip pública ya capturada de la red.
     * @return Ip pública de la red
     */
    
    public String getHost(){
        return saver.getProperty("publicHost");
    }
    
    /**
     * Retorna un boolean en caso de existir alguna ip en el archivo host.txt.
     * @return true si el archivo tiene una ip almacenada y si ésta es distinta a "unknown", 
     * false en cualquier otro caso
     */
    
    public boolean hasHost(){
        String ip = saver.getProperty("publicHost");
        if(ip == null) return false;
        return !ip.isEmpty() || !ip.equalsIgnoreCase("unknown");
    }
    
    /**
     * Cambia el valor de la ip almacenado por el nuevo dato ingresado
     * por parámetro.
     * @param host Nueva ip a almacenar
     * @throws IOException En caso de existir errores en la utilización del archivo host.txt
     */
    
    public void setHost(String host) throws IOException{
        saver.setProperty("publicHost", host);
        saveFile();
    }
    
    /**
     * Borra toda la información del archivo host.txt
     * @throws IOException En caso de existir errores en la utilización del archivo host.txt
     */
    
    public void clear() throws IOException{
        saver.clear();
        saveFile();
    }
    
    /**
     * Actualiza el archivo con los datos que han sido editados
     * y no guardados anteriormente.
     * Nota: Para el caso de los métodos reload(), loadPublicHost() y setHost()
     * el archivo se actualiza automáticamente.
     * @throws FileNotFoundException En caso de no encontrar el archivo
     * @throws IOException En caso de existir errores en la utilización del archivo
     */
    
    public void saveFile() throws FileNotFoundException, IOException{
        os = new FileOutputStream(fileHost);
        saver.store(os, "file that content the current public ip");
        os.close();
    }
    
    /**
     * Configura el archivo host.txt para almacenar la ip, además instancia el objeto
     * fileHost con los datos este archivo.
     * @throws FileNotFoundException En caso de no encontrar el archivo
     * @throws IOException En caso de errores al utilizar el archivo
     */
    
    private void loadFile() throws FileNotFoundException, IOException{
        is = new FileInputStream(fileHost);
        saver.load(is);
    }
}

