package Data;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.AbstractTableModel;

public class PatientTableModel extends AbstractTableModel {
    private String[] columnNames = {"Id","First Name","Last Name"};
    private Object[][] data = {
            {1, "Aidan", "Lees"},
            {2, "Sam", "Wini"},
    };

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
