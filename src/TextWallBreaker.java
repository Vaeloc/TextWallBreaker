
import java.util.Scanner;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TextWallBreaker extends Application {

	TextArea userInput;
	Button submitBtn;
	Button browseFile;

	public static void main(String[] args) {

		launch(args);

	}

	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TextWall Breaker");

        GridPane grid = new GridPane();
		grid.setAlignment(Pos.CENTER);
		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(25, 25, 25, 25));

		// Title text - cannot be edited
		Text sceneTitle = new Text("Enter text or add text file");
		// Set the font to Tahoma, weight to normal, size to 20
		sceneTitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
		// Adds sceneTitle at column 0, row 0, with column span 2 and row span 1
		grid.add(sceneTitle, 0, 0, 2, 1);

		userInput = new TextArea();
		userInput.setWrapText(true);
		grid.add(userInput, 0, 1, 2, 2);


		submitBtn = new Button("Submit");
		HBox hSubmitButton = new HBox(10);
		hSubmitButton.setAlignment(Pos.BOTTOM_RIGHT);
		hSubmitButton.getChildren().add(submitBtn);
		grid.add(hSubmitButton, 1, 4);

		browseFile = new Button("Browse...");
		HBox hBrowseFile = new HBox(10);
		hBrowseFile.setAlignment(Pos.BOTTOM_LEFT);
		hBrowseFile.getChildren().add(browseFile);
		grid.add(hBrowseFile, 0, 4);

		submitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				breakUpText();
			}
		});

		Scene scene = new Scene(grid, 600, 375);
		primaryStage.setScene(scene);
        primaryStage.show();
    }

	private void breakUpText() {
		String text = userInput.getText();
		int sentences = 5;
		int sentenceCount = 0;

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if (String.valueOf(c).equals(".")) {
				sentenceCount++;

				if (sentenceCount == sentences && !String.valueOf(text.charAt(i + 2)).equals("\n")) {
					sentenceCount = 0;
					text = new StringBuilder(text).insert(i + 2, "\n\n").toString();
					userInput.setText(text);
				}
			}
		}
	}
}
