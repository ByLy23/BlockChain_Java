/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

import EDD.ArbolB;
import EDD.ListaDoble;
import EDD.ListaEnlazada;
import EDD.arbolAVL;
import EDD.tablaHash;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;

/**
 *
 * @author byron
 */
public class Clases_Estaticas {
    public  static arbolAVL arbolito;
    public static tablaHash tablita;
    public static Usuario user;
    public static DefaultListModel modelo;
    public static ListaEnlazada<Integer> libtos;
    public static DefaultListModel cajita;
    public static Instruccion instrucciones;
    public Clases_Estaticas(){
        arbolito= new arbolAVL();
        modelo= new DefaultListModel();
        cajita= new DefaultListModel();
        tablita= new tablaHash();
       instrucciones= new Instruccion();
    }
}
