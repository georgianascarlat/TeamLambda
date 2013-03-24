package commands;

import app.MediatorGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/24/13
 * Time: 4:34 PM
 * To change this template use File | Settings | File Templates.
 */
public class LoginButton extends JButton implements Command {

    MediatorGUI med;

    public LoginButton(ActionListener act, MediatorGUI md) {
        super("Login");
        setMargin(new Insets(5, 12, 5, 12));
        setToolTipText("Login");
        addActionListener(act);
        med = md;
    }


    @Override
    public void execute() {
        med.logIn();
    }
}
