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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by Maria on 28/08/2015.
 */
public class PresupuestoFORM implements Initializable {
    @FXML private TableView<ConceptoTable> tableconcepto;

    @FXML private TableColumn colconcepto;
    @FXML private TableColumn colprecio;

    ObservableList<ConceptoTable> listaconceptos;

    @FXML private Button btnexcel;
    @FXML private Button btnsave;
    @FXML private Button btnadd;
    @FXML private Button btnbuscar;
    @FXML private Button btnfilablanco;
    @FXML private Button btnmodifyrow;
    @FXML private Button btndeleterow;

    @FXML private TextField txtfieldconcepto;
    @FXML private TextField txtfieldprecio;
    @FXML private TextField txtfieldbuscacli;
    @FXML private TextField txtfieldncliente;
    @FXML private TextField txtfieldcifcli;
    @FXML private TextField txtfielddireccion;
    @FXML private TextField txtfieldpoblacion;
    @FXML private TextField txtfieldpreciototal;
    @FXML private TextField txtfieldprecioiva;
    @FXML private TextField txtfieldnombrepre;

    ObservableList<String> listaClientes;
    private Map<String,Cliente> client = new HashMap<>();
    @FXML private ComboBox<String> clienteList;

    @FXML private Button btncliente;
    @FXML private Button btnfactura;
    @FXML private Button btnpresupuesto;
    @FXML private Button btnrecibo;

    private double total;
    private double iva;
    private Context context;
    Boolean bool;

    public void setContext(Context context) {
        this.context = context;
        nuevavista();
    }

    public void nuevavista() {
        String npre = context.currentPresupuesto().getNombrepre();
        DAOpresupuesto dao = new DAOpresupuesto();
        Presupuestoc pre = dao.getpre(npre);
        txtfieldpreciototal.setText(String.valueOf(pre.getPreciototal()));
        txtfieldprecioiva.setText(String.valueOf(pre.getPrecioiva()));
        txtfieldnombrepre.setText(pre.getNombrepre());
        System.out.println("MID NUEVA VISTA");
        Cliente cli =  dao.clicif(pre.getIdcliente());
        txtfieldncliente.setText(cli.getNombreComercial());
        txtfieldcifcli.setText(cli.getCif());
        txtfielddireccion.setText(cli.getDireccion());
        txtfieldpoblacion.setText(cli.getPoblacion());

        List<ConceptoTable> l = dao.getpretable(npre);
        System.out.println("Adding");
        for(ConceptoTable i : l) {listaconceptos.add(i) ;System.out.println("añadiendo datos tabla");}
        System.out.println("FIN NUEVA VISTA");

    }
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        colconcepto.setCellValueFactory(new PropertyValueFactory<ConceptoTable, String>("consulta"));
        colprecio.setCellValueFactory(new PropertyValueFactory<ConceptoTable, Integer>("precio"));
        listaconceptos = FXCollections.observableArrayList();
        tableconcepto.setItems(listaconceptos);
        fillClientes();
        bool = true;
        total = 0;
        iva = 0;

