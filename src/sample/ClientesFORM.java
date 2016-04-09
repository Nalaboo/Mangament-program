package sample;


import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.ListChangeListener;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.naming.NamingException;
import java.awt.event.KeyEvent;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;


public class ClientesFORM implements Initializable {

    @FXML private TableView<ClienteTable> tablaClientes;
    @FXML private TableColumn columnaNombre;
    @FXML private TableColumn columnaCif;
    ObservableList<ClienteTable> clientes;

    @FXML private TableView<PagadorTable> tablepagador;
    @FXML private TableColumn colnombre;
    @FXML private TableColumn colncuenta;
    @FXML private TableColumn coldomicilio;
    ObservableList<PagadorTable> listapagadores;

    @FXML private Button btncliente;
    @FXML private Button btnfactura;
    @FXML private Button btnpresupuesto;
    @FXML private Button btnrecibo;

    @FXML private Button btnaddpagador;
    @FXML private Button btnmodpagador;
    @FXML private Button btndeletepagador;

    @FXML private Button btnmuestrafac;
    @FXML private Button btnmuestrapre;
    @FXML private Button btnmuestrarec;
    ObservableList<String> listaClientes;
    private Map<String,Cliente> clientmap = new HashMap<>();
    @FXML private ComboBox<String> clienteList;

    @FXML public AnchorPane Back;

    @FXML private TextField cifField;
    @FXML private TextField comercialField;
    @FXML private TextField fiscalField;
    @FXML private TextField direccionField;
    @FXML private TextField cpField;
    @FXML private TextField poblacionField;
    @FXML private TextField paisField;
    @FXML private TextField personaField;
    @FXML private TextField telefonoField;
    @FXML private TextField movilField;
    @FXML private TextField faxField;
    @FXML private TextField mailField;
    @FXML private TextField webField;

    @FXML private  TextField txtfieldnombrepagador;
    @FXML private  TextField txtfielddomiciliopagador;
    @FXML private  TextField txtfieldncuenta;
    Boolean bool;
    Integer codigo;
    Integer ncuentaaux;
    private Context currentContext =  new Context();

    private Map<String,Cliente> client = new HashMap<>();


