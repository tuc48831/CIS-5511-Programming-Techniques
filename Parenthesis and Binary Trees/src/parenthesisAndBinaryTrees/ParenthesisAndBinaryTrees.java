package parenthesisAndBinaryTrees;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

public class ParenthesisAndBinaryTrees {
	
	int[] L;			//the left array
	int[] R;			//the right array
	int numNodes;		//the number of nodes
	int current = 0;	//the node i'm currently on, always starts at 0 for each instance of the problem
	int numTrees = 0;
	//a constructor that takes the number of nodes as an arg
	public ParenthesisAndBinaryTrees(int numNodes) {
		this.numNodes = numNodes;						//it sets the number of nodes
		
		this.L = new int[numNodes];						//create the left array
		this.R = new int[numNodes];						//create the right array
		//since the problem starts out as a max right tree
		for(int i = 0; i < this.numNodes - 1; i++) {	//iterate up to n - 1
			this.R[i] = i + 1;							//set the element of the right array to the next node
		}
		this.current = numNodes - 1;					//set the current as the final node
	}
	//a function to print the Left and Right arrays, as well as the 1's and 0's representation AND a pre-order traversal
	public void print() {
		this.numTrees++;
		System.out.println("This is tree number: " + Integer.toString(this.numTrees));
		System.out.println("The left  array is: \t" + Arrays.toString(this.L));
		System.out.println("The right array is: \t" + Arrays.toString(this.R));
		System.out.print("The ones and zeroes for this tree are: \t");
		this.generate1s0s();
		
	}
	public void generate1s0s(){
		String sequence = "";
		//assign p to given arg t
		int p = 0;
		//have node q and rtptr
		int q = -1;
		int rtptr = -1;
		//create stack of nodes s
		Stack<Integer> s = new Stack<Integer>();
		//while ((p is not null)||(s isn’t empty))
		boolean oneTime = false;
		while ( (p != -1) || (!s.isEmpty()) ){
			oneTime = true;
			if(p == this.numNodes - 1) {
				sequence += "10";
				for(int i = s.size(); i >= 0; i--) {
					sequence += "0";
				}
				break;
			}
			//if (p is not null)
			if (p != -1){
				//visit(p),     this visit just prints the node's info, its left and right
				sequence += "1";
				
				//push(p, s)
				s.push(p); 
				//if(left(p) is not null))
				if (this.hasLeft(p)) {
					//p = left(p)
					p = this.L[p];
				}else{
					//p = right(p)
					sequence += "0";
					if(this.hasRight(p)){
						p = this.R[p];
					}else{
						sequence += "0";
						p = -1;
					}
				}
			}else{
				do{
					//q = pop(s)
					
					q = s.pop();
					//if(s isn't empty)
					if ( (!s.isEmpty()) ){
						//rtptr = right(item(s))
						if(this.hasRight(s.peek())) {
							rtptr = this.R[s.peek()];
						}else {
							rtptr = -1;
							if(oneTime) {
								sequence += "0";
								oneTime = false;
							}
						}
					//else
					}else{
						//rtptr = null
						rtptr = -1;
					}
				//while (s isn't empty and q is rtptr)
				}while( !s.isEmpty() && (q == rtptr) );
				//p = rtptr
				
				p = rtptr;   
			}
		}
		sequence = sequence.substring(0, this.numNodes * 2);
		System.out.print(sequence);
		System.out.println();
	}
	
