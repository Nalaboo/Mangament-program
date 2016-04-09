package sample;

import javafx.collections.ObservableList;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


public class DAOpresupuesto {

    private String INSERT_PRESUPUESTO = "INSERT INTO presupuesto VALUES (?,?,?,?)";
    private String INSERT_CLIENTES = "INSERT INTO presupuestotable VALUES (?,?,?)";
    private String CONSULTA_CLIENTE = "SELECT * FROM cliente where n_comercial = ?";
    private String DELETE_PRE = "DELETE FROM presupuesto WHERE nombrepre = ?";
    private String DELETE_PRETABLE = "DELETE FROM presupuestotable WHERE nombrepre = ?";
    private String GET_PRE = "SELECT * FROM presupuesto where nombrepre = ?";
    private String CONSULTA_CLIENTE_CIF = "SELECT * FROM cliente where cif = ?";
    private String GET_PRETABLE = "SELECT * FROM  presupuestotable WHERE nombrepre = ?";
    private String DELETE_ROW = "DELETE FROM presupuestotable WHERE nombrepre = ? and concepto = ?";
    private String CONSULTA_PRE_REPETIDO = "SELECT nombrepre FROM presupuesto where nombrepre = ?";



    public Presupuestoc getpre(String npre){
        Presupuestoc presupuesto = new Presupuestoc();
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement insert = c.prepareStatement(GET_PRE);
            insert.setString(1, npre);
            ResultSet rs = insert.executeQuery();
            String idcliente = rs.getString("idcliente");
            Double preciototal = rs.getDouble("preciototal");
            Double precioiva = rs.getDouble("precioiva");
            String nombrepre = rs.getString("nombrepre");
            presupuesto = new Presupuestoc(nombrepre,idcliente,preciototal,precioiva);
        }catch (SQLException e){
            e.printStackTrace();
        }
        return presupuesto;
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

    public List getpretable (String npre){
        List<ConceptoTable> l = new ArrayList<>();
        String sDriverName = "org.sqlite.JDBC";
        ConceptoTable fila;
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        System.out.println("A ver que pasa");
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement insert = c.prepareStatement(GET_PRETABLE);
            insert.setString(1, npre);
            System.out.println("insert hecho");
            try (ResultSet rs = insert.executeQuery()) {
                while (rs.next()) {
                    String concepto = rs.getString("concepto");
                    Double precio = rs.getDouble("precio");
                    ConceptoTable m = new ConceptoTable(concepto,precio);
                    l.add(m);
                }
            }catch (SQLException e) {
                e.printStackTrace();
            }

        }catch (SQLException e){
            e.printStackTrace();
        }
        return l;
    }

    public void savepre (Presupuestoc pre,ObservableList<ConceptoTable> L) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement insert = c.prepareStatement(INSERT_PRESUPUESTO);
            insert.setString(1, pre.getNombrepre());
            insert.setString(2, pre.getIdcliente());
            insert.setDouble(3, pre.getPreciototal());
            insert.setDouble(4, pre.getPrecioiva());
            insert.executeUpdate();

            //........
            PreparedStatement insert2 = c.prepareStatement(INSERT_CLIENTES);
            for (ConceptoTable t : L) {
                insert2.setString(1, pre.getNombrepre());
                insert2.setString(2, t.getConsulta());
                insert2.setDouble(3, Double.valueOf(t.getPrecio()));
                insert2.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addconpre (PresupuestoTable conf, String npre){
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement insert = c.prepareStatement(INSERT_CLIENTES);
            insert.setString(1,npre);
            insert.setString(2, conf.getConcepto());
            insert.setDouble(3, conf.getPrecio());
            insert.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Cliente getCli (String ncliente){
        Cliente cliente = new Cliente();
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(CONSULTA_CLIENTE);
            consulta.setString(1,ncliente);
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

    public void deletepre(String npre) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement delete = c.prepareStatement(DELETE_PRE);
            delete.setString(1, npre);
            delete.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletepretable(String npre) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement delete = c.prepareStatement(DELETE_PRETABLE);
            delete.setString(1, npre);
            delete.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleterow(String concepto, String npre) {
        String sDriverName = "org.sqlite.JDBC";
        System.out.println("DELETE SGUNDO");
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement delete = c.prepareStatement(DELETE_ROW);
            delete.setString(1, npre);
            delete.setString(2, concepto);

            delete.execute();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean nombrepre_repe (String nombre){
        Boolean b = true;
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(CONSULTA_PRE_REPETIDO);
            consulta.setString(1, nombre);
            ResultSet rs = consulta.executeQuery();

            if(rs.next()) b = false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return b;
    }

}
