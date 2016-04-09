package sample;


import java.sql.Date;


public class Facturaconc {
    private Integer nfactura;
    private String idcliente;
    private Date fecha;
    private double preciototal;
    private double precioiva;
    private String nombrefac;
    private String vencimiento;
    private String formapago;

    public Facturaconc(Integer nfactura, String idcliente, Date fecha, Double preciototal, Double precioiva, String nombrefac, String vencimiento, String formapago){
        this.nfactura = nfactura;
        this.idcliente = idcliente;
        this.fecha = fecha;
        this.preciototal = preciototal;
        this.precioiva = precioiva;
        this.nombrefac = nombrefac;
        this.vencimiento = vencimiento;
        this.formapago = formapago;
    }

    public String getIdcliente() {
        return idcliente;
    }

    public void setIdcliente(String idcliente) {
        this.idcliente = idcliente;
    }

    public Integer getNfactura() {
        return nfactura;
    }

    public void setNfactura(Integer nfactura) {
        this.nfactura = nfactura;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

    public double getPreciototal() {
        return preciototal;
    }

    public void setPreciototal(double preciototal) {
        this.preciototal = preciototal;
    }

    public double getPrecioiva() {
        return precioiva;
    }

    public void setPrecioiva(double precioiva) {
        this.precioiva = precioiva;
    }

    public String getnombrefac() { return nombrefac; }

    public void setNombrefac(String nombrefac) { this.nombrefac = nombrefac; }


    public String getVencimiento() { return vencimiento; }

    public void setVencimiento(String vencimiento) { this.vencimiento = vencimiento; }


    public String getFormapago() { return formapago; }

    public void setFormapago(String formapago) { this.formapago = formapago; }
    public Facturaconc (){};



}
