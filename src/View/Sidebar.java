package View;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Created by robert on 2019. 03. 02..
 */
public class Sidebar extends VBox {

    TextField interestbox;

    TextField indexbox;

    DatePicker datePicker;

    BondTableView bondTableView;

    public Sidebar(BondTableView bondTableView) {

        this.bondTableView = bondTableView;

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Text title = new Text("Options");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Label interestRate = new Label("Interest Rates:");
         interestbox = new TextField ();

//        Label indexvalue = new Label("Index Value:");
//         indexbox = new TextField ();

        Label date = new Label("Date:");
         datePicker = new DatePicker();

        Button recalc = new Button("Apply");

        recalc.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {


                System.out.println(getDate() + " " + getInterest());

                bondTableView.setTableContent( getDate() ,Double.parseDouble(getInterest()));

            }
        });




        vbox.getChildren().addAll(interestRate, interestbox, date, datePicker, recalc);


        this.getChildren().add(vbox);

    }

    public String getInterest(){
        try{
            double num = Double.parseDouble(interestbox.getText().toString());
            return String.valueOf(num);
        } catch (NumberFormatException e) {
            return "0.75";
        }
    }


    public String getIndex(){
        try{
            double num = Integer.parseInt(indexbox.getText().toString());
            return String.valueOf(num);
        } catch (NumberFormatException e) {
            return "currentindexrate";
        }

    }

    public Date getDate(){
        if (datePicker.getValue()== null){
            DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new Date();
            return date;
        }

        Date date = Date.from(datePicker.getValue().atStartOfDay(ZoneId.systemDefault()).toInstant());

        return date;

    }

}
