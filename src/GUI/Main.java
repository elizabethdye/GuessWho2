package GUI;

import Network.NetworkManager;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.UnknownHostException;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 829, 622));
        primaryStage.show();
    }


    public static void main(String[] args) {

        try {
            NetworkManager.getInstance().test();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        launch(args);
    }
}
