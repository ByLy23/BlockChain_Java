/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import EDD.ListaDoble;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.Timestamp;
import java.sql.Time;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author byron
 */
public class Instruccion {

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    public int getNonce() {
        return nonce;
    }

    public void setNonce(int nonce) {
        this.nonce = nonce;
    }

    public String getPreviusHash() {
        return previusHash;
    }

    public void setPreviusHash(String previusHash) {
        this.previusHash = previusHash;
    }

    public String getHashActual() {
        return hashActual;
    }

    public void setHashActual(String hashActual) {
        this.hashActual = hashActual;
    }
    private int index;
    private Date tiempo;
    private Date fecha;
    private String tiempoFecha;
    private int nonce;
    private String previusHash;
    private String hashActual;
    private ListaDoble<Bloque> bloques;
    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
    private String data;
    public Instruccion(){
        bloques= new ListaDoble<>();
       previusHash=hashActual;
       nonce=0;
       hashActual="0000";
       index=0;
    }
    private void crearBloque(){
        tiempo= new java.sql.Timestamp(System.currentTimeMillis());
        String datosCambiar= String.valueOf(index)+tiempo+previusHash+data;
        while(true){
            String datosCambiar1= datosCambiar+nonce;
            String siguienteHash= convertirSHA256(datosCambiar1);
            if(siguienteHash.substring(0, 4).equals("0000")){
                previusHash=hashActual;
                hashActual=siguienteHash;
                break;
            }
            System.out.println(hashActual);
            nonce++;
        }
    }
    public void sincronizar() throws Exception{
        formato="{\n\t\"INDEX\":"+index+",\n\"TIMESTAMP\": \""+tiempo+"\",\n\"NONCE\": "+nonce+",\n\"DATA\" : ["+data+"\n],\n\"PREVIUSHASH\": \""+previusHash+"\",\n\"HASH\":\""+hashActual+"\"\n}" ;
        crearBloque();
        guardarBloque();
        bloques.agregar_fin(new Bloque(previusHash,tiempo.toString(),hashActual,String.valueOf(index),String.valueOf(nonce)));
        index++;
        JOptionPane.showMessageDialog(null, "Sincronizado");
        formato="";
        data="";
    }
    
    public void crearUsuario(Usuario user){
        data+="\n{\n\t\"CREAR_USUARIO\": [\n\t{\n\t\"Carnet\": "+user.getCarnet()+",\n\"Nombre\": \""+user.getNombre()+"\",\n\"Apellido\": \""+user.getApellido()+"\",\n\"Carrera\": \""+user.getCarrera()+"\",\n\"Password\": \""+user.getContrasenia()+"\"\n}\n]\n}";
    }
    public void editarUsuario(Usuario user){
        data+="\n{\n\t\"EDITAR_USUARIO\": [\n\t{\n\t\"Carnet\": "+user.getCarnet()+",\n\"Nombre\": \""+user.getNombre()+"\",\n\"Apellido\": \""+user.getApellido()+"\",\n\"Carrera\": \""+user.getCarrera()+"\",\n\"Password\": \""+user.getContrasenia()+"\"\n}\n]\n}";
    }
    public void crearLibro(Libro user){
        data+="\n{\n\t\"CREAR_LIBRO\": [\n\t{\n\t\"ISBN\": "+user.getISBN()+",\n\"Año\": \""+user.getAnio()+"\",\n\"Idioma\": \""+user.getIdioma()+"\",\n\"Titulo\": \""+user.getTitulo()+"\",\n\"Editorial\": \""+user.getEditorial()+"\",\n\"Autor\": \""+user.getAutor()+"\",\n\"Edicion\": "+user.getEdicion()+",\n\"Categoria\": \""+user.getCategoria()+"\"\n}\n]\n}";
    }
    public void eliminarLibro(Libro user){
        data+="\n{\n\t\"ELIMINAR_LIBRO\": [\n\t{\n\t\"ISBN\": "+user.getISBN()+"\",\n\"Titulo\": \""+user.getTitulo()+",\n\"Categoria\": \""+user.getCategoria()+"\"\n}\n]\n}";
    }
    public void crearCategoria(Categoria user){
        data+="\n{\n\t\"CREAR_CATEGORIA\": [\n\t{\n\t\"NOMBRE\": \""+user.getNombreCategoria()+"\"\n}\n]\n}";
    }
    public void eliminarCategoria(Categoria user){
        data+="\n{\n\t\"ELIMINAR_CATEGORIA\": [\n\t{\n\t\"NOMBRE\": \""+user.getNombreCategoria()+"\"\n}\n]\n}";
    }
    
