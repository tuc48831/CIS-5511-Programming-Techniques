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
public class Node<E> { //this is a simple node class for my linked list

	private E element;  //a generic element could contain anything, like an int or a string, etc.
	private Node<E> next;       //a pointer to another node called next
    private Node<E> prev;       //a pointer to another node called prev
	
	public Node(E e) {  //initialize a node with an argument to set it's element
		this.setElement(e);
		this.next = null;
        this.prev = null;
	}
	
	public E getElement() { //standard getter
		return element;
	}

	public void setElement(E element) { //standard setter
		this.element = element;
	}
	
	public Node<E> getNext() {
		return next;
	}

	public void setNext(Node<E> next) {
		this.next = next;
	}

	public Node<E> getPrev() {
		return prev;
	}

	public void setPrev(Node<E> prev) {
		this.prev = prev;
	}
}