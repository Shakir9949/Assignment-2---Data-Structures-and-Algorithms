import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class BakeryOrderApp extends JFrame {
    private JTextField nameField;
    private JTextField phoneField;
    private JComboBox<String> cakeCombo;
    private JRadioButton smallSize, mediumSize, largeSize;
    private JCheckBox deliveryCheck;
    private ButtonGroup sizeGroup;
    private JButton saveButton, quitButton;

    public BakeryOrderApp() {
        setTitle("Bakery Order Application");
        setSize(400, 350);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(7, 1));

        // Customer name
        JPanel namePanel = new JPanel();
        namePanel.add(new JLabel("Customer Name:"));
        nameField = new JTextField(20);
        namePanel.add(nameField);
        add(namePanel);

        // Phone number
        JPanel phonePanel = new JPanel();
        phonePanel.add(new JLabel("Phone Number:"));
        phoneField = new JTextField(20);
        phonePanel.add(phoneField);
        add(phonePanel);

        // Cake type
        JPanel cakePanel = new JPanel();
        cakePanel.add(new JLabel("Cake Type:"));
        String[] cakes = { "Apple", "Carrot", "Cheesecake", "Chocolate", "Coffee", "Opera", "Tiramisu" };
        cakeCombo = new JComboBox<>(cakes);
        cakePanel.add(cakeCombo);
        add(cakePanel);

        // Cake size
        JPanel sizePanel = new JPanel();
        sizePanel.add(new JLabel("Cake Size:"));
        smallSize = new JRadioButton("Small");
        mediumSize = new JRadioButton("Medium");
        largeSize = new JRadioButton("Large");
        sizeGroup = new ButtonGroup();
        sizeGroup.add(smallSize);
        sizeGroup.add(mediumSize);
        sizeGroup.add(largeSize);
        sizePanel.add(smallSize);
        sizePanel.add(mediumSize);
        sizePanel.add(largeSize);
        add(sizePanel);

        // Delivery area
        JPanel deliveryPanel = new JPanel();
        deliveryCheck = new JCheckBox("Within free delivery area");
        deliveryPanel.add(deliveryCheck);
        add(deliveryPanel);

        // Buttons
        JPanel buttonPanel = new JPanel();
        saveButton = new JButton("Save Order");
        quitButton = new JButton("Quit");
        buttonPanel.add(saveButton);
        buttonPanel.add(quitButton);
        add(buttonPanel);

        // Action Listeners
        saveButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saveOrder();
            }
        });

        quitButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose(); // Close the application
            }
        });
    }

    private void saveOrder() {
        String name = nameField.getText().trim();
        String phone = phoneField.getText().trim();
        String cake = (String) cakeCombo.getSelectedItem();
        String size = smallSize.isSelected() ? "Small"
                : mediumSize.isSelected() ? "Medium" : largeSize.isSelected() ? "Large" : "Not selected";
        boolean delivery = deliveryCheck.isSelected();

        if (name.isEmpty() || phone.isEmpty() || size.equals("Not selected")) {
            JOptionPane.showMessageDialog(this, "Please fill all required fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String orderLine = "Name: " + name + ", Phone: " + phone + ", Cake: " + cake +
                ", Size: " + size + ", Free Delivery: " + (delivery ? "Yes" : "No");

        try (BufferedWriter writer = new BufferedWriter(new FileWriter("Orders.txt", true))) {
            writer.write(orderLine);
            writer.newLine();
            JOptionPane.showMessageDialog(this, "Order saved successfully!");
            resetFields();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Error saving order: " + ex.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void resetFields() {
        nameField.setText("");
        phoneField.setText("");
        cakeCombo.setSelectedIndex(0);
        sizeGroup.clearSelection();
        deliveryCheck.setSelected(false);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new BakeryOrderApp().setVisible(true);
        });
    }
}
