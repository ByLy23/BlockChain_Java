/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD;

import Principal.Clases_Estaticas;
import Principal.Libro;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.*;
import java.util.Comparator;
/**
 *
 * @author byron
 */
public class ArbolB  {
    private int minClavetamanio = 1;
    private int minRamasTamanio = minClavetamanio + 1; 
    private int maxClavetamanio = 2 * minClavetamanio; 
    private int maxRamasTamanio = maxClavetamanio + 1; 
    
    private Nodo raiz = null;

    public int getTamanio() {
        return tamanio;
    }

    public void setTamanio(int tamanio) {
        this.tamanio = tamanio;
    }
    private int tamanio = 0;
    
    public ArbolB(int minOrden) {
        this.minClavetamanio = minOrden;
        this.minRamasTamanio = minClavetamanio + 1;
        this.maxClavetamanio = 2 * minClavetamanio;
        this.maxRamasTamanio = maxClavetamanio + 1;
    }
    boolean datoEcnontrado=false;
    public boolean buscarDato(Libro dato, Nodo raiz){
         if(raiz!=null){
             for (int i = 0; i < raiz.clavesTamanio; i++) {
                 if(dato==raiz.claves[i]){
                     datoEcnontrado=true;
                     return false;
                 }
             }
                for (int i = 0; i < raiz.ramasTamanio; i++) {
                 buscarDato(dato, raiz.ramas[i]);
                 }
    }
         if(!datoEcnontrado)
                return true;
         else
             return false;
    }
      public boolean insertar(Libro dato) {
        if (raiz == null) {
            raiz = new Nodo(null, maxClavetamanio, maxRamasTamanio);
            raiz.insertarClave(dato);
        } else {
          if(!buscarDato(dato, raiz)){
          datoEcnontrado=false;
              return false;
          }
            Nodo nodo = raiz;
            while (nodo != null) {
                if (nodo.numeroRamas() == 0) {
                    nodo.insertarClave(dato);
                    if (nodo.numeroClaves() <= maxClavetamanio) {
                        break;
                    }                         
                    dividirNodo(nodo);
                    break;
                }
                Libro menor = nodo.getClave(0);
                if (dato.compareTo(menor) <= 0) {
                    nodo = nodo.getRama(0);
                    continue;
                }
                int numeroClaves = nodo.numeroClaves();
                int ultimo = numeroClaves - 1;
                Libro mayor = nodo.getClave(ultimo);
                if (dato.compareTo(mayor) > 0) {
                    nodo = nodo.getRama(numeroClaves);
                    continue;
                }
                for (int i = 1; i < nodo.numeroClaves(); i++) {
                    Libro prev = nodo.getClave(i - 1);
                    Libro siguiente = nodo.getClave(i);
                    if (dato.compareTo(prev) > 0 && dato.compareTo(siguiente) <= 0) {
                        nodo = nodo.getRama(i);
                        break;
                    }
                }
            }
        }

        tamanio++;

        return true;
        
    }
    
