package Data;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.AbstractTableModel;

public class PatientTableModel extends AbstractTableModel {
    private String[] columnNames = {"ID","First Name","Last Name","Sex"};
    private Object[][] data = {
            {1, "Aidan", "Lees","M"},
            {2, "Sam", "Wini","M"},
    };

    public PatientTableModel(){
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
