package projectgym.gui;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class MemberDetailsController {
    @FXML private Label idLabel;
    @FXML private Label nameLabel;
    @FXML private Label ageLabel;
    @FXML private Label birthdateLabel;
    @FXML private Label planLabel;
    @FXML private Label priceLabel;
    @FXML private Label joinDateLabel;

    public void setMemberData(Member member) {
        if (member == null) {
            return; // Prevent NullPointerException
        }
        idLabel.setText(member.getId());
        nameLabel.setText(member.getName());
        ageLabel.setText(member.getAge());
        birthdateLabel.setText(member.getBirthdate());
        planLabel.setText(member.getPlan());
        priceLabel.setText(member.getPrice());
        joinDateLabel.setText(member.getJoinDate());
    }

    @FXML
    private void handleClose() {
        ((Stage) idLabel.getScene().getWindow()).close();
    }
}