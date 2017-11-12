package view;

import javax.swing.*;
import java.awt.*;

public class ControllPanel extends JPanel {
    private ButtonGroup group;
    private JRadioButton seed;
    private JRadioButton scanLine;
    private boolean selectedFilling;

    public ControllPanel(int width, int height) {
        setPreferredSize(new Dimension(width, height));
        setLayout(new GridLayout(0, 1));

        selectedFilling = true;

        group = new ButtonGroup();
        seed = new JRadioButton("Seed", true);
        scanLine = new JRadioButton("ScanLine");
        group.add(seed);
        group.add(scanLine);

        seed.addActionListener((e) -> {
            selectedFilling = true;
        });

        scanLine.addActionListener((e) -> {
            selectedFilling = false;
        });

        add(seed);
        add(scanLine);

    }

    public boolean getFilling() {
        return selectedFilling;
    }

}
