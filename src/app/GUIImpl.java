package app;

import commands.Command;
import commands.LoginButton;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 2:47 PM
 * To change this template use File | Settings | File Templates.
 */
public class GUIImpl extends JFrame implements ActionListener, GUI {

    private MediatorGUI mediator;

    private JTable table;
    private JLabel loginInfo;

    private JLabel labelUserName = new JLabel("Username: ");
    private JLabel labelPassword = new JLabel("Password: ");
    private JLabel labelType = new JLabel("User Type: ");

    private JTextField tUserName = new JTextField(10);
    private JTextField tType = new JTextField(10);
    private JPasswordField tPassword = new JPasswordField(10);

    public GUIImpl() {
        super("Drawing");


        String[] columnNames = {"First Name",
                "Last Name",
                "Sport",
                "# of Years",
                "Vegetarian"};
        Object[][] data = {
                {"Kathy", "Smith",
                        "Snowboarding", new Integer(5), new Boolean(false)},
                {"John", "Doe",
                        "Rowing", new Integer(3), new Boolean(true)},
                {"Sue", "Black",
                        "Knitting", new Integer(2), new Boolean(false)},
                {"Jane", "White",
                        "Speed reading", new Integer(20), new Boolean(true)},
                {"Joe", "Brown",
                        "Pool", new Integer(10), new Boolean(false)}
        };

        table = new JTable(data, columnNames);

        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        JPanel jp = new JPanel();
        jp.setSize(800, 600);
        getContentPane().add(jp);
        mediator = new MediatorGUIImpl();
        mediator.registerGUI(this);

        GridBagLayout gbl = new GridBagLayout();
        gbl.layoutContainer(this);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = 800;
        constraints.weighty = 800;

        gbl.setConstraints(jp, constraints);

        jp.setLayout(gbl);

        JPanel top = new JPanel(); // 1 row, any number of columns


        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.gridx = 0;
        constraints.gridy = 0;
        jp.add(top, constraints);


        LoginButton loginButton = new LoginButton(this, mediator);

        labelUserName.setLabelFor(tUserName);
        labelPassword.setLabelFor(tPassword);
        labelType.setLabelFor(tType);

        top.add(labelUserName);
        top.add(tUserName);
        top.add(labelType);
        top.add(tType);
        top.add(labelPassword);
        top.add(tPassword);
        top.add(loginButton);


        JPanel bottom = new JPanel();


        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;

        jp.add(bottom, constraints);

        bottom.add(new JScrollPane(table));

        setSize(new Dimension(800, 600));
        setVisible(true);

    }




    public void actionPerformed(ActionEvent e) {
        Command comd = (Command) e.getSource();
        comd.execute();
    }


    public static void main(String[] args) {
        new GUIImpl();
    }

    @Override
    public LoginInfo getLoginInfo() {
        String username = tUserName.getText(), type = tType.getText(), pass = String.valueOf(tPassword.getPassword());

        if("".equals(username) || "".equals(pass) || "".equals(type)){
            JOptionPane.showMessageDialog(this,
                    "Some fields are blank.");
            return null;
        }

        return new LoginInfo(username,type,pass);
    }
}
