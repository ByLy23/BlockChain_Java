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
public class ListaDoble<T> {
    class Nodo{
        public Nodo(T dato){
            this.dato=dato;
            siguiente=anterior=null;
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

        public Nodo getAnterior() {
            return anterior;
        }

        public void setAnterior(Nodo anterior) {
            this.anterior = anterior;
        }
        private T dato;
        private Nodo siguiente;
        private Nodo anterior;
    };
    public ListaDoble(){
        inicio=fin=null;
        tamanio=0;
    }
private boolean estaVacia(){
    return tamanio==0;
}
    public Nodo getInicio() {
        return inicio;
    }
    
public T obtener_at(int index)
{
     if(index >= 0 && index <tamanio)
    {
        Nodo iterador = this.inicio;
        int x = 0;
        while(iterador!=null){
            if(index == x){return iterador.getDato();}
            iterador = iterador.getSiguiente();
            x++;
        }
    }
    return null;
}
public void agregar_entre(T dato, int index)
{
    if(index >= 0 && index <= this.tamanio)
    {
        if(index == 0){ this.agregar_inicio(dato) ; return;}
        if(index == this.tamanio) {this.agregar_fin(dato); return;}
        Nodo aux = this.inicio;
        int x = 0;
        do
        {
            if(x == index){break;}
            aux = aux.getSiguiente();
            x++;
        }while(aux==fin);
        Nodo n = new Nodo(dato);
        aux.getAnterior().setAnterior(n);
        n.setAnterior(aux.getAnterior());
        n.setSiguiente(aux);
        aux.setAnterior(n);
        this.tamanio++;
    }
}
public void agregar_fin(T dato)
{
     Nodo nuevo= new Nodo(dato);
    if(estaVacia())
    {
        this.inicio=this.fin=nuevo;
        nuevo.setAnterior(null);
        nuevo.setSiguiente(null);
        this.tamanio++;
    }
    else
    {
        this.fin.setSiguiente(nuevo);
        nuevo.setAnterior(this.fin);
        nuevo.setSiguiente(null);
        this.fin= nuevo;
        this.tamanio++;
    }
}

public void agregar_inicio(T dato)
{
    Nodo nuevo= new Nodo(dato);
    if(estaVacia())
    {
        this.inicio=this.fin=nuevo;
        nuevo.setAnterior(null);
        nuevo.setSiguiente(null);
        this.tamanio++;
    }
    else
    {
        nuevo.setSiguiente(this.inicio);
        this.inicio.setAnterior(nuevo);
        nuevo.setAnterior(null);
        fin.setSiguiente(null);
        this.inicio= nuevo;
        this.tamanio++;
    }
}

public void eliminar(int index)
{
 if(index>=0 && index<=tamanio)
    {
        if(index==0){
            this.inicio=this.inicio.getSiguiente();
            this.inicio.getAnterior().setSiguiente(null);
            this.inicio.setAnterior(null);
            this.tamanio--;
        }
        else if(index==this.tamanio){
            fin=fin.getAnterior();
            fin.getSiguiente().setAnterior(null);
            fin.setSiguiente(null);
            this.tamanio--;
        }
        else{
        int x=0;
        Nodo actual=this.inicio;
        Nodo anterior= null;
        while(actual!=null)
        {
            if(x==index){break;}
            anterior=actual;
            actual=actual.getSiguiente();
            x++;
        }
        actual.getSiguiente().setAnterior(anterior);
        anterior.setSiguiente(actual.getSiguiente());
        actual.setSiguiente(null);
        actual.setAnterior(null);
        this.tamanio--;
        }
    }
}
    public void setInicio(Nodo inicio) {
        this.inicio = inicio;
    }

    public Nodo getFin() {
        return fin;
    }

    public void setFin(Nodo fin) {
        this.fin = fin;
    }

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }
    private Nodo inicio;
    private Nodo fin;
    private int tamanio;
}
