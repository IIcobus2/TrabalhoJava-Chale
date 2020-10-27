package gui;

import java.io.IOException;
import java.net.URL;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

import application.Main;
import db.DbException;
import gui.listeners.DataChangeListener;
import gui.util.Alerts;
import gui.util.Utils;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.entities.Chale;
import model.entities.Cliente;
import model.entities.Hospedagem;
import model.services.ChaleService;
import model.services.ClienteService;
import model.services.HospedagemService;


public class HospedagemController implements Initializable, DataChangeListener {

	private HospedagemService service;

	@FXML
	private TableView<Hospedagem> tableViewHospedagem;

	@FXML
	private TableColumn<Hospedagem, Integer> tableColumnId;

	@FXML
	private TableColumn<Hospedagem, Chale> tableColumnChale;
	
	@FXML
	private TableColumn<Hospedagem, Cliente> tableColumnCliente;

	@FXML
	private TableColumn<Hospedagem, String> tableColumnEstado;
	
	@FXML
	private TableColumn<Hospedagem, Date> tableColumnDataInicio;
	
	@FXML
	private TableColumn<Hospedagem, Date> tableColumnDataFim;
	
	@FXML
	private TableColumn<Hospedagem, Integer> tableColumnQtdPessoa;
	
	@FXML
	private TableColumn<Hospedagem, Double> tableColumnDesconto;
	
	@FXML
	private TableColumn<Hospedagem, Double> tableColumnValorFinal;

	@FXML
	private TableColumn<Hospedagem, Hospedagem> tableColumnEDIT;
	
	@FXML
	private TableColumn<Hospedagem, Hospedagem> tableColumnREMOVE;


	@FXML
	private Button btNovo;

	private ObservableList<Hospedagem> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parenteStage = Utils.currentStage(event);
		Hospedagem obj = new Hospedagem();
		createDialogForm(obj, "/gui/HospedagemForm.fxml", parenteStage);
	}

	public void setHospedagemService(HospedagemService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {
		
		
		
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnEstado.setCellValueFactory(new PropertyValueFactory<>("Estado"));
		tableColumnDataInicio.setCellValueFactory(new PropertyValueFactory<>("DataInicio"));
		tableColumnDataFim.setCellValueFactory(new PropertyValueFactory<>("DataFim"));
		tableColumnQtdPessoa.setCellValueFactory(new PropertyValueFactory<>("QtdPessoa"));
		tableColumnDesconto.setCellValueFactory(new PropertyValueFactory<>("Desconto"));
		Utils.formatTableColumnDouble(tableColumnDesconto, 2);
		tableColumnValorFinal.setCellValueFactory(new PropertyValueFactory<>("ValorFinal"));
		Utils.formatTableColumnDouble(tableColumnValorFinal, 2);
		
		
		tableColumnChale.setCellValueFactory(new PropertyValueFactory<>("cli"));
		tableColumnCliente.setCellValueFactory(new PropertyValueFactory<>("cha"));
		

		Stage stage = (Stage) Main.getScene().getWindow();

		tableViewHospedagem.prefHeightProperty().bind(stage.heightProperty());

	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Servico e nulo");
		}

		List<Hospedagem> list = service.findAll();

		obsList = FXCollections.observableArrayList(list);
		tableViewHospedagem.setItems(obsList);
		initEditButtons();
		initRemoveButtons();

	}

	private void createDialogForm(Hospedagem obj, String absoluteName, Stage parenteStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			HospedagemFormController controller = loader.getController();
			controller.setHospedagem(obj);
			controller.setServices(new HospedagemService(), new ClienteService(), new ChaleService());
			controller.loadAssociatedObjects();
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Registro da Hospedagem");
			dialogStage.setScene(new Scene(pane));
			dialogStage.setResizable(false);
			dialogStage.initOwner(parenteStage);
			dialogStage.initModality(Modality.WINDOW_MODAL);
			dialogStage.showAndWait();

		} catch (IOException e) {
			Alerts.showAlert("IO Exeption", "Erro loading view", e.getMessage(), AlertType.ERROR);
		}
	}

	@Override
	public void onDataChanged() {
		updateTableView();
	}

	private void initEditButtons() {
		tableColumnEDIT.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnEDIT.setCellFactory(param -> new TableCell<Hospedagem, Hospedagem>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Hospedagem obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/HospedagemForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Hospedagem, Hospedagem>() {
			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(Hospedagem obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> removeEntity(obj));
			}
		});
	}
	
	private void removeEntity(Hospedagem obj) {
		Optional<ButtonType> result = Alerts.showConfirmation("Confirmation", "Are you sure to delete?");

		if (result.get() == ButtonType.OK) {
			if (service == null) {
				throw new IllegalStateException("Service was null");
			}
			try {
				service.remove(obj);
				updateTableView();
			}
			catch (DbException e) {
				Alerts.showAlert("Error removing object", null, e.getMessage(), AlertType.ERROR);
			}
		}
	}
	
	

}
