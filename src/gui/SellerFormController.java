package gui;

import db.DbException;
import gui.listeners.DataChangeListener;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import model.entities.Seller;
import model.exceptions.ValidationException;
import model.services.SellerService;
import util.Alerts;
import util.Constraints;
import util.Utils;

import java.net.URL;
import java.util.*;

public class SellerFormController implements Initializable {

    private Seller entity;

    private SellerService service;

    private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

    @FXML
    private TextField textId;

    @FXML
    private TextField textName;

    @FXML
    private Label labelErrorName;

    @FXML
    private Button btSave;

    @FXML
    private Button btCancel;

    public void setSeller(Seller entity){
        this.entity = entity;
    }

    public void setSellerService(SellerService service){
        this.service = service;
    }

    public void subscribeDataChangeListener(DataChangeListener listener){
        dataChangeListeners.add(listener);
    }
    @FXML
    private void onBtSaveAction(ActionEvent event){
        if (entity == null){
            throw new IllegalStateException("Entity was null");
        }if (service == null){
            throw new IllegalStateException("Service was null");
        }
        try {
            entity = getFormData();
            service.seveOrUpdate(entity);
            notifyDataChengeListeners();
            Utils.currentStage(event).close();
        }
        catch (ValidationException ex){
            setErrorMessage(ex.getErros());
        }
        catch (DbException ex){
            Alerts.showAlert("Error saving object", null, ex.getMessage(), Alert.AlertType.ERROR);
        }
    }

    private void notifyDataChengeListeners() {
        for (DataChangeListener listener : dataChangeListeners){
            listener.onDataChanged();
        }
    }

    private Seller getFormData() {
        Seller obj = new Seller();

        ValidationException exception = new ValidationException("Validation Error");

        obj.setId(Utils.tryPerseToInt(textId.getText()));

        if (textName.getText() == null || textName.getText().trim().equals("")){
            exception.addError("name", "Field can't be empty");
        }
        obj.setName(textName.getText());

        if (exception.getErros().size() > 0){
            throw exception;
        }
        return obj;
    }

    @FXML
    private void onBtCancelAction(ActionEvent event){
        Utils.currentStage(event).close();
    }
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        initializeNodes();
    }

    private void initializeNodes(){
        Constraints.setTextFieldInteger(textId);
        Constraints.setTextFieldMaxLength(textName, 30);
    }

    public void updateFormData(){
        if (entity == null){
            throw new IllegalStateException("Entity was null");
        }
        textId.setText(String.valueOf(entity.getId()));
        textName.setText(entity.getName());
    }

    private void setErrorMessage(Map<String, String> errors){
        Set<String> fields = errors.keySet();

        if( fields.contains("name")){
            labelErrorName.setText(errors.get("name"));
        }
    }


}
