package gui;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.DepartmentService;
import model.services.SellerService;
import sample.Main;
import util.Alerts;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.function.Consumer;

public class MainViewController implements Initializable {

    @FXML
    private MenuItem menuItemSeller;
    @FXML
    private MenuItem menuItemDepartment;
    @FXML
    private MenuItem menuItemAbout;

    @FXML
    public void onMenuItemSellerAction(){
        loadView("/gui/SellerList.fxml", (SellerListController controler) -> {
            controler.setSellerService(new SellerService());
            controler.updateTableView();
        });
    }

    @FXML
    public void onMenuItemDepartmentAction(){
        loadView("/gui/DepartmentList.fxml", (DepartmentListController controler) -> {
            controler.setDepartmentService(new DepartmentService());
            controler.updateTableView();
        });
    }

    @FXML
    public void onMenuItemAboutAction(){
        loadView("/gui/About.fxml", x -> {});
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }

    public synchronized <T> void loadView(String absolutName, Consumer<T> initializingAction){
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(absolutName));
            VBox newVbox = loader.load();

            //Mostrar view dentro da janela principal
            Scene mainSecene = Main.getMainScene();
            VBox mainVbox = (VBox)((ScrollPane)mainSecene.getRoot()).getContent();//mainVbox recebe o primeiro elemento da view principal
            Node mainMenu = mainVbox.getChildren().get(0);//mainMenu recebe o primeiro filho vBox da janela principal
            mainVbox.getChildren().clear();//Limpar os filhos do mainVbox
            mainVbox.getChildren().add(mainMenu);//adiciona mainMeno ao mainVbox
            mainVbox.getChildren().addAll(newVbox.getChildren());//adiciona os filhos do mainMenu do newVbox

            T controler = loader.getController();
            initializingAction.accept(controler);

        }
        catch (IOException ex){
            Alerts.showAlert("IO Exception", "Error Loading View", ex.getMessage(), Alert.AlertType.ERROR);
        }
    }
}
