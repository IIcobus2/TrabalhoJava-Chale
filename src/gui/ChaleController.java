package gui;

import java.io.IOException;
import java.net.URL;
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
import model.services.ChaleService;


public class ChaleController implements Initializable, DataChangeListener {

	private ChaleService service;

	@FXML
	private TableView<Chale> tableViewChale;

	@FXML
	private TableColumn<Chale, Integer> tableColumnId;

	@FXML
	private TableColumn<Chale, String> tableColumnLoc;
	
	@FXML
	private TableColumn<Chale, String> tableColumnCapacidade;

	@FXML
	private TableColumn<Chale, Double> tableColumnValorAlta;
	
	@FXML
	private TableColumn<Chale, Double> tableColumnValorBaixa;

	@FXML
	private TableColumn<Chale, Chale> tableColumnEDIT;
	
	@FXML
	private TableColumn<Chale, Chale> tableColumnREMOVE;


	@FXML
	private Button btNovo;

	private ObservableList<Chale> obsList;

	@FXML
	public void onBtNovoAction(ActionEvent event) {
		Stage parenteStage = Utils.currentStage(event);
		Chale obj = new Chale();
		createDialogForm(obj, "/gui/ChaleForm.fxml", parenteStage);
	}

	public void setChaleService(ChaleService service) {
		this.service = service;
	}

	@Override
	public void initialize(URL url, ResourceBundle rb) {
		initializeNodes();

	}

	private void initializeNodes() {
		
		
		
		tableColumnId.setCellValueFactory(new PropertyValueFactory<>("Id"));
		tableColumnLoc.setCellValueFactory(new PropertyValueFactory<>("Loc"));
		tableColumnCapacidade.setCellValueFactory(new PropertyValueFactory<>("Capacidade"));
		
		tableColumnValorAlta.setCellValueFactory(new PropertyValueFactory<>("ValorAlta"));
		Utils.formatTableColumnDouble(tableColumnValorAlta, 2);
		
		tableColumnValorBaixa.setCellValueFactory(new PropertyValueFactory<>("ValorBaixa"));
		Utils.formatTableColumnDouble(tableColumnValorBaixa, 2);

		Stage stage = (Stage) Main.getScene().getWindow();

		tableViewChale.prefHeightProperty().bind(stage.heightProperty());

	}

	public void updateTableView() {
		if (service == null) {
			throw new IllegalStateException("Servico e nulo");
		}

		List<Chale> list = service.findAll();

		obsList = FXCollections.observableArrayList(list);
		tableViewChale.setItems(obsList);
		initEditButtons();
		initRemoveButtons();

	}

	private void createDialogForm(Chale obj, String absoluteName, Stage parenteStage) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			Pane pane = loader.load();

			ChaleFormController controller = loader.getController();
			controller.setChale(obj);
			controller.setChaleService(new ChaleService());
			controller.subscribeDataChangeListener(this);
			controller.updateFormData();

			Stage dialogStage = new Stage();
			dialogStage.setTitle("Registro de Chale");
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
		tableColumnEDIT.setCellFactory(param -> new TableCell<Chale, Chale>() {
			private final Button button = new Button("Editar");

			@Override
			protected void updateItem(Chale obj, boolean empty) {
				super.updateItem(obj, empty);
				if (obj == null) {
					setGraphic(null);
					return;
				}
				setGraphic(button);
				button.setOnAction(event -> createDialogForm(obj, "/gui/ChaleForm.fxml", Utils.currentStage(event)));
			}
		});
	}

	private void initRemoveButtons() {
		tableColumnREMOVE.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
		tableColumnREMOVE.setCellFactory(param -> new TableCell<Chale, Chale>() {
			private final Button button = new Button("Remover");

			@Override
			protected void updateItem(Chale obj, boolean empty) {
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
	
	private void removeEntity(Chale obj) {
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
