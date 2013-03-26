package app;

import commands.*;
import exceptions.NoSuchUserTypeException;
import mediator.MediatorGUI;
import models.*;
import state.SessionState;
import state.SessionStateFactory;
import state.StateManager;
import worker.DispatchWorker;

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


    private StateManager stateManager;
    private DispatchWorker dispatchWorker;


    public GUIImpl(MediatorGUI mediator) {
        super("Auctions");

        this.mediator = mediator;
        this.mediator.registerGUI(this);

        this.stateManager = new StateManager();

        this.dispatchWorker = new DispatchWorker();

        init(mediator);

        this.dispatchWorker.execute();




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
    public void logIn(final LoginInfo info, final List<String> services) {

        dispatchWorker.submitAction(new Command() {
            @Override
            public void execute() {
                JLabel label = new JLabel("Hi, " + info.getUsername() + "!");
                LogoutButton logoutButton = new LogoutButton(GUIImpl.this, mediator);

                try {

                    SessionState sessionState = SessionStateFactory.createSessionState(info.getType(), services, GUIImpl.this, mediator);
                    stateManager.setSessionState(sessionState);

                } catch (NoSuchUserTypeException e) {
                    JOptionPane.showMessageDialog(GUIImpl.this, "Invalid user type " + e.getType() + ".");
                    return;
                }

                top.removeAll();

                top.add(label);
                top.add(logoutButton);

                bottom.removeAll();
                bottom.add(new JScrollPane(stateManager.getTable()));

                stateManager.login();

                GUIImpl.this.paintAll(GUIImpl.this.getGraphics());
            }
        });



    }

    @Override
    public void logOut() {

        dispatchWorker.submitAction(new Command() {
            @Override
            public void execute() {
                LoginButton loginButton = new LoginButton(GUIImpl.this, mediator);

                top.removeAll();

                top.add(labelUserName);
                top.add(tUserName);
                top.add(labelType);
                top.add(tType);
                top.add(labelPassword);
                top.add(tPassword);
                top.add(loginButton);

                bottom.removeAll();


                GUIImpl.this.paintAll(GUIImpl.this.getGraphics());
            }
        });



    }

    @Override
    public void launchOffer(final int row) {

        dispatchWorker.submitAction(new Command() {
            @Override
            public void execute() {
                stateManager.launchOffer(row);
            }
        });

    }

    @Override
    public void dropOffer(final int row) {

        dispatchWorker.submitAction(new Command() {
            @Override
            public void execute() {
                stateManager.dropOffer(row);
            }
        });

    }

    @Override
    public void addUser(final String username, final String type, final List<String> services) {

        dispatchWorker.submitAction(new Command() {
            @Override
            public void execute() {
                stateManager.addUser(username, type, services);
            }
        });

    }

    @Override
    public void removeUser(final String name, final String type) {

        dispatchWorker.submitAction(new Command() {
            @Override
            public void execute() {
                stateManager.removeUser(name, type);
            }
        });

    }


}
