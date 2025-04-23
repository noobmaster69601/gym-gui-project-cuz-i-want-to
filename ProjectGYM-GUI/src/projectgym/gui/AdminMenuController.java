package projectgym.gui;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;
import java.util.Optional;

public class AdminMenuController {

    @FXML
    private void handleRegister(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("RegisterMember.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Member Registration");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Failed to open registration form");
        }
    }

    @FXML
    private void handleViewMembers(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("ViewMembers.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Member Database");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Failed to open member database");
        }
    }

    @FXML
    private void handleMemberLogin(ActionEvent event) {
        try {
            Parent root = FXMLLoader.load(getClass().getResource("MemberLogin.fxml"));
            Stage stage = new Stage();
            stage.setTitle("Member Login");
            stage.setScene(new Scene(root));
            stage.setResizable(false);
            stage.show();
        } catch (Exception e) {
            showAlert(AlertType.ERROR, "Error", "Failed to open member login");
        }
    }

    @FXML
    private void handleLogout(ActionEvent event) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Confirm Logout");
        alert.setContentText("Are you sure you want to logout?");
        
        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
        }
    }
    
    private void showAlert(AlertType type, String title, String message) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}