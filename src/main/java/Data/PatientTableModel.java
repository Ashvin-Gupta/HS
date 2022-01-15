package Data;
import Database.myDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;
import java.util.ArrayList;
import javax.swing.table.AbstractTableModel;

public class PatientTableModel extends AbstractTableModel {

    myDB dbConn = new myDB();
    Connection conn = dbConn.getConnection();
    private String[] columnNames = {"ID","First Name","Last Name","Sex"};
    private final int columnNum = 4;
    private int rowNum;
    private Object[][] data;

    public PatientTableModel() throws SQLException {

        String sqlStrRowNum = "select count(*) from patients;";

        PreparedStatement prpStmR = conn.prepareStatement(sqlStrRowNum);
        ResultSet rsRow = prpStmR.executeQuery();
        prpStmR.close();
        rsRow.next();
        rowNum = rsRow.getInt(1);

        String sqlStr = "select id,name,surname,sex from patients;";
        PreparedStatement prpStm = conn.prepareStatement(sqlStr);
        ResultSet rs = prpStm.executeQuery();
        prpStm.close();

        int patientcounter = 0;
        data = new Object[rowNum][columnNum];

        while(rs.next()) {

            int id = rs.getInt("id");
            String name = rs.getString("name");
            String surname = rs.getString("surname");
            String sex = rs.getString("sex");

            Object[] row = new Object[columnNum];
            row[0] = id;
            row[1] = name;
            row[2] = surname;
            row[3] = sex;

            data[patientcounter] = row;
            patientcounter++;
        }

                //Setting up table from data array
        int rows, cols, rowCounter, colCounter;

        rows = getRowCount();
        cols = getColumnCount();

        for (rowCounter=0; rowCounter < rows; rowCounter++)
        {
            for (colCounter=0; colCounter < cols; colCounter++)
            {
                setValueAt(data[rowCounter][colCounter],rowCounter,colCounter);
            }
        }

    }

    public int getColumnCount() {
        return columnNames.length;
    }

    public int getRowCount() {
        return data.length;
    }

    public String getColumnName(int col) {
        return columnNames[col];
    }

    public Object getValueAt(int row, int col) {
        return data[row][col];
    }

    public Class getColumnClass(int c) {
        return getValueAt(0, c).getClass();
    }
}

