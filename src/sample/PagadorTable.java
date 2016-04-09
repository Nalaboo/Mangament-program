package sample;

/**
 * Created by Maria on 11/10/2015.
 */
public class PagadorTable {
    private String nombre;
    private String dompag;
    private Integer ncuenta;

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDompag() {
        return dompag;
    }

    public void setDompag(String dompag) {
        this.dompag = dompag;
    }

    public Integer getNcuenta() {
        return ncuenta;
    }

    public void setNcuenta(Integer ncuenta) {
        this.ncuenta = ncuenta;
    }

    public PagadorTable (){};
    public PagadorTable(String pag, String dompag, Integer ncuenta){
        this.nombre = pag;
        this.dompag = dompag;
        this.ncuenta = ncuenta;
    }
}
