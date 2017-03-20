
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.Scanner;

import javax.swing.JFileChooser;

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
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TextWallBreaker extends Application {

	TextArea userInput;
	Button submitBtn;
	Button browseFile;
	String pathname;
	Scanner bufferedScanner;

	public static void main(String[] args) {

		launch(args);

	}

	@Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("TextWall Breaker");

        pathname = null;

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

		browseFile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				JFileChooser fileChooser = new JFileChooser();

				int returnVal = fileChooser.showOpenDialog(fileChooser);

				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File aFile = fileChooser.getSelectedFile();
					try {
						bufferedScanner = new Scanner(new BufferedReader(new FileReader(aFile)));
						breakUpText(bufferedScanner);
					}
					catch (Exception anException) {
						System.out.println("Error: " + anException);
					}

				}
			}

		});

		Scene scene = new Scene(grid, 600, 375);
		primaryStage.setScene(scene);
        primaryStage.show();
    }

	private StringBuilder extractText(Scanner bufferedScanner) {
		StringBuilder extractedText = new StringBuilder();

		while(bufferedScanner.hasNextLine()) {
			extractedText.append(bufferedScanner.nextLine());
		}

		return extractedText;
	}


	private void breakUpText(Scanner bufferedScanner) {
		userInput.setText(extractText(bufferedScanner).toString());
		breakUpText();
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
