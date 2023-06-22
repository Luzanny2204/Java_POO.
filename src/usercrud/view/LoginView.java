package usercrud.view;

import usercrud.dao.UserDAO;
import usercrud.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginView extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;

    public LoginView() {
        super("Login");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(300, 200);
        setLocationRelativeTo(null);

        initComponents();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());
        JPanel formPanel = new JPanel(new GridBagLayout()); // Cambio de GridLayout a GridBagLayout

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.insets = new Insets(5, 5, 5, 5);

        JLabel emailLabel = new JLabel("Email:");
        formPanel.add(emailLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 0;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        emailField = new JTextField(20); // Tamaño predefinido para el campo de texto
        formPanel.add(emailField, constraints);

        constraints.gridx = 0;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.NONE;
        JLabel passwordLabel = new JLabel("Senha:");
        formPanel.add(passwordLabel, constraints);

        constraints.gridx = 1;
        constraints.gridy = 1;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        passwordField = new JPasswordField(20); // Tamaño predefinido para el campo de texto
        formPanel.add(passwordField, constraints);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Cambio de FlowLayout a FlowLayout con alineación central
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                login();
            }
        });
        JButton registerButton = new JButton("Cadastre-se");
        registerButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openUserCRUDView();
            }
        });
        buttonPanel.add(loginButton);
        buttonPanel.add(registerButton);

        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
    }

    private void login() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        UserDAO userDAO = new UserDAO();
        boolean authenticated = userDAO.authenticate(email, password);

        if (authenticated) {
            OfficialsView officialsView = new OfficialsView();
            officialsView.showView();
            dispose();
        } else {
            JOptionPane.showMessageDialog(this, "Combinação de email e senha invalida. Tente novamente.");
        }
    }

    private void openUserCRUDView() {
        UserCRUDView userCRUDView = new UserCRUDView();
        userCRUDView.showView();
        dispose();
    }

    public void showView() {
        setVisible(true);
    }
}
