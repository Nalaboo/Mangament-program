package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * Created by Maria on 16/10/2015.
 */
public class ProveedorFacturaFORM implements Initializable {

    @FXML
    private TableView<ConceptoTable> tablaconcepto;

    @FXML private TableColumn columnaconcepto;
    @FXML private TableColumn columnaprecio;

    ObservableList<ConceptoTable> listaconceptos;
    ObservableList<String> listaClientes;
    private Map<String,Cliente> client = new HashMap<>();

    @FXML private Button btnexcel;
    @FXML private Button btnsavefactura;
    @FXML private Button btnaddfila;
    @FXML private Button btnbuscar;
    @FXML private Button btnmodifyrow;
    @FXML private Button btndeleterow;

    @FXML private Button btncliente;
    @FXML private Button btnfactura;
    @FXML private Button btnpresupuesto;
    @FXML private Button btnrecibo;

    @FXML private Menu menu;

    @FXML private ComboBox<String> clienteList;

    @FXML private TextField txtfieldnfactura;
    @FXML private TextField txtfieldfecha;
    @FXML private TextField txtconcepto;
    @FXML private TextField txtprecio;
    @FXML private TextField txtpreciototal;
    @FXML private TextField txtprecioiva;
    @FXML private TextField txtcliente;
    @FXML private TextField txtfieldmanerapago;
    @FXML private TextField txtfieldvencimiento;
    @FXML private TextField txtnombrecli;
    @FXML private TextField txtcifcli;
    @FXML private TextField txtdireccioncli;
    @FXML private TextField txtfieldpoblacion;
    @FXML private TextField txtfieldnombrefac;

    double total;
    double iva;
    boolean bool;
    boolean control_cliente = false;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
        nuevavista();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        fillClientes();
        total = 0;
        iva = 0;
        bool = true;

