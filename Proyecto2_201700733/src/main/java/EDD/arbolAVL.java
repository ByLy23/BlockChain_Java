/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import Principal.Categoria;
import Principal.Clases_Estaticas;
import Principal.Libro;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;

/**
 *
 * @author byron
 */
public class arbolAVL {
    class NodoAVL{
      private NodoAVL izquierdo;
    private NodoAVL derecho;
    private int altura;

        public Categoria getCategoria() {
            return categoria;
        }

        public void setCategoria(Categoria categoria) {
            this.categoria = categoria;
        }
    private Categoria categoria;

        public ArbolB getArbolito() {
            return arbolito;
        }

        public void setArbolito(ArbolB arbolito) {
            this.arbolito = arbolito;
        }
    private ArbolB arbolito;

    public NodoAVL(){
        izquierdo=null;
        derecho=null;
        categoria=new Categoria();
        arbolito=new ArbolB(2);
        altura=0;
        
    }
    public NodoAVL(Categoria categoria){
        this.categoria=categoria;
        derecho=null;
        izquierdo=null;
        arbolito= new ArbolB(2);
        altura=0;
    }
    public NodoAVL getIzquierdo() {
        return izquierdo;
    }

    public void setIzquierdo(NodoAVL izquierdo) {
        this.izquierdo = izquierdo;
    }

    public NodoAVL getDerecho() {
        return derecho;
    }

    public void setDerecho(NodoAVL derecho) {
        this.derecho = derecho;
    }

    public int getAltura() {
        return altura;
    }

