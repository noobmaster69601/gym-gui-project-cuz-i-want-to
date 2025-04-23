package projectgym.gui;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
public class MainApp extends Application {
    private static Stage primaryStage;
    
    @Override
    public void start(Stage stage) throws Exception {
        primaryStage = stage;
        try {
            // Fixed path to match project structure
            primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/projectgym/gui/images/gym_icon.png")));
        } catch (Exception e) {
            // Catch any image loading errors to prevent app from crashing
            System.err.println("Warning: Could not load application icon: " + e.getMessage());
        }
        showAdminMenu();
    }
    
    public static void showAdminMenu() {
        try {
            Parent root = FXMLLoader.load(MainApp.class.getResource("AdminMenu.fxml"));
            Scene scene = new Scene(root);
            
            try {
                // Try to load the stylesheet - adjust path if needed
                String cssPath = MainApp.class.getResource("styles.css").toExternalForm();
                scene.getStylesheets().add(cssPath);
            } catch (Exception e) {
                // Catch stylesheet loading errors to prevent app from crashing
                System.err.println("Warning: Could not load stylesheet: " + e.getMessage());
            }
            
            primaryStage.setScene(scene);
            primaryStage.setTitle("Gym Management System");
            primaryStage.setResizable(false);
            primaryStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    public static void main(String[] args) {
        launch(args);
    }
    
    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}