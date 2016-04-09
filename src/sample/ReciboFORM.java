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
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Maria on 22/08/2015.
 */
public class ReciboFORM implements Initializable {

    @FXML private Button savebtn;
    @FXML private Button excelbtn;

    @FXML private TextField txtfieldnrecibo;
    @FXML private TextField txtfieldlocexp;
    @FXML private TextField txtfieldimporte;
    @FXML private TextField txtfieldfechaexp;
    @FXML private TextField txtfieldvencimiento;
    @FXML private TextField txtfieldeuros;
    @FXML private TextField txtfieldconcepto;
    @FXML private TextField txtfieldpersona;
    @FXML private TextField txtfielddomicilio;
    @FXML private TextField txtfieldncuenta;
    @FXML private TextField txtfieldnpagador;
    @FXML private TextField txtfielddompag;
    @FXML private TextField txtfieldnombrerecibo;


    @FXML private TableView<PagadorTable> tablepagador;
    @FXML private TableColumn colnombre;
    @FXML private TableColumn colncuenta;
    @FXML private TableColumn coldomicilio;
    ObservableList<PagadorTable> listapagadores;
    boolean bool;
    String idcliente;
    Double total;
    Double euros;
    ObservableList<String> listaClientes;
    private Map<String,Cliente> client = new HashMap<>();
    @FXML private ComboBox<String> clienteList;

    @FXML private Button btncliente;
    @FXML private Button btnfactura;
    @FXML private Button btnpresupuesto;
    @FXML private Button btnrecibo;
    private Context context;

    public void setContext(Context context) {
        this.context = context;
        nuevavista();
    }

    public void nuevavista() {
        Integer nrecibo = context.currentRecibo().getNrecibo();
        DAOrecibo dao = new DAOrecibo();
        Reciboc re = dao.getrecibo(nrecibo);
        //Parte recibo
        txtfieldnrecibo.setText(String.valueOf(re.getNrecibo()));
        txtfieldlocexp.setText(re.getLocexp());
        txtfieldimporte.setText(String.valueOf(re.getImporte()));
        txtfieldvencimiento.setText(re.getVencimiento());
        txtfieldeuros.setText(String.valueOf(re.getEuros()));
        txtfieldconcepto.setText(re.getConcepto());
        txtfieldfechaexp.setText(re.getFechaexp());
        txtfieldnombrerecibo.setText(re.getNombrerecibo());
        //Parte cliente
        Cliente cli =  dao.clicif(re.getIdcliente());
        txtfieldpersona.setText(cli.getNombreComercial());
        txtfielddomicilio.setText(cli.getDireccion());
        //Parte pagador
        List<PagadorTable> l = dao.initabla(cli.getCif());
        for(PagadorTable i : l) listapagadores.add(i);

        Pagador pag = dao.getpagador(re.getPerpag(),re.getIdcliente());
        txtfieldncuenta.setText(String.valueOf(pag.getNcuenta()));
        txtfielddompag.setText(String.valueOf(pag.getDompag()));
        txtfieldnpagador.setText(String.valueOf(pag.getNpersona()));
        total= re.getImporte();
        euros = re.getEuros();
    }


    private String reciboErrors(String nrecibo){
        String error = "";
        DAOconceptosfactura dao = new DAOconceptosfactura();
        if(txtfieldpersona.getText().equals("")){
            error = "Cif cliente no puede estar vacio";
        }
        else if(!isNumber(nrecibo)){
            error = "N?mero nrecibo debe ser un n?mero, no se aceptan letras.";
        }
        else if (nrecibo.equals("")){
            error = "N?mero recibo no puede estar vac?o, se necesita un n?mero.";
        }
        return error;
    }

    private Reciboc getRecibo (String nrecibo){
        Reciboc recibo = new Reciboc();
        recibo.setNrecibo(Integer.valueOf(txtfieldnrecibo.getText()));
        recibo.setLocexp(txtfieldlocexp.getText());
        if(txtfieldimporte.getText().equals("")){
            recibo.setImporte(total);
        }
        else{
            recibo.setImporte(Double.valueOf(txtfieldimporte.getText()));
        }
        recibo.setFechaexp(txtfieldfechaexp.getText());
        recibo.setVencimiento(txtfieldvencimiento.getText());
        if(txtfieldimporte.getText().equals("")){
            recibo.setEuros(euros);
        }
        else{
            recibo.setEuros(Double.valueOf(txtfieldeuros.getText()));
        }
        recibo.setConcepto(txtfieldconcepto.getText());
        recibo.setNombrerecibo(txtfieldnombrerecibo.getText());
        recibo.setPerpag(txtfieldnpagador.getText());
        recibo.setIdcliente(idcliente);
        total = Double.valueOf(0);
        euros = Double.valueOf(0);
        return recibo;
    }