        clienteList.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (!oldValue.equalsIgnoreCase(newValue) && bool) {
                    changeCliente();
                    control_cliente = true;
                } else if (!bool) {
                    bool = true;
                }
            }
        });

        clienteList.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue ov, String t, String t1) {
                if (t1 != null) {
                    bool = false;
                    ponerClienteSeleccionado();
                    control_cliente = true;
                }
            }
        });
    }


    public void nuevavista() {
        Integer nfact  = context.currentFactura().getNfactura();
        DAOProveedorFactura dao = new DAOProveedorFactura();
        Facturaconc fac =  dao.getfact(nfact);
        txtfieldnfactura.setText(String.valueOf(fac.getNfactura()));
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        txtfieldfecha.setText(df.format(fac.getFecha()));
        txtpreciototal.setText(String.valueOf(fac.getPreciototal()));
        txtprecioiva.setText(String.valueOf(fac.getPrecioiva()));
        txtfieldnombrefac.setText(String.valueOf(fac.getnombrefac()));
        txtfieldmanerapago.setText(fac.getFormapago());
        txtfieldvencimiento.setText(fac.getVencimiento());
        Cliente cli =  dao.clicif(fac.getIdcliente());
        control_cliente = true;
        txtnombrecli.setText(cli.getNombreComercial());
        txtcifcli.setText(cli.getCif());
        txtdireccioncli.setText(cli.getDireccion());
        txtfieldpoblacion.setText(cli.getPoblacion());
        total = fac.getPreciototal();
        iva = fac.getPrecioiva();
    }

    private void ponerClienteSeleccionado(){
        String cli = clienteList.getSelectionModel().getSelectedItem();
        int index = cli.indexOf(": ");
        cli = cli.substring(index+2);
        Cliente cliente = client.get(cli);
        txtnombrecli.setText(cliente.getNombreComercial());
        txtcifcli.setText(cliente.getCif());
        txtdireccioncli.setText(cliente.getDireccion());
        txtfieldpoblacion.setText(cliente.getPoblacion());
        control_cliente = true;
    }

    private String factErrors(String nfactura){
        String error = "";
        DAOconceptosfactura dao = new DAOconceptosfactura();
        if(txtcifcli.getText().equals("") || control_cliente == false){
            error = "Cif cliente no puede estar vacio";
        }
        else if(!isNumber(nfactura)){
            error = "N?mero factura debe ser un n?mero, no se aceptan letras.";
        }
        else if (nfactura.equals("")){
            error = "N?mero factura no puede estar vac?o, se necesita un n?mero.";
        }
        else if(txtfieldfecha.getText().equals("")){
            java.util.Date today = Calendar.getInstance().getTime();
            DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
            txtfieldfecha.setText(df.format(today));
        }
        //Si la fecha no es de la forma que toca, poner aviso, pqe si no peta
        return error;
    }

    private java.sql.Date getData(){
        String startDateString =  txtfieldfecha.getText();
        DateFormat df = new SimpleDateFormat("dd-MM-yyyy");
        java.util.Date startDate = null;
        try {
            startDate = df.parse(startDateString);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        java.sql.Date date = new java.sql.Date(startDate.getTime());
        return  date;
    }

    private Facturaconc getFact (String nfactura){
        Facturaconc factura = new Facturaconc();
        factura.setNfactura(Integer.parseInt(nfactura));
        factura.setIdcliente(txtcifcli.getText());
        factura.setNombrefac(txtfieldnombrefac.getText());
        DAOProveedorFactura dao = new DAOProveedorFactura();
        if(txtpreciototal.getText().equals("")) factura.setPreciototal(total);
        else factura.setPreciototal(Double.parseDouble(txtpreciototal.getText()));
        if(txtprecioiva.getText().equals("")) factura.setPrecioiva(iva);
        else factura.setPrecioiva(Double.parseDouble(txtprecioiva.getText()));
        factura.setFormapago(txtfieldmanerapago.getText());
        factura.setVencimiento(txtfieldvencimiento.getText());
        factura.setFecha(getData());
        total = 0;
        iva = 0;
        return factura;
    }

    @FXML private void savefact (ActionEvent event) throws ParseException {
        DAOProveedorFactura dao = new DAOProveedorFactura();
        String nfactura = txtfieldnfactura.getText();
        String error = factErrors(nfactura);
        if(!error.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.showAndWait();
        }
        else if(dao.nfact_repe(Integer.parseInt(nfactura)) == false){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Si acepta, se guardaran los cambios");
            alert.setContentText("Esta seguro que desea guardar los cambios?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Facturaconc factura = getFact(nfactura);
                dao.deletefac(Integer.valueOf(nfactura));
                dao.savefact(factura,listaconceptos, Integer.valueOf(nfactura));
            }
        }
        else{
            Facturaconc factura = getFact(nfactura);
            Integer nfact = (Integer.parseInt(nfactura));
            dao.savefact(factura, listaconceptos, nfact);
        }

    }

    private void fillClientes(){
        listaClientes = FXCollections.observableArrayList();
        DAOProveedor dao = new DAOProveedor();
        try {
            client = dao.consultaproveedorALL();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Cliente> clienteCompleto : client.entrySet()) {
            listaClientes.add(clienteCompleto.getValue().getNombreComercial() + ": " + clienteCompleto.getKey());
        }
        clienteList.setItems(listaClientes);
        control_cliente = true;
    }

    private void changeCliente(){
        String nombre = clienteList.getEditor().getText();
        if(!clienteList.getEditor().getText().isEmpty()){
            ObservableList<String> listaAux = FXCollections.observableArrayList();
            for (Map.Entry<String, Cliente> clienteCompleto : client.entrySet()) {
                if (clienteCompleto.getValue().getNombreComercial().startsWith(nombre) || clienteCompleto.getValue().getCif().startsWith(nombre)) {
                    listaAux.add(clienteCompleto.getValue().getNombreComercial() + ": " + clienteCompleto.getKey());
                }
            }
            listaClientes.clear();
            if (listaAux.size() == 0) listaAux.add("Cliente no encontrado");
            listaClientes = listaAux;
            clienteList.setItems(listaClientes);
        }
    }

    private void calculo_preciototal(){
        for (ConceptoTable t : listaconceptos) {
            total =  total + t.getPrecio();
        }
        iva = total*0.21 + total;
    }
    private boolean isNumber(String s){
        int cont = 0;
        if (s.equals(".")) return false;
        for(char c: s.toCharArray()) {
            if (c > '9' || c < '0') {
                if(c != '.' || cont != 0){
                    return false;
                }
                else ++cont;
            }
        }
        return true;
    }

    @FXML private void changeFactura(ActionEvent event) throws IOException {
        Parent view;
        view = (FXMLLoader.load(getClass().getResource("vistaFactura.fxml")));
        Stage mainStage;
        mainStage = Main.stage;
        mainStage.getScene().setRoot(view);
    }

    @FXML private void changePresupuesto(ActionEvent event) throws IOException {
        Parent view;
        view = (FXMLLoader.load(getClass().getResource("vistaPresupuesto.fxml")));
        Stage mainStage;
        mainStage = Main.stage;
        mainStage.getScene().setRoot(view);
    }

    @FXML
    private void changeRecibo(ActionEvent event) throws IOException {
        Parent view;
        view = (FXMLLoader.load(getClass().getResource("sample2.fxml")));
        Stage mainStage;
        mainStage = Main.stage;
        mainStage.getScene().setRoot(view);
    }

    @FXML
    private void changeCliente(ActionEvent event) throws IOException {
        Parent view;
        view = (FXMLLoader.load(getClass().getResource("vistaBusquedaCliente.fxml")));
        Stage mainStage;
        mainStage = Main.stage;
        mainStage.getScene().setRoot(view);
    }
    @FXML private void changeProveedor(ActionEvent event) throws IOException {
        Parent view;
        view = (FXMLLoader.load(getClass().getResource("vistaProveedor.fxml")));
        Stage mainStage;
        mainStage = Main.stage;
        mainStage.getScene().setRoot(view);
    }

    @FXML private void changeProveedorFactura(ActionEvent event) throws IOException {
        Parent view;
        view = (FXMLLoader.load(getClass().getResource("vistaFacturaProveedor.fxml")));
        Stage mainStage;
        mainStage = Main.stage;
        mainStage.getScene().setRoot(view);
    }
}