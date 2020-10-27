package gui;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import model.entities.Chale;
import model.services.ChaleService;



public class ChaleFormController implements Initializable {

	private Chale entity;

	private ChaleService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtLoc;

	@FXML
	private TextField txtCapacidade;

	@FXML
	private TextField txtValorAlta;

	@FXML
	private TextField txtValorBaixa;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;
	
	@FXML
	private Button btAtualizar;

	public void setChale(Chale entity) {
		this.entity = entity;
	}

	public void setChaleService(ChaleService service) {
		this.service = service;
	}

	@FXML
	public void onBtSalvarAction(ActionEvent event){
		entity = getFormData();
		service.save(entity);
		notifyDataChangeListeners();
		Utils.currentStage(event).close();
	}
	@FXML
	public void onBtAtualizarAction(ActionEvent event){
		entity = getFormData();
		service.update(entity);
		notifyDataChangeListeners();
		Utils.currentStage(event).close();
	}

	@FXML
	public void onBtCancelarAction(ActionEvent event) {
		Utils.currentStage(event).close();
	}
	
	
	private Chale getFormData() {

		Chale obj = new Chale();
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		obj.setLoc(txtLoc.getText());
		obj.setCapacidade(Utils.tryParseToInt(txtCapacidade.getText()));
		obj.setValorAlta(Utils.tryParseToDouble(txtValorAlta.getText()));
	
		obj.setValorBaixa(Utils.tryParseToDouble(txtValorBaixa.getText()));

		return obj;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtLoc, 50);
		Constraints.setTextFieldInteger(txtCapacidade);
		Constraints.setTextFieldDouble(txtValorAlta);
		Constraints.setTextFieldDouble(txtValorBaixa);
	}

	public void updateFormData() {

		if (entity == null) {
			throw new IllegalStateException("Entity was nos defined");
		}

		txtId.setText(String.valueOf(entity.getId()));
		txtLoc.setText(entity.getLoc());
		txtCapacidade.setText(String.valueOf(entity.getCapacidade()));
		txtValorAlta.setText(String.format("%.2f", entity.getValorAlta()));
		txtValorBaixa.setText(String.format("%.2f", entity.getValorBaixa()));

	}
	
	private void notifyDataChangeListeners() {
		
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}

}
