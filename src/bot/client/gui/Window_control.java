package bot.client.gui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JPanel;

import bot.client.event.ActionForWindow_control;
import bot.comm.Context;

public class Window_control extends JPanel{
	private static final long serialVersionUID = 1L;

	public Window_control(){
		ActionForWindow_control awc = new ActionForWindow_control();
		JButton back = new Bot_button(Context.window_control_back);
		back.setToolTipText("�������");
		back.addActionListener(awc);
		JButton min = new Bot_button(Context.window_control_min);
		min.setToolTipText("��С��");
		min.addActionListener(awc);
		JButton max = new Bot_button(Context.window_control_max);
		max.setToolTipText("���");
		max.addActionListener(awc);
		JButton close = new Bot_button(Context.window_control_close);
		close.setToolTipText("�ر�");
		close.addActionListener(awc);
		this.setOpaque(false);
		this.setLayout(new GridLayout(0,4));
		this.add(back);
		this.add(min);
		this.add(max);
		this.add(close);
	}
}