      private void dividirNodo(Nodo nodoToDividirNodo) {
        Nodo nodo = nodoToDividirNodo;
        int numeroClaves = nodo.numeroClaves();
        int medianIndex = numeroClaves / 2;
        Libro medianDato = nodo.getClave(medianIndex);

        Nodo izquierda = new Nodo(null, maxClavetamanio, maxRamasTamanio);
        for (int i = 0; i < medianIndex; i++) {
            izquierda.insertarClave(nodo.getClave(i));
        }
        if (nodo.numeroRamas() > 0) {
            for (int j = 0; j <= medianIndex; j++) {
                Nodo c = nodo.getRama(j);
                izquierda.insertarRama(c);
            }
        }

        Nodo derecha = new Nodo(null, maxClavetamanio, maxRamasTamanio);
        for (int i = medianIndex + 1; i < numeroClaves; i++) {
            derecha.insertarClave(nodo.getClave(i));
        }
        if (nodo.numeroRamas() > 0) {
            for (int j = medianIndex + 1; j < nodo.numeroRamas(); j++) {
                Nodo c = nodo.getRama(j);
                derecha.insertarRama(c);
            }
        }

        if (nodo.padre == null) {
            Nodo newRaiz = new Nodo(null, maxClavetamanio, maxRamasTamanio);
            newRaiz.insertarClave(medianDato);
            nodo.padre = newRaiz;
            raiz = newRaiz;
            nodo = raiz;
            nodo.insertarRama(izquierda);
            nodo.insertarRama(derecha);
        } else {
            Nodo padre = nodo.padre;
            padre.insertarClave(medianDato);
            padre.eliminarRama(nodo);
            padre.insertarRama(izquierda);
            padre.insertarRama(derecha);

            if (padre.numeroClaves() > maxClavetamanio) dividirNodo(padre);
        }
    }
private Libro eliminar(Libro dato, Nodo nodo) {
        if (nodo == null) return null;
        Libro eliminars = null;
        int index = nodo.indexOf(dato);
        eliminars = nodo.eliminarClave(dato);
        if (nodo.numeroRamas() == 0) {
            if (nodo.padre != null && nodo.numeroClaves() < minClavetamanio) {
                this.combinar(nodo);
            } else if (nodo.padre== null && nodo.numeroClaves() == 0) {
         
                raiz = null;
            }
        } else {
  
            Nodo menor = nodo.getRama(index);
            Nodo mayor = this.getMayorNodo(menor);
            Libro replaceDato = this.eliminarMayorDato(mayor);
            nodo.insertarClave(replaceDato);
            if (mayor.padre != null && mayor.numeroClaves() < minClavetamanio) {
                this.combinar(mayor);
            }
            if (mayor.numeroRamas() > maxRamasTamanio) {
                this.dividirNodo(mayor);
            }
        }

        tamanio--;

        return eliminars;
    }

public Libro eliminar(Libro dato) {
        Libro eliminars = null;
        Nodo nodo = this.getNodo(dato);
        eliminars = eliminar(dato,nodo);
        return eliminars;
    }    
 private Libro eliminarMayorDato(Nodo nodo) {
        Libro dato = null;
        if (nodo.numeroClaves() > 0) {
            dato = nodo.eliminarClave(nodo.numeroClaves() - 1);
        }
        return dato;
    }
 
    public void limpiar() {
        raiz = null;
        tamanio = 0;
    }
     private Nodo getNodo(Libro dato) {
        Nodo nodo = raiz;
        while (nodo != null) {
            Libro menor = nodo.getClave(0);
            if (dato.compareTo(menor) < 0) {
                if (nodo.numeroRamas() > 0)
                    nodo = nodo.getRama(0);
                else
                    nodo = null;
                continue;
            }

            int numeroClaves = nodo.numeroClaves();
            int ultimo = numeroClaves - 1;
            Libro mayor = nodo.getClave(ultimo);
            if (dato.compareTo(mayor) > 0) {
                if (nodo.numeroRamas() > numeroClaves)
                    nodo = nodo.getRama(numeroClaves);
                else
                    nodo = null;
                continue;
            }

            for (int i = 0; i < numeroClaves; i++) {
                Libro actualDato = nodo.getClave(i);
                if (actualDato.compareTo(dato) == 0) {
                    return nodo;
                }

                int siguiente = i + 1;
                if (siguiente <= ultimo) {
                    Libro siguienteDato = nodo.getClave(siguiente);
                    if (actualDato.compareTo(dato) < 0 && siguienteDato.compareTo(dato) > 0) {
                        if (siguiente < nodo.numeroRamas()) {
                            nodo = nodo.getRama(siguiente);
                            break;
                        }
                        return null;
                    }
                }
            }
        }
        return null;
    }
     
