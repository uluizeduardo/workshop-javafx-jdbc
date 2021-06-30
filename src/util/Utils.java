package util;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.stage.Stage;

public class Utils {

    public static Stage currentStage(ActionEvent event){
        return (Stage) ((Node) event.getSource()).getScene().getWindow();
    }

    //Método para converter para inteiro
    public static Integer tryPerseToInt(String str){
        try {
            return Integer.parseInt(str);
        }
        catch (NumberFormatException ex){
            return  null;
        }

    }
}
