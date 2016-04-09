package sample;

/**
 * Created by Maria on 13/10/2015.
 */
public class Pagador {
    String idcliente;
    String npersona;
    Integer ncuenta;
    String dompag;

    public Pagador (){};

    public Pagador(String idcliente, String npersona, Integer ncuenta, String dompag) {
        this.idcliente = idcliente;
        this.npersona = npersona;
        this.ncuenta = ncuenta;
        this.dompag = dompag;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public String getNpersona() {
        return npersona;
    }

    public void setNpersona(String npersona) {
        this.npersona = npersona;
    }

    public Integer getNcuenta() {
        return ncuenta;
    }

    public void setNcuenta(Integer ncuenta) {
        this.ncuenta = ncuenta;
    }

    public String getDompag() {
        return dompag;
    }

    public void setDompag(String dompag) {
        this.dompag = dompag;
    }
}