     private boolean combinar(Nodo nodo) {
        Nodo padre = nodo.padre;
        int index = padre.indexOf(nodo);
        int indexOfIzquierdaV = index - 1;
        int indexOfDerechaV = index + 1;

        Nodo derechaV = null;
        int derechaVTamanio = -minRamasTamanio;
        if (indexOfDerechaV < padre.numeroRamas()) {
            derechaV = padre.getRama(indexOfDerechaV);
            derechaVTamanio = derechaV.numeroClaves();
        }
        if (derechaV != null && derechaVTamanio > minClavetamanio) {
            Libro eliminarDato = derechaV.getClave(0);
            int prev = getIndexAnteriorDato(padre, eliminarDato);
            Libro padreDato = padre.eliminarClave(prev);
            Libro vDato = derechaV.eliminarClave(0);
            nodo.insertarClave(padreDato);
            padre.insertarClave(vDato);
            if (derechaV.numeroRamas() > 0) {
                nodo.insertarRama(derechaV.eliminarRama(0));
            }
        } else {
            Nodo izquierdaV = null;
            int izquierdaVTamanio = -minRamasTamanio;
            if (indexOfIzquierdaV >= 0) {
                izquierdaV = padre.getRama(indexOfIzquierdaV);
                izquierdaVTamanio = izquierdaV.numeroClaves();
            }

            if (izquierdaV != null && izquierdaVTamanio > minClavetamanio) {
                Libro eliminarDato = izquierdaV.getClave(izquierdaV.numeroClaves() - 1);
                int prev = getIndexSiguienteDato(padre, eliminarDato);
                Libro padreDato = padre.eliminarClave(prev);
                Libro vDato = izquierdaV.eliminarClave(izquierdaV.numeroClaves() - 1);
                nodo.insertarClave(padreDato);
                padre.insertarClave(vDato);
                if (izquierdaV.numeroRamas() > 0) {
                    nodo.insertarRama(izquierdaV.eliminarRama(izquierdaV.numeroRamas() - 1));
                }
            } else if (derechaV != null && padre.numeroClaves() > 0) {
                Libro eliminarDato = derechaV.getClave(0);
                int prev = getIndexAnteriorDato(padre, eliminarDato);
                Libro padreDato = padre.eliminarClave(prev);
                padre.eliminarRama(derechaV);
                nodo.insertarClave(padreDato);
                for (int i = 0; i < derechaV.clavesTamanio; i++) {
                    Libro v = derechaV.getClave(i);
                    nodo.insertarClave(v);
                }
                for (int i = 0; i < derechaV.ramasTamanio; i++) {
                    Nodo c = derechaV.getRama(i);
                    nodo.insertarRama(c);
                }

                if (padre.padre != null && padre.numeroClaves() < minClavetamanio) {
                    this.combinar(padre);
                } else if (padre.numeroClaves() == 0) {
                    nodo.padre = null;
                    raiz = nodo;
                }
            } else if (izquierdaV != null && padre.numeroClaves() > 0) {
                Libro eliminarDato = izquierdaV.getClave(izquierdaV.numeroClaves() - 1);
                int prev = getIndexSiguienteDato(padre, eliminarDato);
                Libro padreDato = padre.eliminarClave(prev);
                padre.eliminarRama(izquierdaV);
                nodo.insertarClave(padreDato);
                for (int i = 0; i < izquierdaV.clavesTamanio; i++) {
                    Libro v = izquierdaV.getClave(i);
                    nodo.insertarClave(v);
                }
                for (int i = 0; i < izquierdaV.ramasTamanio; i++) {
                    Nodo c = izquierdaV.getRama(i);
                    nodo.insertarRama(c);
                }

                if (padre.padre != null && padre.numeroClaves() < minClavetamanio) {
                    this.combinar(padre);
                } else if (padre.numeroClaves() == 0) {
                    nodo.padre = null;
                    raiz = nodo;
                }
            }
        }

        return true;
    }
     
       private int getIndexAnteriorDato(Nodo nodo, Libro dato) {
        for (int i = 1; i < nodo.numeroClaves(); i++) {
            Libro t = nodo.getClave(i);
            if (t.compareTo(dato) >= 0)
                return i - 1;
        }
        return nodo.numeroClaves() - 1;
    }
       private int getIndexSiguienteDato(Nodo nodo, Libro dato) {
        for (int i = 0; i < nodo.numeroClaves(); i++) {
            Libro t = nodo.getClave(i);
            if (t.compareTo(dato) >= 0)
                return i;
        }
        return nodo.numeroClaves() - 1;
    }
       
    public int tamanio() {
        return tamanio;
    }
    
