package bot.client.gui;

import javax.swing.JButton;
import javax.swing.border.BevelBorder;

import bot.client.event.Button_effect;

public class Bot_button extends JButton{
	private static final long serialVersionUID = 1L;

	public Bot_button(String str){
		super(str);
		this.addMouseListener((new Button_effect()));
		this.setBorder(new BevelBorder(BevelBorder.RAISED));
		this.setContentAreaFilled(false);
		this.setBorderPainted(false);
	}
}
