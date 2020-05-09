/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;
import java.security.MessageDigest;
import javax.xml.bind.DatatypeConverter;
/**
 *
 * @author byron
 */
public class tablaHash {
    public void generarHash(String text) throws Exception{
        String dato= "36AB3F303DB5A5B1E16A37F8F9386C98";
        byte[] mensajeEncriptado= text.getBytes("UTF-8");
        MessageDigest md= MessageDigest.getInstance("MD5");
        byte[] elMensaje= md.digest(mensajeEncriptado);
        String elHash= DatatypeConverter.printHexBinary(elMensaje).toUpperCase();
        System.out.println(elHash);
        if (dato.equals(elHash)) 
            System.out.println("Comprobado");
       else
            System.out.println("no es el mismo");
    }
}