    public boolean validar() {
        if (raiz == null) return true;
        return validarNodo(raiz);
    }
    private boolean validarNodo(Nodo nodo) {
        int clavetamanio = nodo.numeroClaves();
        if (clavetamanio > 1) {
            for (int i = 1; i < clavetamanio; i++) {
                Libro p = nodo.getClave(i - 1);
                Libro n = nodo.getClave(i);
                if (p.compareTo(n) > 0)
                    return false;
            }
        }
        int ramasTamanio = nodo.numeroRamas();
        if (nodo.padre == null) {
            if (clavetamanio > maxClavetamanio) {
                return false;
            } else if (ramasTamanio == 0) {
                return true;
            } else if (ramasTamanio < 2) {
                return false;
            } else if (ramasTamanio > maxRamasTamanio) {
                return false;
            }
        } else {
            if (clavetamanio < minClavetamanio) {
                return false;
            } else if (clavetamanio > maxClavetamanio) {
                return false;
            } else if (ramasTamanio == 0) {
                return true;
            } else if (clavetamanio != (ramasTamanio - 1)) {
                return false;
            } else if (ramasTamanio < minRamasTamanio) {
                return false;
            } else if (ramasTamanio > maxRamasTamanio) {
                return false;
            }
        }

        Nodo first = nodo.getRama(0);
        if (first.getClave(first.numeroClaves() - 1).compareTo(nodo.getClave(0)) > 0)
            return false;

        Nodo last = nodo.getRama(nodo.numeroRamas() - 1);
        if (last.getClave(0).compareTo(nodo.getClave(nodo.numeroClaves() - 1)) < 0)
            return false;

        for (int i = 1; i < nodo.numeroClaves(); i++) {
            Libro p = nodo.getClave(i - 1);
            Libro n = nodo.getClave(i);
            Nodo c = nodo.getRama(i);
            if (p.compareTo(c.getClave(0)) > 0)
                return false;
            if (n.compareTo(c.getClave(c.numeroClaves() - 1)) < 0)
                return false;
        }

        for (int i = 0; i < nodo.ramasTamanio; i++) {
            Nodo c = nodo.getRama(i);
            boolean valid = this.validarNodo(c);
            if (!valid)
                return false;
        }
        return true;
    }
     private Nodo getMayorNodo(Nodo obtener) {
        Nodo nodo = obtener;
        while (nodo.numeroRamas() > 0) {
            nodo = nodo.getRama(nodo.numeroRamas() - 1);
        }
        return nodo;
    }
     
