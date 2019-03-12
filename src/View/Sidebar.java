package View;

import javafx.geometry.Insets;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Created by robert on 2019. 03. 02..
 */
public class Sidebar extends VBox {

    TextField interestbox;

    TextField indexbox;

    DatePicker datePicker;

    public Sidebar() {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Text title = new Text("Options");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Label interestRate = new Label("Interest Rates:");
         interestbox = new TextField ();

        Label indexvalue = new Label("Index Value:");
         indexbox = new TextField ();

        Label date = new Label("Date:");
         datePicker = new DatePicker();


        vbox.getChildren().addAll(interestRate, interestbox, indexvalue, indexbox, date, datePicker);


        this.getChildren().add(vbox);

    }

    public String getInterest(){
        try{
            double num = Integer.parseInt(interestbox.getText().toString());
            return String.valueOf(num);
        } catch (NumberFormatException e) {
            return "currentinterestrate";
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

    public String getDate(){
        return datePicker.getValue().format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }

}
