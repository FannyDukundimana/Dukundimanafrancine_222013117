package library_management;

import javax.swing.*;
import javax.swing.border.Border;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class user_libary {

    public JFrame frame;
    private JButton btnProfile;
    private JButton btnViewProposal;
    private JButton btnLogout;
    private JButton Distributors;
    private JButton Sales;
    private JButton Products;
    private JButton Orders;
    private JButton Users;
    private JButton btnFeedback;

    private JPanel tablePanel;
    private JPanel formPanel;
    private JLabel lblWelcome;

    private JTable table1;
    private JTable table12;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                	user_libary window = new user_libary();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public user_libary() {
        initialize();
        connection();
        table1 = new JTable();
    }

    Connection con;
    PreparedStatement pst;

    void connection() {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost/skolbmp_db", "root", "");
            System.out.print("Connected well");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    private void initialize() {
        frame = new JFrame();
        frame.setBounds(100, 100, 1050, 733);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JPanel topBarPanel = new JPanel();
        topBarPanel.setBackground(new Color(255, 155, 0)); // 
        topBarPanel.setBounds(0, 0, 1050, 50);
        frame.getContentPane().add(topBarPanel);
        topBarPanel.setLayout(null);

        lblWelcome = new JLabel("LIBARY MANAGEMENT SYSTEM");
        lblWelcome.setForeground(Color.RED); //  
        lblWelcome.setHorizontalAlignment(SwingConstants.CENTER);
        lblWelcome.setFont(new Font("Verdana", Font.BOLD, 16));
        lblWelcome.setBounds(0, 0, 1050, 50);
        topBarPanel.add(lblWelcome);

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setBackground(new Color(255, 102, 0)); //  
        sidebarPanel.setBounds(0, 60, 1050, 50); //  
        frame.getContentPane().add(sidebarPanel);
        sidebarPanel.setLayout(new GridLayout(1, 0));

        JLabel lblHomepage = new JLabel("");
        lblHomepage.setForeground(Color.WHITE);
        lblHomepage.setHorizontalAlignment(SwingConstants.CENTER);
        lblHomepage.setFont(new Font("Verdana", Font.BOLD, 19));
        sidebarPanel.add(lblHomepage);

        
 
        Users = new JButton("Books");
        Users.setFont(new Font("Verdana", Font.BOLD, 14));
        Users.setOpaque(true);
        Users.setBackground(new Color(153, 102, 0)); // Dark yellow
        Users.setBorderPainted(false);
        Users.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                book usersPage = new book();
                usersPage.frame.setVisible(true);
                frame.dispose(); // Close the current window
            }
        });
        sidebarPanel.add(Users);

 
         JPanel spacePanel = new JPanel();
        spacePanel.setOpaque(false);
        sidebarPanel.add(spacePanel);

         btnLogout = new JButton("Logout");
        btnLogout.setFont(new Font("Verdana", Font.BOLD, 14));
        btnLogout.setOpaque(true);
        btnLogout.setBackground(Color.RED);
        btnLogout.setBorderPainted(false);
        btnLogout.setForeground(Color.WHITE);
        btnLogout.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                LogIn usersPage = new LogIn();
                usersPage.frame.setVisible(true);
                frame.dispose(); // Close the current window
            }
        });
        sidebarPanel.add(btnLogout);

         showWelcomeMessage("Username");
    }

  

    private void showWelcomeMessage(String username) {
        lblWelcome.setText("LIBARY MANAGEMENT SYSTEM");
    }
}