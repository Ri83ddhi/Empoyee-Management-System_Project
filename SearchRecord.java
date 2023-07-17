import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.*;

public class SearchRecord {

    private JFrame fr, pFrame;
    private JLabel closeIcon;
    private JLabel photoLabel;
    private static final String FONT = "Times New Roman";
    private Connection con;
    private JTextField[] professionalJTextFields;
    private JTextField[] personalJTextFields;
    private JTextField[] deptJTextFields;

    public SearchRecord(JFrame p) {

        pFrame = p;

        fr = new JFrame();
        fr.setSize(1728, 972);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        fr.setUndecorated(true);

        String id = JOptionPane.showInputDialog(fr, "Enter id to search", "Search Record", JOptionPane.QUESTION_MESSAGE);
        boolean found = checkId(id);
        if (!found) {
            JOptionPane.showMessageDialog(fr, "No Record for this id exist!", "ERROR", JOptionPane.ERROR_MESSAGE);
            pFrame.setEnabled(true);
            pFrame.requestFocus();
            fr.dispose();
        } else {
            JPanel panel = new JPanel();
            panel.setBounds(0, 0, 1728, 972);
            panel.setBackground(Color.BLACK);
            panel.setLayout(null);
            panel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.ORANGE));

            //titleL = new JLabel(new ImageIcon(new ImageIcon("images/login.jpg").getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT)));
            JLabel titleL = new JLabel("Search Record");
            titleL.setForeground(Color.ORANGE);
            titleL.setHorizontalAlignment(SwingConstants.CENTER);
            titleL.setFont(new Font(FONT, Font.BOLD, 50));
            titleL.setBounds((1728 - 700) / 2, 0, 700, 75);
            panel.add(titleL);

            closeIcon = new JLabel(new ImageIcon("images/close_small.png"));
            closeIcon.setBounds(1650, 5, 75, 75);
            closeIcon.addMouseListener(new MouseAdapter() {
                public void mouseEntered(MouseEvent me) {
                    closeIcon.setIcon(new ImageIcon("images/close_big.png"));
                }

                public void mouseExited(MouseEvent me) {
                    closeIcon.setIcon(new ImageIcon("images/close_small.png"));
                }

                public void mouseClicked(MouseEvent me) {
                    pFrame.setEnabled(true);
                    pFrame.requestFocus();
                    fr.dispose();
                }
            });
            panel.add(closeIcon);

