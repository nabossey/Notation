
import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

public class GUIDriver extends JFrame implements ActionListener {

    //GUi components
    JButton convertButton = new JButton("Convert");
    JButton evaluateButton = new JButton("Evaluate");
    JButton exitButton = new JButton("Exit");
    JRadioButton inToPostButton = new JRadioButton("Infix To Postfix");
    JRadioButton postToInButton = new JRadioButton("Postfix To Infix");
    JLabel infixLabel = new JLabel("Infix Expression:");
    JLabel postLabel = new JLabel("Infix Expression:");
    JLabel evalResultLabel = new JLabel("");
    JTextField infixConvertedField = new JTextField(20);
    JTextField postfixConvertedField = new JTextField(20);
    JTextField evaluationField = new JTextField(20);

    public GUIDriver() {
        super("Notation Utility");
        setLayout(new BorderLayout());
        JPanel panels = new JPanel(new BorderLayout());
        panels.add(builNotationConevrsionPanel(), BorderLayout.CENTER);
        panels.add(buildNotationEvaluationPanel(), BorderLayout.SOUTH);
        add(panels, BorderLayout.CENTER);
        JPanel exitPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        exitPanel.add(exitButton);
        add(exitPanel, BorderLayout.SOUTH);
        convertButton.addActionListener(this);
        evaluateButton.addActionListener(this);
        inToPostButton.addActionListener(this);
        postToInButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    /**
     * method to build NotationEvaluationPanel
     *
     * @return
     */
    private JPanel buildNotationEvaluationPanel() {
        //adding compunents to GUI
        JPanel evaluationPanel = new JPanel(new GridLayout(2, 2, 15, 15));
        evaluationPanel.add(new JLabel("Postfix Expression:"));
        evaluationPanel.add(evalResultLabel);
        evaluationPanel.add(evaluationField);
        evaluationPanel.add(evaluateButton);
        evaluationPanel.setBorder(new TitledBorder("Notation Evaluation"));
        return evaluationPanel;
    }

    /**
     * method to build NotationConevrsionPanel
     *
     * @return
     */
    private JPanel builNotationConevrsionPanel() {
        //adding compunents to GUI
        JPanel conversionPanel = new JPanel(new GridLayout(3, 3, 15, 15));
        conversionPanel.add(inToPostButton);
        conversionPanel.add(infixLabel);
        conversionPanel.add(postLabel);
        conversionPanel.add(postToInButton);
        conversionPanel.add(infixConvertedField);
        conversionPanel.add(postfixConvertedField);
        conversionPanel.add(convertButton);
        conversionPanel.setBorder(new TitledBorder("Notation Conversion"));
        ButtonGroup bg = new ButtonGroup();
        bg.add(postToInButton);
        bg.add(inToPostButton);
        return conversionPanel;
    }

    public static void main(String[] args) {
        GUIDriver frame = new GUIDriver();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //frame.setSize(650, 300);
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * button action listener
     *
     * @param e
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == convertButton) {
            if (inToPostButton.isSelected()) {
                try {
                    String val = Notation.convertInfixToPostfix(infixConvertedField.getText());
                    postLabel.setVisible(true);
                    postfixConvertedField.setVisible(true);
                    postfixConvertedField.setText(val);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, ex);
                }
            } else if (postToInButton.isSelected()) {
                try {
                    String val = Notation.convertPostfixToInfix(postfixConvertedField.getText());
                    infixLabel.setVisible(true);
                    infixConvertedField.setVisible(true);
                    infixConvertedField.setText(val);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, ex);
                }
            } else {
                JOptionPane.showMessageDialog(rootPane, "Select the option");
            }
        } else if (e.getSource() == evaluateButton) {
            try {
                    Double val = Notation.evaluatePostfixExpression(evaluationField.getText());
                    evalResultLabel.setText("Answer: "+val);
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(rootPane, ex);
                }
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        } else if (e.getSource() == inToPostButton) {
            postLabel.setVisible(false);
            postfixConvertedField.setVisible(false);
            infixConvertedField.setVisible(true);
            infixLabel.setVisible(true);
        } else if (e.getSource() == postToInButton) {
            postLabel.setVisible(true);
            postfixConvertedField.setVisible(true);
            infixConvertedField.setVisible(false);
            infixLabel.setVisible(false);
        }
    }
}
