package sample;

/**
 * Created by Maria on 28/08/2015.
 */
public class PresupuestoTable {
    private String concepto;
    private Integer precio;

    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public Integer getPrecio() {
        return precio;
    }

    public void setPrecio(Integer precio) {
        this.precio = precio;
    }


    public PresupuestoTable(){};

    public PresupuestoTable(String concepto, Integer precio){
        this.concepto = concepto;
        this.precio = precio;
    }
}
