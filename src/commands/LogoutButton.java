package commands;

import app.GUIImpl;
import app.MediatorGUI;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

/**
 * Created with IntelliJ IDEA.
 * User: nogai
 * Date: 3/25/13
 * Time: 12:20 AM
 * To change this template use File | Settings | File Templates.
 */
public class LogoutButton extends JButton implements Command  {

    MediatorGUI med;

    public LogoutButton(ActionListener act, MediatorGUI md) {
        super("Logout");
        setMargin(new Insets(5, 12, 5, 12));
        setToolTipText("Logout");
        addActionListener(act);
        med = md;
    }

    @Override
    public void execute() {
         med.logOut();
    }
}
