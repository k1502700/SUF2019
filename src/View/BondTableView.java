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
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

/**
 * Created by robert on 2019. 03. 02..
 */
public class BondTableView extends Scene {

    private TableView<DisplayBond> bondtable = new TableView<DisplayBond>();
    private final ObservableList<DisplayBond> data =
            FXCollections.observableArrayList(
                    new DisplayBond("bond1", "value1", "IRR1", "10years", "$11", "0.1", "0.11" ),
                    new DisplayBond("bond2", "value2", "IRR2", "20years", "$22", "0.2", "0.22" ),
                    new DisplayBond("bond3", "value3", "IRR3", "30years", "$33", "0.3", "0.33" ),
                    new DisplayBond("bond4", "value4", "IRR4", "40years", "$44", "0.4", "0.44" ),
                    new DisplayBond("bond5", "value5", "IRR5", "50years", "$55", "0.5", "0.55" ));

    final HBox hb = new HBox();

    public BondTableView(@NamedArg("root") Parent root, DataStore dataStore) {
        super(root);

        data.removeAll(data);
        data.addAll(dataStore.getDisplayBonds());

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

         BorderPane bp = new BorderPane();
         bp.setCenter(bondtable);
        bp.setTop(title);

        Sidebar sd = new Sidebar();
        bp.setRight(sd);

        bondtable.getSelectionModel().setSelectionMode(
                SelectionMode.MULTIPLE
        );

        Button button2 = new Button("GetSelection");

        button2.setOnAction(new EventHandler<ActionEvent>() {
            @Override public void handle(ActionEvent e) {

                ObservableList selectedItems = bondtable.getSelectionModel().getSelectedItems();

                for (int i = 0; i < selectedItems.size(); i++) {
                   DisplayBond displayBond = (DisplayBond) selectedItems.get(i);
                    System.out.println(displayBond.getName());
                }

            }
        });

        bp.setBottom(button2);


        ((Group) this.getRoot()).getChildren().addAll(bp);



    }


}