            JPanel pLeft = new JPanel();
            pLeft.setBounds(0, 75, 866, 895);
            pLeft.setBackground(Color.BLACK);
            pLeft.setLayout(null);
            pLeft.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.ORANGE));
            panel.add(pLeft);

            JLabel personal = new JLabel("Personal Details");
            personal.setBounds((866 - 250) / 2, 20, 250, 35);
            personal.setForeground(Color.ORANGE);
            personal.setHorizontalAlignment(SwingConstants.CENTER);
            personal.setFont(new Font(FONT, Font.BOLD, 30));
            pLeft.add(personal);

            String[] personalLabelsString = {"ID", "Name", "Father's Name", "Mother's Name", "Date of Birth", "Gender", "Blood Group", "Mobile", "Email", "Address"};
            JLabel[] personalLabels = new JLabel[personalLabelsString.length];

            int x = 110;
            int y = 100;
            for (int i = 0; i < personalLabelsString.length; i++) {
                personalLabels[i] = new JLabel(personalLabelsString[i]);
                personalLabels[i].setBounds(x, y, 200, 30);
                personalLabels[i].setForeground(Color.ORANGE);
                personalLabels[i].setFont(new Font(FONT, Font.BOLD, 25));
                pLeft.add(personalLabels[i]);
                y += 75;
            }

            personalJTextFields = new JTextField[personalLabelsString.length];

            x = 400;
            y = 100;
            for (int i = 0; i < personalLabelsString.length; i++) {
                personalJTextFields[i] = new JTextField();
                personalJTextFields[i].setBounds(x, y, 300, 40);
                personalJTextFields[i].setForeground(Color.BLACK);
                personalJTextFields[i].setEditable(false);
                personalJTextFields[i].setFont(new Font(FONT, Font.BOLD, 30));
                pLeft.add(personalJTextFields[i]);
                y += 75;
            }

            JPanel pRightTop = new JPanel();
            pRightTop.setBounds(862, 75, 866, 448);
            pRightTop.setBackground(Color.BLACK);
            pRightTop.setLayout(null);
            pRightTop.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.ORANGE));
            panel.add(pRightTop);

            String[] professionalLabelsString = {"Aadhar", "PAN", "Qualification", "Experience"};
            JLabel[] professionalLabels = new JLabel[professionalLabelsString.length];

            x = 110;
            y = 100;
            for (int i = 0; i < professionalLabelsString.length; i++) {
                professionalLabels[i] = new JLabel(professionalLabelsString[i]);
                professionalLabels[i].setBounds(x, y, 200, 30);
                professionalLabels[i].setForeground(Color.ORANGE);
                professionalLabels[i].setFont(new Font(FONT, Font.BOLD, 25));
                pRightTop.add(professionalLabels[i]);
                y += 75;
            }

            personal = new JLabel("Professional Details");
            personal.setBounds((866 - 250) / 2, 20, 250, 35);
            personal.setForeground(Color.ORANGE);
            personal.setHorizontalAlignment(SwingConstants.CENTER);
            personal.setFont(new Font(FONT, Font.BOLD, 30));
            pRightTop.add(personal);

            professionalJTextFields = new JTextField[professionalLabelsString.length];
            x = 400;
            y = 100;
            for (int i = 0; i < professionalLabelsString.length; i++) {
                professionalJTextFields[i] = new JTextField();
                professionalJTextFields[i].setBounds(x, y, 300, 40);
                professionalJTextFields[i].setForeground(Color.BLACK);
                professionalJTextFields[i].setEditable(false);
                professionalJTextFields[i].setFont(new Font(FONT, Font.BOLD, 30));
                pRightTop.add(professionalJTextFields[i]);
                y += 75;
            }

            JPanel pRightBottom = new JPanel();
            pRightBottom.setBounds(862, 520, 866, 450);
            pRightBottom.setBackground(Color.BLACK);
            pRightBottom.setLayout(null);
            pRightBottom.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.ORANGE));
            panel.add(pRightBottom);

            personal = new JLabel("Dept. Details");
            personal.setBounds((866 - 250) / 2, 20, 250, 35);
            personal.setForeground(Color.ORANGE);
            personal.setHorizontalAlignment(SwingConstants.CENTER);
            personal.setFont(new Font(FONT, Font.BOLD, 30));
            pRightBottom.add(personal);

            photoLabel = new JLabel(new ImageIcon("images/placeholder.png"));
            photoLabel.setBounds((866 - 175) / 2, 60, 175, 170);
            photoLabel.setForeground(Color.ORANGE);
            photoLabel.setBorder(BorderFactory.createLineBorder(Color.ORANGE));
            photoLabel.setHorizontalAlignment(SwingConstants.CENTER);
            photoLabel.setFont(new Font(FONT, Font.BOLD, 30));
            pRightBottom.add(photoLabel);

            String[] deptLabelsString = {"Profile", "Package"};
            JLabel[] deptLabels = new JLabel[deptLabelsString.length];

            x = 110;
            y = 290;
            for (int i = 0; i < deptLabelsString.length; i++) {
                deptLabels[i] = new JLabel(deptLabelsString[i]);
                deptLabels[i].setBounds(x, y, 200, 30);
                deptLabels[i].setForeground(Color.ORANGE);
                deptLabels[i].setFont(new Font(FONT, Font.BOLD, 25));
                pRightBottom.add(deptLabels[i]);
                y += 75;
            }

            deptJTextFields = new JTextField[deptLabelsString.length];
            x = 400;
            y = 290;
            for (int i = 0; i < deptLabelsString.length; i++) {
                deptJTextFields[i] = new JTextField();
                deptJTextFields[i].setBounds(x, y, 300, 40);
                deptJTextFields[i].setForeground(Color.BLACK);
                deptJTextFields[i].setEditable(false);
                deptJTextFields[i].setFont(new Font(FONT, Font.BOLD, 30));
                pRightBottom.add(deptJTextFields[i]);
                y += 75;
            }

            fr.add(panel);
            fr.setVisible(true);
            loadResult(id);
        }
    }

    private boolean checkId(String id) {

        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "root");
            PreparedStatement ps = con.prepareStatement("select * from emp where id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return true;
            } else {
                return false;
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
            return false;
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private void loadResult(String id) {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "root");
            PreparedStatement ps = con.prepareStatement("select * from emp where id = ?");
            ps.setString(1, id);
            ResultSet rs = ps.executeQuery();
            rs.next();
            for (int i = 0; i < 10; i++) {
                personalJTextFields[i].setText(rs.getString(i + 1));
            }

            PreparedStatement ps1 = con.prepareStatement("select * from pro where id = ?");
            ps1.setString(1, id);
            ResultSet rs1 = ps1.executeQuery();
            rs1.next();
            for (int i = 0; i < 6; i++) {
                if (i < 4)
                    professionalJTextFields[i].setText(rs1.getString(i + 2));
                else
                    deptJTextFields[i - 4].setText(rs1.getString(i + 2));

            }

            String path = "emp_pic/" + id + ".jpg";
            Image original = Toolkit.getDefaultToolkit().getImage(path);
            Image scaled = original.getScaledInstance(150, 175, Image.SCALE_DEFAULT);
            photoLabel.setIcon(new ImageIcon(scaled));

        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (con != null) {
                try {
                    con.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
