package sample;

import javafx.collections.ObservableList;
import javafx.collections.ObservableListBase;

import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DAO {

    private String SELECT_CLIENTES_ALL = "SELECT * FROM cliente ORDER BY n_comercial";
    private String INSERT_CLIENTES = "INSERT INTO cliente VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
    private String DELETE_CLIENTES = "DELETE FROM cliente WHERE CIF = ?";
    private String UPDATE_CLIENTE = "UPDATE cliente SET cif = ?, n_fiscal = ?,n_comercial = ?,direccion = ?," +
            "pais = ?,cp = ?,poblacion = ?,p_contacto = ?,tlf = ?,movil = ?,fax = ?,email = ?,pweb = ? WHERE cif = ?";
    private String SELECT_FACT_CLI = "SELECT nfactura,nombrefac FROM factura WHERE idcliente = ? ORDER by nfactura";
    private String SELECT_PRE_CLI = "SELECT nombrepre FROM presupuesto WHERE idcliente = ? ";
    private String GET_PAGADORES = "SELECT * FROM pagador WHERE idcliente = ?";
    private String INSERT_PAGADOR = "INSERT INTO pagador VALUES (?,?,?,?)";
    private String DELETE_PAGADOR = "DELETE FROM pagador WHERE idcliente = ? and npersona = ?";
    private String SELECT_RE_CLI = "SELECT nrecibo, nombrerecibo FROM recibo WHERE idcliente = ? ORDER by nrecibo";
    private String CONSULTA_CLIENTE_REPETIDO = "SELECT cif FROM cliente where cif = ?";
    private String CONSULTA_NCUENTA_REPETIDO = "SELECT ncuenta FROM pagador where ncuenta = ?";

    public Map<String, Cliente> consultaClientesAll() throws SQLException {
        Map<String, Cliente> listaClientes = new HashMap<>();
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(SELECT_CLIENTES_ALL);
            try (ResultSet rs = consulta.executeQuery()) {
                while (rs.next()) {
                    rs.getString("cif");
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
                    Cliente cliente = new Cliente(cif, nfiscal, ncomercial, direccion, pais, cp, poblacion, pcontacto, tlf, movil, fax, mail, web);
                    listaClientes.put(cif, cliente);
                    System.out.println(listaClientes.size());
                }
                return listaClientes;
            } catch (SQLException e) {
                System.out.println("Error de Query");
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        return listaClientes;
    }

    public void modificarCliente(Cliente cli, String cif) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement update = c.prepareStatement(UPDATE_CLIENTE);
            update.setString(1, cli.getCif());
            update.setString(2, cli.getNombreFiscal());
            update.setString(3, cli.getNombreComercial());
            update.setString(4, cli.getDireccion());
            update.setString(5, cli.getPais());
            update.setInt(6, cli.getCodigoPostal());
            update.setString(7, cli.getPoblacion());
            update.setString(8, cli.getPersonaContacto());
            update.setString(9, cli.getTelefono());
            update.setString(10, cli.getMovil());
            update.setString(11, cli.getFax());
            update.setString(12, cli.getEmail());
            update.setString(13, cli.getPaginaWeb());
            update.setString(14, cif);
            update.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void addCliente(Cliente cli) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement insert = c.prepareStatement(INSERT_CLIENTES);
            insert.setString(1, cli.getCif());
            insert.setString(2, cli.getNombreFiscal());
            insert.setString(3, cli.getNombreComercial());
            insert.setString(4, cli.getDireccion());
            insert.setString(5, cli.getPais());
            insert.setInt(6, cli.getCodigoPostal());
            insert.setString(7, cli.getPoblacion());
            insert.setString(8, cli.getPersonaContacto());
            insert.setString(9, cli.getTelefono());
            insert.setString(10, cli.getMovil());
            insert.setString(11, cli.getFax());
            insert.setString(12, cli.getEmail());
            insert.setString(13, cli.getPaginaWeb());
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteCliente(String cif) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement delete = c.prepareStatement(DELETE_CLIENTES);
            delete.setString(1, cif);
            delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<MostrarTable> muestrafac(String idclientee) {
        List<MostrarTable> lista = new ArrayList<>();
        String sDriverName = "org.sqlite.JDBC";
        Integer nfact = 0;
        String nombrefac;
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(SELECT_FACT_CLI);
            consulta.setString(1, idclientee);
            try (ResultSet rs = consulta.executeQuery()) {
                while (rs.next()) {
                    nfact = rs.getInt("nfactura");
                    nombrefac = rs.getString("nombrefac");
                    MostrarTable m = new MostrarTable(nfact,nombrefac);
                    lista.add(m);
                }
                return lista;
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Error de Query");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            System.out.println("error 2o catch");
        }
        return lista;
    }

    public List<Mostrartablepre> muestrapre(String idclientee) {
        List<Mostrartablepre> lista = new ArrayList<>();
        String sDriverName = "org.sqlite.JDBC";
        String nombrepre;
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(SELECT_PRE_CLI);
            consulta.setString(1, idclientee);
            try (ResultSet rs = consulta.executeQuery()) {
                while (rs.next()) {
                    nombrepre = rs.getString("nombrepre");
                    Mostrartablepre m = new Mostrartablepre(nombrepre);
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

    public List<MostrarTable> muestrare(String idclientee) {
        List<MostrarTable> lista = new ArrayList<>();
        String sDriverName = "org.sqlite.JDBC";
        Integer nrecibo = 0;
        String nombrere;
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(SELECT_RE_CLI);
            consulta.setString(1, idclientee);
            try (ResultSet rs = consulta.executeQuery()) {
                while (rs.next()) {
                    nrecibo = rs.getInt("nrecibo");
                    nombrere = rs.getString("nombrerecibo");
                    MostrarTable m = new MostrarTable(nrecibo,nombrere);
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


    public List<PagadorTable> getpagadores(String idclientee) {
        List<PagadorTable> lista = new ArrayList<>();
        String sDriverName = "org.sqlite.JDBC";
        String npersona, dompag;
        Integer ncuenta;
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(GET_PAGADORES);
            consulta.setString(1, idclientee);
            try (ResultSet rs = consulta.executeQuery()) {
                while (rs.next()) {
                    npersona = rs.getString("npersona");
                    dompag = rs.getString("dompag");
                    ncuenta = rs.getInt("ncuenta");
                    PagadorTable m = new PagadorTable(npersona,dompag,ncuenta);
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

    public void addpagador( String cif, String nompag,String dompag, Integer ncuenta) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement insert = c.prepareStatement(INSERT_PAGADOR);
            insert.setString(1, cif);
            insert.setString(2, nompag);
            insert.setInt(3, ncuenta);
            insert.setString(4, dompag);
            insert.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deletepagador(String nompag,String cif) {
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement delete = c.prepareStatement(DELETE_PAGADOR);
            delete.setString(1, cif);
            delete.setString(2, nompag);
            delete.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public boolean cliente_repe (String cif){
        Boolean b = true;
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(CONSULTA_CLIENTE_REPETIDO);
            consulta.setString(1,cif);
            ResultSet rs = consulta.executeQuery();
            if(rs.next()) b = false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return b;
    }

    public boolean ncuenta_repe (String ncuenta){
        Boolean b = true;
        String sDriverName = "org.sqlite.JDBC";
        try {
            Class.forName(sDriverName);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try (Connection c = DriverManager.getConnection("jdbc:sqlite:C:/Users/Maria/Desktop/prueba.db")) {
            PreparedStatement consulta = c.prepareStatement(CONSULTA_NCUENTA_REPETIDO);
            consulta.setString(1, ncuenta);
            ResultSet rs = consulta.executeQuery();
            if(rs.next()) b = false;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return b;
    }

}

