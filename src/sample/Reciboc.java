package sample;

import java.util.Date;

/**
 * Created by Maria on 23/08/2015.
 */
public class Reciboc {

    private Integer nrecibo;
    private String locexp;
    private Double importe;
    private String fechaexp;
    private String vencimiento;
    private Double euros;
    private String concepto;
    private String idcliente;
    private String perpag;
    private String nombrerecibo;

    public Reciboc (){};

    public Reciboc(Integer nrecibo, String locexp, Double importe, String fechaexp, String vencimiento, Double euros, String concepto,String idcliente, String perpag, String nombrerecibo) {
        this.nrecibo = nrecibo;
        this.locexp = locexp;
        this.importe = importe;
        this.fechaexp = fechaexp;
        this.vencimiento = vencimiento;
        this.euros = euros;
        this.concepto = concepto;
        this.idcliente = idcliente;
        this.perpag = perpag;
        this.nombrerecibo = nombrerecibo;
    }

    public Integer getNrecibo() {
        return nrecibo;
    }

    public void setNrecibo(Integer nrecibo) {
        this.nrecibo = nrecibo;
    }

    public String getLocexp() {
        return locexp;
    }

    public void setLocexp(String locexp) {
        this.locexp = locexp;
    }

    public Double getImporte() {
        return importe;
    }

    public void setImporte(Double importe) {
        this.importe = importe;
    }

    public String getFechaexp() {
        return fechaexp;
    }

    public void setFechaexp(String fechaexp) {
        this.fechaexp = fechaexp;
    }

    public String getVencimiento() {
        return vencimiento;
    }

    public void setVencimiento(String vencimiento) {
        this.vencimiento = vencimiento;
    }

    public Double getEuros() {
        return euros;
    }

    public void setEuros(Double euros) {
        this.euros = euros;
    }


    public String getConcepto() {
        return concepto;
    }

    public void setConcepto(String concepto) {
        this.concepto = concepto;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public String getPerpag() {
        return perpag;
    }

    public void setPerpag(String perpag) {
        this.perpag = perpag;
    }

    public String getNombrerecibo() {
        return nombrerecibo;
    }

    public void setNombrerecibo(String nombrerecibo) {
        this.nombrerecibo = nombrerecibo;
    }



}
