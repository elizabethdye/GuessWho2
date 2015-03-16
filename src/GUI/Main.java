package GUI;

import Network.NetworkManager;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import javax.swing.*;
import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        setDockIcon();
        Parent root = FXMLLoader.load(getClass().getResource("MainUI.fxml"));
        primaryStage.setTitle("Guess Who v2.0");
        primaryStage.setScene(new Scene(root, 900, 750));
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("logo.jpg")));
        setCloseConditions(primaryStage);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }


    boolean isMac(){
        return System.getProperty("os.name").toLowerCase().contains("mac");
    }

    void setDockIcon(){
        if (isMac()){
            Log("Loading Dock Icon");
            URL image = Main.class.getResource("logo.jpg");
            java.awt.Image dockImage = new ImageIcon(image).getImage();
            //com.apple.eawt.Application.getApplication().setDockIconImage(dockImage);
        }
    }

    public static void Log(String s){
        System.out.println(s);
    }

    public void setCloseConditions(Stage s){
        s.setOnCloseRequest(new EventHandler<WindowEvent>() {

            public void handle(WindowEvent t) {
                NetworkManager.getInstance().stopServer();
                Platform.exit();
            }

        });

    }
}
