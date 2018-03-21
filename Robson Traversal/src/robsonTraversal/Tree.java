/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robsonTraversal;

import java.util.Stack;

/**
 *
 * @author ComPeter
 */
public class Tree {
    
    Node root = null;
    
    //since each tree should only ever have 1 traversal running at a time, I made these part of the tree object so that I don't have to pass them into and out of functions
    //since some functions like moveUp or moveDown need to alter 2 nodes at a time and i dont want to return an array of nodes and unpack it every time, oof
    Node current = null;//the current node
    Node top = null;    //the top node
    Node stack = null; //the stack node
    Node avail = null; //the last terminal node we have seen, kept around for adjusting pointers to/from it
    Node predp = null; //the predecessor node
    Node fictitiousPred = null;
    
    public Tree(){
    } 
    
    public Tree(Node node){
    	//set the root
        this.root = node;
        //set the root to the current node
        this.current = this.root;
        //set fictitious pred
        this.fictitiousPred = new Node(-1);
        this.predp = this.fictitiousPred;
    }
    
    public void resetAll() {
    	//a function to reset all of the variables involved in the traversal
    	this.current = this.root;
    	this.top = null;    //the top node
        this.predp = null; //the predecessor node
        this.avail = null;
        this.stack = null; //i can just set this to null because i know i properly reset the stack/leaf nodes as i traverse back up the tree
    }
    
    public void preOrderTraversal(Node t) {
//REDONE PREORDER TRAVERSAL THAT EXACTLY ADAPTS CODE FROM NOTES
    	if(t == null){
            System.out.println("Root is null, no preorder traversal");
            return;
        //else
        }else{
	    	//assign p to given arg t
			Node p = t;
			//have node q and rtptr
			Node q = null;
			Node rtptr = null;
			//create stack of nodes s
			Stack<Node> s = new Stack<Node>();
			//while ((p is not null)||(s isn’t empty))
			while ( (p != null) || (!s.isEmpty()) ){
				//if (p is not null)
				if (p != null){
					//visit(p),     this visit just prints the node's info, its left and right
					p.visitTraverse();
					//push(p, s)
					s.push(p); 
					//if(left(p) is not null))
					if (p.hasLeft()) {
						//p = left(p)
						p = p.lt;
					}else {
						//p = right(p)
						p = p.rt;
					}
				}else{
					do{
						//q = pop(s)
						q = s.pop();
						//if(s isn't empty)
						if ( (!s.isEmpty()) ){
							//rtptr = right(item(s))
							rtptr = s.peek().rt;
						//else
						}else{
							//rtptr = null
							rtptr = null;
						}
					//while (s isn't empty and q is rtptr)
					}while( !s.isEmpty() && (q == rtptr) );
					//p = rtptr
					p = rtptr;   
				}
			}
        }
    }
    
    public void printPathBack(Node current, Node pred) {
    	Node tempCurrent = current;
    	System.out.print("The path back is: ");
    	Node tempPred = pred;
    	while(tempCurrent != this.fictitiousPred) {
    		System.out.print("\t" + tempCurrent.toString());
    		if(tempPred.lt == null) {//if the predecessor has no left, just follow the right
    			Node temp = tempPred.rt; //temp node to store the predecessor's predecessor
        		tempCurrent = tempPred; 	//going
        		tempPred = temp;			//backwards one step
    		}else{ //else there is a left tree, and rt points to the lt, and lt points to the pred
    			Node temp = tempPred.lt; //temp now stores the predecessor to the predecessor
    			tempCurrent = tempPred;	//going
    			tempPred = temp;			//backwards one step
    		}
    	}
    }
    
  //a helper function for my robson traversal, does the portion of the instructions of printing top, print current, print stack
    public void visitRobson(){
        System.out.println("---------------------------------------------------------------------------------------------------------------------------------------------------------------");
        //print node's data,
        if(this.current == null) {
        	System.out.print("Current is null \t");
        }else {
        	System.out.print("Visiting: " + this.current.toString() + "\t\t");
        }
        
        //print predp's data,
        if(this.predp == null) {
        	System.out.print("PredP is null");
        }else {
        	System.out.print("PredP is: " + this.predp.toString() + "\t\t");
        }
        //top and stack
        if(this.top==null){
            //if top is null print that
            System.out.print("Top is null");
            //if top is null, stack should also be null
            System.out.print("Stack is null");
        }else{
            //print top using my node's toString, prints data, left, and right
            System.out.print("The top is: " + this.top.toString() + "\t\t");
            //this hands off the stack pointer and traverses through their left pointers
            Node temp = this.stack;
            System.out.print("The stack is: " ); //+ top.toString()
            //in the while loop it is prints instead of printlns so that they are all on the same line
            //i used tabs to give some spacing consistency. I google and saw some system.out.format(%4d) examples but
            //those are just decimal numbers and these prints need to be interwoven with text so I went with this cheap implementation
            while(temp!=null){
                System.out.print(temp.toString() + "\t\t");
                temp = temp.lt;
            }
        }
        System.out.println();
        //path back to root
        this.printPathBack(this.current, this.predp);
        System.out.println(); 
    }
    