    @FXML private void savedatos (ActionEvent event){
        DAOrecibo dao = new DAOrecibo();
        String nrecibo = txtfieldnrecibo.getText();
        String error = reciboErrors(nrecibo);
        if(!error.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.showAndWait();
        }
        else if(dao.nrecibo_repe(Integer.valueOf(nrecibo)) == false){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Si acepta, se guardaran los cambios");
            alert.setContentText("Esta seguro que desea guardar los cambios?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Reciboc recibo = getRecibo(nrecibo);
                dao.deletere(Integer.valueOf(nrecibo));
                dao.savedatos(recibo);
            }
        }
        else {
            Reciboc recibo = getRecibo(nrecibo);
            dao.savedatos(recibo);
        }
    }

    @FXML private void generaexcel (ActionEvent event) {
        Integer nrecibo = Integer.parseInt(txtfieldnrecibo.getText());
        //
        String locexp = txtfieldlocexp.getText();
        Double importe = Double.valueOf(txtfieldimporte.getText());
        //
        String fechaexp = txtfieldfechaexp.getText();
        String vencimiento = txtfieldvencimiento.getText();
        Double euros = Double.valueOf(txtfieldeuros.getText());
        String concepto = txtfieldconcepto.getText();
        String perent = txtfieldpersona.getText();
        String domicilioperent = txtfielddomicilio.getText();
        Integer ncuenta = Integer.valueOf(txtfieldncuenta.getText());
        String personapag = txtfieldpersona.getText();
        String domiciliopag = txtfielddomicilio.getText();
        String nombre = txtfieldnombrerecibo.getText();

        recibo recibo = new recibo();
       recibo.main(null, nrecibo, locexp, importe, fechaexp, vencimiento, euros, concepto, perent, domicilioperent, ncuenta, personapag, domiciliopag,nombre);
    }

    private boolean isNumber(String s){
        for(char c: s.toCharArray()) {
            if (c > '9' || c < '0') {
                return false;
            }
        }
        return true;
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

    private void ponerClienteSeleccionado(){
        String cli = clienteList.getSelectionModel().getSelectedItem();
        int index = cli.indexOf(": ");
        cli = cli.substring(index + 2);
        Cliente cliente = client.get(cli);
        listaClientes = FXCollections.observableArrayList();
        idcliente = cliente.getCif();
        txtfieldpersona.setText(cliente.getNombreComercial());
        txtfielddomicilio.setText(cliente.getDireccion());
    }
    private void changeCliente(){
        listaClientes = FXCollections.observableArrayList();
        String nombre = clienteList.getEditor().getText();
        if(!clienteList.getEditor().getText().isEmpty()){
            ObservableList<String> listaAux = FXCollections.observableArrayList();
            for (Map.Entry<String, Cliente> clienteCompleto : client.entrySet()) {
                if (clienteCompleto.getValue().getNombreComercial().startsWith(nombre) || clienteCompleto.getValue().getCif().startsWith(nombre)) {
                    listaAux.add(clienteCompleto.getValue().getNombreComercial() + ": " + clienteCompleto.getKey());
                }
            }
            if (listaAux.size() == 0) listaAux.add("Cliente no encontrado");
            listaClientes.clear();
            listaClientes = listaAux;
            clienteList.setItems(listaClientes);
        }
    }

    public void initialize(URL location, ResourceBundle resources) {
        coldomicilio.setCellValueFactory(new PropertyValueFactory<PagadorTable, String>("domicilio"));
        colnombre.setCellValueFactory(new PropertyValueFactory<PagadorTable, String>("nombre"));
        colncuenta.setCellValueFactory(new PropertyValueFactory<PagadorTable, Integer>("ncuenta"));
        listapagadores = FXCollections.observableArrayList();
        tablepagador.setItems(listapagadores);
        fillClientes();
        total = Double.valueOf(0);
        euros = Double.valueOf(0);
        bool = true;

        clienteList.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (!oldValue.equalsIgnoreCase(newValue) && bool) {
                    listapagadores.clear();
                    changeCliente();
                }
                else if (!bool){
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
                    DAOrecibo dao = new DAOrecibo();
                    listapagadores.clear();
                    List<PagadorTable> l = dao.initabla(idcliente);
                    for (PagadorTable i : l) listapagadores.add(i);
                }
            }
        });

       tablepagador.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
           if (newSelection != null) {
               Integer ncuenta = tablepagador.getSelectionModel().getSelectedItem().getNcuenta();
               String n = tablepagador.getSelectionModel().getSelectedItem().getNombre();
               String dompag = tablepagador.getSelectionModel().getSelectedItem().getDompag();
               txtfieldncuenta.setText(String.valueOf(ncuenta));
               txtfieldnpagador.setText(n);
               txtfielddompag.setText(dompag);
           }
       });
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
