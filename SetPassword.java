import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class SetPassword implements KeyListener, ActionListener {

    private JFrame fr, pFrame;
    private JLabel strengthL;
    private JLabel checkL;
    private JPasswordField newPassPF, confirmPassPF;
    private JButton saveB, cancelB;
    private String userID;
    private Connection con;

    public SetPassword(JFrame pf, String id) {
        pFrame = pf;
        userID = id;
        fr = new JFrame();
        fr.setSize(500, 500);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        fr.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 500, 500);
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.YELLOW));
        panel.setLayout(null);

        JLabel titleL = new JLabel("Set Password");
        titleL.setBounds((500 - 250) / 2, 50, 250, 40);
        titleL.setForeground(Color.ORANGE);
        titleL.setHorizontalAlignment(SwingConstants.CENTER);
        titleL.setFont(new Font("Times New Roman", Font.BOLD, 30));
        panel.add(titleL);

        JLabel newL = new JLabel("New Password");
        newL.setBounds((500 - 300) / 2, 130, 150, 20);
        newL.setFont(new Font("Times New Roman", Font.BOLD, 21));
        newL.setForeground(Color.ORANGE);
        panel.add(newL);

        newPassPF = new JPasswordField();
        newPassPF.setBounds((500 - 300) / 2, 150, 300, 30);
        newPassPF.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        newPassPF.addKeyListener(this);
        panel.add(newPassPF);

        JLabel confirmL = new JLabel("Confirm Password");
        confirmL.setBounds((500 - 300) / 2, 240, 170, 20);
        confirmL.setFont(new Font("Times New Roman", Font.BOLD, 21));
        confirmL.setForeground(Color.ORANGE);
        panel.add(confirmL);

        confirmPassPF = new JPasswordField();
        confirmPassPF.setBounds((500 - 300) / 2, 260, 300, 30);
        confirmPassPF.setFont(new Font("Times New Roman", Font.PLAIN, 30));
        confirmPassPF.addKeyListener(this);
        panel.add(confirmPassPF);

        strengthL = new JLabel();
        strengthL.setBounds(300, 180, 95, 30);
        strengthL.setHorizontalAlignment(JTextField.CENTER);
        strengthL.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        strengthL.setForeground(Color.ORANGE);
        panel.add(strengthL);

        checkL = new JLabel();
        checkL.setBounds(315, 290, 90, 30);
        checkL.setHorizontalAlignment(JTextField.CENTER);
        checkL.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        checkL.setForeground(Color.ORANGE);
        panel.add(checkL);

        saveB = new JButton("Update");
        saveB.setBounds(100, 370, 140, 40);
        saveB.setFont(new Font("Times New Roman", Font.BOLD, 30));
        saveB.addActionListener(this);
        saveB.setFocusPainted(false);
        saveB.setEnabled(false);
        panel.add(saveB);

        cancelB = new JButton("Cancel");
        cancelB.setBounds(260, 370, 140, 40);
        cancelB.setFont(new Font("Times New Roman", Font.BOLD, 30));
        cancelB.addActionListener(this);
        cancelB.setFocusPainted(false);
        panel.add(cancelB);

        JLabel movingLabel = new JLabel("Student Management System");
        movingLabel.setForeground(Color.ORANGE);
        movingLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(movingLabel);

        fr.add(panel);
        fr.setVisible(true);
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {

    }

    public void keyReleased(KeyEvent ke) {
        char[] pass = newPassPF.getPassword();
        int passwordScore = 0;

        if (pass.length >= 10)
            passwordScore += 2;
        else
            passwordScore += 1;

        if (new String(pass).matches("(?=.*[0-9]).*"))
            passwordScore += 2;

        if (new String(pass).matches("(?=.*[a-z]).*"))
            passwordScore += 2;

        if (new String(pass).matches("(?=.*[A-Z]).*"))
            passwordScore += 2;

        if (new String(pass).matches("(?=.*[~!@#$%^&*()_-]).*"))
            passwordScore += 2;

        if (passwordScore <= 2) {
            strengthL.setText("Very Weak");
            strengthL.setForeground(Color.RED);
        } else if (passwordScore <= 4) {
            strengthL.setForeground(Color.ORANGE);
            strengthL.setText("Weak");
        } else if (passwordScore <= 6) {
            strengthL.setForeground(Color.ORANGE);
            strengthL.setText("Medium");
        } else if (passwordScore <= 8) {
            strengthL.setForeground(Color.GREEN);
            strengthL.setText("Strong");
        } else {
            strengthL.setForeground(Color.GREEN);
            strengthL.setText("Very Strong");
        }
        char[] cPass = confirmPassPF.getPassword();
        if (Arrays.equals(pass, cPass)) {
            checkL.setText("Same");
            checkL.setForeground(Color.GREEN);
            saveB.setEnabled(true);
        } else {
            checkL.setText("Not Same");
            checkL.setForeground(Color.RED);
            saveB.setEnabled(false);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == saveB) {
            char pass[] = newPassPF.getPassword();

            try {
                Class.forName("com.mysql.jdbc.Driver");
                con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "root");
                PreparedStatement ps = con.prepareStatement("update admin set pass = ? where id in (?)");
                ps.setString(1, new String(pass));
                ps.setString(2, userID);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    JOptionPane.showMessageDialog(fr, "Password Updated");
                    pFrame.setEnabled(true);
                    fr.dispose();
                } else {
                    JOptionPane.showMessageDialog(fr, "Password not updated", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (ClassNotFoundException ex) {
                ex.printStackTrace();
            } catch (SQLException ex) {
                ex.printStackTrace();
            } finally {
                if (con != null) {
                    try {
                        con.close();
                    } catch (SQLException ex) {
                        ex.printStackTrace();
                    }
                }
            }


        } else if (e.getSource() == cancelB) {
            pFrame.setEnabled(true);
            fr.dispose();
        }
    }
}
