import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

public class Controls extends JPanel {

    private JCheckBox continuous;
    private JSlider speedSelect;
    private JSlider angleSelect;

    private Timer timer;

    private Font controlFont = new Font("Courier", Font.BOLD + Font.ITALIC, 16);
    private Font headingFont = new Font("Courier", Font.BOLD + Font.ITALIC, 20);

    private JLabel speedLabel = new FontJLabel(controlFont, "% Speed Selection", JLabel.CENTER);
    private JLabel angleLabel = new FontJLabel(controlFont, "Degree of Rotation", JLabel.CENTER);
    private JLabel heading = new FontJLabel(headingFont, "Ben's Image Rotator", JLabel.CENTER);

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
        speedSelect.addChangeListener(new specificAngleChangeListener(imageComponent));

        angleSelect = new JSlider(0, 360, 0);
        angleSelect.setAlignmentX(Component.CENTER_ALIGNMENT);
        angleSelect.setMajorTickSpacing(45);
        angleSelect.setMinorTickSpacing(15);
        angleSelect.setPaintTicks(true);
        angleSelect.setPaintLabels(true);
        angleSelect.addChangeListener(new specificAngleChangeListener(imageComponent));

        timer =  new Timer(50, new TimerListener(imageComponent, speedSelect));

        continuous = new JCheckBox("Enable Continuous Spin Mode", true);
        continuous.setFont(new Font("Courier", Font.PLAIN, 16));
        continuous.setAlignmentX(Component.CENTER_ALIGNMENT);
        continuous.setFocusPainted(false);
        continuous.addItemListener(new SpinModeItemListener(imageComponent, speedSelect, timer));

        add(heading);
        // TODO: Add spaces between
        add(continuous);
        add(speedLabel);
        add(speedSelect);
        add(angleLabel);
        add(angleSelect);

        timer.start();

    }

    class specificAngleChangeListener implements ChangeListener {
        private ImageComponent imageComponent;

        public specificAngleChangeListener(ImageComponent imageComponent){
            this.imageComponent = imageComponent;
        }

        public void stateChanged(ChangeEvent e){
            JSlider source = (JSlider)e.getSource();
            if (source == speedSelect){
            }
            else if (source == angleSelect) {
                double angle = source.getValue();
                this.imageComponent.rotate(angle);
            }
        }
    }

    class SpinModeItemListener implements ItemListener {
        private ImageComponent imageComponent;
        private JSlider speedSelect;
        private Timer timer;

        public SpinModeItemListener(ImageComponent imageComponent,
                                    JSlider speedSelect, Timer timer){
            this.imageComponent = imageComponent;
            this.speedSelect = speedSelect;
            this.timer = timer;
        }

        public void itemStateChanged(ItemEvent e){
            JCheckBox source = (JCheckBox)e.getSource();
            if (source.isSelected()){
                speedSelect.setEnabled(true);
                timer.start();
            }
            else{
                speedSelect.setEnabled(false);
                timer.stop();
            }
        }
    }

    class TimerListener implements ActionListener{
        ImageComponent imageComponent;
        JSlider speedSelect;

        public TimerListener(ImageComponent imageComponent, JSlider speedSelect){
            this.imageComponent = imageComponent;
            this.speedSelect = speedSelect;
        }

        public void actionPerformed(ActionEvent e) {
            this.imageComponent.addAngle(speedSelect.getValue()/2);
        }
    }

}