    public void setAltura(int altura) {
        this.altura = altura;
    }
    };
    NodoAVL raiz;

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }
    private int tamanio=0;
    public arbolAVL(){
        raiz=null;
    }
    
    public NodoAVL getRaiz(){
        return raiz;
    }
   
    public void insertar(Categoria dato) throws Exception{
        raiz= insertarNodo(raiz,dato);
        tamanio++;
    }
    private int alturaMaxima(int a, int b){
       return  (a>b) ? a : b;
    }
    private int obtieneAltura(NodoAVL nuevo){
        return (nuevo==null) ? 0: nuevo.getAltura();
    }
    private int getBalance(NodoAVL nuevo){
        return (nuevo==null)? 0: (obtieneAltura(nuevo.getIzquierdo())-obtieneAltura(nuevo.getDerecho()));
    }
    String rotaciones="";

    public String getRotaciones() {
        return rotaciones;
    }

    public void setRotaciones(String rotaciones) {
        this.rotaciones = rotaciones;
    }
    private NodoAVL rotacionDD(NodoAVL n) throws Exception{
         rotaciones= "Rotacion derecha derecha";
        NodoAVL n1= n.getIzquierdo();
        n.setIzquierdo(n1.getDerecho());
        n1.setDerecho(n);
        n.setAltura(alturaMaxima(obtieneAltura(n.getIzquierdo()), obtieneAltura(n.getDerecho()))+1);
        n1.setAltura(alturaMaxima(obtieneAltura(n1.getIzquierdo()), obtieneAltura(n1.getDerecho()))+1);
        return n1;
    }
    private NodoAVL rotacionDI(NodoAVL n) throws Exception{
        rotaciones= "Rotacion derecha Izquierda";
        NodoAVL n2;
        n.setDerecho(rotacionDD(n.getDerecho()));
        n2= rotacionII(n);
        return n2;
    }
    private NodoAVL rotacionID(NodoAVL n) throws Exception{
         rotaciones= "Rotacion izquierda derecha";
        NodoAVL n2;
        n.setIzquierdo(rotacionII(n.getIzquierdo()));
        n2= rotacionDD(n);
        return n2;
    }
    private NodoAVL rotacionII(NodoAVL n) throws Exception{
         rotaciones= "Rotacion izquierda izquierda";
        NodoAVL n1= n.getDerecho();
        n.setDerecho(n1.getIzquierdo());
        n1.setIzquierdo(n);
        n.setAltura(alturaMaxima(obtieneAltura(n.getIzquierdo()), obtieneAltura(n.getDerecho()))+1);
        n1.setAltura(alturaMaxima(obtieneAltura(n1.getIzquierdo()), obtieneAltura(n1.getDerecho()))+1);
        return n1;
    }
    
    private NodoAVL insertarNodo(NodoAVL raiz, Categoria dato) throws Exception{
        if(raiz==null){
           raiz= new NodoAVL(dato);
        }
        else if(dato.compareTo(raiz.getCategoria())<0){
            NodoAVL izquierdo;
            izquierdo= insertarNodo(raiz.getIzquierdo(),dato);
            raiz.setIzquierdo(izquierdo);
            //insertar izquierdo
        }
        else if(dato.compareTo(raiz.getCategoria())>0){
            NodoAVL derecho;
            derecho= insertarNodo(raiz.getDerecho(),dato);
            raiz.setDerecho(derecho);
            //insertarDerecho
        }
        else{
        return raiz;
        }
            raiz.setAltura(1+alturaMaxima(obtieneAltura(raiz.getIzquierdo()),obtieneAltura(raiz.getDerecho())));
            int balance= getBalance(raiz);
            if (balance>1) {
                int balance2= getBalance(raiz.getIzquierdo());
                if(balance2==-1)
                    //rotacion izquierda derecha
                    return rotacionID(raiz);
                else
                    //rotacion izquierda izquierda
                   return  rotacionDD(raiz);
        }
            else if(balance<-1){
                int balance2= getBalance(raiz.getDerecho());
                if(balance2==1)
                    //rotacion derecha izquierda
                    return rotacionDI(raiz);
                else
                    //rotacion derecha derecha
                   return  rotacionII(raiz);
            }
       return raiz;
    }
    
    
    
    String inicio="digraph \"GraficaAVL\"{";
    String enlaces="";
    String cuerpo="";
    private void graficar(NodoAVL nodo){
        if(nodo!=null){
            graficar(nodo.getIzquierdo());
            String alti= String.valueOf(nodo.getAltura()-1);
            cuerpo+= "nodo"+nodo.hashCode()+" [label= \" "+nodo.getCategoria().getNombreCategoria()+"\n Altura: "+alti+"\n CantidadLibros: "+nodo.getArbolito().getTamanio()+"\"];";
            graficar(nodo.getDerecho());
        }
    }
    String fingrafo="";
    private void graficar2(NodoAVL nodo){
        if(nodo!=null){
     if(nodo.getIzquierdo()!=null){
            enlaces+="nodo"+nodo.hashCode()+"->"+"nodo"+nodo.getIzquierdo().hashCode()+"\n";
        }
        if(nodo.getDerecho()!=null){
            enlaces+="nodo"+nodo.hashCode()+"->"+"nodo"+nodo.getDerecho().hashCode()+"\n";
        }
        graficar2(nodo.getIzquierdo());
        graficar2(nodo.getDerecho());
        }
    }
    private NodoAVL nodoMaximo(NodoAVL raiz){
        NodoAVL actual= raiz;
        NodoAVL temporal = raiz;
        while (actual.getDerecho() != null) {
            temporal = actual;
            actual = actual.getDerecho();
        }
        temporal.setDerecho(null);

        return actual;
    }
    private void actualizarNodo(NodoAVL raiz, NodoAVL temp){
        raiz.setCategoria(temp.getCategoria());
    }
    public NodoAVL borrarNodo(NodoAVL raiz, String dato,int carnet)throws Exception{
        
        if(raiz==null){
            return raiz;
        }
        else if(dato.compareTo(raiz.getCategoria().getNombreCategoria())<0){
            NodoAVL izquierdo;
            izquierdo= borrarNodo(raiz.getIzquierdo(),dato,carnet);
            raiz.setIzquierdo(izquierdo);
            //insertar izquierdo
        }
        else if(dato.compareTo(raiz.getCategoria().getNombreCategoria())>0){
            NodoAVL derecho;
            derecho=borrarNodo(raiz.getDerecho(),dato,carnet);
            raiz.setDerecho(derecho);
            //insertarDerecho
        }
        else{
            NodoAVL reemplazo;
            reemplazo= raiz;
            
              if ((raiz.getIzquierdo() == null) || (raiz.getDerecho() == null)) {
                NodoAVL temp = null;
                if (temp == raiz.getIzquierdo()) {
                    temp = raiz.getDerecho();
                } else {
                    temp = raiz.getIzquierdo();
                }

                if (temp == null) {
//                    temp = raiz;
                    raiz = null;
                } else {
                    raiz = temp;
                }
              }
               else {
                NodoAVL temp = nodoMaximo(raiz.getIzquierdo());
                NodoAVL cambiar = raiz.getIzquierdo();
                actualizarNodo(raiz, temp);
                if (raiz.getIzquierdo() == temp) {
                    raiz.setIzquierdo(null);
                }
              }
        }
        if (raiz==null) {
            return raiz;
        }
            raiz.setAltura(1+alturaMaxima(obtieneAltura(raiz.getIzquierdo()),obtieneAltura(raiz.getDerecho())));
            int balance= getBalance(raiz);
            if (balance>1) {
                int balance2= getBalance(raiz.getIzquierdo());
                if(balance2<0 )
                   // rotacion izquierda derecha
                   return rotacionID(raiz);
                else if (balance2>=0)
                    //rotacion izquierda izquierda
                 return  rotacionDD(raiz);
        }
            else if(balance<-1){
                int balance2= getBalance(raiz.getDerecho());
                if(balance2>0)
                    //rotacion derecha izquierda
                    return rotacionDI(raiz);
                else if(balance2<=0)
                    //rotacion derecha derecha
                   return  rotacionII(raiz);
            }      
        return raiz;
    }
       public void verificarLibro(Libro libro) throws Exception{
           buscarLibro(raiz, libro.getCategoria());
        if(bandera){
            agregarLibro(libro);
        }else{
            insertar(new Categoria(libro.getCategoria(), new ArbolB(2), Clases_Estaticas.user.getCarnet()));
            agregarLibro(libro);
        }
        bandera=false;
    }
       private void agregarLibro(Libro libro){
          agregaLibro(raiz,libro);
       }
       private void agregaLibro(NodoAVL raiz, Libro libro){
            if(raiz!=null){
                if(raiz.getCategoria().getNombreCategoria().equals(libro.getCategoria())){
                    ArbolB escribir= raiz.getArbolito();
                    escribir.insertar(libro);
                return;
                }
            agregaLibro(raiz.getIzquierdo(), libro);
            agregaLibro(raiz.getDerecho(),libro);
            }
           
       }
       private void buscarLibro(NodoAVL raiz, String categoria){
        if(raiz!=null){
            if(raiz.getCategoria().getNombreCategoria().equals(categoria)){
                bandera=true;
                return;
            }
            buscarLibro(raiz.getIzquierdo(), categoria);
            buscarLibro(raiz.getDerecho(), categoria);
        }
    }
    boolean bandera=false;
    boolean bandera2=false;
    public void mostrarLibros(String tex){
        mostrarLibros(raiz, tex);
    }
    private void mostrarLibros(NodoAVL raiz, String testo){
        if(raiz!=null){
            raiz.getArbolito().buscarLibros(testo);
            mostrarLibros(raiz.getIzquierdo(), testo);
            mostrarLibros(raiz.getDerecho(), testo);
        }
    }
       public void eliminarLibro(int isbn){
           eliminarLibro(raiz, isbn);
           if(!bandera2){
               JOptionPane.showMessageDialog(null, "No se puede eliminar");
           }else{
               JOptionPane.showMessageDialog(null, "Libro eliminado");
           }
           bandera2=false;
           salir=0;
       }
       int salir=0;
       private void eliminarLibro(NodoAVL raiz, int isbn){
           if(raiz!=null){
               bandera= raiz.getArbolito().eliminarLibro(isbn);
               if(!bandera){
                   if(salir==0){
                   eliminarLibro(raiz.getIzquierdo(), isbn);
                   eliminarLibro(raiz.getDerecho(), isbn);
                   }
               }else{
                   salir=1;
                   bandera2=true;
               }
           }
       }
    public void eliminar(String dato,int carnet) throws Exception{     
        buscarNodo(raiz, dato, carnet);
        if(bandera){
            System.out.println("vamo a borra");
            raiz= borrarNodo(raiz,dato,carnet);}
        else
            JOptionPane.showMessageDialog(null, "Usted no es propietario de esta onda xD");
        bandera=false;
    }
    private void buscarNodo(NodoAVL raiz, String categoria, int carnet){
        if(raiz!=null){
            if(categoria.equals(raiz.getCategoria().getNombreCategoria()) && carnet==raiz.getCategoria().getCarnet()){
                bandera=true;
                return;
            }
            buscarNodo(raiz.getIzquierdo(), categoria, carnet);
            buscarNodo(raiz.getDerecho(), categoria, carnet);
        }
    }
    public void imprimirB(String tex) throws IOException, InterruptedException{
        imprimirB(raiz, tex);
    }
    private void imprimirB(NodoAVL raiz, String testo) throws IOException, InterruptedException{
        if(raiz!=null){
            if(testo.equals(raiz.getCategoria().getNombreCategoria())){
                raiz.getArbolito().imprimirNodo();
            }
            imprimirB(raiz.getIzquierdo(),testo);
            imprimirB(raiz.getDerecho(),testo);
        }
    }
    public void inOrden(NodoAVL raiz){
        if (raiz!=null) {
            inOrden(raiz.getIzquierdo());
            b.append("|Categoria: "+raiz.getCategoria().getNombreCategoria()+" Libros: "+raiz.getArbolito().getTamanio());
            inOrden(raiz.getDerecho());
        }
    }
    public DefaultListModel lista= new DefaultListModel();
    public void preOrdenMuestra(NodoAVL raiz){
        if (raiz!=null) {
            System.out.print(raiz.getCategoria().getNombreCategoria()+", ");
            recorrido+=raiz.getCategoria().getNombreCategoria()+",";
            lista.addElement(raiz.getCategoria().getNombreCategoria());
            preOrdenMuestra(raiz.getIzquierdo());
            preOrdenMuestra(raiz.getDerecho());
        }
    }
    public void colocarLista(){
        preOrdenlista(raiz);;
    }
    private void preOrdenlista(NodoAVL raiz){
        if (raiz!=null) {
            Clases_Estaticas.cajita.addElement(raiz.getCategoria().getNombreCategoria()+"");
            preOrdenlista(raiz.getIzquierdo());
            preOrdenlista(raiz.getDerecho());
        }
    }
        StringBuilder b= new StringBuilder();
    private void graficarPreorden(){
        b.setLength(0);
        b.append("Nodo"+this.hashCode());
        b.append("[label= \"");
       preOrden(raiz); 
        b.append("|\"];\n");
    }
    private void graficarInorden(){
        b.setLength(0);
        b.append("Nodo"+this.hashCode());
        b.append("[label= \"");
        inOrden(raiz);
        b.append("|\"];\n");
    }
    private void graficarPostorden(){
        b.setLength(0);
        b.append("Nodo"+this.hashCode());
        b.append("[label= \"");
        postOrden(raiz);
        b.append("|\"];\n");
    }
    public void graficarOrdenes(String tex) throws IOException, InterruptedException{
        String inicio="";
        switch(tex){
            case "in":
                graficarInorden();
                inicio="digraph Inorden{\nrankdir=LR\nnode[shape= record];\n";
                inicio+=b;
                inicio+="\n}";
                FileWriter file= new FileWriter("inorden.dot");
                PrintWriter impresion= new PrintWriter(file);
                impresion.println(inicio);
                file.close();
                String comando= "dot -Tpng inorden.dot -o inorden.png";
                Runtime rt= Runtime.getRuntime();
                rt.exec(comando);
                Thread.sleep(500);
                inicio="";
                break;
            case "post":
                graficarPostorden();
                inicio="digraph Inorden{\nrankdir=LR\nnode[shape= record];\n";
                inicio+=b;
                inicio+="\n}";
                FileWriter file1= new FileWriter("postorden.dot");
                PrintWriter impresion1= new PrintWriter(file1);
                impresion1.println(inicio);
                file1.close();
                String comando1= "dot -Tpng postorden.dot -o postorden.png";
                Runtime rt1= Runtime.getRuntime();
                rt1.exec(comando1);
                Thread.sleep(500);
                inicio="";
                break;
            case "pre":
                 graficarPreorden();
                inicio="digraph Inorden{\nrankdir=LR\nnode[shape= record];\n";
                inicio+=b;
                inicio+="\n}";
                FileWriter file11= new FileWriter("preorden.dot");
                PrintWriter impresion11= new PrintWriter(file11);
                impresion11.println(inicio);
                file11.close();
                String comando11= "dot -Tpng preorden.dot -o preorden.png";
                Runtime rt11= Runtime.getRuntime();
                rt11.exec(comando11);
                Thread.sleep(500);
                inicio="";
                break;
        }
    }
    public void preOrden(NodoAVL raiz){
        if (raiz!=null) {
            b.append("|Categoria: "+raiz.getCategoria().getNombreCategoria()+" Libros: "+raiz.getArbolito().getTamanio());
            preOrden(raiz.getIzquierdo());
            preOrden(raiz.getDerecho());
        }
    }
    public void recorre(){
        lista.clear();
        preOrdenMuestra(raiz);
    }

    public String getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(String recorrido) {
        this.recorrido = recorrido;
    }
    
    public void postOrden(NodoAVL raiz){
        if (raiz!=null) {
            postOrden(raiz.getIzquierdo());
            postOrden(raiz.getDerecho());
            b.append("|Categoria: "+raiz.getCategoria().getNombreCategoria()+" Libros: "+raiz.getArbolito().getTamanio());
        }
    }
        public void colocarGrafo() throws IOException, InterruptedException{
            fingrafo="";
            cuerpo="";
            enlaces="";
            graficar(getRaiz());
            graficar2(getRaiz());
           fingrafo= inicio+" "+cuerpo+" "+enlaces+" }";
            FileWriter file= new FileWriter("avlTrees.dot");
            PrintWriter impresion=new PrintWriter(file);
            impresion.println(fingrafo);
            file.close();
            String comando="dot -Tpng avlTrees.dot -o avlTrees.png";
            Runtime rt= Runtime.getRuntime();
            rt.exec(comando);
            System.out.println(fingrafo);
        }
         String recorrido="";
}
    //metodos insertar, obtener altura, recorrer
