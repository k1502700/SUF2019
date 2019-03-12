package View;

import Model.DisplayBond;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import java.util.ArrayList;

public class RawTable extends VBox {

    private TableView<DisplayBond> bondtable = new TableView<DisplayBond>();
    private final ObservableList<DisplayBond> data =
            FXCollections.observableArrayList(
                    new DisplayBond("0", "name" ,"date1", "date1", "price", "interest", "coupon" ));

    public RawTable(){

        TableColumn idname = new TableColumn("Id");
        idname.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("id"));

        TableColumn bondName = new TableColumn("Bond Name");
        bondName.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("name"));

        TableColumn bondValue = new TableColumn("Redemption Date");
        bondValue.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("redemptiondate"));

        TableColumn bondIRR = new TableColumn("End Date");
        bondIRR.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("enddate"));

        TableColumn bondDuration = new TableColumn("Clean Price");
        bondDuration.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("cleanprice"));

        TableColumn bondResale = new TableColumn("Interest");
        bondResale.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("interest"));

        TableColumn coupon = new TableColumn("Coupon");
        coupon.setCellValueFactory(
                new PropertyValueFactory<DisplayBond, String>("coupon"));



        bondtable.setPrefHeight(500);
        bondtable.setPrefWidth(900);


        bondtable.setItems(data);
        bondtable.getColumns().addAll(idname,bondName, bondValue, bondIRR, bondDuration, bondResale, coupon);

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
