package Task1Asynchronous;

import javax.swing.JLabel;

public class Node {
	private int id;
	private int round;
	private int readId = 0;
	private int inId = 0;
	private boolean isAwake = false;
	private boolean preAwake = false;
	private boolean nextAwake = false;
	private boolean sendItself = false;
	
	// 0 denote unknown, 1 denote not leader,
	// 2 denote is leader
	private int isLeader = 0;
	int leaderId = 0;
	Node next;
	Node previous;
	
	//this parameter is for UI display
	private JLabel output = new JLabel();;
	private String text;

	public Node(int id, int round) {
		this.id = id;
		this.round = round;
		this.text = "R: " + this.round + "ID: " + this.id + "A:" + this.isAwake ;
		output.setText(this.text);
	}

	private boolean sendMessage(int sendId, SystemCounter counter) {
		// we only implement one direction here, dual direction send will be designed
		// later on.
		if (nextAwake == true) {
			// only transfer when the next node is awake
			next.setReadId(sendId);
			counter.addCounter();
			return true;
		} else {
			// do nothing
			return false;
		}
	}

	private int lastReadId = 0;

	public void readId() {
		if (isAwake == true) {
			this.lastReadId = inId;
			this.inId = readId;
		} else {
			// did not wake, do nothing
		}
	}
//	if this method is run, it is awake
//	private void receiveMessage(int inId) {
//		this.inId = inId;
//	}

	public boolean process(int round, SystemCounter counter) {
		if (isAwake != true) {
			checkAwake(round,counter);
			return false;
		} else {
			// the processor is awake
			if (sendItself == false) {
				if (inId == 0) {
					sendItself = sendMessage(id, counter);
					return false;
				} else {
					int sendId = (int) (Math.max(inId, id));
					sendItself = sendMessage(sendId, counter);
					isLeader = (sendId == id) ? 0 : 1;
					return false;
				}
			} else {
				if (inId != 0) {
					if (id < inId) {
						if (lastReadId != inId) {
							// node is not the leader and send the larger id to next node
							isLeader = 1;
							sendMessage(inId, counter);
							return false;
						} else {
							// do nothing
							return false;
						}

					} else if (id == inId) {
						isLeader = 2;
						broadcastLeader(id, counter);
						return true;
					} else {
						return false;
						// do nothing
					}
				} else {
					return false;
					// do nothing
				}
			}
		}

	}

//	private void broadcastLeader(int leaderId, SystemCounter counter) {
//		Node currentNode = this;
//		do {
//			currentNode.setLeader(leaderId);
//			currentNode = currentNode.next;
//			counter.addExtraMessage();
//			counter.addExtraRound();
//		} while (currentNode != this);
//		counter.addExtraRound();
//		System.out.println("Leader selected. Leader ID is " + id);
//	}
	
	private void broadcastLeader(int leaderId, SystemCounter counter) {
		Node preNode = this.previous;
		Node nextNode = this.next;
		while (preNode.leaderId == 0 || nextNode.leaderId==0) {
			preNode.leaderId = leaderId;
			nextNode.leaderId = leaderId;
			preNode = preNode.previous;
			nextNode = nextNode.next;
			counter.addExtraMessage();
			counter.addExtraMessage();
			counter.addExtraRound();
		}
		
	}

	private void checkAwake(int inputRound, SystemCounter counter) {
//		calculate whether this round will awake
		isAwake = (inputRound < round) ? false : true;
		if (isAwake == true) {
//			broadccast to the neighbour that it is awake
			broadcastAwake(counter);
		} else {
//			do nothing
		}
	}

	public void broadcastAwake(SystemCounter counter) {
		this.previous.setnextAwake();
		this.next.setpreAwake();
		if(this.previous == this.next) {
			counter.addCounter();
		}else {
			counter.addCounter();
			counter.addCounter();
		}
		
	}

//	think about public and private
	public int getId() {
		return id;
	}
	public boolean getisAwake() {
		return isAwake;
	}

	public void setpreAwake() {
		preAwake = true;
	}

	public void setnextAwake() {
		nextAwake = true;
	}

	public int getisLeader() {
		return isLeader;
	}

	public void setReadId(int readId) {
		this.readId = readId;
	}

	public int getReadId() {
		return readId;
	}

	public void setInId(int inId) {
		this.inId = inId;
	}

	public int getInId() {
		return inId;
	}

	public void setLeader(int leaderId) {
		this.leaderId = leaderId;
	}

	public void setRound(int round) {
		this.round = round;
	}
	public int getLeaderId() {
		return this.leaderId;
	}
	public void setLeaderId(int leaderId) {
		this.leaderId = leaderId;
	}
	public int getRound() {
		return this.round;
	}
}
