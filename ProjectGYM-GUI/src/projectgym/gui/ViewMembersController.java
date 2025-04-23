package projectgym.gui;

import javafx.scene.Scene;
import javafx.fxml.FXMLLoader;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class ViewMembersController {
    @FXML private TableView<Member> memberTable;
    @FXML private TableColumn<Member, String> idCol;
    @FXML private TableColumn<Member, String> nameCol;
    @FXML private TableColumn<Member, String> ageCol;
    @FXML private TableColumn<Member, String> birthdateCol;
    @FXML private TableColumn<Member, String> planCol;
    @FXML private TableColumn<Member, String> priceCol;
    @FXML private TableColumn<Member, String> joinDateCol;
    @FXML private TextField searchField;

    private ObservableList<Member> memberData = FXCollections.observableArrayList();
    private FilteredList<Member> filteredData;

    @FXML
    public void initialize() {
        setupTableColumns();
        filteredData = new FilteredList<>(memberData, p -> true);
        memberTable.setItems(filteredData);
        loadMembers();

        searchField.textProperty().addListener((observable, oldValue, newValue) -> {
            filteredData.setPredicate(member -> {
                if (newValue == null || newValue.isEmpty()) {
                    return true;
                }
                String lowerCaseFilter = newValue.toLowerCase();
                if (member.getName().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                } else if (member.getId().toLowerCase().contains(lowerCaseFilter)) {
                    return true;
                }
                return false;
            });
        });
    }

    private void setupTableColumns() {
        idCol.setCellValueFactory(new PropertyValueFactory<>("id"));
        nameCol.setCellValueFactory(new PropertyValueFactory<>("name"));
        ageCol.setCellValueFactory(new PropertyValueFactory<>("age"));
        birthdateCol.setCellValueFactory(new PropertyValueFactory<>("birthdate"));
        planCol.setCellValueFactory(new PropertyValueFactory<>("plan"));
        priceCol.setCellValueFactory(new PropertyValueFactory<>("price"));
        joinDateCol.setCellValueFactory(new PropertyValueFactory<>("joinDate"));
    }

    private void loadMembers() {
        memberData.clear();
        File file = new File("gym_members.txt");
        
        if (!file.exists()) {
            showAlert("Information", "No members found");
            return;
        }

        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(", ");
                if (parts.length == 7) {
                    memberData.add(new Member(
                        parts[0], parts[1], parts[2], parts[3], 
                        parts[4], parts[5], parts[6]
                    ));
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to load members: " + e.getMessage());
        }
    }

    @FXML
    private void handleBack() {
        ((Stage) memberTable.getScene().getWindow()).close();
    }

    @FXML
    private void handleDelete() {
        Member selected = memberTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a member to delete");
            return;
        }

        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirm Delete");
        confirm.setHeaderText("Delete Member");
        confirm.setContentText("Are you sure you want to delete " + selected.getName() + "?");
        
        if (confirm.showAndWait().get() == ButtonType.OK) {
            deleteMember(selected.getId());
            loadMembers();
        }
    }

    @FXML
    private void handleEdit() {
        Member selected = memberTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            showAlert("Error", "Please select a member to edit");
            return;
        }
        
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("EditMember.fxml"));
            Stage stage = new Stage();
            stage.setScene(new Scene(loader.load()));
            stage.setTitle("Edit Member");
            stage.setResizable(false);
            
            EditMemberController controller = loader.getController();
            controller.setMemberData(selected);
            stage.showAndWait();
            
            loadMembers();
        } catch (IOException e) {
            showAlert("Error", "Failed to open edit window");
        }
    }

    private void deleteMember(String id) {
        List<String> lines = new ArrayList<>();
        File file = new File("gym_members.txt");
        
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = br.readLine()) != null) {
                if (!line.startsWith(id)) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            showAlert("Error", "Failed to read member data");
            return;
        }

        try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
            for (String line : lines) {
                out.println(line);
            }
            showAlert("Success", "Member deleted successfully");
        } catch (IOException e) {
            showAlert("Error", "Failed to save changes");
        }
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}