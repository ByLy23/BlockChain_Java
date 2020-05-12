/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

/**
 *
 * @author byron
 */
public class ListaEnlazada<T> {
    class Nodo<T>{
        Nodo<T> siguiente;
        T dato;
        public Nodo(T dato){
            this.dato= dato;
            siguiente=null;
        }

        public Nodo<T> getSiguiente() {
            return siguiente;
        }

        public void setSiguiente(Nodo<T> siguiente) {
            this.siguiente = siguiente;
        }

        public T getDato() {
            return dato;
        }

        public void setDato(T dato) {
            this.dato = dato;
        }
    }
          private Nodo<T> inicio;
        private Nodo<T> fin;
        private int tamanio;
        private boolean estaVacia(){
            return (tamanio==0) ?true:false;
        }

    public Nodo<T> getInicio() {
        return inicio;
    }

    public void setInicio(Nodo<T> inicio) {
        this.inicio = inicio;
    }

    public Nodo<T> getFin() {
        return fin;
    }

    public void setFin(Nodo<T> fin) {
        this.fin = fin;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }
    public void insertarFinal(T dato){
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
    public void insertarInicio(T dato){
        Nodo<T> nuevo = new Nodo(dato);
        if(estaVacia()){
            inicio=fin=nuevo;
            tamanio++;
        }
        else{
        nuevo.setSiguiente(inicio);
        inicio=nuevo;
            tamanio++;
        }
    }
    public void insertar(T dato, int index){
        Nodo<T> nuevo= new Nodo(dato);
        if(estaVacia()){
            inicio=fin=nuevo;
            tamanio++;
        }
        else if(index== tamanio){
            insertarFinal(dato);
        }
        else if(index==0){
            insertarInicio(dato);
        }else{
            Nodo aux= inicio;
            int x=0;
            while(aux!=null){
                if(x==index){break;}
                aux= aux.getSiguiente();
            }
            Nodo sig= aux.getSiguiente();
            aux.setSiguiente(nuevo);
            nuevo.setSiguiente(sig);
            tamanio++;
        }
    }
    public T eliminar(int index){
        T informacion=null;
        if(inicio==fin){
            inicio=fin=null;
            tamanio--;
        }
        else if(index==0){
           informacion =inicio.getDato();
            inicio= inicio.getSiguiente();
            tamanio--;
        }
        else if(index==tamanio){
            informacion=fin.getDato();
            Nodo aux= inicio;
            while(aux.getSiguiente()!=null){
                if(aux.getSiguiente()==fin){break;}
                aux= aux.getSiguiente();
            }
            fin= aux;
            aux.setSiguiente(null);
            tamanio--;
        }
        else{
            Nodo actual=inicio;
            int x=0;
            Nodo anterior=null;
            while(actual!=null){
                if(x==index){
                    break;
                }
                anterior=actual;
                actual=actual.getSiguiente();
                x++;
            }
            anterior.setSiguiente(actual.getSiguiente());
            actual.setSiguiente(null);
            tamanio--;
        }
        return informacion;
    }
    public void buscar(T dato){
        
    }
    public T obtenerElemento(int index){
        Nodo<T> iterador= inicio;
        int x=0;
        while(iterador!= null){
            if(index==x)
                return iterador.getDato();
            iterador= iterador.getSiguiente();
            x++;
        }
        return null;
    }
}
