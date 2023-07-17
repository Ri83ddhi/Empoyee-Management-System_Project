import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.sql.*;

public class AddRecord implements FocusListener, ActionListener {

    private JFrame fr, pFrame;
    private JLabel closeIcon;
    private JLabel saveIcon;
    private JLabel photoLabel;
    private JTextField empNameTF, fatherNameTF, motherNameTF, phoneTF, emailTF, aadharTF, panTF, packageTF;
    private JTextArea addressTA;
    private JComboBox<Integer> yearOB, dayOB;
    private JComboBox<String> monthOB, genderCB, bloodGroupCB, qualifOE, expOE, profileOE;
    private static final int HEIGHT = 40;
    private static final int TF_FONT_SIZE = 25;
    private static final int TF_WIDTH = 320;
    private static final String FONT = "Times New Roman";
    private String path = "nullnull";
    private Image original, scaled;

    public AddRecord(JFrame f) {

        pFrame = f;

        fr = new JFrame();
        fr.setSize(1728, 972);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        fr.setUndecorated(true);

        JPanel panel = new JPanel();
        panel.setBounds(0, 0, 1728, 972);
        panel.setBackground(Color.BLACK);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.ORANGE));

        //titleL = new JLabel(new ImageIcon(new ImageIcon("images/login.jpg").getImage().getScaledInstance(200, 50, Image.SCALE_DEFAULT)));
        JLabel titleL = new JLabel("Add Record");
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

        saveIcon = new JLabel(new ImageIcon("images/save_small.png"));
        saveIcon.setBounds(10, 5, 75, 75);
        saveIcon.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent me) {
                saveIcon.setIcon(new ImageIcon("images/save_big.png"));
            }

            public void mouseExited(MouseEvent me) {
                saveIcon.setIcon(new ImageIcon("images/save_small.png"));
            }

            public void mouseClicked(MouseEvent me) {
                saveRecord();
            }
        });
        panel.add(saveIcon);

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

        String[] personalLabelsString = {"Name", "Father's Name", "Mother's Name", "Date of Birth", "Gender", "Blood Group", "Mobile", "Email", "Address"};
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

        x = 400;
        y = 100;
        empNameTF = new JTextField();
        empNameTF.setBounds(x, y, TF_WIDTH, HEIGHT);
        empNameTF.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        empNameTF.addFocusListener(this);
        empNameTF.setHorizontalAlignment(SwingConstants.CENTER);
        pLeft.add(empNameTF);
        y += 75;

        fatherNameTF = new JTextField();
        fatherNameTF.setBounds(x, y, TF_WIDTH, HEIGHT);
        fatherNameTF.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        fatherNameTF.addFocusListener(this);
        fatherNameTF.setHorizontalAlignment(SwingConstants.CENTER);
        pLeft.add(fatherNameTF);
        y += 75;

        motherNameTF = new JTextField();
        motherNameTF.setBounds(x, y, TF_WIDTH, HEIGHT);
        motherNameTF.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        motherNameTF.addFocusListener(this);
        motherNameTF.setHorizontalAlignment(SwingConstants.CENTER);
        pLeft.add(motherNameTF);
        y += 75;

        yearOB = new JComboBox<>();
        yearOB.setBounds(x, y, 80, HEIGHT);
        yearOB.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        pLeft.add(yearOB);
        for (int i = 1980; i <= 2019; i++) {
            yearOB.addItem(i);
        }

        String[] monthList = {"January", "February", "March", "April", "May", "June", "July", "August", "September", "October", "November", "December"};
        monthOB = new JComboBox<>(monthList);
        monthOB.setBounds(495, y, 150, HEIGHT);
        monthOB.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        monthOB.addActionListener(this);
        pLeft.add(monthOB);

        dayOB = new JComboBox<>();
        dayOB.setBounds(655, y, 65, HEIGHT);
        dayOB.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        pLeft.add(dayOB);
        y += 75;

        String[] genderCBList = {"Select", "Male", "Female", "Other"};
        genderCB = new JComboBox<>(genderCBList);
        genderCB.setBounds(x, y, TF_WIDTH, HEIGHT);
        genderCB.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        pLeft.add(genderCB);
        y += 75;

        String[] bloodGroupCBList = {"Select", "A+", "A-", "B+", "B-", "O+", "O-", "AB+", "AB-"};
        bloodGroupCB = new JComboBox<>(bloodGroupCBList);
        bloodGroupCB.setBounds(x, y, TF_WIDTH, HEIGHT);
        bloodGroupCB.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        pLeft.add(bloodGroupCB);
        y += 75;

        phoneTF = new JTextField();
        phoneTF.setBounds(x, y, TF_WIDTH, HEIGHT);
        phoneTF.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        phoneTF.addFocusListener(this);
        phoneTF.setHorizontalAlignment(SwingConstants.CENTER);
        pLeft.add(phoneTF);
        phoneTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyTyped(KeyEvent ke) {
                if (phoneTF.getText().length() == 10 || ke.getKeyChar() < '0' || ke.getKeyChar() > '9')
                    ke.consume();
            }
        });
        y += 75;

        emailTF = new JTextField();
        emailTF.setBounds(x, y, TF_WIDTH, HEIGHT);
        emailTF.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        emailTF.addFocusListener(this);
        emailTF.setHorizontalAlignment(SwingConstants.CENTER);
        pLeft.add(emailTF);
        y += 75;

        addressTA = new JTextArea();
        addressTA.setBounds(x, y, TF_WIDTH, HEIGHT * 4);
        addressTA.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        addressTA.setRows(4);
        addressTA.addFocusListener(this);
        pLeft.add(addressTA);

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

        x = 400;
        y = 100;
        aadharTF = new JTextField();
        aadharTF.setBounds(x, y, TF_WIDTH, HEIGHT);
        aadharTF.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        aadharTF.addFocusListener(this);
        aadharTF.setHorizontalAlignment(SwingConstants.CENTER);
        pRightTop.add(aadharTF);
        y += 75;

        panTF = new JTextField();
        panTF.setBounds(x, y, TF_WIDTH, HEIGHT);
        panTF.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        panTF.addFocusListener(this);
        panTF.setHorizontalAlignment(SwingConstants.CENTER);
        pRightTop.add(panTF);
        y += 75;

        String[] qualifOEList = {"Select", "Inter", "Grad", "Post Grad", "Phd"};
        qualifOE = new JComboBox<>(qualifOEList);
        qualifOE.setBounds(x, y, TF_WIDTH, HEIGHT);
        qualifOE.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        pRightTop.add(qualifOE);
        y += 75;

        String[] expOEList = {"Select", "Fresher", "Less than 5 Years", "5-10 Years", "10-15 Years", "15+ Years"};
        expOE = new JComboBox<>(expOEList);
        expOE.setBounds(x, y, TF_WIDTH, HEIGHT);
        expOE.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        pRightTop.add(expOE);
        y += 75;

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
        photoLabel.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent me) {
                try {
                    FileDialog fd = new FileDialog(fr, "Select Photo", FileDialog.LOAD);
                    fd.setVisible(true);
                    path = fd.getDirectory() + fd.getFile();

                    if (path.equals("nullnull")) //if nothing selected
                    {
                        System.out.println("Nothing Selected");
                    } else {
                        System.out.println(path);

                        original = Toolkit.getDefaultToolkit().getImage(path);
                        scaled = original.getScaledInstance(150, 175, Image.SCALE_DEFAULT);
                        photoLabel.setIcon(new ImageIcon(scaled));
                    }
                } catch (Exception e) {
                    System.out.println(e);
                }
            }
        });


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

        x = 400;
        y = 290;
        String[] profileOEList = {"Select", "Junior Engg", "Senior Engg", "Junior Dev", "Senior Dev"};
        profileOE = new JComboBox<>(profileOEList);
        profileOE.setBounds(x, y, TF_WIDTH, HEIGHT);
        profileOE.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        pRightBottom.add(profileOE);
        y += 75;

        packageTF = new JTextField();
        packageTF.setBounds(x, y, TF_WIDTH, HEIGHT);
        packageTF.setFont(new Font(FONT, Font.BOLD, TF_FONT_SIZE));
        packageTF.addFocusListener(this);
        packageTF.setHorizontalAlignment(SwingConstants.CENTER);
        pRightBottom.add(packageTF);
        y += 75;

        fr.add(panel);
        fr.setVisible(true);
    }

    private void saveRecord() {

        String eName = empNameTF.getText().trim();
        String fName = fatherNameTF.getText().trim();
        String mName = motherNameTF.getText().trim();
        String year = yearOB.getSelectedItem().toString();
        String month = monthOB.getSelectedItem().toString();
        String day = dayOB.getSelectedItem().toString();
        String gender = genderCB.getSelectedItem().toString();
        String bloodGroup = bloodGroupCB.getSelectedItem().toString();
        String mobile = phoneTF.getText().trim();
        String email = emailTF.getText().trim();
        String address = addressTA.getText().trim();

        String aadhar = aadharTF.getText().trim();
        String pan = panTF.getText().trim();
        String qualification = qualifOE.getSelectedItem().toString();
        String experience = expOE.getSelectedItem().toString();
        String profile = profileOE.getSelectedItem().toString();
        String packageAmt = packageTF.getText().trim();

        if (eName.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (fName.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Father's Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mName.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Mother's Name cannot be empty!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (gender.equalsIgnoreCase("Select")) {
            JOptionPane.showMessageDialog(fr, "Please Select Gender!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (bloodGroup.equalsIgnoreCase("Select")) {
            JOptionPane.showMessageDialog(fr, "Please Select Blood Group!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (mobile.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Phone Number cannot be empty!!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (email.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Email cannot be empty!!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (address.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Address cannot be empty!!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (aadhar.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Aadhar Number cannot be empty!!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (pan.isEmpty()) {
            JOptionPane.showMessageDialog(fr, "Pan number cannot be empty!!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (qualification.equalsIgnoreCase("Select")) {
            JOptionPane.showMessageDialog(fr, "Please Select Qualification!!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (experience.equalsIgnoreCase("Select")) {
            JOptionPane.showMessageDialog(fr, "Please Select Experience!!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (profile.equalsIgnoreCase("Select")) {
            JOptionPane.showMessageDialog(fr, "Please Select Profile!!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (packageAmt.isEmpty() || Integer.parseInt(packageAmt) == 0) {
            JOptionPane.showMessageDialog(fr, "Package Amount cannot be empty!!", "Error", JOptionPane.ERROR_MESSAGE);
        } else if (path.equals("nullnull")) {
            JOptionPane.showMessageDialog(fr, "Please Select Photo!!", "Error", JOptionPane.ERROR_MESSAGE);
        } else {

            String date = year + "/" + month + "/" + day;
            try {
                Class.forName("com.mysql.jdbc.Driver");
                Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ems", "root", "root");
                PreparedStatement ps = con.prepareStatement("insert into emp(name,fname,mname,dob,gender,bloodg,phone,email,address) values(?,?,?,?,?,?,?,?,?)");
                ps.setString(1, eName);
                ps.setString(2, fName);
                ps.setString(3, mName);
                ps.setString(4, date);
                ps.setString(5, gender);
                ps.setString(6, bloodGroup);
                ps.setString(7, mobile);
                ps.setString(8, email);
                ps.setString(9, address);
                int rs = ps.executeUpdate();
                if (rs > 0) {
                    //JOptionPane.showMessageDialog(fr, "Record Added");
                    PreparedStatement ps1 = con.prepareStatement("select * from emp");
                    ResultSet rs1 = ps1.executeQuery();
                    String id = "";
                    while (rs1.next()) {
                        id = rs1.getString(1);
                    }

                    PreparedStatement ps2 = con.prepareStatement("insert into pro values(?,?,?,?,?,?,?)");
                    ps2.setString(1, id);
                    ps2.setString(2, aadhar);
                    ps2.setString(3, pan);
                    ps2.setString(4, qualification);
                    ps2.setString(5, experience);
                    ps2.setString(6, profile);
                    ps2.setString(7, packageAmt);
                    int rs2 = ps2.executeUpdate();
                    if (rs2 > 0) {
                        //JOptionPane.showMessageDialog(fr, "Record Added");
                        try {
                            File source = new File(path);
                            File dest = new File("emp_pic/" + id + ".jpg");
                            Files.copy(source.toPath(), dest.toPath());
                            JOptionPane.showMessageDialog(fr, "Record Added with id: " + id);
                            resetData();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                    }
                } else {
                    JOptionPane.showMessageDialog(fr, "Record Not Added", "Error", JOptionPane.ERROR_MESSAGE);
                }

            } catch (ClassNotFoundException | SQLException e) {
                e.printStackTrace();
            }

        }
    }

    private void resetData() {
        empNameTF.setText("");
    }

    @Override
    public void focusGained(FocusEvent e) {

        if (e.getSource() == addressTA) {
            addressTA.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 4, Color.RED));
            addressTA.selectAll();
        } else {
            JTextField tf = (JTextField) e.getSource();
            tf.selectAll();
            tf.setBorder(BorderFactory.createMatteBorder(2, 2, 2, 4, Color.RED));
        }
    }

    @Override
    public void focusLost(FocusEvent e) {

        if (e.getSource() == addressTA) {
            addressTA.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));
        } else {
            JTextField tf = (JTextField) e.getSource();
            tf.setBorder(BorderFactory.createMatteBorder(0, 0, 0, 0, Color.BLACK));

        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == monthOB) {
            dayOB.removeAllItems();
            int m = monthOB.getSelectedIndex();
            if (m == 1) {
                for (int i = 1; i <= 28; i++) {
                    dayOB.addItem(i);
                }
            } else if (m == 3 || m == 5 || m == 8 || m == 10) {
                for (int i = 1; i <= 30; i++) {
                    dayOB.addItem(i);
                }
            } else {
                for (int i = 1; i <= 31; i++) {
                    dayOB.addItem(i);
                }
            }
        }
    }
}
