package sample;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.naming.NamingException;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Maria on 16/10/2015.
 */
public class ProveedorFORM implements Initializable {


    @FXML private TableView<ClienteTable> tablaClientes;
    @FXML private TableColumn columnaNombre;
    @FXML private TableColumn columnaCif;
    ObservableList<ClienteTable> clientes;

    //MENU
    @FXML private Button btncliente;
    @FXML private Button btnfactura;
    @FXML private Button btnpresupuesto;
    @FXML private Button btnrecibo;

    @FXML private Button btnmuestrafac;
    @FXML private Button btnmuestrapre;
    @FXML private Button btnmuestrarec;

    @FXML private Button btnaddprov;
    @FXML private Button btnmodprov;
    @FXML private Button btndeleteprov;
    @FXML private Button btnclear;

    ObservableList<String> listaClientes;
    private Map<String,Cliente> clientmap = new HashMap<>();
    @FXML private ComboBox<String> clienteList;

    @FXML public AnchorPane Back;

    @FXML private TextField txtfieldcif;
    @FXML private TextField txtfieldncomercial;
    @FXML private TextField txtfieldnfiscal;
    @FXML private TextField txtfielddireccion;
    @FXML private TextField txtfieldcp;
    @FXML private TextField txtfieldpoblacion;
    @FXML private TextField txtfieldpais;
    @FXML private TextField txtfieldpercont;
    @FXML private TextField txtfieldtlf;
    @FXML private TextField txtfieldmovil;
    @FXML private TextField txtfieldfax;
    @FXML private TextField txtfieldmail;
    @FXML private TextField txtfieldweb;

    Boolean valor;
    Boolean bool;
    Integer codigo;
    Integer ncuentaaux;

    private Map<String,Cliente> client = new HashMap<>();
    private Context context;
    private Context currentContext =  new Context();

    public void setContext(Context context) {
        this.context = context;
        nuevavista();
    }

