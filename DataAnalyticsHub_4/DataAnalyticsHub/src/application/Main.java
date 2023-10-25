package application;
	
import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		Model model = new Model();
        View view = new View(primaryStage, model);
        Controller controller = new Controller(model, view);
        view.setController(controller);
        view.showLoginScreen();
	}
	
	public static void main(String[] args) {
		launch(args);
	}
}
