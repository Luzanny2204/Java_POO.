package usercrud.view;

import usercrud.dao.UserDAO;
import usercrud.model.User;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class UserCRUDView extends JFrame {
    private static final int FRAME_WIDTH = 400;
    private static final int FRAME_HEIGHT = 300;
    private static final int COMPONENT_MARGIN = 10;

    private JTextField nameField;
    private JTextField emailField;
    private JPasswordField passwordField;
    private DefaultListModel<User> userListModel;
    private JList<User> userList;

    private UserDAO userDAO;

    public UserCRUDView() {
        super("Usuários");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(FRAME_WIDTH, FRAME_HEIGHT);
        setLocationRelativeTo(null);

        userDAO = new UserDAO();

        initComponents();
        loadUserList();
    }

    private void initComponents() {
    JPanel mainPanel = new JPanel();
    mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
    mainPanel.setBorder(new EmptyBorder(COMPONENT_MARGIN, COMPONENT_MARGIN, COMPONENT_MARGIN, COMPONENT_MARGIN));

    // User form panel
    JPanel formPanel = new JPanel(new GridLayout(3, 2, COMPONENT_MARGIN, COMPONENT_MARGIN));
    formPanel.setBorder(new EmptyBorder(COMPONENT_MARGIN, COMPONENT_MARGIN, COMPONENT_MARGIN, COMPONENT_MARGIN));
    formPanel.setBackground(Color.white);
    JLabel nameLabel = new JLabel("Nome:");
    nameLabel.setFont(nameLabel.getFont().deriveFont(Font.BOLD));
    nameField = new JTextField();
    JLabel emailLabel = new JLabel("Email:");
    emailLabel.setFont(emailLabel.getFont().deriveFont(Font.BOLD));
    emailField = new JTextField();
    JLabel passwordLabel = new JLabel("Senha:");
    passwordLabel.setFont(passwordLabel.getFont().deriveFont(Font.BOLD));
    passwordField = new JPasswordField();
    formPanel.add(nameLabel);
    formPanel.add(nameField);
    formPanel.add(emailLabel);
    formPanel.add(emailField);
    formPanel.add(passwordLabel);
    formPanel.add(passwordField);

    // Button panel
    JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, COMPONENT_MARGIN, COMPONENT_MARGIN));
    buttonPanel.setBackground(Color.white);
    JButton addButton = new JButton("Adicionar");
    addButton.addActionListener(new AddUserAction());
    addButton.setFont(addButton.getFont().deriveFont(Font.BOLD));
    JButton deleteButton = new JButton("Deletar");
    deleteButton.addActionListener(new DeleteUserAction());
    deleteButton.setFont(deleteButton.getFont().deriveFont(Font.BOLD));
    buttonPanel.add(addButton);
    buttonPanel.add(deleteButton);

    // Return to login button panel
    JPanel returnPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
    returnPanel.setBackground(Color.white);
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

    // Add components to the main panel with spacing
    mainPanel.add(formPanel);
    mainPanel.add(Box.createVerticalStrut(COMPONENT_MARGIN));
    mainPanel.add(buttonPanel);
    mainPanel.add(Box.createVerticalStrut(COMPONENT_MARGIN));
    mainPanel.add(userListScrollPane);
    mainPanel.add(Box.createVerticalStrut(COMPONENT_MARGIN));
    mainPanel.add(returnPanel);

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
        JOptionPane.showMessageDialog(this, "Usuário adicionado com sucesso!");
    }

    private void deleteUser() {
        User selectedUser = userList.getSelectedValue();
        if (selectedUser != null) {
            int userId = selectedUser.getId();
            userDAO.deleteUser(userId);
            loadUserList();
            clearFormFields();
            JOptionPane.showMessageDialog(this, "Usuário deletado com sucesso!");
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecione um usuário para deletar");
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
        SwingUtilities.invokeLater(() -> setVisible(true));
    }

    private class AddUserAction extends AbstractAction {
        public AddUserAction() {
            super("Adicionar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            addUser();
        }
    }

    private class DeleteUserAction extends AbstractAction {
        public DeleteUserAction() {
            super("Deletar");
        }

        @Override
        public void actionPerformed(ActionEvent e) {
            deleteUser();
        }
    }
}
