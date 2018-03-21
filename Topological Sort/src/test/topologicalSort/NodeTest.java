/**
 * 
 */
package test.topologicalSort;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import topologicalSort.Node;

/**
 * @author Peter
 * @param <E>
 *
 */

class NodeTest<E> {
	private Node<E> firstNode;
	private Node<E> secondNode;
	private Node<E> thirdNode;
	
	/**
	 * @throws java.lang.Exception
	 */
	@BeforeEach
	void setUp() throws Exception {
		firstNode = new Node<E>(null);
		secondNode = new Node<E>(null);
		thirdNode = new Node<E>(null);
	}

	/**
	 * @throws java.lang.Exception
	 */
	@AfterEach
	void tearDown() throws Exception {
		firstNode = null;
		secondNode = null;
		thirdNode = null;
	}

	/**
	 * Test method for {@link topologicalSort.Node#Node()}.
	 */
	@Test
	void testNode() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link topologicalSort.Node#Node(java.lang.Object)}.
	 */
	@Test
	void testNodeE() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link topologicalSort.Node#getElement()}.
	 */
	@Test
	void testGetElement() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link topologicalSort.Node#setElement(java.lang.Object)}.
	 */
	@Test
	void testSetElement() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link topologicalSort.Node#getNext()}.
	 */
	@Test
	void testGetNext() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link topologicalSort.Node#setNext(topologicalSort.Node)}.
	 */
	@Test
	void testSetNext() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link topologicalSort.Node#getPrev()}.
	 */
	@Test
	void testGetPrev() {
		fail("Not yet implemented"); // TODO
	}

	/**
	 * Test method for {@link topologicalSort.Node#setPrev(topologicalSort.Node)}.
	 */
	@Test
	void testSetPrev() {
		fail("Not yet implemented"); // TODO
	}

}
