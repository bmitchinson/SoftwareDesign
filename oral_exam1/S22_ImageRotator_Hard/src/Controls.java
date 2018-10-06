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
     * The controls constructor configures the speedSelect and
     * angleSelect JSliders as well as the continuous checkbox.
     * A timer is then instantiated, and interaction objects are assigned
     * their proper listeners. Each JComponent is then added to the Controls
     * component, with proper spacing in-between using Box objects. The application
     * defaults to launching in continuous mode, so the timer is started.
     *
     * @param imageComponent the instance of the imageComponent created in MainFrame
     *                       is passed in so that handlers may interact with the
     *                       instance.
     * @see ImageComponent
     * @see Timer
     * @see SpecificAngleChangeListener
     * @see SpinModeItemListener
     * @see TimerListener
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

        add(Box.createRigidArea(new Dimension(0, 10)));
        add(heading);
        add(Box.createRigidArea(new Dimension(0, 30)));
        add(continuous);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(speedLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(speedSelect);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(angleLabel);
        add(Box.createRigidArea(new Dimension(0, 10)));
        add(angleSelect);

        timer.start();

    }

    /**
     * The SpecificAngleChangeListener class implements ChangeListener, and
     * takes an instance of ImageComponent and JCheckBox in order to call
     * ImageComponent's rotate method when necessary. It is assigned to the
     * angleSelect JSlider in Controls.
     *
     * @see Controls
     * @see ChangeListener
     * @see ImageComponent
     * @see JCheckBox
     */
    class SpecificAngleChangeListener implements ChangeListener {
        private ImageComponent imageComponent;
        private JCheckBox continuous;

        /**
         * Stores ImageComponent and JSlider references to the class for later
         * manipulation detailed in stateChanged.
         *
         * @param imageComponent Instance of ImageComponent for referencing it's
         *                       rotate method.
         * @param continuous     Instance of the JCheckBox "Spin Mode" to disable when
         *                       angle is changed.
         * @see #stateChanged(ChangeEvent)
         */
        public SpecificAngleChangeListener(ImageComponent imageComponent,
                                           JCheckBox continuous) {
            this.imageComponent = imageComponent;
            this.continuous = continuous;
        }

        /**
         * Called by JSlider when a ChangeAction is triggered. Disables continuous
         * mode, and triggers a rotation to the proper angle.
         *
         * @param e event given by the event handler from JSlider.
         * @see ChangeEvent
         * @see ChangeListener
         */
        public void stateChanged(ChangeEvent e) {
            JSlider source = (JSlider) e.getSource();

            if (source == angleSelect) {
                this.continuous.setSelected(false);
                double angle = source.getValue();
                this.imageComponent.rotate(angle);
            }
        }
    }

    /**
     * The SpinModeItemListener class implements ItemListener, and takes an
     * instance of JSlider and Timer for manipulation detailed in itemStateChanged.
     * It is assigned as a listener for the JCheckBox named continuous in Controls.
     *
     * @see Controls
     * @see ItemListener
     * @see Timer
     * @see JSlider
     * @see #itemStateChanged(ItemEvent)
     */
    class SpinModeItemListener implements ItemListener {
        private JSlider speedSelect;
        private Timer timer;

        /**
         * Stores speedSelect and timer references to the class for  manipulation
         * detailed in itemStateChanged.
         *
         * @param speedSelect Instance of JSlider for referencing it's current
         *                    value representing speed.
         * @param timer       Instance of the Timer that is triggering constant rotation
         *                    of the image.
         * @see #itemStateChanged(ItemEvent)
         */
        public SpinModeItemListener(JSlider speedSelect, Timer timer) {
            this.speedSelect = speedSelect;
            this.timer = timer;
        }

        /**
         * Called by JCheckBox when a ItemAction is triggered from the handler.
         * Starts and stops the timer as needed, while disabling the speedSelect
         * to make clear to the user that it is not functional.
         *
         * @param e event given by the event handler from JCheckBox
         * @see ItemListener
         * @see ItemEvent
         */
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

    /**
     * The TimeListener class implements ActionListener and takes an instance
     * of both ImageComponent and JSlider for manipulation detailed in
     * actionPerformed. Is it assigned as a listener for the Timer named timer
     * in Controls.
     *
     * @see #actionPerformed(ActionEvent)
     * @see ActionListener
     * @see Controls
     * @see TimerListener
     */
    class TimerListener implements ActionListener {
        private ImageComponent imageComponent;
        private JSlider speedSelect;

        /**
         * Stores imageComponent and speedSelect references for manipulation in
         * actionPerformed.
         *
         * @param imageComponent reference to the imageComponent stored originally
         *                       in MainFrame for manipulation.
         * @param speedSelect    reference to the JSlider speedSelect object in Controls
         *                       in order to check slider value.
         * @see #actionPerformed(ActionEvent)
         */
        public TimerListener(ImageComponent imageComponent, JSlider speedSelect) {
            this.imageComponent = imageComponent;
            this.speedSelect = speedSelect;
        }

        /**
         * Called by Timer when an ActionEvent is triggered from the handler.
         * Calls the andAngle method from ImageComponent with a parameter adjusted
         * by the value in the speedSelect reference.
         *
         * @param e event given by the event handler from Timer
         * @see Timer
         * @see ImageComponent
         * @see ActionEvent
         */
        public void actionPerformed(ActionEvent e) {
            this.imageComponent.addAngle(speedSelect.getValue() / 2);
        }
    }

}
