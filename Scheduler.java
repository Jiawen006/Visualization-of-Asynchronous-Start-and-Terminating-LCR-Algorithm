package Task1Asynchronous;

public class Scheduler {
	Ring ring;
	SystemCounter counter;
	private Node head = null;
	private Node tail = null;
	public Scheduler(Ring input) {
		this.ring = input;
		head = ring.getHead();
		tail = ring.getTail();
		counter = new SystemCounter();
	}
	public void LCR(UI visualize) {
		selectLeader(visualize);
		printResult();
	}
	public void LCR() {
		selectLeader();
		printResult();
	}
	
	private void printResult() {
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("Round: "+ (counter.getRoundCounter()));
		System.out.println("Message Sent: " + (counter.getMessage()));
		System.out.println("Termination extra round: "+ counter.getExtraRound());
		System.out.println("Termination extra message: " + counter.getExtraMessage());
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
		System.out.println("Total Round: " + ((counter.getRoundCounter())+ counter.getExtraRound()));
		System.out.println("Total Message Sent: "+ (counter.getMessage()+ counter.getExtraMessage()));
	}
	private void selectLeader() {
		boolean terminate = false;
		while (terminate == false) {
			counter.addRound();
			readAllId();
			terminate = updateAllId(counter.getRoundCounter());
		}
	}
	
	private boolean updateAllId(int round){
		Node current = head;
		boolean terminate = false;
		do {
			terminate= current.process(round,this.counter);
			current = current.next;
			counter.setLeaderFlag(terminate);
		}while(current!=head && terminate == false);
		return terminate;
	}
	private void readAllId() {
		Node current = head;
		do {
			current.readId();
			current = current.next;
		}while (current != head);
	}
	
	private void selectLeader(UI visualize) {
		
		boolean terminate = false;
		while (terminate == false) {
			counter.addRound();
			readAllId(visualize);
			terminate = updateAllId(counter.getRoundCounter(),visualize);
		}
		
	}
	
	private void readAllId(UI visualize) {
		Node current = head;
		do {
			current.readId();
			current = current.next;
		}while (current != head);
		int size = visualize.getringSize();
		
		visualize.updateInId(head);
		
		try {
			Thread.sleep(1000);
		}catch(InterruptedException ex){
			Thread.currentThread().interrupt();
		}
	}
	
	private boolean updateAllId(int round,UI visualize){
		Node current = head;
		boolean terminate = false;
		do {
			terminate= current.process(round,this.counter);
			current = current.next;
			counter.setLeaderFlag(terminate);
		}while(current!=head && terminate == false);
		int size = visualize.getringSize();
		visualize.updateInfo(head,this.counter);
		
		try {
			Thread.sleep(1000);
		}catch(InterruptedException ex){
			Thread.currentThread().interrupt();
		}
		return terminate;
	}
	public SystemCounter getSystemCounter() {
		return this.counter;
	}
}
