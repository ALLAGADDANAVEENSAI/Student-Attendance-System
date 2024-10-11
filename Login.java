/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Attendance;

/**
 *
 * @author Naveen
 */

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import javax.swing.border.Border;

public class Login {
    
    int usr = 0;
    Image bgImage;
    
    public Login() {
        bgImage = new ImageIcon("D:\\\\SATHYABAMA\\\\Oracle\\\\AttendanceManagement\\\\src\\\\Attendance\\\\imagelogin.jpg").getImage(); // Load your background image
    }
    
    public void loginView() {
        JFrame frame = new JFrame();
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(bgImage, 0, 0, getWidth(), getHeight(), this); // Draw background image
            }
        };
        backgroundPanel.setLayout(null); // Set layout to null for absolute positioning

        Font text = new Font("Times New Roman", Font.PLAIN, 20);
        Home hm = new Home();
        TeacherView tview = new TeacherView();
        StudentView sview = new StudentView();
        
        //-------------------------LOGO--------------------------
//        JLabel attendance = new JLabel("ATTENDANCE");
//        attendance.setForeground(Color.decode("#37474F"));
//        attendance.setBounds(100, 275, 400, 50);
//        attendance.setFont(new Font("Verdana", Font.BOLD, 50));
//        backgroundPanel.add(attendance);
//        
//        JLabel management = new JLabel("MANAGEMENT SYSTEM");
//        management.setForeground(Color.decode("#37474F"));
//        management.setBounds(280, 310, 400, 50);
//        management.setFont(new Font("Verdana", Font.BOLD, 15));
//        backgroundPanel.add(management);
        //-------------------------------------------------------
        
        //--------------------------LOGINTEXT---------------------------
        JLabel lgn = new JLabel("LOGIN");
        lgn.setForeground(Color.decode("#2f4f4f"));
        lgn.setBounds(625, 100, 350, 75);
        lgn.setFont(new Font("Times New Roman", Font.BOLD, 75));
        backgroundPanel.add(lgn);
        //---------------------------------------------------------------
        
        //-------------------------USER--------------------------
        JLabel user = new JLabel("Username");
        user.setForeground(Color.decode("#f8f8ff"));
        user.setBounds(570, 240, 100, 20);
        user.setFont(text);
        backgroundPanel.add(user);
        //-------------------------------------------------------
        
        //-----------------------USERFIELD-----------------------
        JTextField username = new JTextField();
        username.setBounds(570, 270, 360, 35);
        username.setBackground(new Color(255, 255, 255, 200)); // Opaque white
        username.setForeground(Color.decode("#37474F"));
        username.setFont(new Font("Times New Roman", Font.BOLD, 15));
        username.setBorder(BorderFactory.createRoundedBorder(10)); // Rounded border
        backgroundPanel.add(username);
        //---------------------------------------------------------
        
        //-------------------------Password--------------------------
        JLabel pass = new JLabel("Password");
        pass.setForeground(Color.decode("#f8f8ff"));
        pass.setBounds(570, 350, 100, 20);
        pass.setFont(text);
        backgroundPanel.add(pass);
        //-------------------------------------------------------
        
        //-----------------------PASSWORDFIELD-----------------------
        JPasswordField password = new JPasswordField();
        password.setBounds(570, 385, 360, 35);
        password.setBackground(new Color(255, 255, 255, 200)); // Opaque white
        password.setForeground(Color.decode("#37474F"));
        password.setBorder(BorderFactory.createRoundedBorder(10)); // Rounded border
        backgroundPanel.add(password);
        //---------------------------------------------------------
        
        //-------------------------WARNING--------------------------
        JLabel warning = new JLabel();
        warning.setForeground(Color.RED);
        warning.setBounds(625, 425, 250, 20);
        warning.setHorizontalAlignment(warning.CENTER);
        backgroundPanel.add(warning);
        //-------------------------------------------------------
        
        //----------------------LOGIN----------------------------
        JButton login = new JButton("LOGIN");
        login.setBounds(625, 450, 250, 50);
        login.setFont(new Font("Times New Roman", Font.BOLD, 20));
        login.setBackground(Color.decode("#DEE4E7"));
        login.setForeground(Color.decode("#37474F"));
        backgroundPanel.add(login);
        
        //----------------------Action Listener for Login Button----------------
        login.addActionListener(new ActionListener() {
            @SuppressWarnings("deprecation")
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int res = dbCheck(username.getText(), password.getText());
                    if(res == 0) {
                        warning.setText("NO USER FOUND!!!");
                        username.setText("");
                        password.setText("");
                    }
                    else if(res == -1) {
                        warning.setText("WRONG PASSWORD!!!");
                        username.setText("");
                        password.setText("");
                    }
                    else {
                        if(res == 1)
                            hm.homeView(usr);
                        else if(res == 2)
                            tview.tcView(usr);
                        else if (res == 3)
                            sview.stView(usr);
                        frame.dispose();
                    }
                } catch (SQLException e1) {
                    e1.printStackTrace();
                }
            }
        });
        //--------------------------------------------------------------------
        
        //-----------------------Adding backgroundPanel to frame---------------------
        frame.setContentPane(backgroundPanel);
        frame.setSize(1000,600);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        //------------------------------------------------------------------------
    }
    
    //---------------------Method for Database Check-----------------------
    public int dbCheck(String name, String password) throws SQLException {
        //ENTER PORT, USER, PASSWORD.
        String url = "jdbc:mysql://localhost:3306/attendance";
        String user = "root";
        String pass = "password";
        String str = "SELECT * FROM user WHERE username = '" + name + "'";
        Connection con = DriverManager.getConnection(url, user, pass);
        Statement stm = con.createStatement();
        ResultSet rst = stm.executeQuery(str);
        if(rst.next()) {
            if(rst.getString("password").equals(password)) {
                usr = rst.getInt("id");
                return rst.getInt("prio");
            }
            else
                return -1;
        }
        else {
            return 0;
        }
    }
    //---------------------------------------------------------------------
    
    //------------------------Main Method-------------------------
    public static void main(String[] args) {
        Login login = new Login();
        login.loginView();
    }
    //--------------------------------------------------------------
}

// Class to create rounded borders for components
class RoundedBorder implements Border {
    private int radius;

    public RoundedBorder(int radius) {
        this.radius = radius;
    }

    public int getRadius() {
        return radius;
    }

    public void setRadius(int radius) {
        this.radius = radius;
    }

    @Override
    public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
        Graphics2D graphics = (Graphics2D) g;
        graphics.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
    }

    @Override
    public Insets getBorderInsets(Component c) {
        return new Insets(this.radius + 1, this.radius + 1, this.radius + 2, this.radius);
    }

    @Override
    public boolean isBorderOpaque() {
        return true;
    }
}

// Factory class to create rounded borders for components
class BorderFactory {
    public static Border createRoundedBorder(int radius) {
        return new RoundedBorder(radius);
    }
}
