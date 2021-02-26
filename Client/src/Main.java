import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        Parent root = FXMLLoader.load(getClass().getResource("vue/MainView.fxml"));
        Scene scene = new Scene(root);

        primaryStage.setScene(scene);
        //Possible d'ajouter une feuille css si besoin
        primaryStage.setTitle("OnlineLudi");
        primaryStage.setMinHeight(750);
        primaryStage.setMinWidth(1100);
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
