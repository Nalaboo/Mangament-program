package sample;

import javafx.beans.property.SimpleStringProperty;

/**
 * Created by Maria on 31/08/2015.
 */
public class MostrarTable {

    private Integer numero;
    private String nombre;

    public Integer getNumero() {
        return numero;
    }

    public void setNumero(Integer numero) {
        this.numero = numero;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    public MostrarTable(){};

    public MostrarTable(Integer numero, String nombre){
        this.numero = numero;
        this.nombre = nombre;
    }
}
