package Menu;

import Main.MainFrame;

import javax.swing.*;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MenuFrame extends JFrame implements ActionListener {
    JPanel menuPanel = new JPanel();
    JButton start = new JButton();
    JButton exit = new JButton();
    JButton scores = new JButton();
    GridBagConstraints layoutConstraints = new GridBagConstraints();
    public MenuFrame() {
        setTitle("pacman");

        setLayout(new BorderLayout());
        layoutConstraints.insets = new Insets(40, 40, 40, 40);
        layoutConstraints.gridx = 0;
        menuPanel.setOpaque(true);
        menuPanel.setBackground(Color.BLACK);
        addButtons();
        setSize(1280, 720);
        setLocationRelativeTo(null);
        setResizable(true);
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        add(menuPanel, BorderLayout.CENTER);


    }

    private void addButtons() {

        addButton(start, " START ", Color.YELLOW, new Font("Sarai", Font.BOLD, 50), 10, 0);
        addButton(scores, " SCORES ", Color.YELLOW, new Font("Sarai", Font.BOLD, 50), 10, 1);
        addButton(exit, " EXIT ", Color.YELLOW, new Font("Sarai", Font.BOLD, 50), 10, 2);
    }
    private void addButton(JButton button, String text, Color foregroundColor, Font font, int borderThickness, int gridy) {
        button.setBackground(Color.BLUE);
        button.setForeground(foregroundColor);
        button.addActionListener(this);
        button.setText(text);
        button.setFocusable(false);
        button.setFont(font);
        button.setBorder(BorderFactory.createMatteBorder(borderThickness, borderThickness, borderThickness, borderThickness, foregroundColor));
        layoutConstraints.gridx = 0;
        layoutConstraints.gridy = gridy;
        menuPanel.add(button, layoutConstraints);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == start) {
            dispose();
            new MainFrame();
        }



        if(e.getSource() == exit) {

            System.exit(0);
        }
    }
}
