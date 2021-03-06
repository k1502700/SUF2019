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
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by robert on 2019. 03. 02..
 */
public class BondTableView extends Scene {

    DataStore dataStore;

    private DetailsTable detailsTable = new DetailsTable();
    private RawTable rawTable = new RawTable();
    private BorderPane bp;
    Sidebar sd;

    public BondTableView(@NamedArg("root") Parent root) {
        super(root);

        dataStore = new DataStore(new Date(), 0.75);

        detailsTable.setContent(dataStore.getDisplayBonds());

        rawTable.setContent(dataStore.getRawBonds());

        final Label title = new Label("List of Bonds");


        bp = new BorderPane();
        bp.setCenter(detailsTable);
        bp.setTop(title);

        sd = new Sidebar(this);
        bp.setRight(sd);

        Menu menu = new Menu(this);
        bp.setLeft(menu);

        detailsTable.getTable().getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        Button button3 = new Button("Graph");

        button3.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {

                ObservableList selectedItems = detailsTable.getTable().getSelectionModel().getSelectedItems();
                ArrayList<String> bondids = new ArrayList<>();

                for (int i = 0; i < selectedItems.size(); i++) {
                    DisplayBond displayBond = (DisplayBond) selectedItems.get(i);
                   bondids.add(displayBond.getId());
                }

                Stage stage = new Stage();
                stage.setTitle("My New Stage Title");
                GraphScene graphScene = new GraphScene(new Group(),bondids, dataStore);
                stage.setScene(graphScene);
                stage.show();
            }
        });


        HBox botbuts = new HBox();

        botbuts.getChildren().addAll( button3);

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

    public void setTableContent(Date date, double interest){
        dataStore = new DataStore(date, interest);
        detailsTable.setContent(dataStore.getDisplayBonds());
    }

    public Date getDate(){
        return sd.getDate();
    }

    public String getIndex(){
       return sd.getIndex();
    }

    public String getInterest(){
        return sd.getInterest();
    }


}
