package usercrud.view;


import usercrud.dao.OfficialDAO;
import usercrud.model.Official;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

public class OfficialsView extends JFrame {
    private Integer selectedOfficialId;
    private JTextField nomeField;
    private JTextField cargoField;
    private JTextField salarioField;
    private JTextField dataAdmisaoField;
    private DefaultListModel<Official> officialListModel;
    private JList<Official> officialList;
    private JLabel averageSalaryLabel;
    private JLabel addedChargesLabel;
    private JLabel totalOfficialsLabel;
    private JTextField startDateField;
    private JTextField endDateField;

    private OfficialDAO officialDAO;

    public OfficialsView() {
        super("Funcionarios");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 500);
        setLocationRelativeTo(null);

        officialDAO = new OfficialDAO();

        initComponents();
        loadOfficialList();
        calculateAndDisplayStatistics();
    }

    private void initComponents() {
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Statistics panel
        JPanel statisticsPanel = new JPanel(new GridLayout(6, 2, 10, 10));
        statisticsPanel.setBorder(BorderFactory.createTitledBorder("Estadísticas"));
        averageSalaryLabel = new JLabel();
        addedChargesLabel = new JLabel();
        totalOfficialsLabel = new JLabel();

        statisticsPanel.add(new JLabel("Media Salarial:"));
        statisticsPanel.add(averageSalaryLabel);
        statisticsPanel.add(new JLabel("Cargos em porcentagem:"));
        statisticsPanel.add(addedChargesLabel);
        statisticsPanel.add(new JLabel("Quantidade de funcionarios:"));
        statisticsPanel.add(totalOfficialsLabel);

        // Date fields
        startDateField = new JTextField();
        endDateField = new JTextField();
        statisticsPanel.add(new JLabel("Data de inicio (AAAA-MM-DD):"));
        statisticsPanel.add(startDateField);
        statisticsPanel.add(new JLabel("Data de fim (AAAA-MM-DD):"));
        statisticsPanel.add(endDateField);

        // Filter button
        JButton filterButton = new JButton("Filtrar");
        filterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                calculateAndDisplayStatistics();
            }
        });
        statisticsPanel.add(filterButton);

        mainPanel.add(statisticsPanel, BorderLayout.NORTH);

        // Official form panel
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 10, 10));
        formPanel.setBorder(BorderFactory.createTitledBorder("Formulario"));

        JLabel nameLabel = new JLabel("Nome:");
        nomeField = new JTextField();
        JLabel postLabel = new JLabel("Cargo:");
        cargoField = new JTextField();
        JLabel salaryLabel = new JLabel("Salario:");
        salarioField = new JTextField();
        JLabel admissionDateLabel = new JLabel("Data de admisão:");
        dataAdmisaoField = new JTextField();

        formPanel.add(nameLabel);
        formPanel.add(nomeField);
        formPanel.add(postLabel);
        formPanel.add(cargoField);
        formPanel.add(salaryLabel);
        formPanel.add(salarioField);
        formPanel.add(admissionDateLabel);
        formPanel.add(dataAdmisaoField);

        // Button panel
        JPanel buttonPanel = new JPanel();
        JButton addButton = new JButton("Adicionar");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addOfficial();
            }
        });
        JButton updateButton = new JButton("Atualizar");
        updateButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateOfficial();
            }
        });
        JButton deleteButton = new JButton("Deletar");
        deleteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                deleteOfficial();
            }
        });

        buttonPanel.add(addButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);

        // Official list
        officialListModel = new DefaultListModel<>();
        officialList = new JList<>(officialListModel);
        JScrollPane officialListScrollPane = new JScrollPane(officialList);

        // Add components to the main panel
        mainPanel.add(formPanel, BorderLayout.CENTER);
        mainPanel.add(buttonPanel, BorderLayout.SOUTH);
        mainPanel.add(officialListScrollPane, BorderLayout.EAST);

        // Add main panel to the frame
        setContentPane(mainPanel);
    }

    private void loadOfficialList() {
        officialListModel.clear();
        List<Official> officials = officialDAO.getOfficials();
        for (Official official : officials) {
            officialListModel.addElement(official);
        }
        officialList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    Official selectedOfficial = officialList.getSelectedValue();
                    selectedOfficialId = selectedOfficial.getId();
                    nomeField.setText(selectedOfficial.getNome());
                    cargoField.setText(selectedOfficial.getCargo());
                    salarioField.setText(String.valueOf(selectedOfficial.getSalario()));
                    dataAdmisaoField.setText(selectedOfficial.getDataAdmisao());
                }
            }
        });
    }

    private void addOfficial() {
        String name = nomeField.getText();
        String post = cargoField.getText();
        double salary = Double.parseDouble(salarioField.getText());
        String admissionDate = dataAdmisaoField.getText();
        Official official = new Official(0, name, post, salary, admissionDate);
        officialDAO.createOfficial(official);
        loadOfficialList();
        clearFormFields();
        calculateAndDisplayStatistics();
    }

    private void updateOfficial() {
        if (this.selectedOfficialId != null) {
            Official updatedOfficial = new Official(this.selectedOfficialId, nomeField.getText(), cargoField.getText(), Double.parseDouble(salarioField.getText()), dataAdmisaoField.getText());

            officialDAO.updateOfficial(updatedOfficial);
            loadOfficialList();
            clearFormFields();
            calculateAndDisplayStatistics();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecione um funcionario para atualizar.");
        }
    }

    private void deleteOfficial() {
        Official selectedOfficial = officialList.getSelectedValue();
        if (selectedOfficial != null) {
            int officialId = selectedOfficial.getId();
            officialDAO.deleteOfficial(officialId);
            loadOfficialList();
            clearFormFields();
            calculateAndDisplayStatistics();
        } else {
            JOptionPane.showMessageDialog(this, "Por favor selecione um funcionario para deletar");
        }
    }

    private void clearFormFields() {
        nomeField.setText("");
        cargoField.setText("");
        salarioField.setText("");
        dataAdmisaoField.setText("");
        selectedOfficialId = null;
    }

    public void showView() {
        setVisible(true);
    }

    private void calculateAndDisplayStatistics() {        
        List<Official> officials;
        String startDateText = startDateField.getText();
        String endDateText = endDateField.getText();

        officialListModel.clear();
        if (!startDateText.isEmpty() && !endDateText.isEmpty()) {
            officials = officialDAO.getOfficialsByDateRange(startDateText, endDateText);
        } else {
            officials = officialDAO.getOfficials();
        }

        for (Official official : officials) {
            officialListModel.addElement(official);
        }
        officialList.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (e.getValueIsAdjusting()) {
                    Official selectedOfficial = officialList.getSelectedValue();
                    selectedOfficialId = selectedOfficial.getId();
                    nomeField.setText(selectedOfficial.getNome());
                    cargoField.setText(selectedOfficial.getCargo());
                    salarioField.setText(String.valueOf(selectedOfficial.getSalario()));
                    dataAdmisaoField.setText(selectedOfficial.getDataAdmisao());
                }
            }
        });

        double totalSalary = 0;
        int totalOfficials = officials.size();
        int totalAddedCharges = 0;

        for (Official official : officials) {
            totalSalary += official.getSalario();
            totalAddedCharges += official.getCargosAdicionales();
        }

        double averageSalary = totalSalary / totalOfficials;
        double addedChargesPercentage = (double) totalAddedCharges / totalOfficials * 100;

        averageSalaryLabel.setText(String.valueOf(averageSalary));

        Map<String, Double> percentageByCargo = calculatePercentageByCargo(officials);

        // Print the percentage for each cargo
        String addedChargesLabelText = "";
        for (Map.Entry<String, Double> entry : percentageByCargo.entrySet()) {
            addedChargesLabelText += entry.getKey() + ": " + entry.getValue() + "%\n";
        }
        addedChargesLabel.setText(addedChargesLabelText);

        totalOfficialsLabel.setText(String.valueOf(totalOfficials));
    }
    
    public static Map<String, Double> calculatePercentageByCargo(List<Official> officials) {
        Map<String, Integer> countByCargo = new HashMap<>();
        int total = 0;

        // Count the number of objects for each cargo
        for (Official official : officials) {
            String cargo = official.getCargo();
            countByCargo.put(cargo, countByCargo.getOrDefault(cargo, 0) + 1);
            total++;
        }

        // Calculate the percentage for each cargo
        Map<String, Double> percentageByCargo = new HashMap<>();
        for (Map.Entry<String, Integer> entry : countByCargo.entrySet()) {
            double percentage = (entry.getValue() * 100.0) / total;
            percentageByCargo.put(entry.getKey(), percentage);
        }

        return percentageByCargo;
    }

}

