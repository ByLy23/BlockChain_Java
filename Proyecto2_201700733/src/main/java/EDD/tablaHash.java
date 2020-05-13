/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;
import Principal.Usuario;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
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
            guardaUsuario[i]=new ListaEnlazada<>();
        }
    }
    public void obtenerUsuario(int numeroCarnet, String nombre, String apellido, String carrera, String password) throws Exception{
        int posicion= F(numeroCarnet);
        String pass= generarHash(password);
        boolean repetido= buscarRepetido(numeroCarnet);
        if(!repetido){
        if(guardaUsuario[posicion]==null)
            guardaUsuario[posicion].insertarFinal(new Usuario(numeroCarnet, nombre, apellido, carrera, pass));
        else
             guardaUsuario[posicion].insertarInicio(new Usuario(numeroCarnet, nombre, apellido, carrera, pass));
        }else
            JOptionPane.showMessageDialog(null, "Usuario Repetido");
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
     public boolean buscarRepetido(int carnet){
        for (int i = 0; i < size; i++) {
            if(guardaUsuario[i]!=null){
            for (int j = 0; j < guardaUsuario[i].getTamanio(); j++) {
                if(guardaUsuario[i].getTamanio()!=0){
                if(carnet== guardaUsuario[i].obtenerElemento(j).getCarnet()) 
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
        
        public void modificarDatos(Usuario usuario) throws Exception{
            String pass= generarHash(usuario.getContrasenia());
            guardaUsuario[pos].obtenerElemento(elemento).setApellido(usuario.getApellido());
            guardaUsuario[pos].obtenerElemento(elemento).setNombre(usuario.getNombre());
            guardaUsuario[pos].obtenerElemento(elemento).setCarrera(usuario.getCarrera());
            guardaUsuario[pos].obtenerElemento(elemento).setCarnet(usuario.getCarnet());
            guardaUsuario[pos].obtenerElemento(elemento).setContrasenia(pass);
            
        }
        
        public void graficar() throws IOException, InterruptedException{
            String b="";
            b=("Nodo"+guardaUsuario.hashCode());
            b+=("[label=\"<P0>");
            for (int i = 0; i < size; i++) {
                b+=(i);
                b+=("|<P"+(i+1)+">");
                if(guardaUsuario[i].getTamanio()==0){
                    b+=("/");
                }
            }
            b+=("\"];\n");
            
            for (int i = 0; i < size; i++) {
                if(guardaUsuario[i].getTamanio()!=0){
                    for (int j = 0; j < guardaUsuario[i].getTamanio(); j++) {
                       b+=("Nodo"+guardaUsuario[i].obtenerElemento(j).hashCode());
                        b+=("[label=\" Nombre: "+guardaUsuario[i].obtenerElemento(j).getNombre());
                        b+=("\nApellido:  "+guardaUsuario[i].obtenerElemento(j).getApellido());
                        b+=("\nCarnet: "+guardaUsuario[i].obtenerElemento(j).getCarnet());
                        b+=("\nPassWord Encriptada: "+guardaUsuario[i].obtenerElemento(j).getContrasenia());
                        b+="\"];\n";
                        if((j+1)<guardaUsuario[i].getTamanio())
                            b+=("Nodo"+guardaUsuario[i].obtenerElemento(j).hashCode()+"->"+"Nodo"+guardaUsuario[i].obtenerElemento(j+1).hashCode()+"\n");
                    }
                   b+=("Nodo"+guardaUsuario.hashCode()+":P"+i+" ->"+"Nodo"+guardaUsuario[i].obtenerElemento(0).hashCode()+"\n");
                }
            }
            
            System.out.println(b);
            mostrarHash(b);
        }
        private void mostrarHash(String dato) throws IOException, InterruptedException{
            String mensaje="digraph Hash{ \n rankdir= LR\n node[shape=record, width= 0.1, height= 0.1];\n"+dato+"\n}";
            FileWriter file= new FileWriter("Hash.dot");
            PrintWriter impresion= new PrintWriter(file);
            impresion.println(mensaje);
            file.close();
            String comando= "dot -Tpng Hash.dot -o Hash.png";
            Runtime rt= Runtime.getRuntime();
            rt.exec(comando);
            Thread.sleep(500);
        }
}
