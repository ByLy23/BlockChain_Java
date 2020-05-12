/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;
import org.json.*;
/**
 *
 * @author byron
 */
public class Cola <T>{
    private Nodo<T> inicio;
    private Nodo<T> fin;
    private int tamanio=0;

    public int getTamanio() {
        return tamanio;
    }
    public Cola(){
        inicio=fin=null;
    }
    private boolean estaVacia(){
        if (tamanio==0) {
            return true;
        }
        else{
            return false;
        }
    }
    public void Encolar(T dato){
        Nodo<T> nuevo = new Nodo(dato);
        if(estaVacia()){
            inicio=fin=nuevo;
            tamanio++;
        }
        else{
            fin.setSiguiente(nuevo);
            fin=nuevo;
            tamanio++;
        }
    }
    public T obtenerUltimo(){
        T informacion= inicio.getDato();
        return informacion;
    }
    public String recorrerCola(){
        String iniio="digraph \"Cola\"{\n node[shape=record, width= 0.1, height= 0.1];\n";
        String cuerpo="";
        String todo="";
        Nodo<T> p=inicio;
        while(p!=null){
            System.out.print(p.getDato()+"<");
            if(p.getSiguiente()!=null)
                 cuerpo+=p.getDato()+"|";
            p= p.getSiguiente();
        }
        todo=iniio+"nodo[label= \"{"+cuerpo+"}\"];"+"}";
        return todo;
    }
    public T descolar(){
        T informacion= inicio.getDato();
        if (inicio==fin) {
            inicio=fin=null;
            tamanio--;
        }
        else{
            inicio= inicio.getSiguiente();
            tamanio--;
        }
        return informacion;
    }
    public void mostrar(){
        Nodo<T> p=inicio;
        while(p!=null){
            System.out.print(p.getDato()+"<-");
            p= p.getSiguiente();
        }
        System.out.println();
    }
}
class Nodo<T>{
    private T dato;
    private Nodo siguiente;
    public Nodo(T dato){
        this.dato=dato;
        this.siguiente=null;
    }

    public T getDato() {
        return dato;
    }

    public void setDato(T dato) {
        this.dato = dato;
    }

    public Nodo getSiguiente() {
        return siguiente;
    }

    public void setSiguiente(Nodo siguiente) {
        this.siguiente = siguiente;
    }
    
}
