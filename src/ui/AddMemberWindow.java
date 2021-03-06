package ui;

import business.Address;
import business.SystemController;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class AddMemberWindow extends Stage implements LibWindow {
	public static final AddMemberWindow INSTANCE = new AddMemberWindow();

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
	private AddMemberWindow() {
	}

	public void init() {

		VBox topContainer = new VBox();
		topContainer.setId("top-container");
		topContainer.autosize();

		VBox generalContainer = new VBox();
		generalContainer.setId("general-container");
		generalContainer.setPrefHeight(500);
		generalContainer.setPrefWidth(300);
		generalContainer.setPadding(new Insets(10, 10, 10, 10));
		VBox.setMargin(generalContainer, new Insets(20, 20, 20, 20));

		// StackPane basicStack = HelpWindow.getBasicTopPane();
		// topContainer.getChildren().add(basicStack);
		Label labelTitulo = new Label("Add Member ");
		labelTitulo.setId("window-title");
		GridPane gridForm = new GridPane();
		gridForm.setPrefHeight(400);
		gridForm.setPrefWidth(300);
		gridForm.setVgap(10);
		gridForm.add(labelTitulo, 0, 1);
		gridForm.setAlignment(Pos.CENTER);
		TextField memberId = new TextField();
		memberId.setPromptText("Member ID (Mandatory)");
		memberId.setId("memberId");
		TextField firstName = new TextField();
		firstName.setPromptText("First Name");
		firstName.setId("firstName");
		TextField lastName = new TextField();
		lastName.setPromptText("Last Name (Mandatory)");
		lastName.setId("lastName");
		TextField street = new TextField();
		street.setPromptText("Street");
		street.setId("street");
		TextField city = new TextField();
		city.setPromptText("City");
		city.setId("city");
		TextField state = new TextField();
		state.setPromptText("State");
		state.setId("state");
		TextField zip = new TextField();
		zip.setPromptText("ZIP");
		zip.setId("zip");
		TextField telephone = new TextField();
		telephone.setPromptText("Telephone");
		telephone.setId("telephone");
		gridForm.add(memberId, 0, 2);
		gridForm.add(firstName, 0, 3);
		gridForm.add(lastName, 0, 4);
		gridForm.add(street, 0, 5);
		gridForm.add(city, 0, 6);
		gridForm.add(state, 0, 7);
		gridForm.add(zip, 0, 8);
		gridForm.add(telephone, 0, 9);

		Button buttonCancel = new Button("Cancel");
		Button buttonSave = new Button("Save");
		HBox buttonContainer = new HBox();
		buttonCancel.setAlignment(Pos.CENTER_LEFT);
		buttonSave.setAlignment(Pos.CENTER_RIGHT);

		buttonCancel.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {
				MemberWindow.run();
			}
		});

		buttonSave.setOnAction(new EventHandler<ActionEvent>() {
			@Override
			public void handle(ActionEvent e) {

				String memId = memberId.getText().trim();
				String lName = lastName.getText().trim();

				if (memId.length() == 0) {
					HelpWindow.showAlert("Member ID is required!");
					return;
				} else if (lName.length() == 0) {
					HelpWindow.showAlert("Member Last Name is required!");
					return;
				}

				SystemController helpPls = new SystemController();
				helpPls.saveLibraryMemebr(memberId.getText(), firstName.getText(), lastName.getText(),
						new Address(street.getText(), city.getText(), state.getText(), zip.getText()),
						telephone.getText());
				MemberWindow.run();
			}
		});
		buttonContainer.getChildren().add(buttonCancel);
		buttonContainer.getChildren().add(buttonSave);

		gridForm.add(buttonContainer, 0, 10);

		generalContainer.getChildren().add(gridForm);
		topContainer.getChildren().add(generalContainer);

		Scene scene = new Scene(topContainer, 300, 600);
		scene.getStylesheets().add(getClass().getResource("library.css").toExternalForm());
		setScene(scene);

	}


	public static void run() {
		// TODO Auto-generated method stub
		Start.hideAllWindows();
		if (!AddMemberWindow.INSTANCE.isInitialized()) {
			AddMemberWindow.INSTANCE.init();
		}
		AddMemberWindow.INSTANCE.clear();
		AddMemberWindow.INSTANCE.show();
	}

}
