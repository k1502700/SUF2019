package View;

import Model.DisplayBond;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.time.format.DateTimeFormatter;

/**
 * Created by robert on 2019. 03. 02..
 */
public class Menu extends VBox {

    TextField interestbox;

    TextField indexbox;

    DatePicker datePicker;

    BondTableView scene;

    public Menu(BondTableView scene) {

        this.scene = scene;

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Button database = new Button("Database");
        Button details = new Button("Details");

        database.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                scene.setRaw();
            }
        });

        details.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                scene.setDB();
            }
        });

        vbox.getChildren().addAll(database,details);

        this.getChildren().add(vbox);

    }


}
