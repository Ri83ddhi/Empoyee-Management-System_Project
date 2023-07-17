import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.*;

public class ResetPassword implements FocusListener, ActionListener {

    private JFrame fr, pFrame;
    private JTextField[] textField;
    private JButton buttonSearch, buttonCancel;
    private Connection con;

    public ResetPassword(JFrame pf) {
        pFrame = pf;
        fr = new JFrame();
        fr.setSize(500, 500);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        fr.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 500, 500);
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.BLACK));
        panel.setLayout(null);

        JLabel titleL = new JLabel("Reset Password");
        titleL.setBounds((500 - 250) / 2, 50, 250, 40);
        titleL.setForeground(Color.ORANGE);
        titleL.setHorizontalAlignment(SwingConstants.CENTER);
        titleL.setFont(new Font("Times New Roman", Font.BOLD, 30));
        panel.add(titleL);

        JLabel[] label = new JLabel[3];

        int y = 150;
        for (int i = 0; i < 3; i++) {
            String[] labelList = {"ID", "Phone", "Email"};
            label[i] = new JLabel(labelList[i]);
            label[i].setBounds(75, y, 100, 30);
            label[i].setFont(new Font("Times New Roman", Font.BOLD, 25));
            label[i].setForeground(Color.ORANGE);
            panel.add(label[i]);
            y = y + 50;
        }

        y = 150;
        textField = new JTextField[3];

        for (int i = 0; i < 3; i++) {
            textField[i] = new JTextField();
            textField[i].setBounds(200, y, 250, 30);
            textField[i].setFont(new Font("Times New Roman", Font.BOLD, 25));
            textField[i].setForeground(Color.BLACK);
            textField[i].addFocusListener(this);
            panel.add(textField[i]);
            y = y + 50;
        }

        buttonSearch = new JButton("Search");
        buttonSearch.setBounds(75, 350, 150, 40);
        buttonSearch.setFont(new Font("Times New Roman", Font.BOLD, 25));
        buttonSearch.setForeground(Color.BLACK);
        buttonSearch.addActionListener(this);
        panel.add(buttonSearch);

        buttonCancel = new JButton("Cancel");
        buttonCancel.setBounds(300, 350, 150, 40);
        buttonCancel.setFont(new Font("Times New Roman", Font.BOLD, 25));
        buttonCancel.setForeground(Color.BLACK);
        buttonCancel.addActionListener(this);
        panel.add(buttonCancel);

        fr.add(panel);
        fr.setVisible(true);
    }

    @Override
    public void focusGained(FocusEvent e) {

        JTextField tf = (JTextField) e.getSource();
        tf.setBorder(BorderFactory.createMatteBorder(3, 3, 3, 3, Color.ORANGE));

    }

    @Override
    public void focusLost(FocusEvent e) {

        JTextField tf = (JTextField) e.getSource();
        tf.setBorder(BorderFactory.createMatteBorder(1, 1, 1, 1, Color.BLACK));

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonSearch) {
            String id = textField[0].getText().trim();
            String phone = textField[1].getText().trim();
            String email = textField[2].getText().trim();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(fr, "ID cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (phone.length() != 10) {
                JOptionPane.showMessageDialog(fr, "Phone cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (email.isEmpty()) {
                JOptionPane.showMessageDialog(fr, "Email cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "root");
                    PreparedStatement ps = con.prepareStatement("select * from admin where id = ?");
                    ps.setString(1, id);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        if (rs.getString(3).equals(phone) && rs.getString(4).equals(email)) {
                            new SetPassword(fr, id);
                            fr.setEnabled(false);
                        } else {
                            JOptionPane.showMessageDialog(fr, "Incorrect Password", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    } else {
                        JOptionPane.showMessageDialog(fr, "ID does not exist", "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (ClassNotFoundException | SQLException ex) {
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
            }

        } else if (e.getSource() == buttonCancel) {
            pFrame.setEnabled(true);
            fr.dispose();
        }

    }
}
