package sample;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.util.List;

/**
 * Created by Maria on 29/09/2015.
 */
public class MostrarPresupuesto {

    @FXML private Button btnaddpre;
    @FXML private Button btnseepre;
    @FXML private Button btnmodpre;
    @FXML private Button btndeletepre;

    @FXML public AnchorPane Back;

    @FXML private TableView<Mostrartablepre> Tablemostrar;
    @FXML private TableColumn colnumero;
    @FXML private TableColumn colnombre;
    ObservableList<Mostrartablepre> lista;

    @FXML private Button btncliente;
    @FXML private Button btnfactura;
    @FXML private Button btnpresupuesto;
    @FXML private Button btnrecibo;

    private Context currentContext =  new Context();

    private Context context;

    public void setContext(Context context) {
        this.context = context;
        initializeMostrar();
    }

    public void initializeMostrar() {
        colnombre.setCellValueFactory(new PropertyValueFactory<MostrarTable, String>("nombre"));
        lista = FXCollections.observableArrayList();
        Tablemostrar.setItems(lista);
        DAO dao = new DAO();
        String cif  = context.currentCliente().getCif();
        List<Mostrartablepre> listaPre = dao.muestrapre(cif);
        for(Mostrartablepre i : listaPre) lista.add(i);
    }

    @FXML private void deletepre(ActionEvent event) {
        String npre = Tablemostrar.getSelectionModel().getSelectedItem().getNombre();
        DAOpresupuesto dao = new DAOpresupuesto();
        dao.deletepretable(npre);
        //dao.deletepre(idpre);
        lista.remove(Tablemostrar.getSelectionModel().getFocusedIndex());
        Tablemostrar.getSelectionModel().clearSelection();
    }

    @FXML private void newpre(ActionEvent event) {
        URL url = getClass().getResource("vistaPresupuesto.fxml");
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

    @FXML private void seepre(ActionEvent event) {
        URL url = getClass().getResource("vistaPresupuesto.fxml");
        FXMLLoader fxmlloader = new FXMLLoader();
        fxmlloader.setLocation(url);
        fxmlloader.setBuilderFactory(new JavaFXBuilderFactory());
        Back.getChildren().clear();
        try {
            Back.getChildren().add((Node) fxmlloader.load(url.openStream()));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String npre = Tablemostrar.getSelectionModel().getSelectedItem().getNombre();
        currentContext.currentPresupuesto().setNombrepre(npre);
        ((PresupuestoFORM) fxmlloader.getController()).setContext(currentContext);
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