    public void nuevavista() {
        String cif = context.currentCliente().getCif();
        DAOProveedor dao = new DAOProveedor();
        try {
            client = dao.consultaproveedorALL();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        for (Map.Entry<String, Cliente> clienteCompleto : client.entrySet()) {
            ClienteTable cliente = new ClienteTable();
            cliente.setNombre(clienteCompleto.getValue().getNombreComercial());
            cliente.setCif(clienteCompleto.getValue().getCif());
            clientes.add(cliente);
        }
        tablaClientes.setItems(clientes);

    }


    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.inicializarTablaPersonas();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillClientes(){
        listaClientes= FXCollections.observableArrayList();
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

    @FXML private void addCliente (ActionEvent event){
        String error = factErrors(txtfieldcif.getText());
        DAOProveedor dao = new DAOProveedor();
        if(!error.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.showAndWait();
        }
        else if(dao.cliente_repe(txtfieldcif.getText()) == false){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Si acepta, se guardaran los cambios");
            alert.setContentText("Esta seguro que desea guardar los cambios?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Cliente cliente = getCliente();
                dao.deleteCliente(txtfieldcif.getText());
                dao.addCliente(cliente);
            }
        }
        else{
            ClienteTable cliente = new ClienteTable();
            Cliente cli = getCliente();
            cliente.setNombre(cli.getNombreComercial());
            cliente.setCif(cli.getCif());
            clientes.add(cliente);
            client.put(cliente.getCif(), cli);
            dao.addCliente(cli);
        }
    }


    @FXML private void deleteCliente (ActionEvent event){
        String error = factErrors(txtfieldcif.getText());
        if(!error.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.showAndWait();
        }
        else {
            String cif = tablaClientes.getSelectionModel().getSelectedItem().getCif();
            if (cif == null) {
                System.out.println("No hay cliente seleccionado");
                return;
            }
            clientes.remove(tablaClientes.getSelectionModel().getFocusedIndex());
            DAOProveedor dao = new DAOProveedor();
            dao.deleteCliente(cif);
            tablaClientes.getSelectionModel().clearSelection();
            borrar();
        }
    }

    @FXML private void modificarCliente(ActionEvent event){
        String error = factErrors(txtfieldcif.getText());
        if(!error.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.showAndWait();
        }
        else {
            String cif = tablaClientes.getSelectionModel().getSelectedItem().getCif();
            Cliente cliente = getCliente();
            if (cif == null) {
                System.out.println("No hay cliente seleccionado");
                return;
            }
            clientes.remove(tablaClientes.getSelectionModel().getFocusedIndex());
            client.remove(cif);
            client.put(cliente.getCif(), cliente);
            DAOProveedor dao = new DAOProveedor();
            dao.modificarCliente(cliente, cif);
            ClienteTable cli = new ClienteTable();
            cli.setNombre(cliente.getNombreComercial());
            cli.setCif(cliente.getCif());
            clientes.add(cli);
        }
    }

    @FXML private  void clearCampos(ActionEvent event){
        txtfieldcif.setText("");
        txtfieldncomercial.setText("");
        txtfieldnfiscal.setText("");
        txtfielddireccion.setText("");
        txtfieldcp.setText("");
        txtfieldpoblacion.setText("");
        txtfieldpais.setText("");
        txtfieldpercont.setText("");
        txtfieldtlf.setText("");
        txtfieldmovil.setText("");
        txtfieldfax.setText("");
        txtfieldmail.setText("");
        txtfieldweb.setText("");
        tablaClientes.getSelectionModel().clearSelection();
    }

    private void listaaux(){
        String cif = txtfieldcif.getText();
        int i = 0;
        int index = -1;
        for(ClienteTable cli : clientes){
            if(cif.equals(cli.getCif())){
                index = i;
            }
            ++i;
        }
        if (index == -1) System.out.println("NOT FOUND");
        else {
            tablaClientes.scrollTo(index);
            tablaClientes.getSelectionModel().select(index);
        }
    }

    @FXML private void muestrafac(ActionEvent event) throws IOException, NamingException {
        URL url = getClass().getResource("vistaMostrarFacturaProveedor.fxml");
        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(url);
        fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
        Back.getChildren().clear();
        Back.getChildren().add((Node) fxmlloader.load(url.openStream()));
        currentContext.currentCliente().setCif(txtfieldcif.getText());
        ((MostrarProveedorFactura)fxmlloader.getController()).setContext(currentContext);
    }

    @FXML private  void borrar( ){
        txtfieldcif.setText("");
        txtfieldncomercial.setText("");
        txtfieldnfiscal.setText("");
        txtfielddireccion.setText("");
        txtfieldcp.setText("");
        txtfieldpoblacion.setText("");
        txtfieldpais.setText("");
        txtfieldpercont.setText("");
        txtfieldtlf.setText("");
        txtfieldmovil.setText("");
        txtfieldfax.setText("");
        txtfieldmail.setText("");
        txtfieldweb.setText("");
        tablaClientes.getSelectionModel().clearSelection();
    }

    @FXML private void textChange(String nombre){
        ObservableList<ClienteTable> listaAux = FXCollections.observableArrayList();
        for (Map.Entry<String, Cliente> clienteCompleto : client.entrySet()) {
            if (clienteCompleto.getValue().getNombreComercial().startsWith(nombre)) {
                ClienteTable cliente = new ClienteTable();
                cliente.setNombre(clienteCompleto.getValue().getNombreComercial());
                cliente.setCif(clienteCompleto.getValue().getCif());
                listaAux.add(cliente);
            }
        }
        clientes.clear();
        clientes.addAll(listaAux);
    }


    private void ponerClienteSeleccionado() {
        ClienteTable cliente = tablaClientes.getSelectionModel().getSelectedItem();
        if (cliente != null) {
            Cliente clienteCompleto = client.get(cliente.getCif());
            txtfieldcif.setText(clienteCompleto.getCif());
            txtfieldncomercial.setText(clienteCompleto.getNombreComercial());
            txtfieldnfiscal.setText(clienteCompleto.getNombreFiscal());
            txtfielddireccion.setText(clienteCompleto.getDireccion());
            txtfieldcp.setText(clienteCompleto.getCodigoPostal().toString());
            txtfieldpoblacion.setText(clienteCompleto.getPoblacion());
            txtfieldpais.setText(clienteCompleto.getPais());
            txtfieldpercont.setText(clienteCompleto.getPersonaContacto());
            txtfieldtlf.setText(clienteCompleto.getTelefono().toString());
            txtfieldmovil.setText(clienteCompleto.getMovil().toString());
            txtfieldfax.setText(clienteCompleto.getFax().toString());
            txtfieldmail.setText(clienteCompleto.getEmail());
            txtfieldweb.setText(clienteCompleto.getPaginaWeb());

        }
    }

    private void ponerClienteSeleccionado2(){
        String cli = clienteList.getSelectionModel().getSelectedItem();
        int index = cli.indexOf(": ");
        cli = cli.substring(index+2);
        Cliente clienteCompleto = client.get(cli);
        txtfieldcif.setText(clienteCompleto.getCif());
        txtfieldncomercial.setText(clienteCompleto.getNombreComercial());
        txtfieldnfiscal.setText(clienteCompleto.getNombreFiscal());
        txtfielddireccion.setText(clienteCompleto.getDireccion());
        txtfieldcp.setText(clienteCompleto.getCodigoPostal().toString());
        txtfieldpoblacion.setText(clienteCompleto.getPoblacion());
        txtfieldpais.setText(clienteCompleto.getPais());
        txtfieldpercont.setText(clienteCompleto.getPersonaContacto());
        txtfieldtlf.setText(clienteCompleto.getTelefono().toString());
        txtfieldmovil.setText(clienteCompleto.getMovil().toString());
        txtfieldfax.setText(clienteCompleto.getFax().toString());
        txtfieldmail.setText(clienteCompleto.getEmail());
        txtfieldweb.setText(clienteCompleto.getPaginaWeb());
    }

    private String factErrors(String cif){
        String error = "";
        DAOconceptosfactura dao = new DAOconceptosfactura();
        if(txtfieldcif.getText().equals("")){
            error = "Cif cliente no puede estar vacio";
        }
        return error;
    }

    private Cliente getCliente() {
        Cliente cli = new Cliente();
        cli.setCif(txtfieldcif.getText());
        cli.setNombreFiscal(txtfieldnfiscal.getText());
        cli.setNombreComercial(txtfieldncomercial.getText());
        cli.setDireccion(txtfielddireccion.getText());
        cli.setPais(txtfieldpais.getText());
        cli.setPoblacion(txtfieldpoblacion.getText());
        //hacer control codigo postal
        if(txtfieldcp.getText().equals("") || !isNumber(txtfieldcp.getText())) codigo = 0;
        else codigo = Integer.valueOf(txtfieldcp.getText());
        cli.setCodigoPostal(codigo);
        cli.setPersonaContacto(txtfieldpercont.getText());
        cli.setTelefono(txtfieldtlf.getText());
        cli.setMovil(txtfieldmovil.getText());
        cli.setFax(txtfieldfax.getText());
        cli.setEmail(txtfieldmail.getText());
        cli.setPaginaWeb(txtfieldweb.getText());
        return cli;
    }

    private void valor_boton_hidden(boolean valor){
        btnmuestrafac.setVisible(valor);
    }

    private void inicializarTablaPersonas() throws SQLException {
        columnaNombre.setCellValueFactory(new PropertyValueFactory<ClienteTable, String>("nombre"));
        columnaCif.setCellValueFactory(new PropertyValueFactory<ClienteTable, String>("cif"));
        clientes = FXCollections.observableArrayList();
        DAOProveedor dao = new DAOProveedor();
        client = dao.consultaproveedorALL();
        for (Map.Entry<String, Cliente> clienteCompleto : client.entrySet()) {
            ClienteTable cliente = new ClienteTable();
            cliente.setNombre(clienteCompleto.getValue().getNombreComercial());
            cliente.setCif(clienteCompleto.getValue().getCif());
            clientes.add(cliente);
        }
        tablaClientes.setItems(clientes);

        tablaClientes.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ponerClienteSeleccionado();
                valor = true;
                valor_boton_hidden(valor);
            }
        });

        fillClientes();
        bool = true;
        codigo = 0;
        ncuentaaux = 0;
        clienteList.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (!oldValue.equalsIgnoreCase(newValue) && bool) {
                    changeCliente();
                    listaaux();
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
                    ponerClienteSeleccionado2();
                    valor = true;
                    valor_boton_hidden(valor);
                    listaaux();
                }
            }
        });
        valor = false;
        valor_boton_hidden(valor);
    }

    private boolean isNumber(String s){
        for(char c: s.toCharArray()) {
            if (c > '9' || c < '0') {
                return false;
            }
        }
        return true;
    }

    @FXML
    private void changeFactura(ActionEvent event) throws IOException {
        Parent view;
        view = (FXMLLoader.load(getClass().getResource("vistaFactura.fxml")));
        Stage mainStage;
        mainStage = Main.stage;
        mainStage.getScene().setRoot(view);
    }

    @FXML
    private void changePresupuesto(ActionEvent event) throws IOException {
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
