package forStudy;

import java.awt.Color;
import javax.swing.JFrame;

public class Test extends JFrame
{
	public static void main(String[] args) {
	   new Test();
	}
	
	
	public Test()
	{
	   this.setSize(400,300);
	   this.setLocation(400,300);
	   this.setBackground(Color.blue);
	   this.getContentPane().setBackground(Color.GREEN);
	   this.getContentPane().setVisible(true);//�����Ϊtrue��ô�ͱ���˺�ɫ��
	   this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	   this.setVisible(true);
	}
}