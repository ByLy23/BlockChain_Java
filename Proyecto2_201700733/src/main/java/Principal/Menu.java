/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;
import EDD.ArbolB.ArbolB;

/**
 *
 * @author byron
 */
public class Menu {
    public static void main(String[] args){
        ArbolB arbolito= new ArbolB(2);
       arbolito.insertar(10);
    arbolito.insertar(20);
    arbolito.insertar(30);
   arbolito.insertar(40);
   arbolito.insertar(50);
   arbolito.insertar(60);
      arbolito.insertar(70);
   arbolito.insertar(94);
    arbolito.insertar(85);
   arbolito.insertar(191);
    arbolito.insertar(13);
    arbolito.insertar(103);
      arbolito.insertar(70);
   arbolito.insertar(914);
    arbolito.insertar(5);
   arbolito.insertar(91);
    arbolito.insertar(1);
    arbolito.insertar(23);
    arbolito.insertar(93);
   arbolito.imprimirNodo();
        System.out.println("/-*/-*/-*/-*/*-/-*/-*/-*/-*/-*/-*//-*/-*/-*/-*/*-/-*-/");
    arbolito.eliminar(1);
    arbolito.imprimirNodo();
   // arbolito.Imprimir();
    }
}
