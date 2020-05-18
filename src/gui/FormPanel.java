package gui;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.Border;

public class FormPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	private JButton zipFilesBtn, unzipFileBtn;
	private JTextField zipFileField, unzipFileField;
	private FormListener formListener;
	
	
	public FormPanel() {
		
		Border innnerBorder = BorderFactory.createTitledBorder("Huffman Compressor");
		Border outerBorder = BorderFactory.createEmptyBorder(5, 5, 5, 5);
		setBorder(BorderFactory.createCompoundBorder(outerBorder, innnerBorder));

		
		
		zipFilesBtn = new JButton("Zip Files...");
		unzipFileBtn = new JButton("Unzip File...");
		zipFilesBtn.setPreferredSize(new Dimension(120,20));
		unzipFileBtn.setPreferredSize(new Dimension(120,20));
		
		zipFilesBtn.addActionListener(e->{
			FormEvent ev = new FormEvent(this, 0);
			if(formListener !=null) {
				formListener.formEventOccurred(ev);
			}
		});
		
		unzipFileBtn.addActionListener(e->{
			FormEvent ev = new FormEvent(this, 1);
			if(formListener !=null) {
				formListener.formEventOccurred(ev);
			}
		});
		


		zipFileField = new JTextField(40);
		unzipFileField = new JTextField(40);
		zipFileField.setEditable(false);
		unzipFileField.setEditable(false);
		
	
		
		
		layoutComponents();
		
	}
	private void layoutComponents() {
		setLayout(new GridBagLayout());
		GridBagConstraints gc = new GridBagConstraints();
		
		//First row
		gc.gridy = 0;

		gc.gridx = 0;
		gc.weightx = 0.1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.NONE;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(0, 0, 0, 5);
		add(zipFilesBtn, gc);
				

		gc.gridx = 1;
		gc.weightx = 5;
		gc.insets = new Insets(0, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(zipFileField,gc);
		
		
		//Second row
		gc.gridy++;
		gc.gridx = 0;
		gc.weightx = 0.1;
		gc.weighty = 1;
		gc.fill = GridBagConstraints.CENTER;
		gc.anchor = GridBagConstraints.LINE_END;
		gc.insets = new Insets(2, 0, 0, 5);
		
		add(unzipFileBtn, gc);
				

		gc.gridx = 1;
		gc.weightx = 5;
		gc.insets = new Insets(2, 0, 0, 0);
		gc.anchor = GridBagConstraints.LINE_START;
		add(unzipFileField,gc);


		
	}
	public void setFormListener(FormListener formListener) {
		this.formListener = formListener;
	}
	
	public JTextField getChooseFileField() {
		return zipFileField;
	}
	public JTextField getSaveFileField() {
		return unzipFileField;
	}
	
	
	
	
}
