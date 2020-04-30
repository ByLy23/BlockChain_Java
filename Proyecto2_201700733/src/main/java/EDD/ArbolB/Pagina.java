/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD.ArbolB;

/**
 *
 * @author byron
 */
public class Pagina {

    public int getM() {
        return m;
    }

    public void setM(int m) {
        this.m = m;
    }

    public int[] getClaves() {
        return claves;
    }

    public void setClaves(int[] claves) {
        this.claves = claves;
    }

    public Pagina[] getRamas() {
        return ramas;
    }

    public void setRamas(Pagina[] ramas) {
        this.ramas = ramas;
    }

    public int getCuenta() {
        return cuenta;
    }

    public void setCuenta(int cuenta) {
        this.cuenta = cuenta;
    }
   private int m;
    private int[] claves;
    private Pagina[] ramas;
    private int cuenta;
    
    public Pagina(int m){
        this.m=m;
        claves= new int[m];
        ramas= new Pagina[m];
        cuenta=0;
    }
    public boolean nodoLleno(Pagina actual){
        return (actual.cuenta==m-1);
    }
    public boolean nodoSemiVacio(Pagina actual){
        return (actual.cuenta<m/2);
    }
    public void escribeNodo(Pagina actual){
        int k;
        System.out.println("\n Nodo: ");
        for (k= 0; k < actual.cuenta; k++) {
            System.out.println(actual.claves[k]);
        }
    }
}
