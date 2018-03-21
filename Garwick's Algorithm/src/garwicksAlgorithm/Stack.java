package garwicksAlgorithm;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author ComPeter
 */
public class Stack {
    
    public int top;
    public int oldtop;
    public int base;
    public int newbase;
    
    public Stack(){
        this.top = -1;
        this.oldtop = -1;
        this.base = -1;
        this.newbase = -1;
    }
    
    public Stack(int base, int top, int oldtop){
        this.top = top;
        this.oldtop = oldtop;
        this.base = base;
        this.newbase = 0;
    }
    
    public String toString(){
        String retval = "";
        retval += "top is: " + Integer.toString(this.top);
        retval += " old is: " + Integer.toString(this.oldtop);
        retval += " base is: " + Integer.toString(this.base);
        retval += " newbase is: " + Integer.toString(this.newbase);
        return retval;
    }
}
