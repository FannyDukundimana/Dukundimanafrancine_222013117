package library_management;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Vector;

public class book extends JFrame {
    JFrame frame;
    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JLabel lblTopBar;
    private JLabel lblFormTitle;
    private JLabel lblBookName;
    private JLabel lblAuthor;
    private JLabel lblPublishedDate;
    private JLabel lblPublishedCompany;
    private JLabel lblLanguage;
    private JTextField txtBookName;
    private JTextField txtAuthor;
    private JTextField txtPublishedDate;
    private JTextField txtPublishedCompany;
    private JTextField txtLanguage;
    private JButton btnBack;
    private JButton btnDelete;
    private JButton btnUpdate;
    private JButton btnAdd;

    private JTable table1;

    Connection con;
    PreparedStatement pst;

    private void showDataTable1() {
        int cc;
        try {
            pst = con.prepareStatement("SELECT * FROM books");
            ResultSet Rs = pst.executeQuery();
            java.sql.ResultSetMetaData RSMD = Rs.getMetaData();
            cc = RSMD.getColumnCount();
            DefaultTableModel dataInTable = (DefaultTableModel) table1.getModel();
            dataInTable.setRowCount(0);
            while (Rs.next()) {
                Vector v2 = new Vector();
                for (int i = 1; i <= cc; i++) {
                    v2.add(Rs.getString("id"));
                    v2.add(Rs.getString("book_name"));
                    v2.add(Rs.getString("book_author"));
                    v2.add(Rs.getString("book_published_date"));
                    v2.add(Rs.getString("book_bublished_company"));
                    v2.add(Rs.getString("book_langauge"));
                }
                dataInTable.addRow(v2);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/librarydb", "root", "");
            System.out.print("Connected successfully");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void clearForm() {
        txtBookName.setText("");
        txtAuthor.setText("");
        txtPublishedDate.setText("");
        txtPublishedCompany.setText("");
        txtLanguage.setText("");
    }

    private void addBook() {
        try {
            String bookName = txtBookName.getText();
            String author = txtAuthor.getText();
            String publishedDate = txtPublishedDate.getText();
            String publishedCompany = txtPublishedCompany.getText();
            String language = txtLanguage.getText();

            PreparedStatement insertStmt = con.prepareStatement("INSERT INTO books (book_name, book_author, book_published_date, book_bublished_company, book_langauge) VALUES (?, ?, ?, ?, ?)");
            insertStmt.setString(1, bookName);
            insertStmt.setString(2, author);
            insertStmt.setString(3, publishedDate);
            insertStmt.setString(4, publishedCompany);
            insertStmt.setString(5, language);
            insertStmt.executeUpdate();

            JOptionPane.showMessageDialog(frame, "Book added successfully");
            showDataTable1();
            clearForm();
        } catch (SQLException ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(frame, "Error while adding book", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteBook() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to delete this book?", "Confirm Deletion", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String bookId = (String) table1.getValueAt(selectedRow, 0);
                    PreparedStatement deleteStmt = con.prepareStatement("DELETE FROM books WHERE id = ?");
                    deleteStmt.setString(1, bookId);
                    deleteStmt.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "Book deleted successfully");
                    showDataTable1();
                    clearForm();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while deleting book", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a book to delete.");
        }
    }

    private void updateBook() {
        int selectedRow = table1.getSelectedRow();
        if (selectedRow != -1) {
            int confirm = JOptionPane.showConfirmDialog(frame, "Are you sure you want to update this book?", "Confirm Update", JOptionPane.YES_NO_OPTION);
            if (confirm == JOptionPane.YES_OPTION) {
                try {
                    String bookId = (String) table1.getValueAt(selectedRow, 0);
                    String bookName = txtBookName.getText();
                    String author = txtAuthor.getText();
                    String publishedDate = txtPublishedDate.getText();
                    String publishedCompany = txtPublishedCompany.getText();
                    String language = txtLanguage.getText();

                    PreparedStatement updateStmt = con.prepareStatement("UPDATE books SET book_name = ?, book_author = ?, book_published_date = ?, book_bublished_company = ?, book_langauge = ? WHERE id = ?");
                    updateStmt.setString(1, bookName);
                    updateStmt.setString(2, author);
                    updateStmt.setString(3, publishedDate);
                    updateStmt.setString(4, publishedCompany);
                    updateStmt.setString(5, language);
                    updateStmt.setString(6, bookId);
                    updateStmt.executeUpdate();

                    JOptionPane.showMessageDialog(frame, "Book updated successfully");
                    showDataTable1();
                    clearForm();
                } catch (SQLException ex) {
                    ex.printStackTrace();
                    JOptionPane.showMessageDialog(frame, "Error while updating book", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        } else {
            JOptionPane.showMessageDialog(frame, "Please select a book to update.");
        }
    }

    public book() {
        connection();
        frame = this;
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 900, 700);
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);

        // Top Bar Section
        JPanel topBarPanel = new JPanel();
        topBarPanel.setBackground(new Color(153, 102, 0)); // Changed to dark yellow
        topBarPanel.setBounds(0, 0, 900, 50);
        contentPane.add(topBarPanel);
        topBarPanel.setLayout(null);

        lblTopBar = new JLabel("LIBRARY MANAGE BOOKS");
        lblTopBar.setForeground(Color.WHITE);
        lblTopBar.setFont(new Font("Verdana", Font.BOLD, 16));
        lblTopBar.setBounds(10, 10, 400, 30);
        topBarPanel.add(lblTopBar);

        // Back Button
        btnBack = new JButton("Back");
        btnBack.setBounds(800, 10, 80, 30); // Adjust bounds as needed
        btnBack.setBackground(Color.GRAY); // Adjust background color as needed
        topBarPanel.add(btnBack);
        btnBack.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                // Assuming you have a user_skol class with a frame to navigate back to
                user_libary sales_page_ = new user_libary();
                sales_page_.frame.setVisible(true);
                frame.dispose(); // Close the current window
            }
        });

        // Form Section
        JPanel formPanel = new JPanel();
        formPanel.setBounds(0, 50, 450, 300);
        contentPane.add(formPanel);
        formPanel.setLayout(null);

        lblFormTitle = new JLabel("Book Information");
        lblFormTitle.setFont(new Font("Verdana", Font.BOLD, 14));
        lblFormTitle.setBounds(10, 10, 200, 20);
        formPanel.add(lblFormTitle);

        lblBookName = new JLabel("Book Name:");
        lblBookName.setBounds(10, 40, 150, 20);
        formPanel.add(lblBookName);

        txtBookName = new JTextField();
        txtBookName.setBounds(170, 40, 200, 20);
        formPanel.add(txtBookName);
        txtBookName.setColumns(10);

        lblAuthor = new JLabel("Author:");
        lblAuthor.setBounds(10, 70, 150, 20);
        formPanel.add(lblAuthor);

        txtAuthor = new JTextField();
        txtAuthor.setBounds(170, 70, 200, 20);
        formPanel.add(txtAuthor);
        txtAuthor.setColumns(10);

        lblPublishedDate = new JLabel("Published Date:");
        lblPublishedDate.setBounds(10, 100, 150, 20);
        formPanel.add(lblPublishedDate);

        txtPublishedDate = new JTextField();
        txtPublishedDate.setBounds(170, 100, 200, 20);
        formPanel.add(txtPublishedDate);
        txtPublishedDate.setColumns(10);

        lblPublishedCompany = new JLabel("Published Company:");
        lblPublishedCompany.setBounds(10, 130, 150, 20);
        formPanel.add(lblPublishedCompany);

        txtPublishedCompany = new JTextField();
        txtPublishedCompany.setBounds(170, 130, 200, 20);
        formPanel.add(txtPublishedCompany);
        txtPublishedCompany.setColumns(10);

        lblLanguage = new JLabel("Language:");
        lblLanguage.setBounds(10, 160, 150, 20);
        formPanel.add(lblLanguage);

        txtLanguage = new JTextField();
        txtLanguage.setBounds(170, 160, 200, 20);
        formPanel.add(txtLanguage);
        txtLanguage.setColumns(10);

        // Add Button
        btnAdd = new JButton("Add");
        btnAdd.setBounds(10, 190, 80, 30);
        btnAdd.setBackground(new Color(153, 102, 0)); // Changed to dark yellow
        formPanel.add(btnAdd);
        btnAdd.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                addBook();
            }
        });

        // Delete Button
        btnDelete = new JButton("Delete");
        btnDelete.setBounds(100, 190, 80, 30);
        btnDelete.setBackground(Color.RED); // Adjust background color as needed
        formPanel.add(btnDelete);
        btnDelete.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                deleteBook();
            }
        });

        // Update Button
        btnUpdate = new JButton("Update");
        btnUpdate.setBounds(190, 190, 80, 30);
        btnUpdate.setBackground(Color.GREEN); // Adjust background color as needed
        formPanel.add(btnUpdate);
        btnUpdate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                updateBook();
            }
        });

        // Table Section
        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(0, 360, 900, 300);
        contentPane.add(scrollPane);

        Object[][] data = {{null, null, null, null, null, null}};
        String[] columnHeaders = {"ID", "Book Name", "Author", "Published Date", "Published Company", "Language"};
        DefaultTableModel model = new DefaultTableModel(data, columnHeaders);
        table1 = new JTable(model);
        scrollPane.setViewportView(table1);
        showDataTable1();

        // Add ListSelectionListener to the table
        table1.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent e) {
                int selectedRow = table1.getSelectedRow();
                if (selectedRow != -1) {
                    populateFormFromTable(selectedRow);
                }
            }
        });
    }

    private void populateFormFromTable(int selectedRow) {
        txtBookName.setText((String) table1.getValueAt(selectedRow, 1));
        txtAuthor.setText((String) table1.getValueAt(selectedRow, 2));
        txtPublishedDate.setText((String) table1.getValueAt(selectedRow, 3));
        txtPublishedCompany.setText((String) table1.getValueAt(selectedRow, 4));
        txtLanguage.setText((String) table1.getValueAt(selectedRow, 5));
    }

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	book frame = new book();
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
