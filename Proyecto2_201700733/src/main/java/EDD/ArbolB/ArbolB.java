/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD.ArbolB;

import java.util.Comparator;
/**
 *
 * @author byron
 */
public class ArbolB<T extends Comparable <T>> {
    private int minClavetamanio = 1;
    private int minRamasTamanio = minClavetamanio + 1; 
    private int maxClavetamanio = 2 * minClavetamanio; 
    private int maxRamasTamanio = maxClavetamanio + 1; 
    
    private Nodo<T> raiz = null;
    private int tamanio = 0;
    
    public ArbolB(int orden) {
        this.minClavetamanio = orden;
        this.minRamasTamanio = minClavetamanio + 1;
        this.maxClavetamanio = 2 * minClavetamanio;
        this.maxRamasTamanio = maxClavetamanio + 1;
    }
    boolean datoEcnontrado=false;
    public boolean buscarDato(T dato, Nodo raiz){
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
      public boolean insertar(T dato) {
        if (raiz == null) {
            raiz = new Nodo<T>(null, maxClavetamanio, maxRamasTamanio);
            raiz.insertarClave(dato);
        } else {
          if(!buscarDato(dato, raiz)){
          datoEcnontrado=false;
              return false;
          }
            Nodo<T> nodo = raiz;
            while (nodo != null) {
                if (nodo.numeroRamas() == 0) {
                    nodo.insertarClave(dato);
                    if (nodo.numeroClaves() <= maxClavetamanio) {
                        break;
                    }                         
                    dividirNodo(nodo);
                    break;
                }
                T menor = nodo.getClave(0);
                if (dato.compareTo(menor) <= 0) {
                    nodo = nodo.getRama(0);
                    continue;
                }

                // Greater
                int numeroClaves = nodo.numeroClaves();
                int ultimo = numeroClaves - 1;
                T mayor = nodo.getClave(ultimo);
                if (dato.compareTo(mayor) > 0) {
                    nodo = nodo.getRama(numeroClaves);
                    continue;
                }
                for (int i = 1; i < nodo.numeroClaves(); i++) {
                    T prev = nodo.getClave(i - 1);
                    T siguiente = nodo.getClave(i);
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
    
      private void dividirNodo(Nodo<T> nodoToDividirNodo) {
        Nodo<T> nodo = nodoToDividirNodo;
        int numeroClaves = nodo.numeroClaves();
        int medianIndex = numeroClaves / 2;
        T medianDato = nodo.getClave(medianIndex);

        Nodo<T> izquierda = new Nodo<T>(null, maxClavetamanio, maxRamasTamanio);
        for (int i = 0; i < medianIndex; i++) {
            izquierda.insertarClave(nodo.getClave(i));
        }
        if (nodo.numeroRamas() > 0) {
            for (int j = 0; j <= medianIndex; j++) {
                Nodo<T> c = nodo.getRama(j);
                izquierda.insertarRama(c);
            }
        }

        Nodo<T> derecha = new Nodo<T>(null, maxClavetamanio, maxRamasTamanio);
        for (int i = medianIndex + 1; i < numeroClaves; i++) {
            derecha.insertarClave(nodo.getClave(i));
        }
        if (nodo.numeroRamas() > 0) {
            for (int j = medianIndex + 1; j < nodo.numeroRamas(); j++) {
                Nodo<T> c = nodo.getRama(j);
                derecha.insertarRama(c);
            }
        }

        if (nodo.padre == null) {
            Nodo<T> newRaiz = new Nodo<T>(null, maxClavetamanio, maxRamasTamanio);
            newRaiz.insertarClave(medianDato);
            nodo.padre = newRaiz;
            raiz = newRaiz;
            nodo = raiz;
            nodo.insertarRama(izquierda);
            nodo.insertarRama(derecha);
        } else {
            Nodo<T> padre = nodo.padre;
            padre.insertarClave(medianDato);
            padre.eliminarRama(nodo);
            padre.insertarRama(izquierda);
            padre.insertarRama(derecha);

            if (padre.numeroClaves() > maxClavetamanio) dividirNodo(padre);
        }
    }
private T eliminar(T dato, Nodo<T> nodo) {
        if (nodo == null) return null;
        T eliminars = null;
        int index = nodo.indexOf(dato);
        eliminars = nodo.eliminarClave(dato);
        if (nodo.numeroRamas() == 0) {
            if (nodo.padre != null && nodo.numeroClaves() < minClavetamanio) {
                this.combinar(nodo);
            } else if (nodo.padre== null && nodo.numeroClaves() == 0) {
         
                raiz = null;
            }
        } else {
  
            Nodo<T> menor = nodo.getRama(index);
            Nodo<T> mayor = this.getMayorNodo(menor);
            T replaceDato = this.eliminarMayorDato(mayor);
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

public T eliminar(T dato) {
        T eliminars = null;
        Nodo<T> nodo = this.getNodo(dato);
        eliminars = eliminar(dato,nodo);
        return eliminars;
    }    
 private T eliminarMayorDato(Nodo<T> nodo) {
        T dato = null;
        if (nodo.numeroClaves() > 0) {
            dato = nodo.eliminarClave(nodo.numeroClaves() - 1);
        }
        return dato;
    }
 
    public void limpiar() {
        raiz = null;
        tamanio = 0;
    }
     private Nodo<T> getNodo(T dato) {
        Nodo<T> nodo = raiz;
        while (nodo != null) {
            T menor = nodo.getClave(0);
            if (dato.compareTo(menor) < 0) {
                if (nodo.numeroRamas() > 0)
                    nodo = nodo.getRama(0);
                else
                    nodo = null;
                continue;
            }

            int numeroClaves = nodo.numeroClaves();
            int ultimo = numeroClaves - 1;
            T mayor = nodo.getClave(ultimo);
            if (dato.compareTo(mayor) > 0) {
                if (nodo.numeroRamas() > numeroClaves)
                    nodo = nodo.getRama(numeroClaves);
                else
                    nodo = null;
                continue;
            }

            for (int i = 0; i < numeroClaves; i++) {
                T actualDato = nodo.getClave(i);
                if (actualDato.compareTo(dato) == 0) {
                    return nodo;
                }

                int siguiente = i + 1;
                if (siguiente <= ultimo) {
                    T siguienteDato = nodo.getClave(siguiente);
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
     
     private boolean combinar(Nodo<T> nodo) {
        Nodo<T> padre = nodo.padre;
        int index = padre.indexOf(nodo);
        int indexOfIzquierdaV = index - 1;
        int indexOfDerechaV = index + 1;

        Nodo<T> derechaV = null;
        int derechaVTamanio = -minRamasTamanio;
        if (indexOfDerechaV < padre.numeroRamas()) {
            derechaV = padre.getRama(indexOfDerechaV);
            derechaVTamanio = derechaV.numeroClaves();
        }
        if (derechaV != null && derechaVTamanio > minClavetamanio) {
            T eliminarDato = derechaV.getClave(0);
            int prev = getIndexAnteriorDato(padre, eliminarDato);
            T padreDato = padre.eliminarClave(prev);
            T vDato = derechaV.eliminarClave(0);
            nodo.insertarClave(padreDato);
            padre.insertarClave(vDato);
            if (derechaV.numeroRamas() > 0) {
                nodo.insertarRama(derechaV.eliminarRama(0));
            }
        } else {
            Nodo<T> izquierdaV = null;
            int izquierdaVTamanio = -minRamasTamanio;
            if (indexOfIzquierdaV >= 0) {
                izquierdaV = padre.getRama(indexOfIzquierdaV);
                izquierdaVTamanio = izquierdaV.numeroClaves();
            }

            if (izquierdaV != null && izquierdaVTamanio > minClavetamanio) {
                // Try to borrow from izquierda v
                T eliminarDato = izquierdaV.getClave(izquierdaV.numeroClaves() - 1);
                int prev = getIndexSiguienteDato(padre, eliminarDato);
                T padreDato = padre.eliminarClave(prev);
                T vDato = izquierdaV.eliminarClave(izquierdaV.numeroClaves() - 1);
                nodo.insertarClave(padreDato);
                padre.insertarClave(vDato);
                if (izquierdaV.numeroRamas() > 0) {
                    nodo.insertarRama(izquierdaV.eliminarRama(izquierdaV.numeroRamas() - 1));
                }
            } else if (derechaV != null && padre.numeroClaves() > 0) {
                T eliminarDato = derechaV.getClave(0);
                int prev = getIndexAnteriorDato(padre, eliminarDato);
                T padreDato = padre.eliminarClave(prev);
                padre.eliminarRama(derechaV);
                nodo.insertarClave(padreDato);
                for (int i = 0; i < derechaV.clavesTamanio; i++) {
                    T v = derechaV.getClave(i);
                    nodo.insertarClave(v);
                }
                for (int i = 0; i < derechaV.ramasTamanio; i++) {
                    Nodo<T> c = derechaV.getRama(i);
                    nodo.insertarRama(c);
                }

                if (padre.padre != null && padre.numeroClaves() < minClavetamanio) {
                    this.combinar(padre);
                } else if (padre.numeroClaves() == 0) {
                    nodo.padre = null;
                    raiz = nodo;
                }
            } else if (izquierdaV != null && padre.numeroClaves() > 0) {
                T eliminarDato = izquierdaV.getClave(izquierdaV.numeroClaves() - 1);
                int prev = getIndexSiguienteDato(padre, eliminarDato);
                T padreDato = padre.eliminarClave(prev);
                padre.eliminarRama(izquierdaV);
                nodo.insertarClave(padreDato);
                for (int i = 0; i < izquierdaV.clavesTamanio; i++) {
                    T v = izquierdaV.getClave(i);
                    nodo.insertarClave(v);
                }
                for (int i = 0; i < izquierdaV.ramasTamanio; i++) {
                    Nodo<T> c = izquierdaV.getRama(i);
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
     
       private int getIndexAnteriorDato(Nodo<T> nodo, T dato) {
        for (int i = 1; i < nodo.numeroClaves(); i++) {
            T t = nodo.getClave(i);
            if (t.compareTo(dato) >= 0)
                return i - 1;
        }
        return nodo.numeroClaves() - 1;
    }
       private int getIndexSiguienteDato(Nodo<T> nodo, T dato) {
        for (int i = 0; i < nodo.numeroClaves(); i++) {
            T t = nodo.getClave(i);
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
    private boolean validarNodo(Nodo<T> nodo) {
        int clavetamanio = nodo.numeroClaves();
        if (clavetamanio > 1) {
            // Make sure the claves are sorted
            for (int i = 1; i < clavetamanio; i++) {
                T p = nodo.getClave(i - 1);
                T n = nodo.getClave(i);
                if (p.compareTo(n) > 0)
                    return false;
            }
        }
        int ramasTamanio = nodo.numeroRamas();
        if (nodo.padre == null) {
            // raiz
            if (clavetamanio > maxClavetamanio) {
                // check max clave tamanio. raiz does not have a min clave tamanio
                return false;
            } else if (ramasTamanio == 0) {
                // if raiz, no ramas, and claves are valid
                return true;
            } else if (ramasTamanio < 2) {
                // raiz should have zero or at least two ramas
                return false;
            } else if (ramasTamanio > maxRamasTamanio) {
                return false;
            }
        } else {
            // non-raiz
            if (clavetamanio < minClavetamanio) {
                return false;
            } else if (clavetamanio > maxClavetamanio) {
                return false;
            } else if (ramasTamanio == 0) {
                return true;
            } else if (clavetamanio != (ramasTamanio - 1)) {
                // If there are chilren, there should be one more rama then
                // claves
                return false;
            } else if (ramasTamanio < minRamasTamanio) {
                return false;
            } else if (ramasTamanio > maxRamasTamanio) {
                return false;
            }
        }

        Nodo<T> first = nodo.getRama(0);
        if (first.getClave(first.numeroClaves() - 1).compareTo(nodo.getClave(0)) > 0)
            return false;

        Nodo<T> last = nodo.getRama(nodo.numeroRamas() - 1);
        if (last.getClave(0).compareTo(nodo.getClave(nodo.numeroClaves() - 1)) < 0)
            return false;

        for (int i = 1; i < nodo.numeroClaves(); i++) {
            T p = nodo.getClave(i - 1);
            T n = nodo.getClave(i);
            Nodo<T> c = nodo.getRama(i);
            if (p.compareTo(c.getClave(0)) > 0)
                return false;
            if (n.compareTo(c.getClave(c.numeroClaves() - 1)) < 0)
                return false;
        }

        for (int i = 0; i < nodo.ramasTamanio; i++) {
            Nodo<T> c = nodo.getRama(i);
            boolean valid = this.validarNodo(c);
            if (!valid)
                return false;
        }

        return true;
    }
     private Nodo<T> getMayorNodo(Nodo<T> obtener) {
        Nodo<T> nodo = obtener;
        while (nodo.numeroRamas() > 0) {
            nodo = nodo.getRama(nodo.numeroRamas() - 1);
        }
        return nodo;
    }
     
     
      public void imprimirNodo(){
            imprimirRaiz(this.raiz);
        }
        public void imprimirRaiz(Nodo raiz){
            StringBuilder b= new StringBuilder();
            if(raiz!=null){
                 b.append("Nodo"+raiz.hashCode());
        b.append("[label=\"<P0>");
    for (int i = 0; i < raiz.clavesTamanio; i++) {
       b.append("|"+ raiz.claves[i]);
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
        }
        
      private static class Nodo<T extends Comparable<T>> {

        private T[] claves = null;
        private int clavesTamanio = 0;
        private Nodo<T>[] ramas = null;
        private int ramasTamanio = 0;
        private Comparator<Nodo<T>> comparator = new Comparator<Nodo<T>>() {
            @Override
            public int compare(Nodo<T> arg0, Nodo<T> arg1) {
                return arg0.getClave(0).compareTo(arg1.getClave(0));
            }
        };
        protected Nodo<T> padre = null;

        private Nodo(Nodo<T> padre, int maxClavetamanio, int maxRamasTamanio) {
            this.padre = padre;
            this.claves = (T[]) new Comparable[maxClavetamanio + 1];
            this.clavesTamanio = 0;
            this.ramas = new Nodo[maxRamasTamanio + 1];
            this.ramasTamanio = 0;
        }

        private T getClave(int index) {
            return claves[index];
        }

        private int indexOf(T dato) {
            for (int i = 0; i < clavesTamanio; i++) {
                if (claves[i].equals(dato)) return i;
            }
            return -1;
        }

        private void insertarClave(T dato) {
            claves[clavesTamanio++] = dato;
            java.util.Arrays.sort(claves, 0, clavesTamanio);
        }

        private T eliminarClave(T dato) {
            T eliminars = null;
            boolean found = false;
            if (clavesTamanio == 0) return null;
            for (int i = 0; i < clavesTamanio; i++) {
                if (claves[i].equals(dato)) {
                    found = true;
                    eliminars = claves[i];
                } else if (found) {
                    // shift the rest of the claves down
                    claves[i - 1] = claves[i];
                }
            }
            if (found) {
                clavesTamanio--;
                claves[clavesTamanio] = null;
            }
            return eliminars;
        }

        private T eliminarClave(int index) {
            if (index >= clavesTamanio)
                return null;
            T dato = claves[index];
            for (int i = index + 1; i < clavesTamanio; i++) {
                // shift the rest of the claves down
                claves[i - 1] = claves[i];
            }
            clavesTamanio--;
            claves[clavesTamanio] = null;
            return dato;
        }

        private int numeroClaves() {
            return clavesTamanio;
        }

        private Nodo<T> getRama(int index) {
            if (index >= ramasTamanio)
                return null;
            return ramas[index];
        }

        private int indexOf(Nodo<T> rama) {
            for (int i = 0; i < ramasTamanio; i++) {
                if (ramas[i].equals(rama))
                    return i;
            }
            return -1;
        }

        private boolean insertarRama(Nodo<T> rama) {
            rama.padre = this;
            ramas[ramasTamanio++] = rama;
            java.util.Arrays.sort(ramas, 0, ramasTamanio, comparator);
            return true;
        }

        private boolean eliminarRama(Nodo<T> rama) {
            boolean encontrado = false;
            if (ramasTamanio == 0)
                return encontrado;
            for (int i = 0; i < ramasTamanio; i++) {
                if (ramas[i].equals(rama)) {
                    encontrado = true;
                } else if (encontrado) {
                    // shift the rest of the claves down
                    ramas[i - 1] = ramas[i];
                }
            }
            if (encontrado) {
                ramasTamanio--;
                ramas[ramasTamanio] = null;
            }
            return encontrado;
        }

        private Nodo<T> eliminarRama(int index) {
            if (index >= ramasTamanio)
                return null;
            Nodo<T> dato = ramas[index];
            ramas[index] = null;
            for (int i = index + 1; i < ramasTamanio; i++) {
                // shift the rest of the claves down
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
                T dato = getClave(i);
                builder.append(dato);
                if (i < numeroClaves() - 1)
                    builder.append(", ");
            }
            builder.append("]\n");

            if (padre != null) {
                builder.append("parent=[");
                for (int i = 0; i < padre.numeroClaves(); i++) {
                    T dato = padre.getClave(i);
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
