package View;

import Model.DataStore;
import Model.DisplayBond;
import javafx.beans.NamedArg;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Created by robert on 2019. 03. 02..
 */
public class BondTableView extends Scene {


    private DetailsTable detailsTable = new DetailsTable();
    private RawTable rawTable = new RawTable();
    private BorderPane bp;
    DataStore dataStore;
    Sidebar sd;

    public BondTableView(@NamedArg("root") Parent root, DataStore dataStore) {
        super(root);

        this.dataStore = dataStore;

        detailsTable.setContent(dataStore.getDisplayBonds());

        rawTable.setContent(dataStore.getRawBonds());

        final Label title = new Label("List of Bonds");


        bp = new BorderPane();
        bp.setCenter(detailsTable);
        bp.setTop(title);

        sd = new Sidebar();
        bp.setRight(sd);

        Menu menu = new Menu(this);
        bp.setLeft(menu);

        detailsTable.getTable().getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        Button button2 = new Button("GetSelection");

        Button button3 = new Button("Graph");

        button3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                Stage stage = new Stage();
                stage.setTitle("My New Stage Title");
                GraphScene graphScene = new GraphScene(new Group());
                stage.setScene(graphScene);
                stage.show();
            }
        });

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {

                ObservableList selectedItems = detailsTable.getTable().getSelectionModel().getSelectedItems();

                for (int i = 0; i < selectedItems.size(); i++) {
                    DisplayBond displayBond = (DisplayBond) selectedItems.get(i);
                    System.out.println(displayBond.getName());
                }

            }
        });

        HBox botbuts = new HBox();

        botbuts.getChildren().addAll(button2, button3);

        bp.setBottom(botbuts);


        ((Group) this.getRoot()).getChildren().addAll(bp);


    }

    public void setDB(){
        bp.setCenter(detailsTable);
        detailsTable.setContent(dataStore.getDisplayBonds());
    }

    public void setRaw(){
        bp.setCenter(rawTable);
        rawTable.setContent(dataStore.getRawBonds());
    }

    public String getDate(){
        return sd.getDate();
    }

    public String getIndex(){
       return sd.getIndex();
    }

    public String getInterest(){
        return sd.getInterest();
    }


}
