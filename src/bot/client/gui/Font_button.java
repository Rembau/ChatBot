package bot.client.gui;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JLabel;
import javax.swing.SwingConstants;

import bot.client.event.Font_button_effect;

public class Font_button extends JLabel{
	private static final long serialVersionUID = 1L;
	public Font_button(String str){
		super(str,SwingConstants.CENTER);
		this.addMouseListener(new Font_button_effect());
		this.setBorder(null);
		//this.setContentAreaFilled(false);
		this.setPreferredSize(new Dimension(25,25));
	}
	public void paint(Graphics g){
		super.paint(g);
	}
}
