package View;

import javafx.geometry.Insets;
import javafx.scene.control.Hyperlink;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;

/**
 * Created by robert on 2019. 03. 02..
 */
public class Sidebar extends VBox {

    public Sidebar() {

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);

        Text title = new Text("Options");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        vbox.getChildren().add(title);

        Label interestRate = new Label("Interest Rates");
        Label indexvalue = new Label("Index Value");
        vbox.getChildren().addAll(indexvalue, interestRate);


        this.getChildren().add(vbox);

    }
}
