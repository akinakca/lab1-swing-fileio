import javax.swing.*;
import java.awt.*;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ProjectFormPanel extends JPanel {

    private JTextField projectNameField;
    private JTextField teamLeaderField;
    private JComboBox<String> teamSizeCombo;
    private JComboBox<String> projectTypeCombo;
    private JTextField startDateField;
    private JButton saveButton;
    private JButton clearButton;

    public ProjectFormPanel() {
        setLayout(new GridLayout(7, 2, 5, 5));

        initializeComponents();

        addComponents();
    }

    private void initializeComponents() {
        projectNameField = new JTextField();

        teamLeaderField = new JTextField();

        String[] teamSizes = {"1–3", "4–6", "7–10", "10+"};
        teamSizeCombo = new JComboBox<>(teamSizes);

        String[] projectTypes = {"Web", "Mobile", "Desktop", "API"};
        projectTypeCombo = new JComboBox<>(projectTypes);

        startDateField = new JTextField();

        saveButton = new JButton("Save");
        clearButton = new JButton("Clear");

        saveButton.addActionListener(e -> handleSave());
        clearButton.addActionListener(e -> handleClear());
    }

    private void addComponents() {
        add(new JLabel("Project Name:"));
        add(projectNameField);

        add(new JLabel("Team Leader:"));
        add(teamLeaderField);

        add(new JLabel("Team Size:"));
        add(teamSizeCombo);

        add(new JLabel("Project Type:"));
        add(projectTypeCombo);

        add(new JLabel("Start Date (DD/MM/YYYY):"));
        add(startDateField);

        add(new JLabel(""));
        JPanel buttonPanel = new JPanel(new FlowLayout());
        buttonPanel.add(saveButton);
        buttonPanel.add(clearButton);
        add(buttonPanel);
    }

    private void handleSave() {
        String name = projectNameField.getText();
        String leader = teamLeaderField.getText();
        String size = (String) teamSizeCombo.getSelectedItem();
        String type = (String) projectTypeCombo.getSelectedItem();
        String date = startDateField.getText();

        if (name.isEmpty() || leader.isEmpty() || size == null || type == null || date.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please fill in all fields!", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        String recordTime = now.format(formatter);

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("projects.txt", true))) {
            writer.write("=== Project Entry ===");
            writer.newLine();
            writer.write("Project Name  : " + name);
            writer.newLine();
            writer.write("Team Leader   : " + leader);
            writer.newLine();
            writer.write("Team Size     : " + size);
            writer.newLine();
            writer.write("Project Type  : " + type);
            writer.newLine();
            writer.write("Start Date    : " + date);
            writer.newLine();
            writer.write("Record Time   : " + recordTime);
            writer.newLine();
            writer.write("=====================");
            writer.newLine();

            JOptionPane.showMessageDialog(this, "Project saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);

        } catch (IOException e) {
            JOptionPane.showMessageDialog(this, "Error writing to file: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void handleClear() {
        projectNameField.setText("");
        teamLeaderField.setText("");
        teamSizeCombo.setSelectedIndex(0);
        projectTypeCombo.setSelectedIndex(0);
        startDateField.setText("");
    }
}