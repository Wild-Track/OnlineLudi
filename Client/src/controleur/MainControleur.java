package controleur;

import javafx.event.ActionEvent;
import javafx.scene.input.MouseEvent;

import java.awt.*;
import java.io.IOException;
import java.net.URI;

public class MainControleur
{

    public void hyperlink_Github_OnClick(ActionEvent actionEvent)
    {
        try {
            Desktop.getDesktop().browse(URI.create("https://github.com/Wild-Track/OnlineLudi"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void goToPendu(MouseEvent mouseEvent)
    {
    }

    public void goToAllumettes(MouseEvent mouseEvent)
    {
    }

    public void goToOthello(MouseEvent mouseEvent)
    {
    }
}
