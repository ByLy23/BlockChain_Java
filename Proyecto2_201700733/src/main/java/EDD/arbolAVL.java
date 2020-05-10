/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import javax.swing.ImageIcon;

/**
 *
 * @author byron
 */
public class arbolAVL {
    class NodoAVL{
      private NodoAVL izquierdo;
    private NodoAVL derecho;
    private int altura;
    private int dato;

    public NodoAVL(){
        izquierdo=null;
        derecho=null;
        dato=0;
        altura=0;
    }
    public NodoAVL(int dato){
        this.dato=dato;
        derecho=null;
        izquierdo=null;
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

    public int getDato() {
        return dato;
    }

    public void setDato(int dato) {
        this.dato = dato;
    }
};
    NodoAVL raiz;
    public arbolAVL(){
        raiz=null;
    }
    
    public NodoAVL getRaiz(){
        return raiz;
    }
   
    public void insertar(int dato) throws Exception{
        raiz= insertarNodo(raiz,dato);
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
    
    private NodoAVL insertarNodo(NodoAVL raiz, int dato) throws Exception{
        if(raiz==null){
           raiz= new NodoAVL(dato);
        }
        else if(dato<raiz.getDato()){
            NodoAVL izquierdo;
            izquierdo= insertarNodo(raiz.getIzquierdo(),dato);
            raiz.setIzquierdo(izquierdo);
            //insertar izquierdo
        }
        else if(dato> raiz.getDato()){
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
    private void graficar(NodoAVL nodo,int index){
        if(nodo!=null){
            graficar(nodo.getIzquierdo(), index);
            cuerpo+= "nodo"+nodo.getDato()+" [label= \" "+nodo.getDato()+"\n Altura: "+nodo.getAltura()+"\"];";
            graficar(nodo.getDerecho(), index);
             if(nodo.getDato()==index)
               cuerpo+="nodo"+nodo.getDato()+" [style=filled, fillcolor=pink, label= \" "+nodo.getDato()+"\n Altura: "+nodo.getAltura()+"\"];";
        }
    }
    String fingrafo="";
    private void graficar2(NodoAVL nodo){
        if(nodo!=null){
     if(nodo.getIzquierdo()!=null){
            enlaces+="nodo"+nodo.getDato()+"->"+"nodo"+nodo.getIzquierdo().getDato()+"\n";
        }
        if(nodo.getDerecho()!=null){
            enlaces+="nodo"+nodo.getDato()+"->"+"nodo"+nodo.getDerecho().getDato()+"\n";
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
        raiz.setDato(temp.getDato());
    }
    public NodoAVL borrarNodo(NodoAVL raiz, int dato)throws Exception{
        
        if(raiz==null){
            return raiz;
        }
        else if(dato<raiz.getDato()){
            NodoAVL izquierdo;
            izquierdo= borrarNodo(raiz.getIzquierdo(),dato);
            raiz.setIzquierdo(izquierdo);
            //insertar izquierdo
        }
        else if(dato> raiz.getDato()){
            NodoAVL derecho;
            derecho=borrarNodo(raiz.getDerecho(),dato);
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
    public void eliminar(int dato) throws Exception{
        raiz= borrarNodo(raiz,dato);
    }
    public void inOrden(NodoAVL raiz, javax.swing.JLabel imagen) throws Exception{
        if (raiz!=null) {
            inOrden(raiz.getIzquierdo(),imagen);
            System.out.print(raiz.getDato()+", ");
            colocarIn(imagen,raiz);
            recorrido+=raiz.getDato()+",";
            inOrden(raiz.getDerecho(),imagen);
        }
    }
    public void preOrden(NodoAVL raiz, javax.swing.JLabel imagen) throws Exception{
        if (raiz!=null) {
            System.out.print(raiz.getDato()+", ");
            colocarPre(imagen,raiz);
            recorrido+=raiz.getDato()+",";
            preOrden(raiz.getIzquierdo(),imagen);
            preOrden(raiz.getDerecho(),imagen);
        }
    }

    public String getRecorrido() {
        return recorrido;
    }

    public void setRecorrido(String recorrido) {
        this.recorrido = recorrido;
    }
    
    public void postOrden(NodoAVL raiz, javax.swing.JLabel imagen) throws Exception{
        if (raiz!=null) {
            postOrden(raiz.getIzquierdo(),imagen);
            postOrden(raiz.getDerecho(),imagen);
            System.out.print(raiz.getDato()+", ");
            colocarpost(imagen,raiz);
            recorrido+=raiz.getDato()+",";
        }
    }
    int contador=0;
        public void colocarGrafo() throws IOException, InterruptedException{
            fingrafo="";
            cuerpo="";
            enlaces="";
            graficar(getRaiz(),getRaiz().getDato());
            graficar2(getRaiz());
           fingrafo= inicio+" "+cuerpo+" "+enlaces+" }";
            FileWriter file= new FileWriter("avlTrees.dot");
            PrintWriter impresion=new PrintWriter(file);
            impresion.println(fingrafo);
            file.close();
            String comando="dot -Tpng avlTrees.dot -o avlTrees"+contador+".png";
            Runtime rt= Runtime.getRuntime();
            rt.exec(comando);
            System.out.println(fingrafo);
        }

    public int getCons() {
        return cons;
    }

    public void setCons(int cons) {
        this.cons = cons;
    }

    public int getCons1() {
        return cons1;
    }

    public void setCons1(int cons1) {
        this.cons1 = cons1;
    }

    public int getCons2() {
        return cons2;
    }

    public void setCons2(int cons2) {
        this.cons2 = cons2;
    }
        
        int cons=0;
        public void colocarIn(javax.swing.JLabel img, NodoAVL nod) throws IOException, InterruptedException{
            fingrafo="";
            cuerpo="";
            enlaces="";
            graficar(getRaiz(),nod.getDato());
            graficar2(getRaiz());
           fingrafo= inicio+" "+cuerpo+" "+enlaces+" }";
            FileWriter file= new FileWriter("avlTrees.dot");
            PrintWriter impresion=new PrintWriter(file);
            impresion.println(fingrafo);
            file.close();
            String comando="dot -Tpng avlTrees.dot -o avlTreesin"+cons+".png";
            Runtime rt= Runtime.getRuntime();
            rt.exec(comando);
            Thread.sleep(500);
             if(cons==0){
            img.setIcon(new ImageIcon("avlTreesin0.png"));
            img.revalidate();
            img.repaint();
            }
            else{
            img.setIcon(new ImageIcon("avlTreesin"+cons+".png"));
            img.revalidate();
            img.repaint();
            }
            cons++;
            System.out.println(fingrafo);
        }
        String recorrido="";
          int cons1=0;
        public void colocarPre(javax.swing.JLabel img, NodoAVL nod) throws IOException, InterruptedException{
            fingrafo="";
            cuerpo="";
            enlaces="";
            graficar(getRaiz(),nod.getDato());
            graficar2(getRaiz());
           fingrafo= inicio+" "+cuerpo+" "+enlaces+" }";
            FileWriter file= new FileWriter("Dots\\avlTrees.dot");
            PrintWriter impresion=new PrintWriter(file);
            impresion.println(fingrafo);
            file.close();
            String comando="dot -Tpng Dots\\avlTrees.dot -o Imagenes\\avlTreespre"+cons1+".png";
            Runtime rt= Runtime.getRuntime();
            rt.exec(comando);
            Thread.sleep(500);
             if(cons1==0){
            img.setIcon(new ImageIcon("Imagenes\\avlTreespre0.png"));
            img.revalidate();
            img.repaint();
            }
            else{
            img.setIcon(new ImageIcon("Imagenes\\avlTreespre"+cons1+".png"));
            img.revalidate();
            img.repaint();
            }
            cons1++;
            System.out.println(fingrafo);
        }
        
          int cons2=0;
        public void colocarpost(javax.swing.JLabel img, NodoAVL nod) throws IOException, InterruptedException{
            fingrafo="";
            cuerpo="";
            enlaces="";
            graficar(getRaiz(),nod.getDato());
            graficar2(getRaiz());
           fingrafo= inicio+" "+cuerpo+" "+enlaces+" }";
            FileWriter file= new FileWriter("Dots\\avlTrees.dot");
            PrintWriter impresion=new PrintWriter(file);
            impresion.println(fingrafo);
            file.close();
            String comando="dot -Tpng Dots\\avlTrees.dot -o Imagenes\\avlTreespost"+cons2+".png";
            Runtime rt= Runtime.getRuntime();
            rt.exec(comando);
            Thread.sleep(500);
             if(cons2==0){
            img.setIcon(new ImageIcon("Imagenes\\avlTreespost0.png"));
            img.revalidate();
            img.repaint();
            }
            else{
            img.setIcon(new ImageIcon("Imagenes\\avlTreespost"+cons2+".png"));
            img.revalidate();
            img.repaint();
            }
            cons2++;
            System.out.println(fingrafo);
        }
}
    //metodos insertar, obtener altura, recorrer
