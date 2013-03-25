package app;

import commands.*;
import exceptions.NoSuchUserTypeException;
import mediator.MediatorGUI;
import models.*;
import state.SessionState;
import state.SessionStateFactory;
import state.StateManager;

import java.util.List;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import static state.StateManager.*;

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


    private StateManager stateManager;


    public GUIImpl(MediatorGUI mediator) {
        super("Auctions");

        this.mediator = mediator;
        this.mediator.registerGUI(this);


        init(mediator);


    }

    private void init(MediatorGUI mediator) {

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
            JOptionPane.showMessageDialog(this, "Some fields are blank.");
            return null;
        }

        return new LoginInfo(username, type, pass);
    }

    @Override
    public void logIn(LoginInfo info, List<String> services) {

        JLabel label = new JLabel("Hi, " + info.getUsername() + "!");
        LogoutButton logoutButton = new LogoutButton(this, mediator);
        MyTableModel tableModel;

        try {

            SessionState sessionState = SessionStateFactory.createSessionState(info.getType(), services, this, mediator);
            stateManager = setStateManager(stateManager, sessionState);

        } catch (NoSuchUserTypeException e) {
            JOptionPane.showMessageDialog(this, "Invalid user type " + e.getType() + ".");
            return;
        }

        top.removeAll();

        top.add(label);
        top.add(logoutButton);

        bottom.removeAll();
        bottom.add(new JScrollPane(stateManager.getTable()));

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

    @Override
    public void launchOffer(int row) {
        stateManager.launchOffer(row);
    }

    @Override
    public void dropOffer(int row) {
        stateManager.dropOffer(row);
    }


}
