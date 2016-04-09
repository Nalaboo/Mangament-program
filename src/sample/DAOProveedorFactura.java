package sample;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Maria on 16/10/2015.
 */
public class DAOProveedorFactura {
    private String INSERT_FACTURA = "INSERT INTO facturaproveedor VALUES (?,?,?,?,?,?,?,?)";
    private String GET_FACTURA = "SELECT * FROM facturaproveedor where nfactura = ?";
    private String CONSULTA_NFACT_REPETIDO = "SELECT nfactura FROM facturaproveedor where nfactura = ?";
    private String CONSULTA_CLIENTE_CIF = "SELECT * FROM proveedor where cif = ?";
    private String DELETE_FAC = "DELETE FROM facturaproveedor WHERE nfactura = ?";

    public Facturaconc getfact(Integer nfacturaaux){
        Facturaconc factura = new Facturaconc();
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement insert = c.prepareStatement(GET_FACTURA);
            insert.setInt(1, nfacturaaux);
            ResultSet rs = insert.executeQuery();
            if(rs.next()) {
                Integer nfactura = rs.getInt("nfactura");//Obtencion de la informacion de las columnas de la query
                String idcliente = rs.getString("idcliente");
                Date fecha = rs.getDate("fecha");
                Double preciototal = rs.getDouble("preciototal");
                Double precioiva = rs.getDouble("precioiva");
                String nombrefac = rs.getString("nombrefac");
                String formapago = rs.getString("formapago");
                String vencimiento = rs.getString("vencimiento");

                factura = new Facturaconc(nfactura, idcliente, fecha, preciototal, precioiva, nombrefac,vencimiento,formapago);
            }
            else {
                //a?adir salida de no encontrado
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return factura;
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
            consulta.setString(1, cifaux);
            ResultSet rs = consulta.executeQuery();
            if(rs.next()) {
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
                cliente = new Cliente(cif, nfiscal, ncomercial, direccion, pais, cp, poblacion, pcontacto, tlf, movil, fax, mail, web);
            }
            else {
                //a?adir salida de no encontrado
            }
        }catch (SQLException e){
            e.printStackTrace();
        }
        return cliente;
    }

    public void savefact (Facturaconc fact,ObservableList<ConceptoTable> L, Integer nfact) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement insert = c.prepareStatement(INSERT_FACTURA);
            insert.setInt(1, fact.getNfactura());
            insert.setString(2, fact.getIdcliente());
            insert.setDate(3, fact.getFecha());
            insert.setDouble(4, fact.getPreciototal());
            insert.setDouble(5, fact.getPrecioiva());
            insert.setString(6, fact.getnombrefac());
            insert.setString(7, fact.getFormapago());
            insert.setString(8, fact.getVencimiento());
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean nfact_repe (Integer nfact){
        Boolean b = true;
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(CONSULTA_NFACT_REPETIDO);
            consulta.setInt(1,nfact);
            ResultSet rs = consulta.executeQuery();
            if(rs.next()) b = false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return b;
    }

    public void deletefac(Integer nfac) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement delete = c.prepareStatement(DELETE_FAC);
            delete.setInt(1, nfac);
            delete.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