     /*   tableconcepto.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
            if (newSelection != null) {
                ponerClienteSeleccionado();
            }
        });*/
        clienteList.getEditor().textProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable,
                                String oldValue, String newValue) {
                if (!oldValue.equalsIgnoreCase(newValue) && bool) {
                    cambiocliente();
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
                }
            }
        });
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

    private void cambiocliente(){
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


    private void ponerClienteSeleccionado(){
        String cli = clienteList.getSelectionModel().getSelectedItem();
        int index = cli.indexOf(": ");
        cli = cli.substring(index + 2);
        Cliente cliente = client.get(cli);
        txtfieldncliente.setText(cliente.getNombreComercial());
        txtfieldcifcli.setText(cliente.getCif());
        txtfielddireccion.setText(cliente.getDireccion());
        txtfieldpoblacion.setText(cliente.getPoblacion());
    }

    @FXML private void addconpre (ActionEvent event){
        String precio = txtfieldprecio.getText();
        String concepto = txtfieldconcepto.getText();
        if (precio.equals("") && concepto.equals("")) {
            concepto = " ";
            Double precioaux = Double.valueOf(0);
            ConceptoTable fila = new ConceptoTable(concepto,precioaux);
            listaconceptos.add(fila);
        } else if(!isNumber(precio)){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Precio debe ser un numero, no se aceptan letras.");
            alert.showAndWait();
        }
        else if(precio.equals("") && !concepto.equals("")){
            Double precioaux = Double.valueOf(0);
            ConceptoTable fila = new ConceptoTable(concepto,precioaux);
            listaconceptos.add(fila);
        }
        else {
            if (precio.equals("")) Double.valueOf(0);
            ConceptoTable fila = new ConceptoTable(concepto,Double.valueOf(txtfieldprecio.getText()));
            listaconceptos.add(fila);
            total += Double.valueOf(txtfieldprecio.getText());
            iva += Double.valueOf(txtfieldprecio.getText())*1.21;
            iva = (double)Math.round(iva * 100d) / 100d;
            txtfieldpreciototal.setText(String.valueOf(total));
            txtfieldprecioiva.setText(String.valueOf(iva));

        }
        txtfieldconcepto.setText("");
        txtfieldprecio.setText("");
    }

    private boolean isNumber(String s){
        for(char c: s.toCharArray()) {
            if (c > '9' || c < '0') {
                return false;
            }
        }
        return true;
    }


    private String preErrors(String npre){
        String error = "";
        DAOpresupuesto dao = new DAOpresupuesto();
        if(txtfieldcifcli.getText().equals("")){
            error = "Cif cliente no puede estar vacio";
        }
        else if(npre.equals("")){
            error = "Se necesita un nombre de factura";
        }
        return error;
    }

    private Presupuestoc getPre (String npre){
        Presupuestoc pre = new Presupuestoc();
            pre.setNombrepre(txtfieldnombrepre.getText());
            pre.setIdcliente(txtfieldcifcli.getText());
            pre.setPreciototal(total);
            pre.setPrecioiva(iva);
            total = 0;
            iva = 0;
        return pre;
    }

    @FXML private void savepre (ActionEvent event){
        DAOpresupuesto dao = new DAOpresupuesto();
        String npre= txtfieldnombrepre.getText();
        String error = preErrors(npre);
        if(!error.equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText(error);
            alert.showAndWait();
        }
        else if(dao.nombrepre_repe(txtfieldnombrepre.getText()) == false){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmación");
            alert.setHeaderText("Si acepta, se guardaran los cambios");
            alert.setContentText("Esta seguro que desea guardar los cambios?");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.get() == ButtonType.OK){
                Presupuestoc presupuesto = getPre(npre);
                dao.deletepre(npre);
                dao.deletepretable(npre);
                dao.savepre(presupuesto, listaconceptos);
            }
        }
        else {
            Presupuestoc pre = getPre(npre);
            dao.savepre(pre, listaconceptos);
        }
    }

    @FXML private void addfilablanco (ActionEvent event){
        String concepto = " ";
        Double precio = Double.valueOf(0);
        ConceptoTable fila;
        fila = new ConceptoTable(concepto, precio);
        listaconceptos.add(fila);
    }

    @FXML private void generaexcel (ActionEvent event){ //guardar excel con nombrepre
        String ncliente;
        if(txtfieldncliente.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debe escribir el nombre del cliente o buscarlo con el boton.");
            alert.showAndWait();
        }
        else {
            ncliente = txtfieldncliente.getText();
            String cifcliente = txtfieldcifcli.getText();
            String direcliente = txtfielddireccion.getText();
            String poblacioncli = txtfieldpoblacion.getText();
            String nombrepre = txtfieldnombrepre.getText();
            calculo_preciototal();
            presupuesto presupuesto = new presupuesto();
            presupuesto.main(null, nombrepre, listaconceptos, total, iva, ncliente, cifcliente, direcliente, poblacioncli);
        }
    }

    @FXML private void getCli (ActionEvent event){
        DAOpresupuesto dao = new DAOpresupuesto();
        String ncliente;
        if(txtfieldbuscacli.getText().equals("")){
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Debe escribir el nombre del cliente");
            alert.showAndWait();
            ncliente = txtfieldbuscacli.getText();
        }
        else {
            ncliente = txtfieldbuscacli.getText();
            Cliente cli = dao.getCli(ncliente);
            txtfieldcifcli.setText(cli.getCif());
            txtfielddireccion.setText(cli.getDireccion());
            txtfieldncliente.setText(cli.getNombreComercial());
            txtfieldpoblacion.setText(cli.getPoblacion());
            txtfieldbuscacli.setText("");
        }

    }

    private ConceptoTable getconceptosfactura(){
        ConceptoTable pret = new ConceptoTable();
        pret.setConsulta(txtfieldconcepto.getText());
        try {
            pret.setPrecio(Double.valueOf(txtfieldprecio.getText()));
        }
        catch (NumberFormatException e) {
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Error");
            alert.setHeaderText(null);
            alert.setContentText("Precio debe ser un número, no se aceptan letras.");
            alert.showAndWait();
            pret.setPrecio(Double.valueOf(txtfieldprecio.getText()));
        }
        return pret;
    }

    private void calculo_preciototal(){
        total = 0;
        iva = 0;
        for (ConceptoTable t : listaconceptos) {
            total =  total + t.getPrecio();
        }
        iva = total*0.21 + total;
    }

    @FXML private void deleterow (ActionEvent event){
        String concepto = tableconcepto.getSelectionModel().getSelectedItem().getConsulta();
        listaconceptos.remove(tableconcepto.getSelectionModel().getFocusedIndex());
        DAOpresupuesto dao = new DAOpresupuesto();
        String npre = context.currentPresupuesto().getNombrepre();
        dao.deleterow(concepto, npre);
        tableconcepto.getSelectionModel().clearSelection();
    }

    @FXML private void modifyrow (ActionEvent event){
        String concepto = txtfieldconcepto.getText() ;
        Double precio  = Double.valueOf(txtfieldprecio.getText());
        DAOpresupuesto dao = new DAOpresupuesto();
        String npre = context.currentPresupuesto().getNombrepre();
        dao.deleterow(concepto, npre);
        Integer index = tableconcepto.getSelectionModel().getSelectedIndex();
        listaconceptos.remove(tableconcepto.getSelectionModel().getFocusedIndex());
        ConceptoTable fila = new ConceptoTable(concepto,precio);
        listaconceptos.add(index,fila);
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
