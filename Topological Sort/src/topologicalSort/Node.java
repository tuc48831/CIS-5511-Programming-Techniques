package topologicalSort;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */


/**
 *
 * @author tuc48831
 * I WROTE THISE CODE FOR A 2168 ASSIGNMENT
 * I AM JUST RE-USING IT
 * I WILL MAKE SURE TO RE-FAMILIARIZE MYSELF/COMMENT IT RELEVANT TO THIS ASSIGNMENT
 * @param <Integer>
 */
public class Node<Integer> { //this is a simple node class for my linked list

	private int number;  //a generic number could contain anything, like an int or a string, etc.
	private Node<Integer> next;       //a pointer to another node called next
    private Node<Integer> prev;       //a pointer to another node called prev
	
	public Node(int number) {  //initialize a node with an argument to set it's number
		this.setNumber(number);
		this.next = null;
        this.prev = null;
	}
	
	public Node() {	//initialize a null node
		this.setNext(null);
		this.setPrev(null);
	}

	public int getNumber() { //standard getter
		return number;
	}

	public void setNumber(int number) { //standard setter
		this.number = number;
	}
	
	public Node<Integer> getNext() {
		return next;
	}

	public void setNext(Node<Integer> next) {
		this.next = next;
	}

	public Node<Integer> getPrev() {
		return prev;
	}

	public void setPrev(Node<Integer> prev) {
		this.prev = prev;
	}
	
	public void printNode() {
		System.out.print("The node is: " + String.valueOf(this.getNumber()));
		if(this.next != null) {
			System.out.print(" and it's next is: " + String.valueOf(this.next.getNumber()));
		}
		if(this.prev != null) {
			System.out.print(" and it's prev is: " + String.valueOf(this.prev.getNumber()));
		}
		System.out.print("\n");
	}
	
	public void testing() {
		//test create a null node && test printing the node
		System.out.println("creating node with no input");
		Node testNode = new Node();
		testNode.printNode();
		testNode = null;
		//test create node with values
		System.out.println("creating first node with 0 as number");
		testNode = new Node(0);
		testNode.printNode();
		//test setting the number
		System.out.println("Setting node's number to 1 and printing it");
		testNode.setNumber(1);
		testNode.printNode();
		
		System.out.println("Setting node's number to 2 and printing it");
		testNode.setNumber(2);
		testNode.printNode();
		//test getting the number
		System.out.println("getNumber on first node should be 2 and is: " + String.valueOf(testNode.getNumber()));
		//test making multiple nodes and setting next/prev
		System.out.println("Creating a second node with the number 4");
		Node secondNode = new Node(4);
		secondNode.printNode();
		
		System.out.println("setting first node's next to the second node");
		testNode.setNext(secondNode);
		testNode.printNode();
		
		System.out.println("setting first node's prev to the second node");
		testNode.setPrev(secondNode);
		testNode.printNode();
		//test get next/get prev
		
		
		System.out.println("End of testing for Node");
	}
}