import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Arrays;

public class SignUp implements Runnable, ActionListener {

    private JFrame fr, pFrame;
    private JTextField[] textField;
    private JPasswordField passPF, cPassPF;
    private JLabel movingLabel;
    private boolean flag = false;
    private Connection con;
    private PreparedStatement ps;
    private JButton buttonSave, buttonCancel;

    public SignUp(JFrame f) {

        pFrame = f;

        fr = new JFrame();
        fr.setSize(750, 750);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        fr.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 750, 750);
        panel.setBackground(Color.BLACK);
        panel.setBorder(BorderFactory.createEtchedBorder(Color.RED, Color.RED));
        panel.setLayout(null);

        JLabel titleL = new JLabel("Sign Up");
        titleL.setBounds((750 - 250) / 2, 50, 250, 50);
        titleL.setHorizontalAlignment(JTextField.CENTER);
        titleL.setForeground(Color.RED);
        titleL.setFont(new Font("Times New Roman", Font.BOLD, 30));
        panel.add(titleL);

        String[] labelList = {"ID", "Name", "Phone", "Email", "Password", "Confirm"};
        JLabel[] label = new JLabel[labelList.length];

        int y = 150;
        for (int i = 0; i < labelList.length; i++) {
            label[i] = new JLabel(labelList[i]);
            label[i].setForeground(Color.RED);
            label[i].setFont(new Font("Times New Roman", Font.PLAIN, 25));
            label[i].setBounds(130, y, 120, 30);
            panel.add(label[i]);
            y = y + 75;
        }

        textField = new JTextField[4];
        y = 150;
        for (int i = 0; i < 4; i++) {
            textField[i] = new JTextField();
            textField[i].setBounds(300, y, 250, 30);
            textField[i].setFont(new Font("Times New Roman", Font.BOLD, 25));
            panel.add(textField[i]);
            textField[i].addFocusListener(new FocusListener() {
                @Override
                public void focusGained(FocusEvent e) {
                    JTextField tf = (JTextField) e.getSource();
                    tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GREEN));
                }

                @Override
                public void focusLost(FocusEvent e) {
                    JTextField tf = (JTextField) e.getSource();
                    tf.setBorder(BorderFactory.createLineBorder(Color.BLACK));
                }
            });
            y = y + 75;
        }

        passPF = new JPasswordField();
        passPF.setBounds(300, y, 250, 30);
        passPF.setFont(new Font("Times New Roman", Font.BOLD, 25));
        panel.add(passPF);
        passPF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                passPF.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GREEN));
            }

            @Override
            public void focusLost(FocusEvent e) {
                passPF.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        y = y + 75;

        cPassPF = new JPasswordField();
        cPassPF.setBounds(300, y, 250, 30);
        cPassPF.setFont(new Font("Times New Roman", Font.BOLD, 25));
        panel.add(cPassPF);
        cPassPF.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent e) {
                cPassPF.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 2, Color.GREEN));
            }

            @Override
            public void focusLost(FocusEvent e) {
                cPassPF.setBorder(BorderFactory.createLineBorder(Color.BLACK));
            }
        });
        y = y + 75;

        buttonSave = new JButton("Save");
        buttonSave.setBounds(130, y, 150, 40);
        buttonSave.setFont(new Font("Times New Roman", Font.BOLD, 30));
        buttonSave.addActionListener(this);
        panel.add(buttonSave);

        buttonCancel = new JButton("Cancel");
        buttonCancel.setBounds(400, y, 150, 40);
        buttonCancel.setFont(new Font("Times New Roman", Font.BOLD, 30));
        buttonCancel.addActionListener(this);
        panel.add(buttonCancel);

        movingLabel = new JLabel("Student Management System");
        movingLabel.setForeground(Color.RED);
        movingLabel.setFont(new Font("Times New Roman", Font.PLAIN, 20));
        panel.add(movingLabel);

        fr.add(panel);
        fr.setVisible(true);

        Thread th = new Thread(this);
        flag = true;
        th.start();
    }

    @Override
    public void run() {

        int x = 750;

        while (flag) {
            try {
                Thread.sleep(5);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            if (x == -200)
                x = 750;
            movingLabel.setBounds(x, 700, 250, 30);
            x--;
        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {

        if (e.getSource() == buttonSave) {
            String id = textField[0].getText().trim();
            String name = textField[1].getText().trim();
            String phone = textField[2].getText().trim();
            String email = textField[3].getText().trim();
            char[] pass = passPF.getPassword();
            char[] cPass = cPassPF.getPassword();

            if (id.isEmpty()) {
                JOptionPane.showMessageDialog(fr, "ID cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (name.isEmpty()) {
                JOptionPane.showMessageDialog(fr, "Name cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (phone.isEmpty()) {
                JOptionPane.showMessageDialog(fr, "Phone cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (email.isEmpty()) {
                JOptionPane.showMessageDialog(fr, "Email cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (pass.length == 0) {
                JOptionPane.showMessageDialog(fr, "Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (cPass.length == 0) {
                JOptionPane.showMessageDialog(fr, "Confirm Password cannot be empty", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!Arrays.equals(pass, cPass)) {
                JOptionPane.showMessageDialog(fr, "Password and Confirm Password does not match", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                try {
                    Class.forName("com.mysql.jdbc.Driver");
                    con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "root");
                    PreparedStatement ps = con.prepareStatement("insert into admin values(?,?,?,?,?)");
                    ps.setString(1, id);
                    ps.setString(2, name);
                    ps.setString(3, phone);
                    ps.setString(4, email);
                    ps.setString(5, new String(pass));
                    int rs = ps.executeUpdate();
                    if (rs > 0) {
                        JOptionPane.showMessageDialog(fr, "Admin Created");
                    } else {
                        JOptionPane.showMessageDialog(fr, "Admin not created", "Error", JOptionPane.ERROR_MESSAGE);
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
            }
        } else if (e.getSource() == buttonCancel) {
            flag = false;
            pFrame.setEnabled(true);
            fr.dispose();
        }

    }
}
