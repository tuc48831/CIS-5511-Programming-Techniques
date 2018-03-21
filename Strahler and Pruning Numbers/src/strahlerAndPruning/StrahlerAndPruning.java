package strahlerAndPruning;

import java.util.Arrays;
import java.util.Scanner;
import java.util.Stack;

//1. Modify the program from Assignment 5 that generated all binary trees with n nodes so as it generates each tree it determines the Strahler number of the tree and outputs the number of n node binary trees that have each possible Strahler number. Test the program on some sample trees and output information so we can tell what the tree was and what its Strahler number was. Produce a table for n from 2 to 15(or as high as you can get) of the number of binary trees with n nodes that have each possible Strahler number. 
//2. Using the binary tree representation for forests of trees, again generate all the n node binary trees(which is the same as generating all the n node forests) and determine how many n node forests have each possible pruning number. Produce a table as in part 1 but for pruning numbers for n node forests rather than for Strahler numbers for n node binary trees. 
//3. What do you see when you look at these two tables? 

public class StrahlerAndPruning {
	
	int[] L;			//the left array
	int[] R;			//the right array
	int[] strahlerRecord;	//the array to record the various strahler numbers of the trees
	int[] pruningRecord;
	int numNodes;		//the number of nodes
	int current = 0;	//the node i'm currently on, always starts at 0 for each instance of the problem
	int numTrees = 0;
	//a constructor that takes the number of nodes as an arg
	public StrahlerAndPruning(int numNodes) {
		this.numNodes = numNodes;						//it sets the number of nodes
		
		this.L = new int[numNodes];						//create the left array
		this.R = new int[numNodes];						//create the right array
		//since the problem starts out as a max right tree
		for(int i = 0; i < this.numNodes - 1; i++) {	//iterate up to n - 1
			this.R[i] = i + 1;							//set the element of the right array to the next node
		}
		this.strahlerRecord = new int[log(numNodes + 1, 2)];
		this.pruningRecord = new int[log(numNodes + 1, 2)];
		//System.out.println("the maximum strahler number for numNodes: " + Integer.toString(numNodes) + " is: " + Integer.toString(log(numNodes + 1, 2)));
		this.current = numNodes - 1;
	}
	//a function to print the Left and Right arrays, as well as the 1's and 0's representation AND a pre-order traversal
	public void print() {
		this.numTrees++;
		//System.out.println("This is tree number: " + Integer.toString(this.numTrees));
		//System.out.println("The left  array is: \t" + Arrays.toString(this.L));
		//System.out.println("The right array is: \t" + Arrays.toString(this.R));
		this.recordStrahlerNum();
		this.recordPruningNum();
		//System.out.print("The ones and zeroes for this tree are: \t");
		//this.generate1s0s();
		//System.out.println("The current list of strahler numbers is: " + Arrays.toString(this.strahlerRecord));
	}
	
