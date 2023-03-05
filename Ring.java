package Task1Asynchronous;

import java.util.ArrayList;
import java.util.Collections;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Ring {
	private Node head = null;
	private Node tail = null;
	private int size;
	public Ring() {

	}

//    this method add the node at the tail
	public void addNode(int id, int round) {
		Node newNode = new Node(id, round);

		// if list is empty, head and tail points to newNode
		if (head == null) {
			head = tail = newNode;
			// head's previous will be null
			head.previous = tail;
			// tail's next will be null
			tail.next = head;
		} else {
			// add newNode to the end of list. tail->next set to newNode
			tail.next = newNode;
			// newNode->previous set to tail
			newNode.previous = tail;
			// newNode becomes new tail
			tail = newNode;
			// tail's next point to null
			tail.next = head;
			head.previous = tail;
		}
	}

//	this method add node "after" a certain position
	public void addNode(int id, int round, int position) {
		Node newNode = new Node(id, round);
		Node place = findEle(position);
		if (place == null) {
			System.out.println("No such posiiton, node can not be added successfully.");
		} else {
			if (place.getId() == tail.getId()) {
//				tail = newNode;
				addNode(id, round);
			} else {
				newNode.next = place.next;
				place.next.previous = newNode;
				place.next = newNode;
				newNode.previous = place;
			}
			System.out.println("Node is added successfully!");
		}
	}

	public Node getHead() {
		return head;
	}

	public Node getTail() {
		return tail;
	}

	public boolean containELe(int searchValue) {
		Node currentNode = head;
		if (head == null) {
			return false;
		} else {
			do {
				if (currentNode.getId() == searchValue) {
					return true;
				}
				currentNode = currentNode.next;
			} while (currentNode != head);
			return false;
		}
	}

	public Node findEle(int searchValue) {
		Node currentNode = head;
		if (head == null) {
			return null;
		} else {
			do {
				if (currentNode.getId() == searchValue) {
					return currentNode;
				}
				currentNode = currentNode.next;
			} while (currentNode != head);
			return null;
		}

	}

	public void display() {
		Node current = head;
		if (head == null) {
			System.out.println("List is empty");
		} else {
			System.out.println("Nodes of the circular linked list: ");
			do {
				// Prints each node by incrementing pointer.
				System.out.print(" " + current.getId());
				current = current.next;
			} while (current != head);
			System.out.println();
		}
	}

	public int initialize() {
		size = 0;
		int type = 0;
		Scanner sc = new Scanner(System.in);
		// user inputs the size of the network
		System.out.println("Enter the size of the network(1 to 100): \nVisualization can not be done when size is greater than 10");
		try {
			size = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
		if (size > 100 || size <= 0) {
			throw new IllegalArgumentException("size should be in the range of 1 to 1100");
		}
		// user inputs the type of the network
		System.out.println("Enter the type of the network: (id sequence)");
		System.out.println("1. Random\t2.Ascending\t3.Descending");
		try {
			type = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
		if (type > 3 || type <= 0) {
			throw new IllegalArgumentException("the number should be in the range of 1 to 3");
		} else if (type == 1) {
			randomRing(size);
		} else if (type == 2) {
			ascendingRing(size);
		} else {
			descendingRing(size);
		}
		System.out.println("Enter the type of the network: (round sequence)");
		System.out.println("1. Random\t2.Ascending\t3.Descending");
		try {
			type = sc.nextInt();
		} catch (InputMismatchException e) {
			System.out.println(e.getMessage());
		}
		if (type > 3 || type <= 0) {
			throw new IllegalArgumentException("the number should be in the range of 1 to 3");
		} else if (type == 1) {
			this.setRandomRound();
		} else if (type == 2) {
			this.setAscendingRound();
		} else {
			this.setDescendingRound(size);
		}
		sc.close();
		return size;
	}

	// generate random rings
	private void randomRing(int size) {
		Ring ring = new Ring();
		ArrayList<Integer> ids = new ArrayList<Integer>();
		int id = 0;
		// generate ascending ids
		for (int i = 0; i < size; i++) {
			id = id + (int) (Math.random() * 3) + 1;
			ids.add(id);
		}
		Collections.shuffle(ids);
		for (int i = 0; i < size; i++) {
			int round = (int) (10 * Math.random() + 1);
			ring.addNode(ids.get(i), round);
		}
		this.head = ring.head;
		this.tail = ring.tail;
	}

	// generate ascending clockwise rings
	private void ascendingRing(int size) {
		Ring ring = new Ring();
		int id = 0;
		int round = 0;
		for (int i = 0; i < size; i++) {
			id = id + 3 * (int) (Math.random()) + 1;
			round = (int) (10 * Math.random() + 1);
			ring.addNode(id, round);
		}
		this.head = ring.head;
		this.tail = ring.tail;
	}

	// generate descending clockwise rings
	private void descendingRing(int size) {
		Ring ring = new Ring();
		int id = 0;
		int round = 0;
		int[] idList = new int[size];
		for (int i = 0; i < size; i++) {
			id = id + 3 * (int) (Math.random()) + 1;
			idList[i] = id;
		}
		for (int i = 0; i < size; i++) {
			int tmp_id = idList[size - i - 1];
			round = (int) (10 * Math.random() + 1);
			ring.addNode(tmp_id, round);
		}
		this.head = ring.head;
		this.tail = ring.tail;
	}
	public void setAscendingRound() {
		int round = 1;
		Node current = this.head;
		do {
			current.setRound(round);
			System.out.println("processor" + round + "'s ID: " + current.getId() + " Round: " + current.getRound());
			round++;
			current = current.next;
		}while (current != head);
	}
	public void setDescendingRound(int size) {
		int round = size;
		Node current = this.head;
		do {
			current.setRound(round);
			round--;
			System.out.println("processor" + round + "'s ID: " + current.getId() + " Round: " + current.getRound());
			current = current.next;
		}while (current != head);
		
	}
	
	public void setRandomRound() {
		Node current = this.head;
		int num = 1;
		do {
			System.out.println("processor" + num + "'s ID: " + current.getId() + " Round: " + current.getRound());
			num++;
			current = current.next;
		}while (current != head);
	}
	public void setHead(Node head) {
		this.head = head;
	}

	public void setTail(Node tail) {
		this.tail = tail;
	}
	
	
	

	
	
	
	
	
//	private void selectLeader() {
//		int round  = 0;
//		boolean terminate = false;
//		while (terminate == false) {
//			round +=1;
//			readAllId();
//			terminate = updateAllId(round);
//			
//		}
//		System.out.println("Round"+ (round));
//	}
//	
//	private void readAllId() {
//		Node current = head;
//		do {
//			current.readId();
//			current = current.next;
//		}while (current != head);
//	}
//	private boolean updateAllId(int round){
//		Node current = head;
//		boolean terminate = false;
//		do {
//			terminate = current.process(round);
//			current = current.next;
//		}while(current!=head && terminate == false);
//		return terminate;
//	}
//
//	public void findLeader() {
//		selectLeader();
//	}

}
