package sample;


import java.sql.Date;

public class Context {
    private final static Context instance = new Context();

    public static Context getInstance() {
        return instance;
    }

    private Cliente cliente = new Cliente();
    private Facturaconc factura = new Facturaconc();
    private Presupuestoc presupuesto = new Presupuestoc();
    private Reciboc recibo = new Reciboc();
    private Pagador pagador = new Pagador();

    public void setClienteParams (String cif, String nombreFiscal, String nombreComercial,String direccion,String pais,Integer codigoPostal,String poblacion,String personaContacto,String telefono,String movil,String fax,String email,String paginaWeb){
        Cliente cli = new Cliente(cif,nombreFiscal,  nombreComercial, direccion, pais, codigoPostal, poblacion, personaContacto, telefono, movil, fax, email, paginaWeb);
        cliente = cli;
    }

    public void setCliente(Cliente cli) {
        cliente = cli;
    }

    public Cliente currentCliente() {
        return cliente;
    }
    //Factira
    public void setFacturaParams (Integer nfactura, String idcliente, Date fecha, Double preciototal, Double precioiva, String nombrefac, String vencimiento,String formapago){
        Facturaconc fac = new Facturaconc(nfactura, idcliente, fecha, preciototal, precioiva, nombrefac,vencimiento,formapago);
        factura = fac;
    }

    public void setFactura(Facturaconc fac) {
        factura = fac;
    }

    public Facturaconc currentFactura() {
        return factura;
    }


    //Presupuesto
    public void setPresupuestoParams (String idcliente, Double preciototal, Double precioiva, String nombrepre){
        Presupuestoc pre = new Presupuestoc(nombrepre, idcliente, preciototal, precioiva);
        presupuesto = pre;
    }

    public void setPresupuesto(Presupuestoc pre) { presupuesto = pre; }

    public Presupuestoc currentPresupuesto() {
        return presupuesto;
    }

    //Recibo
    public void setReciboParams (Integer nrecibo, String locexp, Double importe, String fechaexp, String vencimiento, Double euros, String concepto, String perent, String domicilioperent, Integer ncuenta, String personapag, String domiciliopag){
            Reciboc re = new Reciboc();
            recibo = re;
    }

    public void setPresupuesto(Reciboc re) { recibo = re; }

    public Reciboc currentRecibo() {
        return recibo;
    }

    //Pagador
    public void selectPagadorParams (String idcliente, String npersona, Integer ncuenta, String dompag){
        Pagador pag = new Pagador();
        pagador = pag;
    }

    public void setPagador(Pagador pag) { pagador = pag; }

    public Pagador currentPagador() {
        return pagador;
    }
}
