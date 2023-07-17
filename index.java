package EMS;
import javax.swing.*;

import MainMenu;
import ResetPassword;
import SignUp;

import java.awt.*;
import java.awt.event.*;
import java.sql.*;
import java.util.Arrays;

class index {
    private JFrame fr;
    private JLabel closeIcon;
    private JLabel forgotPassL;
    private JLabel newUserL;
    private JTextField usernameTF;
    private JPasswordField passPF;
    String data;

    public index() {
        fr = new JFrame("Login");
        fr.setSize(700, 700);
        fr.setLocationRelativeTo(null);
        fr.setContentPane(new JLabel(new ImageIcon("images/login_bg.jpg")));
        fr.setLayout(null);
        fr.setUndecorated(true);

        closeIcon = new JLabel(new ImageIcon("images/close_small.png"));
        closeIcon.setBounds(600, 30, 75, 75);
        fr.add(closeIcon);

        closeIcon.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                closeIcon.setIcon(new ImageIcon("images/close_big.png"));
            }

            public void mouseExited(MouseEvent me) {
                closeIcon.setIcon(new ImageIcon("images/close_small.png"));
            }

            public void mouseClicked(MouseEvent me) {
                fr.dispose();
            }
        });

        JPanel panel = new JPanel();
        panel.setBounds((700 - 400) / 2, (700 - 600) / 2, 400, 600);
        panel.setLayout(null);
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.RED));
        fr.add(panel);

        JLabel titleL = new JLabel(new ImageIcon(new ImageIcon("images/login.jpg").getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT)));
        titleL.setBounds((400 - 200) / 2, 50, 200, 50);
        panel.add(titleL);

        usernameTF = new JTextField("Enter Username");
        usernameTF.setBounds((400 - 250) / 2, 200, 250, 50);
        usernameTF.setFont(new Font("Times New Roman", Font.BOLD, 25));
        usernameTF.setHorizontalAlignment(JTextField.CENTER);
        usernameTF.setBorder(BorderFactory.createEmptyBorder(0, 45, 0, 0));
        panel.add(usernameTF);

        usernameTF.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent fe) {
                String data = usernameTF.getText().toString().trim();
                if (data.equalsIgnoreCase("Enter Username")) {
                    usernameTF.setText("");
                }
            }

            public void focusLost(FocusEvent fe) {
                String data = usernameTF.getText().toString().trim();
                if (data.equalsIgnoreCase("") || data.isEmpty()) {
                    usernameTF.setText("Enter Username");
                }
            }
        });

        JLabel userIcon = new JLabel(new ImageIcon(new ImageIcon("images/user.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        userIcon.setBounds(5, 5, 40, 40);
        usernameTF.add(userIcon);

        passPF = new JPasswordField("Enter Password");
        passPF.setBounds((400 - 250) / 2, 300, 250, 50);
        passPF.setEchoChar('*');
        passPF.setHorizontalAlignment(JTextField.CENTER);
        passPF.setFont(new Font("Times New Roman", Font.BOLD, 25));
        passPF.setBorder(BorderFactory.createEmptyBorder(0, 45, 0, 0));
        panel.add(passPF);

        passPF.addFocusListener(new FocusListener() {
            public void focusGained(FocusEvent fe) {
                char[] data = passPF.getPassword();
                char[] enter = new char[]{'E', 'n', 't', 'e', 'r', ' ', 'P', 'a', 's', 's', 'w', 'o', 'r', 'd'};
                if (Arrays.equals(data, enter)) {
                    passPF.setText("");
                }
            }

            public void focusLost(FocusEvent fe) {
                char[] data = passPF.getPassword();
                if (data.length == 0) {
                    passPF.setText("Enter Password");
                }
            }
        });

        JLabel keyIcon = new JLabel(new ImageIcon(new ImageIcon("images/key.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT)));
        keyIcon.setBounds(5, 5, 40, 40);
        passPF.add(keyIcon);

        JButton loginB = new JButton("Login");
        loginB.setBounds((400 - 250) / 2, 400, 250, 50);
        loginB.setFont(new Font("Times New Roman", Font.BOLD, 25));
        loginB.setFocusPainted(false);
        fr.getRootPane().setDefaultButton(loginB);
        panel.add(loginB);

        loginB.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent ae) {
                data = usernameTF.getText().trim();
                char[] passD = passPF.getPassword();
                char[] enter = new char[]{'E', 'n', 't', 'e', 'r', ' ', 'P', 'a', 's', 's', 'w', 'o', 'r', 'd'};

                if (data.isEmpty() || data.equalsIgnoreCase("Enter Username")) {
                    JOptionPane.showMessageDialog(fr, "ID cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else if (passD.length == 0 || Arrays.equals(passD, enter)) {
                    JOptionPane.showMessageDialog(fr, "Password cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    try {
                        Class.forName("com.mysql.jdbc.Driver");
                        Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "root");
                        PreparedStatement ps = con.prepareStatement("select * from admin where id = ?");
                        ps.setString(1, data);
                        ResultSet rs = ps.executeQuery();
                        if (rs.next()) {
                            if (rs.getString(5).equals(new String(passD))) {
                                //JOptionPane.showMessageDialog(fr, "Success");
                                new MainMenu(data);
                                fr.dispose();
                            } else {
                                JOptionPane.showMessageDialog(fr, "Incorrect Password", "Error", JOptionPane.ERROR_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(fr, "ID does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                        }

                    } catch (ClassNotFoundException | SQLException e) {
                        e.printStackTrace();
                    }
                }
            }
        });

        forgotPassL = new JLabel("Forgot Password?");
        forgotPassL.setBounds(25, 550, 175, 25);
        forgotPassL.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(forgotPassL);

        forgotPassL.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                forgotPassL.setForeground(Color.GREEN);
                forgotPassL.setFont(new Font("Times New Roman", Font.BOLD, 20));
            }

            public void mouseExited(MouseEvent me) {
                forgotPassL.setForeground(Color.BLACK);
                forgotPassL.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            }

            public void mouseClicked(MouseEvent me) {
                new ResetPassword(fr);
                fr.setEnabled(false);
            }
        });

        newUserL = new JLabel("New User? Sign Up!");
        newUserL.setBounds(200, 550, 175, 25);
        newUserL.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(newUserL);

        newUserL.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                newUserL.setForeground(Color.GREEN);
                newUserL.setFont(new Font("Times New Roman", Font.BOLD, 20));
            }

            public void mouseExited(MouseEvent me) {
                newUserL.setForeground(Color.BLACK);
                newUserL.setFont(new Font("Times New Roman", Font.PLAIN, 20));
            }

            public void mouseClicked(MouseEvent me) {
                //fr.dispose();
                fr.setEnabled(false);
                new SignUp(fr);
            }
        });

        fr.setVisible(true);
    }
}
