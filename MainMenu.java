import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class MainMenu implements Runnable {

    JFrame fr;
    JPanel panel;
    JLabel addRecord, editRecord, deleteRecord, searchRecord, titleL, closeIcon;
    Thread th;
    int ox = (1728 - 1306) / 2;
    int y = (875 / 2) - (256 / 2);

    public MainMenu(String user) {

        fr = new JFrame();
        fr.setSize(1728, 972);
        fr.setLocationRelativeTo(null);
        fr.setLayout(null);
        fr.setEnabled(false);
        fr.setUndecorated(true);

        panel = new JPanel();
        panel.setBounds(0, 0, 1728, 972);
        panel.setBackground(Color.BLACK);
        panel.setLayout(null);
        panel.setBorder(BorderFactory.createMatteBorder(5, 5, 5, 5, Color.ORANGE));

        titleL = new JLabel("Employee Management System");
        titleL.setHorizontalAlignment(JTextField.CENTER);
        titleL.setBackground(Color.ORANGE);
        titleL.setForeground(Color.RED);
        titleL.setFont(new Font("Monotype Corsiva", Font.BOLD | Font.ITALIC, 70));
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
                fr.dispose();
            }
        });
        panel.add(closeIcon);

        addRecord = new JLabel(new ImageIcon("images/addRecordIcon.png"));
        //addRecord.setBounds(ox, y, 256, 256);
        addRecord.setToolTipText("<html><body><b><font color = red size = 4>Add Record</font></b></body></html>");
        addRecord.setHorizontalAlignment(SwingConstants.CENTER);
        //addRecord.setBorder(BorderFactory.createLineBorder(Color.RED));
        addRecord.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fr.setEnabled(false);
                new AddRecord(fr);
            }
        });
        panel.add(addRecord);

        deleteRecord = new JLabel(new ImageIcon("images/delRecordIcon.png"));
        //deleteRecord.setBounds(ox+350, y, 256, 256);
        deleteRecord.setHorizontalAlignment(SwingConstants.CENTER);
        deleteRecord.setToolTipText("Delete Record");
        deleteRecord.setToolTipText("<html><body><b><font color = red size = 4>Delete Record</font></b></body></html>");
        //deleteRecord.setBorder(BorderFactory.createLineBorder(Color.RED));
        deleteRecord.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fr.setEnabled(false);
                new DeleteRecord(fr);
            }
        });
        panel.add(deleteRecord);

        editRecord = new JLabel(new ImageIcon("images/editRecordIcon.png"));
        //editRecord.setBounds(ox+700, y, 256, 256);
        editRecord.setHorizontalAlignment(SwingConstants.CENTER);
        editRecord.setToolTipText("Edit Record");
        editRecord.setToolTipText("<html><body><b><font color = red size = 4>Edit Record</font></b></body></html>");
        //editRecord.setBorder(BorderFactory.createLineBorder(Color.RED));
        editRecord.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fr.setEnabled(false);
                new EditRecord(fr);
            }
        });
        panel.add(editRecord);

        searchRecord = new JLabel(new ImageIcon("images/searchRecordIcon.png"));
        //searchRecord.setBounds(ox+1050, y, 256, 256);
        searchRecord.setToolTipText("Search Record");
        searchRecord.setHorizontalAlignment(SwingConstants.CENTER);
        searchRecord.setToolTipText("<html><body><b><font color = red size = 4>Search Record</font></b></body></html>");
        //searchRecord.setBorder(BorderFactory.createLineBorder(Color.RED));
        searchRecord.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                fr.setEnabled(false);
                new SearchRecord(fr);
            }
        });
        panel.add(searchRecord);

        fr.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        fr.add(panel);
        fr.setVisible(true);

        th = new Thread(this);
        th.start();
    }

    @Override
    public void run() {

        int a = 972;
        int b = -350;
        while (a != y) {
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            addRecord.setBounds(ox, a, 256, 256);
            editRecord.setBounds(ox + 700, a, 256, 256);
            a--;

            deleteRecord.setBounds(ox + 350, b, 256, 256);
            searchRecord.setBounds(ox + 1050, b, 256, 256);
            b++;
        }
        fr.setEnabled(true);
        titleL.setBounds((1728 - 900) / 2, 50, 900, 70);
    }
}
