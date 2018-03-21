/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package robsonTraversal;

/**
 *
 * @author ComPeter
 */
public class Node {
    
    public int info;
    public Node lt;
    public Node rt;
    
    //overloaded constructor for no info given
    public Node(){
        this.info = -1;
        this.lt = null;
        this.rt = null;
    }
    //constructor that takes the info to be stored as an arg
    public Node(int info){
        this.info = info;
        this.lt = null;
        this.rt = null;
    }
    public boolean visitCreate(int counter, int input) {
    	this.info = counter;
    	
        if(input == 11){
            //if 11, add both a right and left
            this.rt = new Node();
            this.lt = new Node();
        }else if(input == 10){
            //if 10 add a left
            this.lt = new Node();
        }else if(input == 1){
            //if 01 add a right
            this.rt = new Node();
        }else if(input == 0) {
        	//do nothing
        }else{
        	return false;
        }
        return true;
    }
    public void visitTraverse() {
    	System.out.println(this.toString());
    }
    //boolean function to check if it is a leaf (both left and right are null)
    public boolean isLeaf(){
        if(this.lt == null && this.rt == null){
            return true;
        }
        return false;
    }
    //boolean function to see if it ONLY has a right
    public boolean onlyRight(){
        if(this.lt == null && this.rt != null){
            return true;
        }
        return false;
    }
    //boolean funciton to see if it ONLY has a left
    public boolean onlyLeft(){
        if(this.lt != null && this.rt == null){
            return true;
        }
        return false;
    }
    //boolean function to check existence of right
    public boolean hasRight(){
        if(this.rt != null){
            return true;
        }
        return false;
    }
    //boolean function to check existence of left
    public boolean hasLeft(){
        if(this.lt != null){
            return true;
        }
        return false;
    }
    //a function to clear both the left and right pointer
    public void clearPointers(){
        this.lt = null;
        this.rt = null;
    }
    //a to string function to make it easy to print out various information
    public String toString(){
        String retString = "";
        
        retString += "(Info:";
        retString += Integer.toString(this.info) + ", left:";
        if(lt == null){
            retString += "null, right:";
        }else{
            retString += Integer.toString(lt.info) + ", right:";
        }
        if(rt == null){
            retString += "null)";
        }else{
            retString += Integer.toString(rt.info) + ")";
        }
        
        return retString;
    }
}
