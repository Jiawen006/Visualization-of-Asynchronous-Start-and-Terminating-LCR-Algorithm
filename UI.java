package Task1Asynchronous;

import java.util.ArrayList;

import javax.swing.*;
import java.awt.*;

public class UI extends JFrame {
	private int radius = 150;
	private int ringSize = 0;
	private int centerX = 200;
	private int centerY = 200;
	private int processorSize = 30;
	private final double PI = Math.PI;
	JFrame frame = new JFrame();
    JPanel panel = (JPanel) frame.getContentPane();

	private JLabel roundDisplay;
	private JLabel messageSent;
	private JLabel broadcastMessage;
	private JLabel colorInfo;
	private JLabel hint;
	
	private ArrayList<JLabel> labelId = new ArrayList<JLabel>();
	private ArrayList<JLabel> inID = new ArrayList<JLabel>();
	
	
	public UI(Ring input, int ringsize,SystemCounter counter) {
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setTitle("Asynchronous LCR, clockwise message sent version");
	    panel.setLayout(null);
	    this.ringSize = ringsize;
	    radius = ringsize * 25;

		
	    InitializeBasic(counter);
		InitializeNode(input);
		getContentPane().add(panel);
	}
		public void paint(Graphics g) {
			super.paint(g);
			double x;
			double y;
			//g.drawOval(200, 200, 2 * radius, 2*  radius);

			g.setColor(Color.red);
			for (int i = 0; i < ringSize; i++) {
				x = centerX+ radius + Math.cos((float) i / ringSize * 2 * PI) * radius;
				y = centerY + radius + Math.sin((float) i / ringSize * 2 * PI) * radius;
//				System.out.println("i=" + i + " x=" + x + " y=" + y);
				g.drawOval((int) x, (int) y, this.processorSize, this.processorSize);
				JLabel label = labelId.get(i);
				panel.add(label);
				Dimension size = label.getPreferredSize();
				label.setBounds((int)(x-1.3*processorSize), (int)(y+0.2*processorSize), 2*size.width,size.height);
				JLabel in = inID.get(i);
				panel.add(in);
				size = in.getPreferredSize();
				in.setBounds((int)(x-0.4*processorSize), (int)(y-1.5*processorSize), 2*size.width,size.height);
				
//				JLabel label1 = new JLabel(Integer.toString(i));
//				panel.add(label1);
//				Dimension size = roundDisplay.getPreferredSize();
//				label1.setBounds((int)(x+0.1*radius), (int)(y-0.2*radius), size.width,size.height);

			}


		}
		
		public void paintComponent (Graphics g) {
			super.paintComponents(g);

		}

		private void addString(Graphics g,String id,int centerX, int centerY) {
			g.drawString(id, centerX, centerY);
		}
		private void updateFrame(Graphics g) {
			repaint();
		}
		

		private void InitializeBasic(SystemCounter counter) {
			this.roundDisplay = new JLabel(roundFormat(counter.getRoundCounter()));
			roundDisplay.setFont(new Font("Serif", Font.PLAIN, 20));
			panel.add(roundDisplay);
			Dimension size = roundDisplay.getPreferredSize();
			roundDisplay.setBounds(20, 20, 2*size.width, size.height);

			this.messageSent = new JLabel(messageFormat(counter.getMessage()));
			messageSent.setFont(new Font("Serif", Font.PLAIN, 20));
			panel.add(messageSent);
			size = messageSent.getPreferredSize();
			messageSent.setBounds(20, 40, 2*size.width, size.height);

			this.broadcastMessage = new JLabel(broadcastFormat(counter.getExtraRound(),counter.getExtraMessage()));
			panel.add(broadcastMessage);
			size = broadcastMessage.getPreferredSize();
			broadcastMessage.setBounds(20, 60, 2*size.width, size.height);
			
			
			this.hint = new JLabel("Hint: ID denote processor ID. R denote the round it would wake. T means awake while F means asleep."
					+ " See terminal for final result.");
			panel.add(hint);
			size = hint.getPreferredSize();
			hint.setBounds(20, 80, 2*size.width, size.height);
			this.colorInfo = new JLabel("<html><font color='green'>Processor Sleep </font> <font color='black'> Leader not known </font> <font color='Blue'>Is not the leader </font><font color='Red'>Is Leader </font></html>");
			//this.colorInfo = new JLabel("Label Color: Black(Processor sleep), Brown(Processor is executing), Blue(Processor know it is not the leader), Red(Processor is the leader)");
			panel.add(colorInfo);
			size = colorInfo.getPreferredSize();
			colorInfo.setBounds(20, 100, 2*size.width, size.height);
			
		}
		private void InitializeNode(Ring ring) {
			Node current = ring.getHead();
			do {
				JLabel processorInfo = new JLabel(processorFormat(current));
				processorInfo.setForeground(Color.GREEN);
				JLabel inID = new JLabel(inIDFormat(current));
				this.labelId.add(processorInfo);
				this.inID.add(inID);
				current = current.next;
			}while(current != ring.getHead());
		}
		private String roundFormat(int round) {
			return "Round: "+ round;
		}

		private String messageFormat(int message) {
			return "Message sent: " + message;
		}

		private String broadcastFormat(int round, int message) {
			return "Broadcast Round: " + round + " Broadcast message: " + message;
		}
		
		private String processorFormat(Node node) {
			String awake = node.getisAwake()? "T": "F";
			return "ID:" + node.getId() +" R:" + node.getRound() + " Awake:" + awake;
		}
		
		private String inIDFormat(Node node) {
			int inID = node.getInId();
			if (inID==0) {
				return "In ID: null";
			}else {
				return "In ID: " + inID;
			}
		}
		
		public void updateInId(Node head) {
			Node current = head;
			int idx = 0;
			do {
				inID.get(idx).setText(inIDFormat(current));
				System.out.println("idx " + idx+"\t" +inID.get(idx).getText());
				idx+=1;
				current = current.next;
			}while(current != head);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>");
		}
		public void updateInfo(Node head,SystemCounter counter) {
			Node current = head;
			int idx = 0;
			do {
				labelId.get(idx).setText(processorFormat(current));
				System.out.println("idx " + idx+"\t" +labelId.get(idx).getText());
				if(current.getisAwake()) {
					labelId.get(idx).setForeground(Color.black);
				}
				if(current.getisLeader() == 1) {
					labelId.get(idx).setForeground(Color.BLUE);
				}
				if(current.getisLeader() == 2) {
					labelId.get(idx).setForeground(Color.RED);
				}
				idx+=1;
				current = current.next;
			}while(current != head);
			System.out.println(">>>>>>>>>>>>>>>>>>>>>>");
			roundDisplay.setText(roundFormat(counter.getRoundCounter()));
			messageSent.setText(messageFormat(counter.getMessage()));
			broadcastMessage.setText(broadcastFormat(counter.getExtraRound(),counter.getExtraMessage()));
		}
		public int getringSize() {
			return this.ringSize;
		}


//	public static void main(String[] args) {
//		RingDistribution f = new RingDistribution(150,200,200, 10,30);
//		f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//		f.setSize(600, 600);
//		f.show();
//	}
}


