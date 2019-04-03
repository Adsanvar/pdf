package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TabPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Home.fxml"));
        //store the root so that the controllers have access to it
        TabPane root = (TabPane) loader.load();
        //create & style scene
        Scene scene = new Scene(root, 1000, 800);
        primaryStage.setTitle("Service Management System");
        primaryStage.setScene(scene);
        primaryStage.show();
        Controller controller = loader.getController();
        controller.Init();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
