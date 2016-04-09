package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import javax.naming.NamingException;
import java.awt.*;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * Created by Maria on 06/09/2015.
 */
public class MostrarFactura {

    @FXML private Button btndelete;
    @FXML private Button btnmodificar;
    @FXML private Button btnnewfact;
    @FXML private Button btnseefact;

    @FXML private Button btncliente;
    @FXML private Button btnfactura;
    @FXML private Button btnpresupuesto;
    @FXML private Button btnrecibo;

    @FXML public AnchorPane Back;

    @FXML private TableView<MostrarTable> Tablemostrar;
    @FXML private TableColumn colnumero;
    @FXML private TableColumn colnombre;
    ObservableList<MostrarTable> lista;
    private Context currentContext = new Context();

    private Context context;

    public void setContext(Context context) {
        this.context = context;
        initializeMostrar();
    }

    public void initializeMostrar() {
        colnumero.setCellValueFactory(new PropertyValueFactory<MostrarTable, Integer>("numero"));
        colnombre.setCellValueFactory(new PropertyValueFactory<MostrarTable, String>("nombre"));
        lista = FXCollections.observableArrayList();
        Tablemostrar.setItems(lista);
        DAO dao = new DAO();
        String cif = context.currentCliente().getCif();
        List<MostrarTable> listaFact = dao.muestrafac(cif);
        for (MostrarTable i : listaFact) lista.add(i);
    }

    @FXML private void deletefac(ActionEvent event) {
        Integer nfac = Tablemostrar.getSelectionModel().getSelectedItem().getNumero();
        lista.remove(Tablemostrar.getSelectionModel().getFocusedIndex());
        DAOconceptosfactura dao = new DAOconceptosfactura();
        dao.deletefacconpre(nfac);
        dao.deletefac(nfac);
        Tablemostrar.getSelectionModel().clearSelection();
    }

    @FXML private void modifyfac(ActionEvent event) {
        //elegimos que factura
        Integer nfac = Tablemostrar.getSelectionModel().getSelectedItem().getNumero();
        //se abre la ventana de factura
        URL url = getClass().getResource("vistaFactura.fxml");
        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(url);
        fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
        Back.getChildren().clear();
        try {
            Back.getChildren().add((Node) fxmlloader.load(url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer nfact = Tablemostrar.getSelectionModel().getSelectedItem().getNumero();
        currentContext.currentFactura().setNfactura(nfact);
        ((ConceptosfactFORM) fxmlloader.getController()).setContext(currentContext);
        //eliminamos la factura de la lista y de la BD
        lista.remove(nfac);
        DAOconceptosfactura dao = new DAOconceptosfactura();
        dao.deletefacconpre(nfac);
        dao.deletefac(nfac);
    }

    @FXML private void newfact(ActionEvent event) {
        URL url = getClass().getResource("vistaFactura.fxml");
        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(url);
        fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
        Back.getChildren().clear();
        try {
            Back.getChildren().add((Node) fxmlloader.load(url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML private void seefact(ActionEvent event) {
        URL url = getClass().getResource("vistaFactura.fxml");
        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(url);
        fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
        Back.getChildren().clear();
        try {
            Back.getChildren().add((Node) fxmlloader.load(url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        Integer nfact = Tablemostrar.getSelectionModel().getSelectedItem().getNumero();
        currentContext.currentFactura().setNfactura(nfact);
        ((ConceptosfactFORM) fxmlloader.getController()).setContext(currentContext);
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

    @FXML private void changeRecibo(ActionEvent event) throws IOException {
        Parent view;
        view = (FXMLLoader.load(getClass().getResource("sample2.fxml")));
        Stage mainStage;
        mainStage = Main.stage;
        mainStage.getScene().setRoot(view);
    }

    @FXML private void changeCliente(ActionEvent event) throws IOException {
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

