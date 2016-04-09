package sample;

import javafx.beans.property.SimpleStringProperty;


public class ClienteTable {

    public String getNombre() {
        return nombre.get();
    }

    public SimpleStringProperty nombreProperty() {
        return nombre;
    }

    public String getCif() {
        return cif.get();
    }

    public SimpleStringProperty cifProperty() {return cif;}

    public SimpleStringProperty nombre = new SimpleStringProperty();

    public void setCif(String cif) {
        this.cif.set(cif);
    }

    public void setNombre(String nombre) {
        this.nombre.set(nombre);
    }

    public SimpleStringProperty cif = new SimpleStringProperty();



}
