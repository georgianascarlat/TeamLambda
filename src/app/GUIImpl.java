package app;

import commands.*;
import models.*;

import java.util.List;
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

    public static final int WIDTH = 800;
    public static final int HEIGHT = 600;
    private MediatorGUI mediator;



    private JTextField tUserName = new JTextField(10);
    private JTextField tType = new JTextField(10);
    private JPasswordField tPassword = new JPasswordField(10);

    private JPanel bottom = new JPanel(new FlowLayout());
    private JPanel top = new JPanel();


    private JLabel labelUserName = new JLabel("Username: ");
    private JLabel labelPassword = new JLabel("Password: ");
    private JLabel labelType = new JLabel("User Type: ");

    private MyTableModel tableModel = null;



    private static final String[] columnNames = {"Service Name",
            "Users List",
            "Auction Status",
            "Download Progress"};

    public GUIImpl(MediatorGUI mediator) {
        super("Auctions");

        this.mediator = mediator;
        this.mediator.registerGUI(this);


        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });


        JPanel jp = new JPanel();
        jp.setSize(WIDTH, HEIGHT);
        getContentPane().add(jp);

        GridBagLayout gbl = new GridBagLayout();
        gbl.layoutContainer(this);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.anchor = GridBagConstraints.NORTHWEST;
        constraints.fill = GridBagConstraints.BOTH;
        constraints.weightx = WIDTH;
        constraints.weighty = HEIGHT;

        gbl.setConstraints(jp, constraints);

        jp.setLayout(gbl);


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


        constraints.fill = GridBagConstraints.BOTH;
        constraints.gridx = 0;
        constraints.gridy = 1;



        jp.add(bottom, constraints);

        setSize(new Dimension(WIDTH, HEIGHT));
        setVisible(true);


    }


    @Override
    public void actionPerformed(ActionEvent e) {
        Command comd = (Command) e.getSource();
        comd.execute();
    }


    @Override
    public LoginInfo getLoginInfo() {
        String username = tUserName.getText(), type = tType.getText(), pass = String.valueOf(tPassword.getPassword());

        if ("".equals(username) || "".equals(pass) || "".equals(type)) {
            JOptionPane.showMessageDialog(this,
                    "Some fields are blank.");
            return null;
        }

        return new LoginInfo(username, type, pass);
    }

    @Override
    public void logIn(LoginInfo info, List<String> services) {

        JLabel label = new JLabel("Hi, " + info.getUsername() + "!");
        LogoutButton logoutButton = new LogoutButton(this, mediator);

        top.removeAll();

        top.add(label);
        top.add(logoutButton);


        tableModel = new MyTableModel(columnNames, getDataFromServices(services));

        bottom.removeAll();

        bottom.add(new JScrollPane(tableModel.createJTable()));

        this.paintAll(this.getGraphics());

    }

    @Override
    public void logOut() {

        LoginButton loginButton = new LoginButton(this, mediator);

        top.removeAll();

        top.add(labelUserName);
        top.add(tUserName);
        top.add(labelType);
        top.add(tType);
        top.add(labelPassword);
        top.add(tPassword);
        top.add(loginButton);

        bottom.removeAll();


        this.paintAll(this.getGraphics());

    }




    private Object[][] getDataFromServices(List<String> services) {
        int size = services.size();
        Object[][] data = new Object[size][4];

        for(int i=0;i<size;i++){

            data[i][0] = services.get(i);
            data[i][1] = new DefaultListModel<String>();
            data[i][2] = StatusTypes.Inactive;
            data[i][3] =  new ServiceImpl();

        }

        return data;
    }


}
