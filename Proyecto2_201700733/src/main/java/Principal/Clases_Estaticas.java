/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import EDD.ArbolB;
import EDD.arbolAVL;
import EDD.tablaHash;

/**
 *
 * @author byron
 */
public class Clases_Estaticas {
    public  static arbolAVL arbolito;
    public static tablaHash tablita;
    public static Usuario user;
    public Clases_Estaticas(){
        arbolito= new arbolAVL();
        tablita= new tablaHash();
    }
}