    //there is no blanket move up one level function, i need to know whether i need to move up to the left or to the right
    //i use indicator as an argument to decode when i want to move where
    public void moveUpOne(int indicator) {
    	//if indicator is one, i am moving up from the right child using the right pointer
    	if(indicator == 1){
    		if(this.predp.lt == null) {//if the predecessor has no left, just follow the right
    			Node temp = this.predp.rt; //temp node to store the predecessor's predecessor
        		this.predp.rt = this.current; //undoing the modified robson step
        		this.current = this.predp; 	//going
                this.predp = temp;			//backwards
    		}else{ //else there is a left tree, and rt points to the lt, and lt points to the pred
    			Node temp = this.predp.lt; //temp now stores the predecessor to the predecessor
    			this.predp.lt = this.predp.rt; //reset the left pointer to point to the left tree
    			this.predp.rt = this.current; //reset the right pointer to point to current
    			this.current = this.predp;	//going
    			this.predp = temp;			//backwards
    		}
        //else move up from the left child using the left pointer
    	}else{
    		Node temp = this.predp.lt; //temp node to store the predecessor's predecessor
    		this.predp.lt = this.current; //undoing the modified robson step
    		this.current = this.predp;	//going
            this.predp = temp;			//backwards
    	}
    }
    
    //a function to move down to the left, straightforward
    public void robsonMoveDownLeft() {
    	//store the current's left in a temp node
        Node temp = this.current.lt;
        //set current's left to its predecessor (modified robson inversion step)
        this.current.lt = this.predp;
        //move the pred from the previous node to the current node
    	this.predp = this.current;
    	//move the current node to the next node
        this.current = temp;  
    }
    
    //very straightforward since i don't have to do anything with temp or stack, just to remember the leaves as avail
    public void robsonTraverseLeft(){
    	//since we are basing this off of pre-order traversals, this is a pretty straight forward "just keep going down and to the left" snippet of code
    	while(this.current != null){
    		//visit the node, when should i do this? before i move, as I'm moving? or after and the link is inversed?

            this.visitRobson();
            //check if we are current at a leaf node (a terminal node), need to do this before the robson move step and the left pointer points to the predecessor which would trick
            if(this.current.isLeaf()){
                //remember the move recent leaf with avail, but i don't need to set anything unless i need to make a stack entry which i handle later
                this.avail = this.current;
            }
            //call robson move down left to move down to the next level
            this.robsonMoveDownLeft();
            //as a note, i HAVE to move 1 level past the tree so that the leaf node experiences the robson pointer inversion
        }
    	//i moved past the tree by 1 level so that the leaf properly sets it left pointer backwards and experiences true robson inversion
    	//so current is null, and predecessor is the leaf node
    	//move up one level from the left
        this.moveUpOne(0);
        //clear the left pointer of the leaf (undo the modified robson inversion) from the last step of the left traversal
        this.current.lt = null;
    }
    
    //a function to move down to the right, it needs to handle setting temp unlike my move down left function
    public void robsonMoveDownRight() {
    	//changed to be better
    	if(this.current.hasLeft()) {
    		//store the current's right in a temp node
            Node temp = this.current.rt;
            //set current's right pointer to the left subtree
        	this.current.rt = this.current.lt;
        	//set the left to the predecessor
        	this.current.lt = this.predp;
            //move down
            this.predp = this.current;
            this.current = temp;
    	}else{ //if doesnt have a left, so make its right point to the predecessor
    		//store the current's right in a temp node
            Node temp = this.current.rt;
            //set current's right pointer to the predecessor (modified robson inversion step)
        	this.current.rt = this.predp;
            //move down
            this.predp = this.current;
            this.current = temp;
    	}
    }
    
