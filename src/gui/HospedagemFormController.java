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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.util.Callback;
import model.entities.Chale;
import model.entities.Cliente;
import model.entities.Hospedagem;
import model.services.ChaleService;
import model.services.ClienteService;
import model.services.HospedagemService;



public class HospedagemFormController implements Initializable {

	private Hospedagem entity;

	private HospedagemService service;
	
	private ClienteService clienteService;
	
	private ChaleService chaleService;
	
	private List<DataChangeListener> dataChangeListeners = new ArrayList<>();

	@FXML
	private TextField txtId;
	
	@FXML
	private ComboBox<Chale> comboBoxChale;
	
	@FXML
	private ComboBox<Cliente> comboBoxCliente;
	
	@FXML
	private TextField txtEstado;
	
	@FXML
	private DatePicker dpDataIncio;
	
	@FXML
	private DatePicker dpDataFim;
	
	@FXML
	private TextField txtQtdPessoa;
	
	@FXML
	private TextField txtDesconto;
	
	@FXML
	private TextField txtValorFinal;
	
	@FXML
	private Button btSalvar;

	@FXML
	private Button btCancelar;
	
	@FXML
	private Button btAtualizar;
	
	private ObservableList<Cliente> obsListCli;
	
	private ObservableList<Chale> obsListCha;
	
	

	public void setHospedagem(Hospedagem entity) {
		this.entity = entity;
	}

	public void setServices(HospedagemService service, ClienteService clienteService, ChaleService chaleService) {
		this.service = service;
		this.chaleService = chaleService;
		this.clienteService = clienteService;
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
	
	
	private Hospedagem getFormData() {

		Hospedagem obj = new Hospedagem();
		
		obj.setId(Utils.tryParseToInt(txtId.getText()));
		
		obj.setCli(comboBoxCliente.getValue());
		
		obj.setCha(comboBoxChale.getValue());

		obj.setEstado(txtEstado.getText());

		Instant instant = Instant.from(dpDataIncio.getValue().atStartOfDay(ZoneId.systemDefault()));
		obj.setDataInicio(Date.from(instant));
		
		Instant instant2 = Instant.from(dpDataFim.getValue().atStartOfDay(ZoneId.systemDefault()));
		obj.setDataFim(Date.from(instant2));
		
		obj.setQtdPessoa(Utils.tryParseToInt(txtQtdPessoa.getText()));
		
		obj.setDesconto(Utils.tryParseToDouble(txtDesconto.getText()));
		
		obj.setValorFinal(Utils.tryParseToDouble(txtValorFinal.getText()));
		
		
		return obj;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	public void initializeNodes() {
		Constraints.setTextFieldInteger(txtId);
		initializeComboBoxChale();
		initializeComboBoxCliente();
		Utils.formatDatePicker(dpDataIncio, "yyyy-MM-dd");
		Utils.formatDatePicker(dpDataFim, "yyyy-MM-dd");
		Constraints.setTextFieldInteger(txtQtdPessoa);
		Constraints.setTextFieldDouble(txtDesconto);
		Constraints.setTextFieldDouble(txtValorFinal);
	}

	public void updateFormData() {

		if (entity == null) {
			throw new IllegalStateException("Entity was nos defined");
		}

		txtId.setText(String.valueOf(entity.getId()));
		
		if (entity.getCli() == null) {
			comboBoxCliente.getSelectionModel().selectFirst();
		}else {
			comboBoxCliente.setValue(entity.getCli());
		}
		if (entity.getCha() == null) {
			comboBoxChale.getSelectionModel().selectFirst();
		}else {
			comboBoxChale.setValue(entity.getCha());
		}
		
		txtEstado.setText(entity.getEstado());
		
		if (entity.getDataInicio() != null) {
			dpDataIncio.setValue(LocalDate.ofInstant(entity.getDataInicio().toInstant(), ZoneId.systemDefault()));
		}
		
		
		if (entity.getDataFim() != null) {
			dpDataFim.setValue(LocalDate.ofInstant(entity.getDataFim().toInstant(), ZoneId.systemDefault()));
		}
		txtQtdPessoa.setText(String.valueOf(entity.getQtdPessoa()));
		txtDesconto.setText(String.format("%.2f", entity.getDesconto()));
		txtValorFinal.setText(String.format("%.2f", entity.getValorFinal()));
		
	}
	
	public void loadAssociatedObjects() {
		if (clienteService == null) {
			throw new IllegalStateException("DepartmentService was null");
		}
		List<Cliente> list = clienteService.findAll();
		obsListCli = FXCollections.observableArrayList(list);
		comboBoxCliente.setItems(obsListCli);
		
		if (chaleService == null) {
			throw new IllegalStateException("DepartmentService was null");
		}
		List<Chale> list2 = chaleService.findAll();
		obsListCha = FXCollections.observableArrayList(list2);
		comboBoxChale.setItems(obsListCha);
	}

	
	private void notifyDataChangeListeners() {
		
		for (DataChangeListener listener : dataChangeListeners) {
			listener.onDataChanged();
		}
	}
	
	public void subscribeDataChangeListener(DataChangeListener listener) {
		dataChangeListeners.add(listener);
	}
	
	private void initializeComboBoxChale() {
		Callback<ListView<Chale>, ListCell<Chale>> factory = lv -> new ListCell<Chale>() {
			@Override
			protected void updateItem(Chale item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : Integer.toString( item.getId() ));
			}
		};
		comboBoxChale.setCellFactory(factory);
		comboBoxChale.setButtonCell(factory.call(null));
	}

	private void initializeComboBoxCliente() {
		Callback<ListView<Cliente>, ListCell<Cliente>> factory = lv -> new ListCell<Cliente>() {
			@Override
			protected void updateItem(Cliente item, boolean empty) {
				super.updateItem(item, empty);
				setText(empty ? "" : Integer.toString( item.getId() ));
			}
		};
		comboBoxCliente.setCellFactory(factory);
		comboBoxCliente.setButtonCell(factory.call(null));
	}
	
}
