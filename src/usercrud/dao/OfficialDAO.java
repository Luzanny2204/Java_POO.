package usercrud.dao;

import usercrud.model.Official;
import usercrud.util.DatabaseUtil;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OfficialDAO {
    public void createOfficial(Official official) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("INSERT INTO funcionario (nomeFuncionario, cargoFuncionario, salarioFuncionario, dataAdmisaoFuncionario) VALUES (?, ?, ?, ?)")) {
            stmt.setString(1, official.getNome());
            stmt.setString(2, official.getCargo());
            stmt.setDouble(3, official.getSalario());
            stmt.setString(4, official.getDataAdmisao());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Official> getOfficials() {
        List<Official> officials = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM funcionario")) {
            while (rs.next()) {
                int id = rs.getInt("idfuncionario");
                String nomeFuncionario = rs.getString("nomeFuncionario");
                String cargoFuncionario = rs.getString("cargoFuncionario");
                double salarioFuncionario = rs.getDouble("salarioFuncionario");
                String dataAdmisaoFuncionario = rs.getString("dataAdmisaoFuncionario");
                Official official = new Official(id, nomeFuncionario, cargoFuncionario, salarioFuncionario, dataAdmisaoFuncionario);
                officials.add(official);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return officials;
    }

    public List<Official> getOfficialsByDates(String startDate, String endDate) {
        List<Official> officials = new ArrayList<>();

        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("SELECT * FROM funcionario WHERE dataAdmisaoFuncionario >= ? AND dataAdmisaoFuncionario <= ?")) {
            stmt.setString(1, startDate);
            stmt.setString(2, endDate);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("idfuncionario");
                String nomeFuncionario = rs.getString("nomeFuncionario");
                String cargoFuncionario = rs.getString("cargoFuncionario");
                double salarioFuncionario = rs.getDouble("salarioFuncionario");
                String dataAdmisaoFuncionario = rs.getString("dataAdmisaoFuncionario");
                Official official = new Official(id, nomeFuncionario, cargoFuncionario, salarioFuncionario, dataAdmisaoFuncionario);
                officials.add(official);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return officials;
    }

    public void updateOfficial(Official official) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("UPDATE funcionario SET nomeFuncionario = ?, cargoFuncionario = ?, salarioFuncionario = ?, dataAdmisaoFuncionario = ? WHERE idfuncionario = ?")) {
            stmt.setString(1, official.getNome());
            stmt.setString(2, official.getCargo());
            stmt.setDouble(3, official.getSalario());
            stmt.setString(4, official.getDataAdmisao());
            stmt.setInt(5, official.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteOfficial(int officialId) {
        try (Connection conn = DatabaseUtil.getConnection();
             PreparedStatement stmt = conn.prepareStatement("DELETE FROM funcionario WHERE idfuncionario = ?")) {
            stmt.setInt(1, officialId);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Official> getOfficialsByDateRange(String startDate, String endDate) {
    List<Official> officials = new ArrayList<>();

    try (Connection conn = DatabaseUtil.getConnection();
         PreparedStatement stmt = conn.prepareStatement("SELECT * FROM funcionario WHERE dataAdmisaoFuncionario BETWEEN ? AND ?")) {
        stmt.setString(1, startDate);
        stmt.setString(2, endDate);
        ResultSet rs = stmt.executeQuery();

        while (rs.next()) {
            int id = rs.getInt("idfuncionario");
            String nomeFuncionario = rs.getString("nomeFuncionario");
            String cargoFuncionario = rs.getString("cargoFuncionario");
            double salarioFuncionario = rs.getDouble("salarioFuncionario");
            String dataAdmisaoFuncionario = rs.getString("dataAdmisaoFuncionario");
            Official official = new Official(id, nomeFuncionario, cargoFuncionario, salarioFuncionario, dataAdmisaoFuncionario);
            officials.add(official);
        }
    } catch (SQLException e) {
        e.printStackTrace();
    }

    return officials;
}
}
