package usercrud.view;

import usercrud.dao.UserDAO;
import usercrud.model.User;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserCRUDView extends JFrame {
    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private DefaultListModel<User> userListModel;
    private JList<User> userList;

    private UserDAO userDAO;

    public UserCRUDView() {
        super("Usuarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 300);
        setLocationRelativeTo(null);

        userDAO = new UserDAO();

        initComponents();
        loadUserList();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout());

        // User form panel
        JPanel formPanel = new JPanel(new GridLayout(3, 2));
        JLabel nameLabel = new JLabel("Nome:");
        nameField = new JTextField();
        JLabel emailLabel = new JLabel("Email:");
        emailField = new JTextField();
        JLabel passwordLabel = new JLabel("Senha:");
        passwordField = new JPasswordField();
        formPanel.add(nameLabel);
        formPanel.add(nameField);
        formPanel.add(emailLabel);
        formPanel.add(emailField);
        formPanel.add(passwordLabel);
        formPanel.add(passwordField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addUser();
            }
        });
        JButton deleteButton = new JButton("Deletar");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteUser();
            }
        });
        buttonPanel.add(addButton);
        buttonPanel.add(deleteButton);
        
        // Return to login button panel
        JPanel returnPanel = new JPanel();
        JButton returnButton = new JButton("Voltar ao Login");
        returnButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                openLoginView();
            }
        });
        returnPanel.add(returnButton);

        // User list
        userListModel = new DefaultListModel<>();
        userList = new JList<>(userListModel);
        JScrollPane userListScrollPane = new JScrollPane(userList);

        // Add components to the main panel
        mainPanel.add(formPanel, BorderLayout.NORTH);
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        mainPanel.add(userListScrollPane, BorderLayout.SOUTH);
        mainPanel.add(returnPanel, BorderLayout.WEST);

        // Add main panel to the frame
        setContentPane(mainPanel);
    }

    private void loadUserList() {
        userListModel.clear();
        List<User> users = userDAO.getUsers();
        for (User user : users) {
            userListModel.addElement(user);
        }
    }

    private void addUser() {
        String name = nameField.getText();
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());
        User user = new User(0, name, email, password);
        userDAO.createUser(user);
        loadUserList();
        clearFormFields();
    }

    private void deleteUser() {
        User selectedUser = userList.getSelectedValue();
        if (selectedUser != null) {
            int userId = selectedUser.getId();
            userDAO.deleteUser(userId);
            loadUserList();
            clearFormFields();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecione um usuario para deletar");
        }
    }

    private void clearFormFields() {
        nameField.setText("");
        emailField.setText("");
        passwordField.setText("");
    }

    private void openLoginView() {
        LoginView loginView = new LoginView();
        loginView.showView();
        dispose(); // Close the UserCRUDView window
    }

    public void showView() {
        setVisible(true);
    }
}
