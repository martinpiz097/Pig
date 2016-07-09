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
import java.util.LinkedList;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @version 1.0
 * @author martin
 */
public final class Pig {

    private final Properties saver; 
    private final File fileHost;
    private String host;
    private FileOutputStream os;
    private FileInputStream is;
    private static Pig pig;
    
    /**
     * Método singleton de la clase Pig
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
     * llamado host.txt
     * @throws IOException En caso de no existir el archivo utilizado o al
     * existir errores de conexión
     */
    
    public Pig() throws IOException {
        saver = new Properties();
        fileHost = new File("host.txt");
        if(!fileHost.exists()) saveFile();
        else loadFile();
        loadPublicHost();
    }

    /**
     * Captura nuevamente la ip pública de la red
     * @throws IOException En caso de existir errores de conexión o en la utilización del
     * archivo
     */
    
    public void reload() throws IOException{
        loadPublicHost();
    }
    
    /**
     * Rescata la ip pública de la red actual
     * @throws IOException 
     */
    
    private void loadPublicHost() throws IOException{
        URL url = new URL("http://www.icanhazip.com");
        BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
        reader.lines().forEach((host) -> {
            try {
                this.setHost(host);
            } catch (IOException ex) {
                Logger.getLogger(Pig.class.getName()).log(Level.SEVERE, null, ex);
            }
        });
    }

    /**
     * Retorna la ip pública convertida en un arreglo numérico
     * @return un arreglo short con los datos de la ip
     */

    public Number[] getHostInBytes(){

        LinkedList<String> list = new LinkedList<>();
        char[] charArray = getHost().toCharArray();
        int currentIndex = 0;
        list.add("");
        
        for (int i = 0; i < charArray.length; i++) {
            if (charArray[i] != '.') 
                list.set(currentIndex, list.get(currentIndex) + charArray[i]);
            else{
                currentIndex++;
                list.add("");
            }
        }
        Number[] arrayNumberIp = new Number[list.size()];
        for (int i = 0; i < list.size(); i++) 
            arrayNumberIp[i] = Short.parseShort(list.get(i));
            
        return arrayNumberIp;
    }
    
    /**
     * Retorna una lista con los valores de la ip pública separados por puntos
     * @return Lista de valores separados por puntos de la ip pública
     */
    
    public LinkedList<Number> getHostInList(){
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
        
        LinkedList<Number> listIpValues = new LinkedList<>();
        listHost.stream().forEach((value) -> listIpValues.add(Short.parseShort(value)));
        return listIpValues;
    }
    
    /**
     * Retorna la ip pública de la red
     * @return Ip pública de la red
     */
    
    public String getHost(){
        return saver.getProperty("publicHost");
    }
    
    /**
     * Cambia el valor de la ip almacenado por el nuevo dato ingresado
     * por parámetro
     * @param host Nuevo valor de la ip a almacenar
     * @throws IOException En caso de existir errores en la utilización del archivo
     */
    
    public void setHost(String host) throws IOException{
        saver.setProperty("publicHost", host);
        saveFile();
    }
    
    /**
     * Actualiza el archivo con los datos que han sido editados
     * y no guardados anteriormente.
     * Nota: En el caso de los métodos reload(), loadPublicHost() y setHost()
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
     * Instancia el objeto fileHost como archivo con los datos de saver
     * @throws FileNotFoundException En caso de no encontrar el archivo
     * @throws IOException En caso de errores al utilizar el archivo
     */
    
    private void loadFile() throws FileNotFoundException, IOException{
        is = new FileInputStream(fileHost);
        saver.load(is);
    }
}

