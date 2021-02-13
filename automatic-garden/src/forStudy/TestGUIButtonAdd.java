package forStudy;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class TestGUIButtonAdd extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JPanel jp1 = new JPanel();
	private JPanel jp2 = new JPanel();
	private JPanel jp3 = new JPanel();
	private JPanel jp4 = new JPanel();
	private JPanel jp = new JPanel();
	private JButton btnAdd = new JButton("��");
	private JLabel lbl1 = new JLabel("�������һ��ʵ����");
	private JLabel lbl2 = new JLabel("������ڶ���ʵ����");
	private JLabel lblResult = new JLabel("��������");
	private JTextField txt1 = new JTextField(10);
	private JTextField txt2 = new JTextField(10);
	private JTextField txtResult = new JTextField(10);
	public String a;
	public TestGUIButtonAdd() {
		this.setLayout(new BorderLayout());
		this.setTitle("����ʵ�����");
		jp.setLayout(new BorderLayout());
		jp1.add(lbl1);
		jp1.add(txt1);
		jp.add(jp1, BorderLayout.NORTH);
		jp2.add(lbl2);
		jp2.add(txt2);
		jp.add(jp2, BorderLayout.CENTER);
		jp3.add(lblResult);
		jp3.add(txtResult);
		jp.add(jp3, BorderLayout.SOUTH);
		this.add(jp, BorderLayout.NORTH);
		btnAdd.addActionListener(this);
		jp4.add(btnAdd);
		this.add(jp4, BorderLayout.CENTER);
		this.setSize(300, 300);
		this.setLocationRelativeTo(null);
		this.setResizable(false);
		this.setVisible(true);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);	
	}

	public static void main(String[] args) {
		TestGUIButtonAdd T = new TestGUIButtonAdd();
		while(true) {
			if( T.a == null ){
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			else 
				break;				
		}
		System.out.println(T.a);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		
		double d1 = Double.valueOf(txt1.getText());	//��ȡ��һ���ı��������
		double d2 = Double.valueOf(txt2.getText());	//��ȡ�ڶ����ı��������
		double result = d1 + d2;//�ӷ�����
		String str = String.valueOf(result);
		txtResult.setText(str);//��������õ��������ı���	
		a = str;
	}
}