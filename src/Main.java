import Model.DataStore;
import View.BondTableView;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Main extends Application {

    DataStore dataStore = new DataStore();

    BondTableView scene = new BondTableView(new Group(), dataStore);

    public static void main   (String[] args) {

        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("MR.BOND");
        final Label title = new Label("List of Bonds");

        primaryStage.setWidth(1200);
        primaryStage.setHeight(600);

        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
