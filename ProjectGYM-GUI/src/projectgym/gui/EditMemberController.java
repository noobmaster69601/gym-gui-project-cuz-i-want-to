package projectgym.gui;
import projectgym.gui.Member;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class EditMemberController {
    @FXML private TextField nameField;
    @FXML private TextField ageField;
    @FXML private TextField birthdateField;
    @FXML private ComboBox<String> planBox;
    
    private Member memberToEdit;

    @FXML
    public void initialize() {
        planBox.getItems().addAll("WEEKLY", "MONTHLY", "YEARLY");
    }
    
    public void setMemberData(Member member) {
        this.memberToEdit = member;
        nameField.setText(member.getName());
        ageField.setText(member.getAge());
        birthdateField.setText(member.getBirthdate());
        planBox.setValue(member.getPlan());
    }

    @FXML
    private void handleSave() {
        String name = nameField.getText().trim();
        String plan = planBox.getValue();

        if (name.isEmpty() || plan == null) {
            showAlert("Error", "Please fill all fields");
            return;
        }

        try {
            updateMember(name, plan);
            showAlert("Success", "Member updated successfully");
            closeWindow();
        } catch (IOException e) {
            showAlert("Error", "Failed to update member");
        }
    }

    @FXML
    private void handleCancel() {
        closeWindow();
    }
    
    private void updateMember(String name, String plan) throws IOException {
        File file = new File("gym_members.txt");
        List<String> lines = new ArrayList<>();
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (line.startsWith(memberToEdit.getId())) {
                    String[] parts = line.split(", ");
                    line = String.join(", ", 
                        parts[0], // ID
                        name,     // New name
                        parts[2], // Age (unchanged)
                        parts[3], // Birthdate (unchanged)
                        plan,     // New plan
                        calculatePrice(plan), // Recalculated price
                        parts[6]  // Join date (unchanged)
                    );
                }
                lines.add(line);
            }
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            for (String line : lines) {
                out.println(line);
            }
        }
    }
    
    private String calculatePrice(String plan) {
        switch (plan.toUpperCase()) {
            case "WEEKLY": return "170.00";
            case "MONTHLY": return "500.00";
            case "YEARLY": return "5000.00";
            default: return "0.00";
        }
    }

    private void closeWindow() {
        ((Stage) nameField.getScene().getWindow()).close();
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
