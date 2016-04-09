package sample;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Rectangle2D;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class Main extends Application {
    @FXML private Button clientebtn;

    public static Stage stage;

    @Override
    public void start(Stage primaryStage) throws Exception{

        stage = primaryStage;

        Parent root = FXMLLoader.load(getClass().getResource("vistaBusquedaCliente.fxml"));
        primaryStage.setTitle("Clientes");
        primaryStage.setScene(new Scene(root, 1000, 540));
        primaryStage.show();

        Rectangle2D primaryScreenBounds = Screen.getPrimary().getVisualBounds();

        //set Stage boundaries to visible bounds of the main screen
        stage.setX(primaryScreenBounds.getMinX());
        stage.setY(primaryScreenBounds.getMinY());
        stage.setWidth(primaryScreenBounds.getWidth());
        stage.setHeight(primaryScreenBounds.getHeight());

        /*Screen screen = Screen.getPrimary();
        Rectangle2D bounds = screen.getVisualBounds();

        primaryStage.setX(bounds.getMinX());
        primaryStage.setY(bounds.getMinY());
        primaryStage.setWidth(bounds.getWidth());
        primaryStage.setHeight(bounds.getHeight());
*/
   /*    Parent root = FXMLLoader.load(getClass().getResource("vistaFactura.fxml"));
        primaryStage.setTitle("vistaFactura");
        primaryStage.setScene(new Scene(root, 1000, 540));
        primaryStage.show();*/

       /* Parent root = FXMLLoader.load(getClass().getResource("sample2.fxml"));
        primaryStage.setTitle("Recibo");
        primaryStage.setScene(new Scene(root, 1000, 540));
        primaryStage.show();
*/

     /*  Parent root = FXMLLoader.load(getClass().getResource("vistaPresupuesto.fxml"));
        primaryStage.setTitle("Presupuesto");
        primaryStage.setScene(new Scene(root, 1000, 540));
        primaryStage.show();*/

    }


    public static void main(String[] args) {
        launch(args);
    }
}
