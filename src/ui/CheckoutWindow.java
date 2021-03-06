package ui;

import java.text.DateFormat;
import java.text.SimpleDateFormat;

import business.CheckoutEntry;
import business.SystemController;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Callback;

public class CheckoutWindow extends Stage implements LibWindow {
	public static final CheckoutWindow INSTANCE = new CheckoutWindow();

	private boolean isInitialized = false;

	public boolean isInitialized() {
		return isInitialized;
	}

	public void isInitialized(boolean val) {
		isInitialized = val;
	}

	private Text messageBar = new Text();

	public void clear() {
		messageBar.setText("");
	}

	/* This class is a singleton */
	private CheckoutWindow() {
	}

	public void init() {

		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		topContainer.autosize();

		VBox generalContainer = new VBox();
		generalContainer.setId("general-container");
		generalContainer.setPrefHeight(600);
		generalContainer.setPrefWidth(600);
		generalContainer.setPadding(new Insets(20, 30, 20, 30));
		VBox.setMargin(generalContainer, new Insets(20, 20, 20, 20));

		StackPane basicStack = HelpWindow.getBasicTopPane();
		Label labelTitulo = new Label("Checkout records ");
		labelTitulo.setId("window-title");
		Button buttonAdd = new Button("Add");

		buttonAdd.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				AddCheckoutWindow.run();
			}
		});
		HBox buttonContainer = new HBox();
		buttonContainer.setPrefWidth(500);

		buttonContainer.getChildren().add(buttonAdd);
		// newStack.getChildren().add(labelUsuario);
		topContainer.getChildren().add(basicStack);
		generalContainer.getChildren().add(labelTitulo);
		generalContainer.getChildren().add(buttonContainer);
		generalContainer.getChildren().add(createTable());
		topContainer.getChildren().add(generalContainer);

		Scene scene = new Scene(topContainer, 800, 500);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		setScene(scene);
	}

	public static TableView<CheckoutEntry> createTable() {
		// Members
		TableColumn<CheckoutEntry, String> dueDateStringColumn = new TableColumn<>("Due Date");
		dueDateStringColumn.setMinWidth(180);
		dueDateStringColumn
				.setCellValueFactory(new Callback<CellDataFeatures<CheckoutEntry, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<CheckoutEntry, String> data) {
						DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY");
						return new SimpleStringProperty(dateFormat.format(data.getValue().getDueDate()));
					}
				});

		TableColumn<CheckoutEntry, String> checkoutDateStringColumn = new TableColumn<>("Checkout Date");
		checkoutDateStringColumn.setMinWidth(180);
		checkoutDateStringColumn
				.setCellValueFactory(new Callback<CellDataFeatures<CheckoutEntry, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<CheckoutEntry, String> data) {
						DateFormat dateFormat = new SimpleDateFormat("MM-dd-YYYY");
						return new SimpleStringProperty(dateFormat.format(data.getValue().getCheckoutDate()));
					}
				});

		TableColumn<CheckoutEntry, String> libraryMemberColumn = new TableColumn<>("Library Member");
		libraryMemberColumn.setMinWidth(180);
		libraryMemberColumn
				.setCellValueFactory(new Callback<CellDataFeatures<CheckoutEntry, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<CheckoutEntry, String> data) {
						return new SimpleStringProperty(data.getValue().getLibraryMember().toString());
					}
				});

		TableColumn<CheckoutEntry, String> bookColumn = new TableColumn<>("Book ");
		bookColumn.setMinWidth(180);
		bookColumn
				.setCellValueFactory(new Callback<CellDataFeatures<CheckoutEntry, String>, ObservableValue<String>>() {
					@Override
					public ObservableValue<String> call(CellDataFeatures<CheckoutEntry, String> data) {
						return new SimpleStringProperty(data.getValue().getBookCopy().toString());
					}
				});

		TableView<CheckoutEntry> tableMember = new TableView<CheckoutEntry>();
		tableMember.setItems(getCheckoutEntrys());
		tableMember.getColumns().addAll(dueDateStringColumn, checkoutDateStringColumn, libraryMemberColumn, bookColumn);
		return tableMember;
	}

	private static ObservableList<CheckoutEntry> getCheckoutEntrys() {
		// TODO Auto-generated method stub
		ObservableList<CheckoutEntry> asd = FXCollections.observableArrayList();
		SystemController sc = new SystemController();
		asd.addAll(sc.allCheckoutEntries());
		return asd;
	}

	public static void run() {
		Start.hideAllWindows();
		// TODO Auto-generated method stub
		if (!CheckoutWindow.INSTANCE.isInitialized()) {
			CheckoutWindow.INSTANCE.init();
		}
		CheckoutWindow.INSTANCE.clear();
		CheckoutWindow.INSTANCE.show();
	}

}
