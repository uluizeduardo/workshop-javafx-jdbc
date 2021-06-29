package gui;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.entities.Department;
import model.services.DepartmentService;
import sample.Main;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DepartmentListController implements Initializable {

    private DepartmentService service;

    @FXML
    private TableView<Department> tableViewDepartment;

    @FXML
    private TableColumn<Department, Integer> tableColumId;

    @FXML
    private TableColumn<Department, String> tableColumName;

    @FXML
    private Button btNew;

    private ObservableList<Department> obsList;


    public void setDepartmentService(DepartmentService service){
        this.service = service;
    }

    public void onBtNewAction(){
        System.out.println("onBtNewAction");
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    //Método para iniciar tela
    private void initializeNodes() {
        //Setando o comportamento da tabela
        tableColumId.setCellValueFactory(new PropertyValueFactory<>("id"));
        tableColumName.setCellValueFactory(new PropertyValueFactory<>("Name"));

        //Tabela preenchar todo o tamanho da tela
        Stage stage = (Stage) (Main.getMainScene().getWindow());
        tableViewDepartment.prefHeightProperty().bind(stage.heightProperty());
    }

    public void updateTableView(){
        if(service == null){
            throw new IllegalStateException("The service was null");
        }
        List<Department> list = service.findAll();
        obsList = FXCollections.observableArrayList(list);
        tableViewDepartment.setItems(obsList);
    }
}
