package APIGRAFICA;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainGrafica extends Application {
    public static void main(String[] args) {
        launch(args);
    }
    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("Grafica.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setTitle("Projecto de LP e AED");
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
