import javax.swing.JFrame;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.SwingConstants;

import org.w3c.dom.events.EventException;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import javax.swing.JSlider;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.JScrollPane;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class SecretMessagesGUI extends JFrame {
	private JTextField textKey;
	private JTextArea textIn;
	private JTextArea textOut;
	private JSlider slider; 
	private JButton btnMoveUp;
	private JButton btnEncodedecode;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	
	public String encode(String message, int keyVal) {
		String output = "";
		char key = (char) keyVal;
		for (int x = 0; x < message.length(); x++) {
			char input = message.charAt(x);
			if (input >= 'A' && input <='Z') {
				input += key;
				if (input > 'Z') 
					input -= 26;
				if (input < 'A')
					input += 26;
			} 
			else if (input >= 'a' && input <='z') {
				input += key;
				if (input > 'z') 
					input -= 26;
				if (input < 'a')
					input += 26; 
			}
			else if (input >= '0' && input<= '9') {
				input += (keyVal % 10);
				if (input > '9')
					input -= 10;
				if (input < '0')
					input += 10;
			}
			output += input;
		}
		return output;
	}
	
	public SecretMessagesGUI() {
		getContentPane().setBackground(Color.GRAY);
		getContentPane().setForeground(Color.WHITE);
		setTitle("Alexandr Secret Message App");
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		getContentPane().setLayout(null);
		
		scrollPane = new JScrollPane();
		scrollPane.setBounds(10, 11, 563, 134);
		getContentPane().add(scrollPane);

		textIn = new JTextArea();  //необходимо удалить JTextArea в начале строки, так как добаивли всё сверху
		scrollPane.setViewportView(textIn);
		textIn.setWrapStyleWord(true);
		textIn.setLineWrap(true);
		textIn.setFont(new Font("Times New Roman", Font.PLAIN, 14));
		
		scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(10, 216, 563, 134);
		getContentPane().add(scrollPane_1);

		textOut = new JTextArea(); //необходимо удалить JTextArea в начале строки, так как добавили сверху
		scrollPane_1.setViewportView(textOut);
		textOut.setWrapStyleWord(true);
		textOut.setLineWrap(true);
		textOut.setFont(new Font("Times New Roman", Font.PLAIN, 14));

		textKey = new JTextField();
		textKey.addKeyListener(new KeyAdapter() {
			@Override
			public void keyReleased(KeyEvent e) {
				int key = Integer.parseInt(textKey.getText());
				slider.setValue(key);
			}
		});
		textKey.setFont(new Font("Tahoma", Font.BOLD, 11));
		textKey.setHorizontalAlignment(SwingConstants.CENTER);
		textKey.setText("1");
		textKey.setBounds(275, 178, 32, 20);
		getContentPane().add(textKey);
		textKey.setColumns(10);

		JLabel lblKey = new JLabel("Key:");
		lblKey.setHorizontalAlignment(SwingConstants.RIGHT);
		lblKey.setFont(new Font("Tahoma", Font.BOLD, 13));
		lblKey.setBounds(233, 180, 32, 14);
		getContentPane().add(lblKey);
// защита от записи букв вместо чисел
		/*JButton*/ btnEncodedecode = new JButton("Encode/Decode");
		btnEncodedecode.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					String message = textIn.getText();
					int key = Integer.parseInt(textKey.getText());
					String output = encode (message, key);
					textOut.setText(output);
				} catch (Exception ex){
					JOptionPane.showMessageDialog(null,"Please enter a whole number value for the encryption key.");
					textKey.requestFocus();
					textKey.selectAll();
				}
			}
		});
		btnEncodedecode.setBounds(317, 177, 137, 28);
		getContentPane().add(btnEncodedecode);
		
		slider = new JSlider();
		slider.addChangeListener(new ChangeListener() {
			public void stateChanged(ChangeEvent arg0) {
				textKey.setText("" + slider.getValue());
				String message = textIn.getText();
				int key = slider.getValue();
				String output = encode (message, key);
				textOut.setText(output);	
			}
		});
		slider.setValue(1);
		slider.setPaintTicks(true);
		slider.setMajorTickSpacing(13);
		slider.setMinorTickSpacing(1);
		slider.setMinimum(-26);
		slider.setMaximum(26);
		slider.setPaintLabels(true);
		slider.setBounds(23, 156, 200, 49);
		getContentPane().add(slider);
		
		btnMoveUp = new JButton("Move Up^");
		btnMoveUp.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String message = textOut.getText();
				textIn.setText(message);
				String output = textIn.getText();
				textOut.setText(output);
				slider.setValue(-slider.getValue());
				int key = slider.getValue();	
			}
		});
		btnMoveUp.setBounds(464, 177, 89, 28);
		getContentPane().add(btnMoveUp);
	}

	public static void main(String[] args) {
		SecretMessagesGUI theApp = new SecretMessagesGUI();
		theApp.setSize (new java.awt.Dimension (600,400));
		theApp.setVisible(true);

	}
}
