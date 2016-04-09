package sample;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maria on 23/08/2015.
 */
public class DAOrecibo {
    private String INSERT_RECIBO = "INSERT INTO recibo VALUES (?,?,?,?,?,?,?,?,?,?)";
    private String GET_RE = "SELECT * FROM recibo where nrecibo = ?";
    private String SELECT_RE = "SELECT perent FROM recibo WHERE nrecibo = ? ORDER by idrecibo";
    private String DELETE_RE = "DELETE FROM recibo WHERE nrecibo = ?";
    private String CONSULTA_CLIENTE_CIF = "SELECT * FROM cliente where cif = ?";
    private String GET_PAGADOR = "SELECT * FROM pagador where idcliente = ?";
    private String GET_PAGADOR_GUARDADO = "SELECT ncuenta,dompag FROM pagador where npersona = ? and idcliente = ?";
    private String CONSULTA_NRECIBO_REPETIDO = "SELECT nrecibo FROM recibo where nrecibo = ?";


    public void savedatos (Reciboc recibo) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement insert = c.prepareStatement(INSERT_RECIBO);
            insert.setInt(1, recibo.getNrecibo());
            insert.setString(2, recibo.getLocexp());
            insert.setDouble(3, recibo.getImporte());
            insert.setString(4, recibo.getFechaexp());
            insert.setString(5, recibo.getVencimiento());
            insert.setDouble(6, recibo.getEuros());
            insert.setString(7, recibo.getConcepto());
            insert.setString(8, recibo.getIdcliente());
            insert.setString(9, recibo.getPerpag());
            insert.setString(10, recibo.getNombrerecibo());
            insert.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Reciboc getrecibo(Integer nreciboaux){
        Reciboc recibo = new Reciboc();
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement insert = c.prepareStatement(GET_RE);
            insert.setInt(1, nreciboaux);
            ResultSet rs = insert.executeQuery();
            Integer nrecibo = rs.getInt("nrecibo");
            String locexp = rs.getString("locexp");
            Double importe = rs.getDouble("importe");
            String fechaexp = rs.getString("fechaexp");
            String vencimiento = rs.getString("vencimiento");
            Double euros = rs.getDouble("euros");
            String concepto = rs.getString("concepto");
            String idcliente = rs.getString("idcliente");
            String perpag = rs.getString("perpag");
            String nombrerecibo = rs.getString("nombrerecibo");
            recibo = new Reciboc(nrecibo, locexp, importe, fechaexp,vencimiento,euros,concepto,idcliente,perpag,nombrerecibo);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return recibo;
    }


    public Pagador getpagador(String npersonaaux, String idclientee){
        Pagador pagador = new Pagador();
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement insert = c.prepareStatement(GET_PAGADOR_GUARDADO);
            insert.setString(1, npersonaaux);
            insert.setString(2, idclientee);
            ResultSet rs = insert.executeQuery();
            Integer ncuenta = rs.getInt("ncuenta");
            System.out.println(ncuenta);
            String dompag = rs.getString("dompag");
            pagador = new Pagador(idclientee, npersonaaux,ncuenta,dompag);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return pagador;
    }

    public List<MostrarTable> muestrare(Integer nreciboaux) {
        List<MostrarTable> lista = new ArrayList<>();
        String sDriverName = "org.sqlite.JDBC";
        String perent;
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(SELECT_RE);
            consulta.setInt(1, nreciboaux);
            try (ResultSet rs = consulta.executeQuery()) {
                while (rs.next()) {
                    perent = rs.getString("perent");
                    MostrarTable m = new MostrarTable(nreciboaux,perent);
                    lista.add(m);
                }
                return lista;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public void deletere(Integer nreciboaux) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement delete = c.prepareStatement(DELETE_RE);
            delete.setInt(1, nreciboaux);
            delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public Cliente clicif (String cifaux){
        Cliente cliente = new Cliente();
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(CONSULTA_CLIENTE_CIF);
            consulta.setString(1,cifaux);
            ResultSet rs = consulta.executeQuery();
            String cif = rs.getString("cif");//Obtencion de la informacion de las columnas de la query
            String nfiscal = rs.getString("n_fiscal");
            String ncomercial = rs.getString("n_comercial");
            String direccion = rs.getString("direccion");
            String pais = rs.getString("pais");
            Integer cp = rs.getInt("cp");
            String poblacion = rs.getString("poblacion");
            String pcontacto = rs.getString("p_contacto");
            String tlf = rs.getString("tlf");
            String movil = rs.getString("movil");
            String fax = rs.getString("fax");
            String mail = rs.getString("email");
            String web = rs.getString("pweb");
            cliente = new Cliente(cif,nfiscal,ncomercial,direccion,pais,cp,poblacion,pcontacto,tlf,movil,fax,mail,web);

        }catch (SQLException e){
            e.printStackTrace();
        }
        return cliente;
    }

    public List<PagadorTable>  initabla (String idcliente){
        List<PagadorTable> lista = new ArrayList<>();
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(GET_PAGADOR);
            consulta.setString(1, idcliente);
            try (ResultSet rs = consulta.executeQuery()) {
                while (rs.next()) {
                    String npersona = rs.getString("npersona");//Obtencion de la informacion de las columnas de la query
                    Integer ncuenta = rs.getInt("ncuenta");
                    String dompag = rs.getString("dompag");
                    PagadorTable m = new PagadorTable(npersona, dompag, ncuenta);
                    lista.add(m);
                }
                return lista;
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    public boolean nrecibo_repe (Integer nfact){
        Boolean b = true;
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(CONSULTA_NRECIBO_REPETIDO);
            consulta.setInt(1,nfact);
            ResultSet rs = consulta.executeQuery();
            if(rs.next()) b = false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return b;
    }
}
