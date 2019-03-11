package View;

import Model.DisplayBond;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class DetailsTable extends VBox {

    private TableView<DisplayBond> bondtable = new TableView<DisplayBond>();
    private final ObservableList<DisplayBond> data =
            FXCollections.observableArrayList(
                    new DisplayBond("bond1", "value1", "IRR1", "10years", "$11", "0.1", "0.11" ));

    public DetailsTable(){


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

        this.getChildren().add(bondtable);

    }

    public void setContent(ArrayList<DisplayBond> input){
        data.clear();
        data.addAll(input);
    }

    public  TableView getTable(){
        return bondtable;
    }

}