	public void printTest() {
		this.numTrees++;
		System.out.println("This is tree number: " + Integer.toString(this.numTrees));
		System.out.println("The left  array is: \t" + Arrays.toString(this.L));
		System.out.println("The right array is: \t" + Arrays.toString(this.R));
		this.recordStrahlerNum();
		this.recordPruningNum();
		System.out.print("The ones and zeroes for this tree are: \t");
		this.generate1s0s();
		System.out.println("The current strahler number is: " + Integer.toString(this.getStrahlerNum(0)));
		System.out.println("The current pruning number is: " + Integer.toString(this.getPruningNum(0)));
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
	
	static int log(int x, int base) {
		return (int) (Math.log(x) / Math.log(base));
	}
	
	public void recordStrahlerNum() {
		//code to figure out strahler number and update array
		int strahNum = this.getStrahlerNum(0);
		//System.out.println("The strahler number of the current tree is: " + Integer.toString(strahNum));
		this.strahlerRecord[strahNum - 1]++;
	}
	
	public int getStrahlerNum(int input) {
		if(this.isLeaf(input)) {//if it's a leaf, return 1
			return 1;
		}else if(this.hasTwoChildren(input)){//if it has 2 children, get their numbers. if the nums are equal, return num + 1, else return max or 2 numbers
			int left = this.getStrahlerNum(this.L[input]);
			int right = this.getStrahlerNum(this.R[input]);
			if(left == right) {
				return (left + 1);
			}
			//else return the max of left and right
			return Math.max(left, right);
		}else if(this.hasLeft(input)) { //only has left, return that strahler number
			return this.getStrahlerNum(this.L[input]);
		}else{ //must have right, return that strahler number
			return this.getStrahlerNum(this.R[input]);
		}
	}
	
	public void recordPruningNum() {
		//code to get pruning num and update array
		int prunNum = this.getPruningNum(0);
		//System.out.println("The pruning number of the current tree is: " + Integer.toString(prunNum));
		this.pruningRecord[prunNum - 1]++;
	}
	
	public int getPruningNum(int input) {
		if(this.isLeaf(input)) {
			return 1;
		}
		int[] listToCompare = this.getRightList(input);
		//System.out.println("list to compare is: " + Arrays.toString(listToCompare));
		//iterate through the list, changing the numbers to their pruning number
		for(int i = 0; i < listToCompare.length; i++) {
			if(this.L[listToCompare[i]] == 0) {//if it has no left, it is a leaf, make it 1
				listToCompare[i] = 1;
			}else { //else get the pruning number for the current node
				//System.out.println("i is: " + Integer.toString(i));
				listToCompare[i] = getPruningNum(this.L[listToCompare[i]]);
			}
		}
		int max = 0;
		int timesOccurred = 0;
		for(int i : listToCompare) { //iterate through my list which is now all the pruning numbers of the children
			if(i > max) {//reset max if current > max
				max = i;
				timesOccurred = 1;
			}else if(i == max) { //else increment number seen
				timesOccurred++;
			}
		}
		if(input == 0) { //if the node is the root of the tree, i dont want to return max + 1, just the maximal pruning number
			return max;
		}else if(timesOccurred > 1) { //else if not the root, if times occurs > 1, then add 1 to the current pruning number
			return max+1;
		}
		return max; //else return the max
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
		if(!this.hasLeft(input) && !this.hasRight(input)) {
			return true;
		}
		return false;
	}
	//a function to see if the node has 2 children
	public boolean hasTwoChildren(int input) {
		if(this.hasLeft(input) && this.hasRight(input)) {
			return true;
		}
		return false;
	}
	
	//a function that returns an array of ints of the line of right children (current node included) of the current node
	public int[] getRightList(int input) {
		int len = this.rightLineLength(input); //get the right line length from a function so I know how long to make the array
		int[] retArray = new int[len];
		int tempCurrent = input;
		for(int i = 0; i < len; i++) { //make the array
			retArray[i] = tempCurrent;
			tempCurrent = this.R[tempCurrent];
		}
		return retArray; //return the array
	}
	
	public int rightLineLength(int input) { //a function to get the length of the line of right children (parent inclusive)
		int rightListLen = 1; //length is at least one because it includes the current node
		if(this.R[input] == 0) { //if it has no right children, just immediately return
			return rightListLen;
		}else {	//else while the right is not 0, iterate and increase the counter
			int tempCurrent = input;
			while(this.R[tempCurrent] != 0) {
				tempCurrent = this.R[tempCurrent];
				rightListLen++;
			}
		}
		return rightListLen; //return the line length
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
		System.out.println("For n = " + Integer.toString(this.numNodes) + " The number of trees is: " + Integer.toString(this.numTrees) + ".");
		System.out.println("The strahler numbers are: " + Arrays.toString(this.strahlerRecord));
		System.out.println("The pruning numbers are: " + Arrays.toString(this.pruningRecord));
		System.out.println();
	}
	
	public static void miniTest() { //i have these trees in my copy book drawn out in both binary tree and forest tree form
		StrahlerAndPruning test1 = new StrahlerAndPruning(11);
		test1.L = new int[] {1, 0, 3, 0, 5, 6, 0, 0, 9, 0, 0};
		test1.R = new int[] {4, 2, 0, 0, 8, 7, 0, 0, 10, 0, 0};
		test1.printTest();
		
		StrahlerAndPruning test2 = new StrahlerAndPruning(7);
		test2.L = new int[] {1, 2, 0, 0, 5, 0, 0};
		test2.R = new int[] {4, 3, 0, 0, 6, 0, 0};
		test2.printTest();
		
		StrahlerAndPruning test3 = new StrahlerAndPruning(7);
		test3.L = new int[] {1, 0, 3, 0, 5, 0, 0};
		test3.R = new int[] {2, 0, 4, 0, 6, 0, 0};
		test3.printTest();
		
		StrahlerAndPruning test4 = new StrahlerAndPruning(7);
		test4.L = new int[] {1, 0, 0, 4, 0, 0, 0};
		test4.R = new int[] {0, 2, 3, 6, 5, 0, 0};
		test4.printTest();
		
		StrahlerAndPruning test5 = new StrahlerAndPruning(7);
		test5.L = new int[] {1, 2, 0, 0, 5, 0, 0};
		test5.R = new int[] {0, 4, 3, 0, 0, 6, 0};
		test5.printTest();
	}
	
	public static void main(String[] args) {
		miniTest();	//run my test trees
		//ask for input
		//while keyboard has next int
			//capture the int input
			//run the problem for that int
		System.out.println("Please Enter the number of nodes to do...");
        Scanner kb = new Scanner(System.in);
        int nextInt = kb.nextInt();
        kb.close();
        for(int i = 1; i <= nextInt; i++) {
        	StrahlerAndPruning temp = new StrahlerAndPruning(i);
    		//temp.print();
    		//System.out.println(temp.findParent(2));
    		temp.runProblem();
        }
	}
}
