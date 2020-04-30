/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package EDD.ArbolB;

import java.awt.Container;
/**
 *
 * @author byron
 */
public class ArbolB {
   public void crearArbolB(Pagina raiz) {
       raiz=null;
   }
   public Pagina buscar(Pagina actual, int cl, int indice){
       if(actual==null){
           return null;
       }else{
           boolean esta;
           esta=buscarNodo(actual,cl,indice);
           if(esta)
               return actual;
           else
               return buscar(actual.getRamas()[indice],cl,indice);
       }
   }
   public void insertar(Pagina raiz, int cl){
       
   }
   public void eliminar(Pagina raiz, int cl){
       
   }
   
   boolean buscarNodo(Pagina actual, int cl, int k){
       boolean encontrado;
       if(cl< actual.getClaves()[1]){
           encontrado=false;
           k=0;
       }else{
           k=actual.getCuenta();
           while((cl<actual.getClaves()[k]) && (k>1)){
               k--;
           }
           encontrado= (cl== actual.getClaves()[k]);
       }
       return encontrado;
   }
   
   void empujar(Pagina actual, int cl, int subeArriba, int mediana, Pagina nuevo){
       
   }
   void meterHoja(Pagina actual, int cl, Pagina rd, int k){
       
   }
   
   void dividirNodo(Pagina actual, int cl, Pagina rd, int  k,int mediana, Pagina nuevo){
       
   }
   
   
}
