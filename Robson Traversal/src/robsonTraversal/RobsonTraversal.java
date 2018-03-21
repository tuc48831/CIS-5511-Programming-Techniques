/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robsonTraversal;

import java.util.Scanner;
import java.util.Stack;

/**
 *
 * @author ComPeter
 */
public class RobsonTraversal {

    /**
     * @param args the command line arguments
     */
    //a void constructor to call my get input and create tree methods
    public RobsonTraversal(){
    }
    //the create function that returns a pointer to the tree
    //i know the assignment says create should return a pointer to the root
    //this is purely a conceptual choice and I could change it easily
    public Tree create(){
        //create null return value, and valid bool
        boolean valid = false;
        Tree retTree = null;
        //get int input from keyboard
        System.out.println("Please start creating a tree by entering preorder sequence information");
        Scanner kb = new Scanner(System.in);
        int input = Integer.parseInt(kb.nextLine());
        
        //decode first input
        if(input == 0){
            //if 0, return null tree
            retTree = new Tree();
        }else if(input == 1){
            //else create node numbered 1, insert as root of tree
            valid = true;
            Node temp = new Node(1);
            retTree = new Tree(temp);
        }else{
            //else inappropriate input, terminate
            System.out.println("invalid input, terminating...");
            return retTree;
        }
        
        //if i initialized a valid tree, get more inputs to build the tree
        if(valid){
//REDONE CREATE TREE SECTION THAT EXACTLY ADAPTS CODE FROM NOTES
        	//rename root to t
        	Node t = retTree.root;
        	//set p to t, create other nodes I will need
        	Node p = t;
        	Node q = null;
        	Node rtptr = null;
            //create a stack, create counter
            Stack<Node> s = new Stack<Node>();
            int counter = 1;
            //while the stack isn't empty
            while( (p != null) || (!s.isEmpty()) ){
                if( p != null) {
                	//get the next input
                	input = Integer.parseInt(kb.nextLine());
                	//visit(p),   this visit stores the counter in the node AND takes the input to make the left and right children as necessary
                	//visit returns a boolean of true if the input was valid, and false if it was invalid
                	//that way I can break if something goes wrong
                	if(!p.visitCreate(counter, input)) {
                		System.out.println("Invalid input, terminating");
                		return retTree;
                	}
                	//increment counter because i successfully made a node
                	counter += 1;
                	//push(p, s)
                	s.push(p);
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
        return retTree;
    }
    
    public Tree createPreMade(int input){
    	//PREMADE EXAMPLES, DO NOT COUNT THIS
        Tree retTree;
        Node temp = new Node(1);
        retTree = new Tree(temp);
        int[] list;
        
        if(input == 2){
            list = new int[] {11, 11, 11,11,0,0,0,11,11,11,11,0,0,1,11,0,0,11,0,0,11,0,0,11,11,0,11,0,0,11,0,10,0};
        }else if(input == 3){
            list = new int[] {11,11,11,0,0,0,1,0};
        }else if(input == 4){
            list = new int[] {11,11,01,10,00,01,10,00,01,01,00};
        }else{
            return retTree;
        }
        //PREMADE EXAMPLES DO NOT COUNT THIS
        Stack<Node> stackNode = new Stack<Node>();
        stackNode.push(retTree.root);
        int counter = 1;
        //while the stack isn't empty
        for(int item: list){
            //get current node from stack, it's the ith node, set info, increment counter
            temp = stackNode.pop();
            temp.info = counter;
            counter += 1;
          //PREMADE EXAMPLES DO NOT COUNT THIS
            if(item == 11){
                //if 11, add both a right and left to temp
                temp.rt = new Node();
                temp.lt = new Node();
                //add right and left to stack in that order
                stackNode.push(temp.rt);
                stackNode.push(temp.lt);
              //PREMADE EXAMPLES DO NOT COUNT THIS
            }else if(item == 10){
                //if 10 add a left and push it onto the stack
                temp.lt = new Node();
                stackNode.push(temp.lt);
              //PREMADE EXAMPLES DO NOT COUNT THIS
            }else if(item == 1){
                //if 01 add a right and push it onto the stack
                temp.rt = new Node();
                stackNode.push(temp.rt);
              //PREMADE EXAMPLES DO NOT COUNT THIS
            }else if(item == 0){
                //if 00 add nothing
                continue;
            }
        }
            
      //PREMADE EXAMPLES DO NOT COUNT THIS
        return retTree;
    }
    
    public void getInput(){
        //create scanner
        System.out.println("Enter 'y' to create a tree, or 'n' to quit...");
        Scanner kb = new Scanner(System.in);
        //while input continues
        while(kb.hasNextLine()){
            String text = kb.nextLine();
            if(text.equals("n")){
                System.out.println("Terminating...");
                return;
            }else if(text.equals("y")){
                System.out.println("type 'y' to create your own tree, or '2', '3', or '4' to run pre-done examples, anything else to quit");
                text = kb.nextLine();
                //preprogrammed cases
                if(text.equals("2")){
                    Tree temp = createPreMade(2);
                    temp.preOrderTraversal(temp.root);
                    temp.ModifiedRobsonTraversal();
                    temp.resetAll();
                    temp.preOrderTraversal(temp.root);
                }else if(text.equals("3")){
                    Tree temp = createPreMade(3);
                    temp.preOrderTraversal(temp.root);
                    temp.ModifiedRobsonTraversal();
                    temp.resetAll();
                    temp.preOrderTraversal(temp.root);
                }else if(text.equals("4")){
                    Tree temp = createPreMade(4);
                    temp.preOrderTraversal(temp.root);
                    temp.ModifiedRobsonTraversal();
                    temp.resetAll();
                    temp.preOrderTraversal(temp.root);
                }else if(text.equals("y")){
                    //user inputs tree
                    Tree temp = create();
                    temp.preOrderTraversal(temp.root);
                    temp.ModifiedRobsonTraversal();
                    temp.resetAll();
                    temp.preOrderTraversal(temp.root);
                }else{
                    System.out.println("Terminating...");
                    return;
                }
                
            }else{
                System.out.println("invalid input supplied");
                System.out.println("Terminating...");
                return;
            }
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        //get input, from given input, either do preprogrammed examples or enter input as needed
        RobsonTraversal tester = new RobsonTraversal();
        tester.getInput();
    }
    
}
