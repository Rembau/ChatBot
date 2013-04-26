package bot.client.gui;

import java.awt.Color;

import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;

import bot.comm.Context;

public class FontType extends SimpleAttributeSet{
	private static final long serialVersionUID = 1L;
	
	public FontType(){
		StyleConstants.setFontSize(this, Integer.valueOf(Context.font_size_default));
		StyleConstants.setLeftIndent(this, 20);
		StyleConstants.setFontFamily(this, Context.font_name_default);
	}
	public FontType(String name){
		StyleConstants.setForeground(this, Color.blue);
		StyleConstants.setFontSize(this, Integer.valueOf(Context.font_size_default));
	}
	public void setSize(int size){
		StyleConstants.setFontSize(this, size);
	}
	public void setFontColor(Color color){
		StyleConstants.setForeground(this, color);
	}
	public void setItalic(boolean b){
		StyleConstants.setItalic(this, b);
	}
	public void setBold(boolean b){
		StyleConstants.setBold(this, b);
	}
	public void setUnderline(boolean b){
		StyleConstants.setUnderline(this, b);
	}
	public void setFontFamily(String name){
		StyleConstants.setFontFamily(this, name);
	}
}