     public boolean eliminarLibro(int isbn){
         buscarLibro(isbn);
         Libro aux= libroEliminar;
         if(aux!=null){
             eliminar(aux);
             return true;
         }
         else
             return false;
     }
     Libro libroEliminar= null;
     Libro buscarL=null;
     private void buscarLibro(int isbn){
         buscarLibro(raiz, isbn);
     }
     public void buscarLibros(String testo){
         buscarLibros(raiz, testo);
     }
     private void buscarLibros(Nodo raiz, String testo){
         if(raiz!=null){
             for (int i = 0; i < raiz.clavesTamanio; i++) {
                 if(raiz.claves[i].getTitulo().contains(testo)){
                     Clases_Estaticas.modelo.addElement("[**ISBN**]: "+raiz.claves[i].getISBN()+" [**TITULO**]: "+raiz.claves[i].getTitulo()+ " [**EDICION**]: "+raiz.claves[i].getEdicion()+" [**EDITORIAL**]: "+raiz.claves[i].getEditorial()+" [**AUTOR**]: "+raiz.claves[i].getAutor()+" [**CATEGORIA**]: "+raiz.claves[i].getCategoria()+" [**IDIOMA**]: "+raiz.claves[i].getIdioma()+" [**ANIO**]: "+raiz.claves[i].getAnio());
                     break;
                 }
             }
             for (int i = 0; i < raiz.ramasTamanio; i++) {
                 buscarLibros(raiz.ramas[i], testo);
             }
         }
     }
     public void buscarLibroscat(){
         Clases_Estaticas.modelo.clear();
         buscarLibroscat(raiz);
     }
     private void buscarLibroscat(Nodo raiz){
         if(raiz!=null){
             for (int i = 0; i < raiz.clavesTamanio; i++) {
                     Clases_Estaticas.modelo.addElement("[**ISBN**]: "+raiz.claves[i].getISBN()+" [**TITULO**]: "+raiz.claves[i].getTitulo()+ " [**EDICION**]: "+raiz.claves[i].getEdicion()+" [**EDITORIAL**]: "+raiz.claves[i].getEditorial()+" [**AUTOR**]: "+raiz.claves[i].getAutor()+" [**CATEGORIA**]: "+raiz.claves[i].getCategoria()+" [**IDIOMA**]: "+raiz.claves[i].getIdioma()+" [**ANIO**]: "+raiz.claves[i].getAnio());          
             }
             for (int i = 0; i < raiz.ramasTamanio; i++) {
                 buscarLibroscat(raiz.ramas[i]);
             }
         }
     }
     private void buscarLibro(Nodo raiz, int isbn){
         if(raiz!=null){
             for (int i = 0; i < raiz.clavesTamanio; i++) {
                 if(raiz.claves[i].getISBN()==isbn && raiz.claves[i].getCarnetUsuario()==Clases_Estaticas.user.getCarnet()){
                     libroEliminar= raiz.claves[i];
                     break;
                 }
             }
             for (int i = 0; i < raiz.ramasTamanio; i++) {
                 buscarLibro(raiz.ramas[i], isbn);
             }
         }
     }
      public void imprimirNodo() throws IOException, InterruptedException{
         b.setLength(0);
            imprimirRaiz(this.raiz);
        }
      public void imprimirNiveles(){
          imprimirNiveles(raiz);
      }
      public void imprimirNiveles(Nodo raiz){
  if(raiz!=null){
    for (int i = 0; i < raiz.ramasTamanio; i++) {
        imprimirNiveles(raiz.ramas[i]);
        System.out.print("\n");
    }
    for (int i = 0; i < raiz.clavesTamanio; i++) {
      System.out.print(raiz.claves[i]);
    }
  }
}
            StringBuilder b= new StringBuilder();
        public void imprimirRaiz(Nodo raiz) throws IOException, InterruptedException{
            if(raiz!=null){
                 b.append("Nodo"+raiz.hashCode());
        b.append("[label=\"<P0>");
    for (int i = 0; i < raiz.clavesTamanio; i++) {
       b.append("|"+ raiz.claves[i].getISBN()+", "+raiz.claves[i].getTitulo());
       b.append("|<P"+(i+1)+">");
    }
    b.append("\"];\n");
                for (int i = 0; i < raiz.ramasTamanio; i++) {
                 imprimirRaiz(raiz.ramas[i]);
                b.append("Nodo"+raiz.hashCode()+":P"+i+"->"+"Nodo"+raiz.ramas[i].hashCode()+";\n");
                 }
    }
            
                /*
                b.append("Nodo"+raiz.hashCode());
                b.append("[label=\"<P0>");
            for (int i = 0; i < raiz.clavesTamanio; i++) {
                System.out.println(raiz.claves[i]);
                 b.append("|"+ raiz.claves[i]);
                 b.append("|<P"+(i+1)+">");
            }*/
              
           /* b.append("\"];\n");
           
            for (int i = 0; i < raiz.ramasTamanio; i++) {
                b.append("Nodo"+raiz.ramas[i].hashCode());
                b.append("[label=\"<P0>");
                for (int j = 0; j < raiz.ramas[i].clavesTamanio; j++) {
                    System.out.print(raiz.ramas[i].claves[j]+" ");
                 b.append("|"+ raiz.ramas[i].claves[j]);
                 b.append("|<P"+(i+1)+">");
                }
            b.append("\"];\n");
                imprimirRaiz(raiz.ramas[i]);
                System.out.println("");
            }
            
               for (int i = 0; i < raiz.ramasTamanio; i++) {
        b.append("Nodo"+raiz.hashCode()+":P"+i+"->"+"Nodo"+raiz.ramas[i].hashCode()+";\n");
                }
        }*/
            System.out.println(b);
            mostrarB(b);
        }
        private void mostrarB(StringBuilder dato) throws IOException, InterruptedException{
            String mensaje="digraph Btree{ \n  node[shape=record, width= 0.1, height= 0.1];\n"+dato+"\n}";
            FileWriter file= new FileWriter("bTree.dot");
            PrintWriter impresion= new PrintWriter(file);
            impresion.println(mensaje);
            file.close();
            String comando= "dot -Tpng bTree.dot -o bTree.png";
            Runtime rt= Runtime.getRuntime();
            rt.exec(comando);
        }
        
      private static class Nodo {

        private Libro[] claves = null;
        private int clavesTamanio = 0;
        private Nodo[] ramas = null;
        private int ramasTamanio = 0;
        protected Nodo padre = null;
        private Comparator<Nodo> comparator = new Comparator<Nodo>() {
            @Override
            public int compare(Nodo arg0, Nodo arg1) {
                return arg0.getClave(0).compareTo(arg1.getClave(0));
            }
        };
        private Nodo(Nodo padre, int maxClavetamanio, int maxRamasTamanio) {
            this.padre = padre;
            this.claves =  new Libro[maxClavetamanio + 1];
            this.clavesTamanio = 0;
            this.ramas = new Nodo[maxRamasTamanio + 1];
            this.ramasTamanio = 0;
        }

        private Libro getClave(int index) {
            return claves[index];
        }

        private int indexOf(Libro dato) {
            for (int i = 0; i < clavesTamanio; i++) {
                if (claves[i].equals(dato)) return i;
            }
            return -1;
        }

