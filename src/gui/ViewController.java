package gui;

import java.io.IOException;
import java.text.ParseException;
import java.util.function.Consumer;

import application.Main;
import gui.util.Alerts;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import model.services.ChaleService;
import model.services.ClienteService;
import model.services.HospedagemService;

public class ViewController {
	
	
	@FXML
	private MenuItem miCliente;
	
	public void onMenuItemClienteAction() throws ParseException {
		
		loadView("/gui/Cliente.fxml", (ClienteController controller ) -> {
			controller.setClienteService(new ClienteService());
			controller.updateTableView();
		} );
	}

	@FXML
	private MenuItem miChale;
	
	public void onMenuItemChaleAction() {
		loadView("/gui/Chale.fxml", (ChaleController controller ) -> {
			controller.setChaleService(new ChaleService());
			controller.updateTableView();
		} );
	}

	@FXML
	private MenuItem miHospedagem;
	
	public void onMenuItemHospedagemAction() {
		loadView("/gui/Hospedagem.fxml", (HospedagemController controller ) -> {
			controller.setHospedagemService(new HospedagemService());
			controller.updateTableView();
		} );
	}
	
	private synchronized <T> void loadView(String absoluteName, Consumer<T> initializeAction) {
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource(absoluteName));
			VBox newVbox = loader.load();
			
			Scene mainScene = Main.getScene();
			
			VBox mainVBox = (VBox)((ScrollPane) mainScene.getRoot()).getContent();
			
			Node mainMenu = mainVBox.getChildren().get(0);
			mainVBox.getChildren().clear();
			mainVBox.getChildren().add(mainMenu);
			mainVBox.getChildren().addAll(newVbox.getChildren());
			
			T controller = loader.getController();
			initializeAction.accept(controller);
			
		}catch(IOException e) {
			Alerts.showAlert("IO EXEPEXTION", "ERROR LOAD VIEW", e.getMessage(), AlertType.ERROR);
		}
		
	}
	
		
}
