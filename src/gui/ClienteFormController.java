package gui;

import java.net.URL;
import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.ResourceBundle;

import gui.listeners.DataChangeListener;
import gui.util.Constraints;
import gui.util.Utils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import model.entities.Cliente;
import model.services.ClienteService;

public class ClienteFormController implements Initializable {

	private Cliente entity;

	private ClienteService service;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;

	@FXML
	private TextField txtName;

	@FXML
	private TextField txtRg;

	@FXML
	private TextField txtEndereco;

	@FXML
	private TextField txtBairro;

	@FXML
	private TextField txtCidade;

	@FXML
	private TextField txtEstado;

	@FXML
	private TextField txtCEP;

	@FXML
	private DatePicker dpNascimento;

	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;
	
	@FXML
	private Button btAtualizar;

	public void setCliente(Cliente entity) {
		this.entity = entity;
	}

	public void setClienteService(ClienteService service) {
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
	
	
	private Cliente getFormData() {

		Cliente obj = new Cliente();

		obj.setId(Utils.tryParseToInt(txtId.getText()));

		obj.setBairro(txtBairro.getText());

		obj.setCidade(txtCidade.getText());

		obj.setEndereco(txtEndereco.getText());

		obj.setEstado(txtEstado.getText());

		obj.setCEP(txtCEP.getText());

		obj.setRG(txtRg.getText());

		obj.setName(txtName.getText());
		

		Instant instant = Instant.from(dpNascimento.getValue().atStartOfDay(ZoneId.systemDefault()));
		obj.setDataNascimento(Date.from(instant));

		return obj;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		Constraints.setTextFieldMaxLength(txtName, 50);
		Constraints.setTextFieldMaxLength(txtRg, 10);
		Constraints.setTextFieldMaxLength(txtEndereco, 50);
		Constraints.setTextFieldMaxLength(txtCidade, 50);
		Constraints.setTextFieldMaxLength(txtBairro, 50);
		Constraints.setTextFieldMaxLength(txtEstado, 50);
		Constraints.setTextFieldMaxLength(txtCEP, 50);
		Utils.formatDatePicker(dpNascimento, "yyyy-MM-dd");

	}

	public void updateFormData() {

		if (entity == null) {
			throw new IllegalStateException("Entity was nos defined");
		}

		txtId.setText(String.valueOf(entity.getId()));
		txtName.setText(entity.getName());
		txtRg.setText(entity.getRG());
		txtEndereco.setText(entity.getEndereco());
		txtBairro.setText(entity.getBairro());
		txtCidade.setText(entity.getCidade());
		txtEstado.setText(entity.getEstado());
		txtCEP.setText(entity.getCEP());
		
		if (entity.getDataNascimento() != null) {
			dpNascimento.setValue(LocalDate.ofInstant(entity.getDataNascimento().toInstant(), ZoneId.systemDefault()));
		}

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
