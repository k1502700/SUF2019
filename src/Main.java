import Model.DisplayBond;
import View.BondTableView;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {


    BondTableView scene = new BondTableView(new Group());

    private TableView<DisplayBond> bondtable = new TableView<DisplayBond>();
    private final ObservableList<DisplayBond> data =
            FXCollections.observableArrayList(
                    new DisplayBond("bond1", "value1", "IRR1", "10years", "$11", "0.1", "0.11" ),
                    new DisplayBond("bond2", "value2", "IRR2", "20years", "$22", "0.2", "0.22" ),
                    new DisplayBond("bond3", "value3", "IRR3", "30years", "$33", "0.3", "0.33" ),
                    new DisplayBond("bond4", "value4", "IRR4", "40years", "$44", "0.4", "0.44" ),
                    new DisplayBond("bond5", "value5", "IRR5", "50years", "$55", "0.5", "0.55" ));

    final HBox hb = new HBox();



    public static void main   (String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {

        primaryStage.setTitle("MR.BOND");
        final Label title = new Label("List of Bonds");

        TableColumn bondName = new TableColumn("Bond Name");
        bondName.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("name"));

        TableColumn bondValue = new TableColumn("Value");
        bondValue.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("value"));

        TableColumn bondIRR = new TableColumn("IRR");
        bondIRR.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("irr"));

        TableColumn bondDuration = new TableColumn("Duration");
        bondDuration.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("duration"));

        TableColumn bondResale = new TableColumn("Resale Value");
        bondResale.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("resale"));


        TableColumn bondProjection = new TableColumn("Interest Projection");

        TableColumn firstYearProjection = new TableColumn("Primary");
        firstYearProjection.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("projection1"));

        TableColumn secondYearProjection = new TableColumn("Secondary");
        secondYearProjection.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("projection2"));

        bondProjection.getColumns().addAll(firstYearProjection, secondYearProjection);

        bondtable.setPrefHeight(500);
        bondtable.setPrefWidth(900);


        bondtable.setItems(data);
        bondtable.getColumns().addAll(bondName, bondValue, bondIRR, bondDuration, bondResale, bondProjection);

        final VBox vbox = new VBox();
        vbox.setSpacing(5);
        vbox.setPadding(new Insets(10, 0, 0, 10));
        vbox.getChildren().addAll(title, bondtable);

        ((Group) scene.getRoot()).getChildren().addAll(vbox);


        primaryStage.setWidth(1000);
        primaryStage.setHeight(600);


        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
