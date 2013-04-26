package bot.client.gui;

import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GraphicsEnvironment;

import javax.swing.JComboBox;
import javax.swing.JPanel;

import bot.client.event.Font_combobox_action;
import bot.comm.Context;

public class FontSetPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public FontSetPanel(){
		super();
		Font[] fonts=GraphicsEnvironment.getLocalGraphicsEnvironment().getAllFonts();
		String names[] = new String[fonts.length];
		for (int i = 0; i < fonts.length; i++) {
			names[i] = fonts[fonts.length-i-1].getName();
		}
		
		JComboBox font_name = new JComboBox(names);		//×ÖÌå
		for (int i = 0; i < font_name.getItemCount(); i++) {
			if(font_name.getItemAt(i).equals(Context.font_name_default)){
				font_name.setSelectedIndex(i);
				break;
			}
		}
		font_name.setPreferredSize(new Dimension(120,25));
		font_name.addActionListener(new Font_combobox_action(Context.font_name));
		
		
		String size[] = new String[20];
		for (int i = 0; i < size.length; i++) {
			size[i]=(5+i)+"";
		}
		JComboBox font_size = new JComboBox(size);
		for (int i = 0; i < font_size.getItemCount(); i++) {
			if(font_size.getItemAt(i).equals(Context.font_size_default)){
				font_size.setSelectedIndex(i);
				break;
			}
		}
		
		font_size.setPreferredSize(new Dimension(40,25));
		font_size.addActionListener(new Font_combobox_action(Context.font_size));
		
		Font_button bold = new Font_button(Context.font_bold);
		Font_button italic = new Font_button(Context.font_italic);
		Font_button underline = new Font_button(Context.font_underline);
		
		FlowLayout flt = new FlowLayout(FlowLayout.LEFT);
		flt.setVgap(0);
		this.setLayout(flt);
		this.add(font_name);
		this.add(font_size);
		this.add(bold);
		this.add(italic);
		this.add(underline);
		this.setOpaque(false);
		this.setBorder(null);
	}
}
