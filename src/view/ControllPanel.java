package view;

import javax.swing.*;
import java.awt.*;

public class ControllPanel extends JPanel {
    private ButtonGroup group, colorGroup;
    private JRadioButton seed, seedPatern, scanLine, colorRed, colorBlue, colorGreen;
    private int selectedFilling, selectedColor;
    private JButton btnHelp;
    private static final int RED = 0xff0000;
    private static final int GREEN = 0x00ff00;
    private static final int BLUE = 0x0000ff;

    public ControllPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridLayout(0, 1));

        selectedColor = RED;

        group = new ButtonGroup();
        seed = new JRadioButton("Seed", true);
        scanLine = new JRadioButton("ScanLine");
        seedPatern = new JRadioButton("SeedPatern");
        group.add(seed);
        group.add(scanLine);
        group.add(seedPatern);

        colorGroup = new ButtonGroup();
        colorRed = new JRadioButton("Červená", true);
        colorRed.setForeground(Color.red);
        colorGreen = new JRadioButton("Zelená");
        colorGreen.setForeground(Color.green);
        colorBlue = new JRadioButton("Modrá");
        colorBlue.setForeground(Color.blue);

        colorRed.addActionListener(e -> selectedColor = RED);
        colorGreen.addActionListener(e -> selectedColor = GREEN);
        colorBlue.addActionListener(e -> selectedColor = BLUE);

        colorGroup.add(colorRed);
        colorGroup.add(colorGreen);
        colorGroup.add(colorBlue);

        seed.addActionListener((e) -> selectedFilling = 0);
        seedPatern.addActionListener(e -> selectedFilling = 1);
        scanLine.addActionListener((e) -> selectedFilling = 2);

        btnHelp = new JButton("Nápověda");

        btnHelp.addActionListener(e ->
            JOptionPane.showMessageDialog(this.getTopLevelAncestor(),"Pro vykreslení výplně stiskněte prostřední tlačíko myši" +
                    "\nPro ořezání polygonu stiskněte pravé tlačítko myši","Nápověda",JOptionPane.INFORMATION_MESSAGE)
        );

        add(new JLabel("Typ výplně"));
        add(seed);
        add(seedPatern);
        add(scanLine);
        add(new JLabel("Barva výplně"));
        add(colorRed);
        add(colorGreen);
        add(colorBlue);
        add(btnHelp);
    }

    public int getFilling() {
        return selectedFilling;
    }

    public int getSelectedColor() {
        return selectedColor;
    }
}