        private void insertarClave(Libro dato) {
            claves[clavesTamanio++] = dato;
            java.util.Arrays.sort(claves, 0, clavesTamanio);
        }

        private Libro eliminarClave(Libro dato) {
            Libro eliminars = null;
            boolean found = false;
            if (clavesTamanio == 0) return null;
            for (int i = 0; i < clavesTamanio; i++) {
                if (claves[i].equals(dato)) {
                    found = true;
                    eliminars = claves[i];
                } else if (found) {
                    claves[i - 1] = claves[i];
                }
            }
            if (found) {
                clavesTamanio--;
                claves[clavesTamanio] = null;
            }
            return eliminars;
        }

        private Libro eliminarClave(int index) {
            if (index >= clavesTamanio)
                return null;
            Libro dato = claves[index];
            for (int i = index + 1; i < clavesTamanio; i++) {
                claves[i - 1] = claves[i];
            }
            clavesTamanio--;
            claves[clavesTamanio] = null;
            return dato;
        }

        private int numeroClaves() {
            return clavesTamanio;
        }

        private Nodo getRama(int index) {
            if (index >= ramasTamanio)
                return null;
            return ramas[index];
        }

        private int indexOf(Nodo rama) {
            for (int i = 0; i < ramasTamanio; i++) {
                if (ramas[i].equals(rama))
                    return i;
            }
            return -1;
        }

        private boolean insertarRama(Nodo rama) {
            rama.padre = this;
            ramas[ramasTamanio++] = rama;
            java.util.Arrays.sort(ramas, 0, ramasTamanio,comparator);
            return true;
        }

        private boolean eliminarRama(Nodo rama) {
            boolean encontrado = false;
            if (ramasTamanio == 0)
                return encontrado;
            for (int i = 0; i < ramasTamanio; i++) {
                if (ramas[i].equals(rama)) {
                    encontrado = true;
                } else if (encontrado) {
                    ramas[i - 1] = ramas[i];
                }
            }
            if (encontrado) {
                ramasTamanio--;
                ramas[ramasTamanio] = null;
            }
            return encontrado;
        }

        private Nodo eliminarRama(int index) {
            if (index >= ramasTamanio)
                return null;
            Nodo dato = ramas[index];
            ramas[index] = null;
            for (int i = index + 1; i < ramasTamanio; i++) {
                ramas[i - 1] = ramas[i];
            }
            ramasTamanio--;
            ramas[ramasTamanio] = null;
            return dato;
        }

        private int numeroRamas() {
            return ramasTamanio;
        }

        /**
         * {@inheritDoc}
         */
        @Override
        public String toString() {
            StringBuilder builder = new StringBuilder();

            builder.append("claves=[");
            for (int i = 0; i < numeroClaves(); i++) {
                Libro dato = getClave(i);
                builder.append(dato);
                if (i < numeroClaves() - 1)
                    builder.append(", ");
            }
            builder.append("]\n");

            if (padre != null) {
                builder.append("parent=[");
                for (int i = 0; i < padre.numeroClaves(); i++) {
                    Libro dato = padre.getClave(i);
                    builder.append(dato);
                    if (i < padre.numeroClaves() - 1)
                        builder.append(", ");
                }
                builder.append("]\n");
            }

            if (ramas != null) {
                builder.append("clavetamanio=").append(numeroClaves()).append(" ramas=").append(numeroRamas()).append("\n");
            }

            return builder.toString();
        }
    }
 
 /*

public void Imprimir(){
    Mostrar(raiz);
}
int contador=0;
  private void Mostrar(Nodo x) {
    assert (x == null);
       b.append("Nodo"+x.hashCode());
        b.append("[label=\"<P0>");
    for (int i = 0; i < x.n; i++) {
        if(x.clave[i]!=0){
       b.append("|"+ x.clave[i]);
       b.append("|<P"+(i+1)+">");
      System.out.print(x.clave[i] + " ");
        }
       // if (x.hoja) {
         //   System.out.println("\n");
     //   }
    }
    b.append("\"];\n");
    contador++;
   //
    if (!x.hoja) {
      for (int i = 0; i <= x.n; i++) {
        Mostrar(x.rama[i]);
        b.append("Nodo"+x.hashCode()+":P"+i+"->"+"Nodo"+x.rama[i].hashCode()+";\n");
      }
    }
      System.out.println(b);
  }
*/
}
