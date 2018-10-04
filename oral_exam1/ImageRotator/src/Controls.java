import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

/**
 * The Controls class is an extension of JPanel that holds two JSliders and a
 * JCheckBox dedicated to the manipulation of the ImageComponent instance.
 * These three components and their styled fonts are held in the class, as well
 * as classes for their event listeners.
 *
 * @see JPanel
 * @see JSlider
 * @see SpecificAngleChangeListener
 * @see JCheckBox
 * @see SpinModeItemListener
 * @see Timer
 * @see TimerListener
 * @see #Controls(ImageComponent)
 *
 */
public class Controls extends JPanel {

    private JCheckBox continuous;
    private JSlider speedSelect;
    private JSlider angleSelect;

    private Timer timer;

    private Font controlFont = new Font("Courier", Font.BOLD + Font.ITALIC, 16);
    private Font headingFont = new Font("Courier", Font.BOLD + Font.ITALIC, 25);

    private JLabel speedLabel = new FontJLabel(controlFont, "% Speed Selection", JLabel.CENTER);
    private JLabel angleLabel = new FontJLabel(controlFont, "Degree of Rotation", JLabel.CENTER);
    private JLabel heading = new FontJLabel(headingFont, "Ben's Image Rotator", JLabel.CENTER);

    /**
     * The controls constructor configures the {@code speedSelect} and
     * {@code angleSelect} JSliders as well as the {@code continuous} checkbox.
     * A timer is then instantiated, and all interaction objects are assigned
     * the proper listeners. The components default
     *
     * @param imageComponent the instance of the imageComponent created in MainFrame
     *                       is passed in so that handlers may interact with the
     *                       instance.
     */
    public Controls(ImageComponent imageComponent) {
        super();
        this.setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
        setPreferredSize(new Dimension(350, 300));

        speedSelect = new JSlider(0, 100, 50);
        speedSelect.setAlignmentX(Component.CENTER_ALIGNMENT);
        speedSelect.setMajorTickSpacing(10);
        speedSelect.setMinorTickSpacing(2);
        speedSelect.setPaintTicks(true);
        speedSelect.setPaintLabels(true);

        continuous = new JCheckBox("Enable Continuous Spin Mode", true);
        continuous.setFont(new Font("Courier", Font.PLAIN, 16));
        continuous.setAlignmentX(Component.CENTER_ALIGNMENT);
        continuous.setFocusPainted(false);

        angleSelect = new JSlider(0, 360, 0);
        angleSelect.setAlignmentX(Component.CENTER_ALIGNMENT);
        angleSelect.setMajorTickSpacing(45);
        angleSelect.setMinorTickSpacing(15);
        angleSelect.setPaintTicks(true);
        angleSelect.setPaintLabels(true);

        timer = new Timer(50, new TimerListener(imageComponent, speedSelect));

        angleSelect.addChangeListener(new SpecificAngleChangeListener(imageComponent, continuous));
        continuous.addItemListener(new SpinModeItemListener(speedSelect, timer));
        speedSelect.addChangeListener(new SpecificAngleChangeListener(imageComponent, continuous));


        add(Box.createRigidArea(new Dimension(0,10)));
        add(heading);
        add(Box.createRigidArea(new Dimension(0,30)));
        add(continuous);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(speedLabel);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(speedSelect);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(angleLabel);
        add(Box.createRigidArea(new Dimension(0,10)));
        add(angleSelect);

        timer.start();

    }

    class SpecificAngleChangeListener implements ChangeListener {
        private ImageComponent imageComponent;
        private JCheckBox continuous;

        public SpecificAngleChangeListener(ImageComponent imageComponent,
                                           JCheckBox continuous) {
            this.imageComponent = imageComponent;
            this.continuous = continuous;
        }

        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();

            if (source == angleSelect) {
                this.continuous.setSelected(false);
                double angle = source.getValue();
                this.imageComponent.rotate(angle);
            }
        }
    }

    class SpinModeItemListener implements ItemListener {
        private JSlider speedSelect;
        private Timer timer;

        public SpinModeItemListener(JSlider speedSelect, Timer timer) {
            this.speedSelect = speedSelect;
            this.timer = timer;
        }

        public void itemStateChanged(ItemEvent e) {
            JCheckBox source = (JCheckBox) e.getSource();

            if (source.isSelected()) {
                speedSelect.setEnabled(true);
                timer.start();
            } else {
                speedSelect.setEnabled(false);
                timer.stop();
            }
        }
    }

    class TimerListener implements ActionListener {
        private ImageComponent imageComponent;
        private JSlider speedSelect;

        public TimerListener(ImageComponent imageComponent, JSlider speedSelect) {
            this.imageComponent = imageComponent;
            this.speedSelect = speedSelect;
        }

        public void actionPerformed(ActionEvent e) {
            this.imageComponent.addAngle(speedSelect.getValue() / 2);
        }
    }

}
