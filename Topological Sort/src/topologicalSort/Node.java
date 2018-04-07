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
 * @param <E>
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
		System.out.println("The node is: " + String.valueOf(this.getNumber()));
	}
}