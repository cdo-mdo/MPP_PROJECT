package lab_app;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class TableExample {

    public static void main(String[] args) {
        // Create a new JFrame (window)
        JFrame frame = new JFrame("Library Checkout Records");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 400);

        // Data for the table
        String[] columnNames = {"Checkout Date", "Due Date", "Title", "Copy Number"};
        Object[][] data = {
            {"2024-10-01", "2024-10-15", "Java Programming", "1"},
            {"2024-10-03", "2024-10-17", "Data Structures", "2"},
            {"2024-10-05", "2024-10-19", "Database Design", "3"},
            {"2024-10-07", "2024-10-21", "Algorithms", "4"}
        };

        // Create a DefaultTableModel and pass data and column names
        DefaultTableModel model = new DefaultTableModel(data, columnNames);
        JTable table = new JTable(model);

        // Wrap the JTable inside a JScrollPane for scrolling
        JScrollPane scrollPane = new JScrollPane(table);

        // Add the JScrollPane (containing the table) to the frame
        frame.add(scrollPane, BorderLayout.CENTER);

        // Make the frame visible
        frame.setVisible(true);
    }
}
