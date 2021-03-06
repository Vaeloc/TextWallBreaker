
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
import javafx.scene.control.Slider;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class TextWallBreaker extends Application {

	// Declare the UI components
	TextArea userInput;
	Button submitBtn;
	Button browseFile;
	Slider sentencesPerParagraph;

	// Declare the bufferedScanner for handling files
	Scanner bufferedScanner;

	public static void main(String[] args) {
		launch(args);
	}

	@Override
    public void start(Stage primaryStage) {
		// Set stage title
        primaryStage.setTitle("TextWall Breaker");

        // Create the grid layout
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

		// Create TextArea to hold text to break
		userInput = new TextArea();
		userInput.setWrapText(true);
		grid.add(userInput, 0, 1, 2, 2);

		// Create Slider so customise paragraph length
		sentencesPerParagraph = new Slider();
		sentencesPerParagraph.setMin(1);
		sentencesPerParagraph.setMax(10);
		sentencesPerParagraph.setValue(5);
		sentencesPerParagraph.setBlockIncrement(1);
		sentencesPerParagraph.setShowTickLabels(true);
		sentencesPerParagraph.setSnapToTicks(true);
		sentencesPerParagraph.setMajorTickUnit(1);
		sentencesPerParagraph.setMaxWidth(340);
		sentencesPerParagraph.setMinorTickCount(0);
		grid.add(sentencesPerParagraph, 1, 5, 3, 3);

		// Create Button to submit text for breaking
		submitBtn = new Button("Submit");
		HBox hSubmitButton = new HBox(10);
		hSubmitButton.setAlignment(Pos.BOTTOM_RIGHT);
		hSubmitButton.getChildren().add(submitBtn);
		grid.add(hSubmitButton, 1, 4);

		// Create Button to allow user to import file
		browseFile = new Button("Browse...");
		HBox hBrowseFile = new HBox(10);
		hBrowseFile.setAlignment(Pos.BOTTOM_LEFT);
		hBrowseFile.getChildren().add(browseFile);
		grid.add(hBrowseFile, 0, 4);

		// Submit Button listener to trigger text breaking
		submitBtn.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				breakUpText();
			}
		});

		// BrowseFile Button to open File Chooser
		browseFile.setOnAction(new EventHandler<ActionEvent>() {

			@Override
			public void handle(ActionEvent e) {
				// Creat FileChooser to let user import files
				JFileChooser fileChooser = new JFileChooser();

				int returnVal = fileChooser.showOpenDialog(fileChooser);

				if(returnVal == JFileChooser.APPROVE_OPTION) {
					File aFile = fileChooser.getSelectedFile();
					try {
						// Create Scanner object to read the file
						bufferedScanner = new Scanner(new BufferedReader(new FileReader(aFile)));
						initialiseFileText(bufferedScanner);
					}
					catch (Exception anException) {
						System.out.println("Error: " + anException);
					}
					finally {
						// Close the reader stream
						bufferedScanner.close();
					}

				}
			}

		});

		// Create scene and set the stage
		Scene scene = new Scene(grid, 600, 375);
		primaryStage.setScene(scene);
        primaryStage.show();
    }

	/**
	 * Takes a Scanner object and loops through loops through it
	 * adding while appending the text to a StringBuilder object.
	 *
	 * Once bufferedScanner.hasNextLine() returns false, the method
	 * returns the StringBuilder object.
	 */
	private StringBuilder extractText(Scanner bufferedScanner) {
		// Create StringBuilder object to store file text
		StringBuilder extractedText = new StringBuilder();

		// While there is text remaining in the file,
		// add it to the StringBuilder
		while(bufferedScanner.hasNextLine()) {
			extractedText.append(bufferedScanner.nextLine());
		}

		// Return the StringBuilder containing file text
		return extractedText;
	}


	/**
	 * Takes a Scanner object and passes it to the
	 * extractText() method and sets the userInput TextArea
	 * text to the returned StringBuilder object from extractText().
	 */
	private void initialiseFileText(Scanner bufferedScanner) {
		userInput.setText(extractText(bufferedScanner).toString());
	}

	/**
	 * Iterates over the text in the userInput TextArea and
	 * adds new lines after every number of sentences specified
	 * by the slider bar.
	 */
	private void breakUpText() {
		// Sets the value of text to the text that is in the userInput TextArea.
		String text = userInput.getText();
		// Set the number of sentences per paragraph to the value of the Slider bar.
		int sentences = (int) sentencesPerParagraph.getValue();
		int sentenceCount = 0;

		for (int i = 0; i < text.length(); i++) {
			char c = text.charAt(i);

			if (String.valueOf(c).equals(".")) {
				sentenceCount++;

				try {
					if (sentenceCount == sentences && !String.valueOf(text.charAt(i + 2)).equals("\n")) {
						sentenceCount = 0;
						text = new StringBuilder(text).insert(i + 2, "\n\n").toString();
						userInput.setText(text);
					}
				}
				catch (StringIndexOutOfBoundsException error) {
					System.out.println("Error: " + error);
					error.printStackTrace();
				}
			}
		}
	}
}
