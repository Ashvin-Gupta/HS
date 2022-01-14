package Data;
import Database.myDB;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.table.AbstractTableModel;

public class AlertTableModel extends AbstractTableModel {

    myDB dbConn = new myDB();
    Connection conn = dbConn.getConnection();
    private String[] columnNames = {"Patient ID","Last Name","Time","Alert Type"};
    private final int columnNum = 4;
    private int rowNum;
    private Object[][] data;
    private ResultSet rs;

    public AlertTableModel(int patientID) throws SQLException {

        String sqlStrRowNum = "select count(*) from alerts;";
        PreparedStatement prpStmR = conn.prepareStatement(sqlStrRowNum);
        ResultSet rsRow = prpStmR.executeQuery();
        prpStmR.close();
        rsRow.next();
        rowNum = rsRow.getInt(1);

        if (patientID == 0) {
            String sqlStr = "select patient_id,surname,time,alerttype from alerts;";
            PreparedStatement prpStm = conn.prepareStatement(sqlStr);
            rs = prpStm.executeQuery();
            prpStm.close();
        }

        else {
            String stringpatientID = String.valueOf(patientID);
            String sqlStr = "select patient_id,surname,time,alerttype from alerts where patient_id=" + stringpatientID;
            PreparedStatement prpStm = conn.prepareStatement(sqlStr);
            rs = prpStm.executeQuery();
            prpStm.close();
        }

        int alertcounter = 0;
        data = new Object[rowNum][columnNum];

        while(rs.next()) {

            int patientid = rs.getInt("patient_id");
            String surname = rs.getString("surname");
            String time = rs.getString("time");
            String alerttype = rs.getString("alerttype");

            Object[] row = new Object[columnNum];
            row[0] = patientid;
            row[1] = surname;
            row[2] = time;
            row[3] = alerttype;

            data[alertcounter] = row;
            alertcounter++;
        }

        int rows, cols, rowCounter, colCounter;

        rows = getRowCount();
        cols = getColumnCount();
        System.out.println(rows);
        System.out.println(cols);

        for (rowCounter=0; rowCounter < rows; rowCounter++)
        {
            for (colCounter=0; colCounter < cols; colCounter++)
            {
                setValueAt(data[rowCounter][colCounter],rowCounter,colCounter);
            }
        }

    }

    public int getColumnCount() {
        return (columnNum);
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