    private void guardarBloque() throws Exception{
        String ruta= "json/bloque"+index+".json";
        String contenido= formato;
        File fila= new File(ruta);
        fila.createNewFile();
        FileWriter fw= new FileWriter(fila);
        BufferedWriter bw= new BufferedWriter(fw);
        bw.write(contenido);
        bw.close();
        //aca se van a crear los json para guardarlo en diferente
    }
    
    public String convertirSHA256(String password) {
	MessageDigest md = null;
	try {
		md = MessageDigest.getInstance("SHA-256");
	} 
	catch (NoSuchAlgorithmException e) {		
		e.printStackTrace();
		return null;
	}
	    
	byte[] hash = md.digest(password.getBytes());
	StringBuffer sb = new StringBuffer();
	    
	for(byte b : hash) {        
		sb.append(String.format("%02x", b));
	}
	    
	return sb.toString();
}
    
    
    public void graficar() throws Exception{
        String cuerpo="NodoNulli[label=\"Null\"];\nNodoNullf[label=\"Null\"];\n";
        String enlaces="";
        String todo="";
            for (int i = 0; i < bloques.getTamanio(); i++) {
                cuerpo+="Nodo"+i+"[label=\"INDEX: "+bloques.obtener_at(i).getIndex()+"\\nTIMESTAMP: "+bloques.obtener_at(i).getTimeStap()+"\\nNONCE: "+bloques.obtener_at(i).getNonce()+"\\nPreviousHash: "+bloques.obtener_at(i).getPreviousHash()+"\\nHash: "+bloques.obtener_at(i).getNewHash()+"\"];\n";   
        }
            for (int i = 0; i < bloques.getTamanio(); i++) {
             if(i==0){
                   enlaces+="Nodo"+i+"-> NodoNulli\n"+"Nodo"+i+"->"+"Nodo"+(i+1)+"\n";
               } if((i+1)==bloques.getTamanio()){
                   enlaces+="Nodo"+i+"->"+"NodoNullf\n"+"\n"+"Nodo"+i+"->"+"Nodo"+(i-1)+"\n";
               }
               if(i>0 && (i+1)<bloques.getTamanio()){
                   enlaces+="Nodo"+i+"->"+"Nodo"+(i+1)+"\n"+"Nodo"+i+"->"+"Nodo"+(i-1)+"\n";
               }
        }
              todo="digraph bloques{\nrankdir=LR\nnode[shape=record, width= 0.1, height= 0.1];"+cuerpo+"\n"+enlaces+"\n}";
                    FileWriter file= new FileWriter("Bloque.dot");
            PrintWriter impresion= new PrintWriter(file);
            impresion.println(todo);
            file.close();
            String comando= "dot -Tjpg Bloque.dot -o Bloque.jpg -Gcharset=latin1";
            Runtime rt= Runtime.getRuntime();
            rt.exec(comando);
            Thread.sleep(500);
    }
    String formato;
    //cada vez que se genera una instruccion se va a guardar en una lista de instrucciones json
    //cuando sincronice va a generar el nuevo hash, va a colocar el hash nuevo como el anterior,
    //y luego va a guardar ese bloque y limpia los datos agregados 
}