    public void robsonTraverseRight() {
    	//if i have a right
    	if(this.current.hasRight()){
    		//before I move down, check for a left tree, if there is one i need to remember this node with top
        	//since im a node with a non null left tree whose right tree is about to be traversed
            if(this.current.hasLeft()){
            	//if top is not null, there is already a top/special node and i need to add/move something to the stack
                if(this.top != null){
                    //set avail's right pointer to the old top
                    this.avail.rt = this.top;
                    //if the stack isn't null, point avail's left to the stack
                    if(this.stack != null){
                        this.avail.lt = this.stack;
                    }
                    //add avail to the stack
                    this.stack = this.avail; 
                }
                //if there was no previous top, I don't need to worry about making or handling a stack, set top to current, don't need to involve avail
                this.top = this.current;
            }
            //move down one level to the right
            this.robsonMoveDownRight();
        }
    }
    
    public boolean backtrack() {
    	while(this.current != null){
    		//check at the beginning of each loop since backtrack will continue to go until I find the next right subtree to go down
    		//if at any point, if the predecessor is null, i have traversed the whole tree and backtracked up to my root which has no predecessor
    		if(this.current == this.root){
	            //return true to end the modified robson traversal
    			//this.moveUpOne(1);
    			
	        	return true;
        	//else predp is not null
    		}else{
	            //i consider the default case that i got here because i need to switch from a left tree to a right tree
	            boolean switchingOverFromLeft = true;
	            
	            //check if im not switching over from the left
	            if(this.predp.hasRight() && !this.predp.hasLeft()){
	            	switchingOverFromLeft = false;
	            }
	            
	            //directly from notes, if predp equals tops then we have just finished traversal of the right subtree
	            if(this.predp == this.top){ //aka im switching over from the right
                //handle removing an item from the stack
	            	//2 cases, top is not null and stack has an entry
	                if(this.top != null && this.stack != null){
	                        //move top to the previous special node, getting it from the stack
	                    	this.top = this.stack.rt;
	                        //capture stack node
	                        Node temp = this.stack;
	                        //move the stack back one
	                        this.stack = this.stack.lt;
	                        //clear the left and right pointers as I remove an item from the stack (the resets the leaf to have null pointers like it should)
	                        temp.clearPointers();
	                //else either both top and stack were empty, or just stack since stack is "behind" top, regardless, just reset top
	                }else{
	                    //top is no longer useful so set it to null
	                	this.top = null;
	                }
	                //move up one from the right
	                this.moveUpOne(1);
	                //continue looping
	                continue;
	            //else if im switching over from a left to a non null right
	            }else if(switchingOverFromLeft && this.predp.rt != null){ //switching from the left AND there is a right tree for me to go into
	                //move up one from the left
	            	this.moveUpOne(0);
	            	//return to exit the function so i can move down into the right tree
	            	return false;
	            //else im switching over from a left to a null right
	            }else if(switchingOverFromLeft) { //switching from the left
	            	//move up one from the left
	            	this.moveUpOne(0);
		            //keep looping back since there is no right to move into currently
	            	continue;
	            }else{ //else im switching over from the right
	                //move up one from the right
	            	this.moveUpOne(1);
		            //keep looping around until we reach one of the other cases and have somewhere to move OR we backtrack to the root and we're done traversing
	            	continue;
	            }
	        }
    		//loop back around
    	}
    	return false; //this is just so i don't get a "doesnt return" error, this will never be reached
    }
    
    //modified robson traversal code
    public void ModifiedRobsonTraversal(){
        //check if null tree and return if it is
        if(this.root == null){
            System.out.println("Root is null, no Modified Robson's Traversal possible");
        }else{
            System.out.println("MODIFIED ROBSON TRAVERSAL:");
            //start traversing since I know it isn't null
            while(true){
            	//my traverse left function handles visiting and then traversing down any left subtrees
            	//this is the only function where i visit a node, my traverse right breaks for every individual right node it sees
            	//so i loop back around and visit it either when i traverse a singular node, OR a whole left subtree
            	this.robsonTraverseLeft();
                //pretty much finished as far left as i can go, and backtracked to be back inside the tree
                
                //if there is no right for me to go to, we have done everything we can down this left path, we need to backtrack
                if(!this.current.hasRight()) {
                	//during my backtrack function, i check if i reached the root from the right while backtracking
                	//if i did, i've traversed the whole tree, so i can return
                	if(this.backtrack()) {
                		return;
                	}
                }
                
                //else go right one, loop around to visit the new node and any left subtree's
                //as i traverse the right, handle any updates/overwrites/removals from top/stack
                this.robsonTraverseRight();
                //loop back around
            }
            
        }
    }
}
