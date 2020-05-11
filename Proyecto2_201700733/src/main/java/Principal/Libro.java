/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Principal;

/**
 *
 * @author byron
 */
public class Libro implements Comparable<Libro>{

    public int getISBN() {
        return ISBN;
    }

    public void setISBN(int ISBN) {
        this.ISBN = ISBN;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public String getAnio() {
        return anio;
    }

    public void setAnio(String anio) {
        this.anio = anio;
    }

    public String getEdicion() {
        return edicion;
    }

    public void setEdicion(String edicion) {
        this.edicion = edicion;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    public String getIdioma() {
        return idioma;
    }

    public void setIdioma(String idioma) {
        this.idioma = idioma;
    }

    public String getCarnetUsuario() {
        return carnetUsuario;
    }

    public void setCarnetUsuario(String carnetUsuario) {
        this.carnetUsuario = carnetUsuario;
    }

    public Libro(int ISBN, String titulo, String autor, String editorial, String anio, String edicion, String categoria, String idioma, String carnetUsuario) {
        this.ISBN = ISBN;
        this.titulo = titulo;
        this.autor = autor;
        this.editorial = editorial;
        this.anio = anio;
        this.edicion = edicion;
        this.categoria = categoria;
        this.idioma = idioma;
        this.carnetUsuario = carnetUsuario;
    }
    private int ISBN;
    private String titulo;
    private String autor;
    private String editorial;
    private String anio;
    private String edicion;
    private String categoria;
    private String idioma;
    private String carnetUsuario;

    @Override
    public int compareTo(Libro o) {
        int resultado=0;
        if(this.ISBN<o.getISBN()){resultado=-1;}
        else if(this.ISBN>o.getISBN()){resultado=1;}
        else{resultado=0;}
        return resultado;
    }
}
