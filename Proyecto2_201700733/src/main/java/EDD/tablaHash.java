/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;
import Principal.Usuario;
import java.security.MessageDigest;
import javax.swing.JOptionPane;
import javax.xml.bind.DatatypeConverter;
import javax.swing.JTextField;
/**
 *
 * @author byron
 */
public class tablaHash {
    private ListaEnlazada<Usuario>[] guardaUsuario;
    //insertarInicio
    private int size= 45;
    private int pos=0;
    private int elemento=0;
    public tablaHash(){
        guardaUsuario= new ListaEnlazada[size];
        for (int i = 0; i < size; i++) {
            guardaUsuario[i]=null;
        }
    }
    public void obtenerUsuario(int numeroCarnet, String nombre, String apellido, String carrera, String password) throws Exception{
        int posicion= F(numeroCarnet);
        String pass= generarHash(password);
        if(guardaUsuario[posicion]==null)
            guardaUsuario[posicion].insertarFinal(new Usuario(numeroCarnet, nombre, apellido, carrera, pass));
        else
             guardaUsuario[posicion].insertarInicio(new Usuario(numeroCarnet, nombre, apellido, carrera, pass));
    }
   
    private int F(int numeroCarnet){
        int pos=0;
        pos= numeroCarnet % size;
        return pos;
    }

    private String generarHash(String text) throws Exception{
        byte[] mensajeEncriptado= text.getBytes("UTF-8");
        MessageDigest md= MessageDigest.getInstance("MD5");
        byte[] elMensaje= md.digest(mensajeEncriptado);
        String elHash= DatatypeConverter.printHexBinary(elMensaje).toUpperCase();
        System.out.println(elHash);
        return elHash;        
    }
    public boolean buscarCredenciales(String user, String pass) throws Exception{
        String password= generarHash(pass);
        int carnet= Integer.parseInt(user);
        for (int i = 0; i < size; i++) {
            if(guardaUsuario[i]!=null){
            for (int j = 0; j < guardaUsuario[i].getTamanio(); j++) {
                if(carnet== guardaUsuario[i].obtenerElemento(j).getCarnet() && password.equals(guardaUsuario[i].obtenerElemento(j).getContrasenia())) {
                    this.pos=i;
                    this.elemento=j;
                    return true;
                }
            }
            }
        }
        return false;
    }
    public void eliminarDato(String user, String pass) throws Exception{
         boolean bandera= buscarCredenciales(user, pass);
        if(bandera){
          guardaUsuario[pos].eliminar(elemento);
          JOptionPane.showMessageDialog(null, "Usuario Eliminado");
          }else{
            JOptionPane.showMessageDialog(null, "No se encontro usuario");
     }
    }
        public Usuario modificarDatos(String user, String pass) throws Exception{
            boolean bandera= buscarCredenciales(user,pass);
            Usuario persona= guardaUsuario[pos].obtenerElemento(elemento);
            if(bandera)
                return persona;
            else
                return null;
        }
 
}
