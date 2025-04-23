package projectgym.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.*;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class RegisterMemberController {
    @FXML private TextField nameField;
    @FXML private TextField birthdateField;
    @FXML private ComboBox<String> planBox;

    @FXML
    public void initialize() {
        planBox.getItems().addAll("WEEKLY", "MONTHLY", "YEARLY");
    }

    @FXML
    private void handleRegister() {
        String name = nameField.getText().trim();
        String birthdate = birthdateField.getText().trim();
        String plan = planBox.getValue();

        if (name.isEmpty() || birthdate.isEmpty() || plan == null) {
            showAlert("Error", "Please fill all fields");
            return;
        }

        if (!isValidDate(birthdate)) {
            showAlert("Error", "Invalid date format. Use DD-MM-YYYY");
            return;
        }

        int age = calculateAge(birthdate);
        if (age <= 12) {
            showAlert("Error", "Age must be above 12");
            return;
        }

        double price = calculatePrice(plan);
        String id = generateId();
        String joinDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        try {
            saveMember(id, name, String.valueOf(age), birthdate, plan, String.valueOf(price), joinDate);
            showAlert("Success", "Member registered successfully!\nID: " + id);
            clearFields();
        } catch (IOException e) {
            showAlert("Error", "Failed to save member data");
        }
    }

    @FXML
    private void handleBack() {
        ((Stage) nameField.getScene().getWindow()).close();
    }

    private boolean isValidDate(String date) {
        try {
            LocalDate.parse(date, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    private int calculateAge(String birthdate) {
        LocalDate dob = LocalDate.parse(birthdate, DateTimeFormatter.ofPattern("dd-MM-yyyy"));
        return LocalDate.now().getYear() - dob.getYear();
    }

    private double calculatePrice(String plan) {
        switch (plan.toUpperCase()) {
            case "WEEKLY": return 170.00;
            case "MONTHLY": return 500.00;
            case "YEARLY": return 5000.00;
            default: return 0.0;
        }
    }

    private String generateId() {
        return UUID.randomUUID().toString().substring(0, 8).toUpperCase();
    }

    private void saveMember(String id, String name, String age, String birthdate, 
                          String plan, String price, String joinDate) throws IOException {
        File file = new File("gym_members.txt");
        if (!file.exists()) {
            file.createNewFile();
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(file, true))) {
            out.println(String.join(", ", id, name, age, birthdate, plan, price, joinDate));
        }
    }

    private void clearFields() {
        nameField.clear();
        birthdateField.clear();
        planBox.getSelectionModel().clearSelection();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}