    public void initialize(URL location, ResourceBundle resources) {
        try {
            this.inicializarTablaPersonas();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void fillClientes(){
        listaClientes = FXCollections.observableArrayList();
        DAO dao = new DAO();
        try {
            client = dao.consultaClientesAll();
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

    private void deleteCliente(){
        String nombre = clienteList.getEditor().getText();
        if(!clienteList.getEditor().getText().isEmpty()){
            ObservableList<String> listaAux = FXCollections.observableArrayList();
            for (Map.Entry<String, Cliente> clienteCompleto : client.entrySet()) {
                if (clienteCompleto.getValue().getNombreComercial().startsWith(nombre) || clienteCompleto.getValue().getCif().startsWith(nombre)) {
                    listaAux.remove(clienteCompleto.getValue().getNombreComercial() + ": " + clienteCompleto.getKey());
                }
            }
            listaClientes.clear();
            if (listaAux.size() == 0) listaAux.add("Cliente no encontrado");
            listaClientes = listaAux;
            clienteList.setItems(listaClientes);
        }
    }

    private void ponerClienteSeleccionado2(){
        String cli = clienteList.getSelectionModel().getSelectedItem();
        int index = cli.indexOf(": ");
        cli = cli.substring(index+2);
        Cliente cliente = client.get(cli);
        cifField.setText(cliente.getCif());
        comercialField.setText(cliente.getNombreComercial());
        fiscalField.setText(cliente.getNombreFiscal());
        direccionField.setText(cliente.getDireccion());
        cpField.setText(cliente.getCodigoPostal().toString());
        poblacionField.setText(cliente.getPoblacion());
        paisField.setText(cliente.getPais());
        personaField.setText(cliente.getPersonaContacto());
        telefonoField.setText(cliente.getTelefono().toString());
        movilField.setText(cliente.getMovil().toString());
        faxField.setText(cliente.getFax().toString());
        mailField.setText(cliente.getEmail());
        webField.setText(cliente.getPaginaWeb());
        /////////////////////////////////////////////////
        colnombre.setCellValueFactory(new PropertyValueFactory<PagadorTable, String>("nombre"));
        coldomicilio.setCellValueFactory(new PropertyValueFactory<PagadorTable, String>("domicilio"));
        colncuenta.setCellValueFactory(new PropertyValueFactory<PagadorTable, String>("ncuenta"));
        listapagadores = FXCollections.observableArrayList();
        DAO dao = new DAO();
        List<PagadorTable> l = dao.getpagadores(cliente.getCif());
        for(PagadorTable i : l) listapagadores.add(i);
        tablepagador.setItems(listapagadores);
    }

    private void ponerPagadorSeleccionado(){
        String nombre = tablepagador.getSelectionModel().getSelectedItem().getNombre();
        String domicilio = tablepagador.getSelectionModel().getSelectedItem().getDompag();
        Integer ncuenta = tablepagador.getSelectionModel().getSelectedItem().getNcuenta();
        txtfieldnombrepagador.setText(nombre);
        txtfielddomiciliopagador.setText(domicilio);
        txtfieldncuenta.setText(String.valueOf(ncuenta));
    }
    @FXML private void addCliente (ActionEvent event){
        String error = factErrors(cifField.getText());
        DAO dao = new DAO();
        if(!error.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.showAndWait();
        }
       else if(dao.cliente_repe(cifField.getText()) == false){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Si acepta, se guardaran los cambios");
            alert.setContentText("Esta seguro que desea guardar los cambios?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Cliente cliente = getCliente();
                dao.deleteCliente(cifField.getText());
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
        String error = factErrors(cifField.getText());
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
            DAO dao = new DAO();
            dao.deleteCliente(cif);
            tablaClientes.getSelectionModel().clearSelection();
            deleteCliente();
        }
    }

    @FXML private void modificarCliente(ActionEvent event){
        String error = factErrors(cifField.getText());
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
            DAO dao = new DAO();
            dao.modificarCliente(cliente, cif);
            ClienteTable cli = new ClienteTable();
            cli.setNombre(cliente.getNombreComercial());
            cli.setCif(cliente.getCif());
            clientes.add(cli);
        }
    }

    @FXML private  void clearCampos(ActionEvent event){
        cifField.setText("");
        comercialField.setText("");
        fiscalField.setText("");
        direccionField.setText("");
        cpField.setText("");
        poblacionField.setText("");
        paisField.setText("");
        personaField.setText("");
        telefonoField.setText("");
        movilField.setText("");
        faxField.setText("");
        mailField.setText("");
        webField.setText("");
        tablaClientes.getSelectionModel().clearSelection();
        listapagadores.clear();
        listapagadores = FXCollections.observableArrayList();
        tablepagador.setItems(listapagadores);
    }

  private void listaaux(){
        String cif = cifField.getText();
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
        URL url = getClass().getResource("vistaMostrar.fxml");
        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(url);
        fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
        Back.getChildren().clear();
        Back.getChildren().add((Node) fxmlloader.load(url.openStream()));
        currentContext.currentCliente().setCif(cifField.getText());
        ((MostrarFactura)fxmlloader.getController()).setContext(currentContext);
    }

    @FXML private void muestrapre(ActionEvent event) throws IOException, NamingException{
        URL url = getClass().getResource("vistaMostrarPresupuesto.fxml");
        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(url);
        fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
        Back.getChildren().clear();
        Back.getChildren().add((Node) fxmlloader.load(url.openStream()));
        currentContext.currentCliente().setCif(cifField.getText());
        ((MostrarPresupuesto)fxmlloader.getController()).setContext(currentContext);
    }

    @FXML private void muestrarec(ActionEvent event) throws IOException, NamingException{
        URL url = getClass().getResource("vistaMostrarRecibo.fxml");
        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(url);
        fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
        Back.getChildren().clear();
        Back.getChildren().add((Node) fxmlloader.load(url.openStream()));
        currentContext.currentCliente().setCif(cifField.getText());
        ((MostrarRecibo)fxmlloader.getController()).setContext(currentContext);
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
            cifField.setText(clienteCompleto.getCif());
            comercialField.setText(clienteCompleto.getNombreComercial());
            fiscalField.setText(clienteCompleto.getNombreFiscal());
            direccionField.setText(clienteCompleto.getDireccion());
            cpField.setText(clienteCompleto.getCodigoPostal().toString());
            poblacionField.setText(clienteCompleto.getPoblacion());
            paisField.setText(clienteCompleto.getPais());
            personaField.setText(clienteCompleto.getPersonaContacto());
            telefonoField.setText(clienteCompleto.getTelefono().toString());
            movilField.setText(clienteCompleto.getMovil().toString());
            faxField.setText(clienteCompleto.getFax().toString());
            mailField.setText(clienteCompleto.getEmail());
            webField.setText(clienteCompleto.getPaginaWeb());
            /////////////////////////////////////////////////
            colnombre.setCellValueFactory(new PropertyValueFactory<PagadorTable, String>("nombre"));
            colncuenta.setCellValueFactory(new PropertyValueFactory<PagadorTable, String>("ncuenta"));
            coldomicilio.setCellValueFactory(new PropertyValueFactory<PagadorTable, String>("domicilio"));
            listapagadores = FXCollections.observableArrayList();
            DAO dao = new DAO();
            List<PagadorTable> l = dao.getpagadores(clienteCompleto.getCif());
            for(PagadorTable i : l) listapagadores.add(i);
            tablepagador.setItems(listapagadores);
        }
    }


    private String factErrors(String cif){
        String error = "";
        DAOconceptosfactura dao = new DAOconceptosfactura();
        if(cifField.getText().equals("")){
            error = "Cif cliente no puede estar vacio";
        }
        return error;
    }

    private Cliente getCliente() {
        Cliente cli = new Cliente();
            cli.setCif(cifField.getText());
            cli.setNombreFiscal(fiscalField.getText());
            cli.setNombreComercial(comercialField.getText());
            cli.setDireccion(direccionField.getText());
            cli.setPais(paisField.getText());
            cli.setPoblacion(poblacionField.getText());
            //hacer control codigo postal
            if(cpField.getText().equals("") || !isNumber(cpField.getText())) codigo = 0;
            else codigo = Integer.valueOf(cpField.getText());
            cli.setCodigoPostal(codigo);
            cli.setPersonaContacto(personaField.getText());
            cli.setTelefono(telefonoField.getText());
            cli.setMovil(movilField.getText());
            cli.setFax(faxField.getText());
            cli.setEmail(mailField.getText());
            cli.setPaginaWeb(webField.getText());
            return cli;
    }

    private void inicializarTablaPersonas() throws SQLException {
        columnaNombre.setCellValueFactory(new PropertyValueFactory<ClienteTable, String>("nombre"));
        columnaCif.setCellValueFactory(new PropertyValueFactory<ClienteTable, String>("cif"));
        clientes = FXCollections.observableArrayList();
        DAO dao = new DAO();
        client = dao.consultaClientesAll();
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
                btnmuestrafac.setVisible(true);
                btnmuestrapre.setVisible(true);
                btnmuestrarec.setVisible(true);
            }
        });

        tablepagador.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ponerPagadorSeleccionado();
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
                    btnmuestrafac.setVisible(true);
                    btnmuestrapre.setVisible(true);
                    btnmuestrarec.setVisible(true);
                    listaaux();
                }
            }
        });

        btnmuestrafac.setVisible(false);
        btnmuestrarec.setVisible(false);
        btnmuestrapre.setVisible(false);
    }

    private boolean isNumber(String s){
        for(char c: s.toCharArray()) {
            if (c > '9' || c < '0') {
                return false;
            }
        }
        return true;
    }

    private String pagadorErrors(String ncuenta){
        String error = "";
        DAOconceptosfactura dao = new DAOconceptosfactura();
        if (!isNumber(txtfieldncuenta.getText()))    {
            error = "Ncuenta solo acepta numeros";
        }

        return error;
    }

    @FXML private void addpagador(ActionEvent event){
        String error = factErrors(cifField.getText());
        String error2 = pagadorErrors(cifField.getText());
        DAO dao = new DAO();
        if(!error.equals("") || !error2.equals("")) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.showAndWait();
        }
        else if (dao.ncuenta_repe(txtfieldncuenta.getText()) == false) {
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Error");
                alert.setHeaderText(null);
                alert.setContentText("Numero de cuenta repetido");
                alert.showAndWait();
        }
        else{
            if (txtfieldncuenta.getText().equals("")) {
                ncuentaaux = 0;
            }
                String dompag = txtfielddomiciliopagador.getText();
                ncuentaaux = Integer.valueOf(txtfieldncuenta.getText());
                String nompag = txtfieldnombrepagador.getText();
                String cif = cifField.getText();
                dao.addpagador(cif, nompag, dompag, ncuentaaux);
                PagadorTable fila = new PagadorTable(nompag, dompag, ncuentaaux);
                listapagadores.add(fila);
        }
    }

    @FXML private void modpagador(ActionEvent event){
        String error = factErrors(cifField.getText());
        if(!error.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.showAndWait();
        }
        else {
            String nombre = tablepagador.getSelectionModel().getSelectedItem().getNombre();
            Integer index = tablepagador.getSelectionModel().getSelectedIndex();
            String cif = cifField.getText();
            DAO dao = new DAO();
            dao.deletepagador(nombre, cif);
            String nombreaux = txtfieldnombrepagador.getText();
            String domicilio = txtfielddomiciliopagador.getText();
            Integer ncuenta = Integer.valueOf(txtfieldncuenta.getText());
            listapagadores.remove(tablepagador.getSelectionModel().getFocusedIndex());
            dao.addpagador(cif, nombreaux, domicilio, ncuenta);
            PagadorTable fila = new PagadorTable(nombreaux, domicilio, ncuenta);
            listapagadores.add(index, fila);
        }
    }

    @FXML private void deletepagador(ActionEvent event){
        String error = factErrors(cifField.getText());
        if(!error.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.showAndWait();
        }
        else {
            String nombre = tablepagador.getSelectionModel().getSelectedItem().getNombre();
            listapagadores.remove(tablepagador.getSelectionModel().getFocusedIndex());
            String cif = cifField.getText();
            DAO dao = new DAO();
            dao.deletepagador(nombre, cif);
            tablepagador.getSelectionModel().clearSelection();
        }
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