	//a function to check if the array is only a left subtree
	public boolean checkAllLefts(){
		for(int i = 0; i < this.numNodes; i++) {
			if(this.R[i] != 0) {
				return false;
			}
		}
		return true;
	}
	//this function takes as input a number corresponding to a node
	//it returns the number of the parent node, or -1 if no such node exists or it is out of bounds
	public int findParent(int input){
		if(input >= this.numNodes || input < 0) {
			return -1;
		}else{
			for(int i = 0; i < this.numNodes; i++) {
				if(this.L[i] == input || this.R[i] == input) {
					return i;
				}
			}
		}
		return -1;
	}
	//a boolean to return true if the input node has a left, false otherwise
	public boolean hasLeft(int input){
		if(this.L[input] != 0) {
			return true;
		}
		return false;
	}
	//a boolean to return true if the input node has a right, false otherwise
	public boolean hasRight(int input){
		if(this.R[input] != 0) {
			return true;
		}
		return false;
	}
	//a boolean to return true if the input node is a leaf
	public boolean isLeaf(int input){
		if(!hasLeft(input) && !hasRight(input)) {
			return true;
		}
		return false;
	}
	//a function to reset things to a right only subtree from after input to the end
	public void resetBeyond(int input){
		if(input == 0) {
			return;
		}
		int newNode = input + 1;
		//if newNode isnt out of bounds
		while(newNode < this.numNodes - 1) {
			this.L[newNode] = 0;
			this.R[newNode] = newNode + 1;
			newNode++;
		}
	}

	//this is void because it doesn't return the next tree
	//it just edits the arrays to move to the next iteration of the problem so I don't have to make a new object
	public boolean nextTree() {
		int parent = this.findParent(current);
		//4 cases for current, and current's parent's properties
		//current is a right child, parent has no left
		if(this.R[parent] == current && this.L[parent] == 0){
			//get current's left and store it as temp
			int tempNode = this.L[current];
			//move to the left of the parent, reset parent's right
			this.L[parent] = current;
			this.R[parent] = 0;
			//reset current's pointers
			this.L[current] = 0;
			this.R[current] = 0;
			//get the topright
			int topRight = 0;
			while(this.hasRight(topRight)) {
				topRight = this.R[topRight];
			}
			//set the topright's right to the captured node
			this.R[topRight] = tempNode;
			//reset the all left subtree beyond current to an all right subtree
			this.resetBeyond(current);
			//set current to n
			this.current = this.numNodes - 1;
			//return true that the tree has changed
			return true;
		//current is a right child, parent has a left	
		}else if(this.R[parent] == current) {
			//i know there is a node to the parent's left
			int temp = this.L[parent];
			//get the rightmost node from the parent's left
			while(this.hasRight(temp)) {
				temp = this.R[temp];
			}
			//place current at its new location, remove current from end
			this.R[temp] = current;
			this.R[parent] = 0;
			
			//capture current's left, reset current's pointers
			int tempNode = this.L[current];
			this.L[current] = 0;
			this.R[current] = 0;
			//get the topright
			int topRight = 0;
			while(this.hasRight(topRight)) {
				topRight = this.R[topRight];
			}
			//set the topright's right to the captured node
			this.R[topRight] = tempNode;
			//reset the topright's right subtree
			this.resetBeyond(current);
			//reset current to the last node
			current = this.numNodes - 1;
			//return true because it is now a different tree
			return true;
		//current is a left child, parent has no right
		}else if(this.L[parent] == current && this.R[parent] == 0){
			this.current--;
			return false;
		//current is a left child, parent has a right
		}else if(this.L[parent] == current){
			//i never reach this case
			//its unnecessary
		//else something broke incredibly
		}else{
			System.out.println("Something went incredibly wrong");
			System.exit(0);
		}
		return false;
	}

	public void runProblem() {
		//print the first iteration
		this.print();
		//while i haven't reached the left only binary tree
		while(!this.checkAllLefts()) {
			//try to find the next tree, if it returns true that the tree has changed
			if(this.nextTree()) {
				//print the new tree
				this.print();
			}
			//else continue
		}
	}
	
	public static void main(String[] args) {
		//ask for input
		//while keyboard has next int
			//capture the int input
			//run the problem for that int
		System.out.println("Please Enter the number of nodes to do...");
        Scanner kb = new Scanner(System.in);
        int nextInt = kb.nextInt();
		ParenthesisAndBinaryTrees temp = new ParenthesisAndBinaryTrees(nextInt);
		kb.close();
		//temp.print();
		//System.out.println(temp.findParent(2));
		temp.runProblem();
	}
}
