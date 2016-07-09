/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.pig.net;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.Enumeration;

/**
 *
 * @author martin
 */
public class Main {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws UnknownHostException, SocketException {
        Enumeration<NetworkInterface> networkInterfaces = NetworkInterface.getNetworkInterfaces();
    
        while (networkInterfaces.hasMoreElements()) {
            NetworkInterface next = networkInterfaces.nextElement();
            System.out.println("Nombre: " + next.getDisplayName());
            Enumeration<InetAddress> ee = next.getInetAddresses();

            while (ee.hasMoreElements()) {
                InetAddress next2 = ee.nextElement();
                System.out.println("Direccion: " + next2.getHostAddress());
            }
            System.out.println("---------------------------------------------------------------");
        }
    }
    
}
