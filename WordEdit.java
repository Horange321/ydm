package com.Horange.wordEdit;

import java.awt.FileDialog;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.SwingUtilities;
import javax.swing.undo.UndoManager;

public class WordEdit extends JFrame{
	private JButton neww = new JButton("new"), open = new JButton("open"), save = new JButton("save"), quit = new JButton("quit"), cx = new JButton("cx"), hf = new JButton("hf");
	private JTextArea te = new JTextArea(20, 40);
	private FileDialog op = new FileDialog(this), sa = new FileDialog(this);
	private ButtonListener newwl = new ButtonListener(0), openl = new ButtonListener(1), savel = new ButtonListener(2), quitl = new ButtonListener(3), cxl = new ButtonListener(4), hfl = new ButtonListener(5);
	private UndoManager um = new UndoManager();
	private String lass = new String("");
	class ButtonListener implements ActionListener{
		private Integer mm = new Integer(0);
		public ButtonListener(int m) {
			mm = m;
		}
		public void actionPerformed(ActionEvent e) {
			switch(mm) {
			case 0:
				if(!te.getText().equals(lass)) {
					Object[] opt = {"YES", "NO", "CANCEL"};
					switch(JOptionPane.showOptionDialog(null, "Do you want to save it?", "", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opt, opt[0])) {
					case 1:
						te.setText("");
						break;
					case 0:
						sav();
					}
				}
				else{
					te.setText("");
				}
				break;
			case 1:
				if(!te.getText().equals(lass)) {
					Object[] opt = {"YES", "NO", "CANCEL"};
					switch(JOptionPane.showOptionDialog(null, "Do you want to save it?", "", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opt, opt[0])) {
					case 1:
						opt();
						lass = te.getText();
						break;
					case 0:
						sav();
					}
				}
				else{
					opt();
					lass = te.getText();
				}
				break;
			case 2:
				sav();
				break;
			case 3:
				if(!te.getText().equals(lass)) {
					Object[] opt = {"YES", "NO", "CANCEL"};
					switch(JOptionPane.showOptionDialog(null, "Do you want to save it?", "", JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null, opt, opt[0])) {
					case 1:
						System.exit(0);
						break;
					case 0:
						sav();
					}
				}
				else System.exit(0);
				break;
			case 4:
				if(um.canUndo())
					um.undo();
				break;
			case 5:
				if(um.canRedo())
					um.redo();
				break;
			}
		}
	}
	public void opt() {
		op.setVisible(true);
		String path = op.getDirectory(), name = op.getFile();
		if(path == null || name == null) return;
		te.setText(null);
		File file = new File(path, name);
		try {
			BufferedReader buf = new BufferedReader(new FileReader(file));
			String line = null;
			while((line = buf.readLine()) != null)
				te.append(line+"\r\n");
			buf.close();
		}catch(FileNotFoundException e1) {
			e1.printStackTrace();
		}catch(IOException e2) {
			e2.printStackTrace();
		}
	}
	public void sav() {
		sa.setVisible(true);
		String pathh = sa.getDirectory(), namee = sa.getFile();
		if(pathh == null || namee == null) return;
		File filee = new File(pathh, namee);
		try {
			BufferedWriter buf = new BufferedWriter(new FileWriter(filee));
			buf.write(te.getText());
			buf.close();
		}catch(FileNotFoundException e1) {
			e1.printStackTrace();
		}catch(IOException e2) {
			e2.printStackTrace();
		}
	}
	public WordEdit() {
		te.getDocument().addUndoableEditListener(um);
		setSize(500, 500);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLayout(new FlowLayout());
		add(neww);
		add(open);
		add(save);
		add(cx);
		add(hf);
		add(quit);
		add(te);
		add(new JScrollPane(te));
		setVisible(true);
		neww.addActionListener(newwl);
		open.addActionListener(openl);
		save.addActionListener(savel);
		quit.addActionListener(quitl);
		cx.addActionListener(cxl);
		hf.addActionListener(hfl);
	}
	static WordEdit asd;
	public static void main(String[] args) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				asd = new WordEdit();
			}
		});
	}
}
