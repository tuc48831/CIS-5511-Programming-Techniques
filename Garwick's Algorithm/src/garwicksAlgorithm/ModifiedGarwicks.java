/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package garwicksAlgorithm;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

/**
 *
 * @author ComPeter
 */
public class ModifiedGarwicks {
    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

    /**
     * @param args the command line arguments
     */
    
    int[] storage;
    Stack[] stacks;
    int numStacks;
    double rho;
    int spaceRem;
    boolean oneTime = true;
    //data i want to capture
    int numMoves = 0;
    int numMoves70 = 0;
    int numOverflows = 0;
    
    //good
    public ModifiedGarwicks(int storeLength, int numStacks, double rho){
        this.storage = new int[storeLength];
        this.spaceRem = storeLength;
        Arrays.fill(storage, -1);
        this.stacks = new Stack[numStacks+1];
        this.numStacks = numStacks;
        this.rho = rho;
        int distance = storeLength / numStacks;
        
        //loops necessary to init garwicks
        for(int i = 0; i <= numStacks; i++){
            if(i%2==0){//even
                int base = i*distance;
                int top = base;
                int oldtop = base;
                stacks[i] = new Stack(base, top, oldtop);
            }else{//odd
                int base = (i+1)*distance-1;
                int top = base;
                int oldtop = base;
                stacks[i] = new Stack(base, top, oldtop);
            }
        }
        
        stacks[numStacks].base = storeLength-1;
        stacks[numStacks].top = stacks[numStacks].base;
    }
    //good
    public int[] modSpaceAndIncrease(){
        int retarr[] = new int[2]; //[0] is the space, and [1] is the increase
        int space = 0;
        int increase = 0;
        //loop through all my stacks, capture total size and incrase from top vs base AND top vs oldtop
        for(int i=0; i < numStacks; i++){
            if(i%2==0){//even
                space += (stacks[i].top - stacks[i].base);
                increase += (stacks[i].top - stacks[i].oldtop);
            }else{//odd
                space += (stacks[i].base - stacks[i].top);
                increase += (stacks[i].oldtop - stacks[i].top);
            }
        }
        //do them both at the same time so you only have to loop once
        retarr[0] = space;
        retarr[1] = increase;
        //System.out.println("space and Increase is returning space: " + retarr[0] + "and increase: " + retarr[1]);
        return retarr;
    }
    //good
    public double modAlloc(int rem, int stackNum, int sumIncrease, int spaceUsed){
        double increase;
        double size;
        if(stackNum%2==0){//even
            increase = stacks[stackNum].top - stacks[stackNum].oldtop;
            size = stacks[stackNum].top - stacks[stackNum].base;
        }else{//odd
            increase = stacks[stackNum].oldtop - stacks[stackNum].top;
            size = stacks[stackNum].base - stacks[stackNum].top;
        }
        
        double one = 1.0;
        double numOfStacks = (double) this.numStacks;
        
        double sumOfIncrease = (double) sumIncrease;
        double spaceAlreadyUsed = (double) spaceUsed;
        double allocation = rem * (one / numOfStacks * .1 + (increase)/sumOfIncrease * this.rho * .9 + (size)/spaceAlreadyUsed * (1 - this.rho) * .9 );
        
        return allocation;
    }
    //good
    public boolean modGarwicksAlgo(int stackNum, int numToAdd){
        //increase the stack size, then calculate total space used and size of increase since last run
        //System.out.println(Arrays.toString(storage));
        int storeTop = stacks[stackNum].top;
        if(stackNum%2==0){//even
            stacks[stackNum].top += numToAdd;
        }else{//odd
            stacks[stackNum].top -= numToAdd;
        }
        
        int[] holder = this.modSpaceAndIncrease();
        int spaceUsed = holder[0];
        int sumIncrease = holder[1];
        //check if space used > array length
        int rem = this.storage.length - spaceUsed;
        this.spaceRem = rem;
        //System.out.println("storage length is: " + this.storage.length + " and spaceUsed is: " + spaceUsed + " and sumIncrease is: " + sumIncrease);
        if(rem < 0){
            //System.out.println("more space required than array has, exiting");
            return false;
        }
        double carryover = 0.0;
        int deltaNminusOne = 0;
        for(int i = 0; i < this.numStacks; i++){
            double allocation = this.modAlloc(rem, i, sumIncrease, spaceUsed);
            int delta = (int) allocation;
            carryover += allocation % 1;
            if(carryover >= 1){
                delta += 1;
                carryover -= 1;
            }
            if(i>0){
                if(i%2==0){//even
                    stacks[i].newbase = stacks[i-1].newbase + 1;
                }else{//odd
                    /*int temp = 0;
                    if(stacks[i].top<0){
                        stacks[i].top = 0;
                    }*/
                    stacks[i].newbase = stacks[i-1].newbase + (stacks[i-1].top - stacks[i-1].base) + deltaNminusOne + delta + (stacks[i].base - (stacks[i].top+1));
                    
                }
            }
            deltaNminusOne = delta;
            //System.out.println("The "+ i +"th stack's alloc is: " + Double.toString(allocation) + " and the delta is: " + delta);
        }
        //decrease the stack size and call the moving algorithm
        stacks[stackNum].top = storeTop;
        modMovingAlgo();
        //increase the stack size
        //add items, mark the old tops, reset the new bases
        
        if(stackNum%2==0){//even
            for(int i = 0; i < numToAdd; i++){
                storage[ stacks[stackNum].top ] = stackNum;
                stacks[stackNum].top += 1;
            }
        }else{
            for(int i = 0; i < numToAdd; i++){
                storage[ stacks[stackNum].top ] = stackNum;
                stacks[stackNum].top -= 1;  
            }
        }
        
        for(int i = 0; i < this.numStacks; i++){
            stacks[i].oldtop = stacks[i].top;
        }
        //i will add to the stack in the original calling stack of add()
        //System.out.println("The number of move is: "+numMoves+" and the number of overflows is: "+numOverflows);
        
        holder = this.modSpaceAndIncrease();
        spaceUsed = holder[0];
        double temp1 = (double) spaceUsed;
        double temp2 = (double) this.storage.length;
        double percent = temp1 / temp2;
        if(percent >= .7 && oneTime){
            this.numMoves70 = numMoves;
            oneTime = false;
        }
        rem = this.storage.length - spaceUsed;
        this.spaceRem = rem;
        return true;
    }
    
    public void modMovingAlgo(){
        
        for(int i = 0; i < numStacks; i++){
            //System.out.println(stacks[i].toString());
        }
        
        //System.out.println("Array before moving: " + Arrays.toString(storage));
        //for loop going through all the stacks
        for(int i = 1; i < this.numStacks; i++){
           // System.out.println(i+ "th loop of moving algo");
            if(stacks[i].newbase < stacks[i].base){
                if( (i % 2) == 0){
                    //calculate the difference/delta between where it is and where it needs to be
                    int delta = stacks[i].base - stacks[i].newbase;
                    //loop through for each element of the stack and move it down by the delta
                    for(int h = stacks[i].base; h < stacks[i].top; h++){
                        storage[h - delta] = storage[h];
                        numMoves++;
                        storage[h] = -1;
                        //System.out.println("Array after moving once: " + Arrays.toString(storage));
                    }//set the base and the top to their new values
                    stacks[i].base = stacks[i].newbase;
                    stacks[i].top = stacks[i].top - delta;
                }else{
                    //calculate the difference/delta between where it is and where it needs to be
                    int delta = stacks[i].base - stacks[i].newbase;
                    //loop through for each element of the stack and move it down by the delta
                    for(int h = stacks[i].top+1; h <= stacks[i].base; h++){
                        storage[h - delta] = storage[h];
                        numMoves++;
                        storage[h] = -1;
                        //System.out.println("Array after moving once: " + Arrays.toString(storage));
                    }//set the base and the top to their new values
                    stacks[i].base = stacks[i].newbase;
                    stacks[i].top = stacks[i].top - delta;
                }
                
            }else if(stacks[i].newbase > stacks[i].base){
                int holder = this.numStacks;
                for(int j = this.numStacks; j > i; j--){
                    if(stacks[j].newbase < stacks[j].base){
                        holder = j;
                    }
                }
                for(int t = holder - 1; t >= i; t--){
                    if(t%2==0){//even
                        int delta = stacks[t].newbase - stacks[t].base;
                        for(int h = stacks[t].top-1; h >= stacks[t].base; h--){ //-1
                            storage[h + delta] = storage[h];
                            numMoves++;
                            storage[h] = -1;
                            //System.out.println("Array after moving once: " + Arrays.toString(storage));
                        }
                        stacks[t].base = stacks[t].newbase;
                        stacks[t].top = stacks[t].top + delta;
                    }else{//odd
                        int delta = stacks[t].newbase - stacks[t].base;
                        for(int h = stacks[t].base; h >= stacks[t].top+1; h--){ //-1
                            storage[h + delta] = storage[h];
                            numMoves++;
                            storage[h] = -1;
                            //System.out.println("Array after moving once: " + Arrays.toString(storage));
                        }
                        stacks[t].base = stacks[t].newbase;
                        stacks[t].top = stacks[t].top + delta;
                    }

                }
                i = holder;
            }
           
        }
        for(int i = 0; i < numStacks; i++){
            //System.out.println(stacks[i].toString());
        }
    }
    //good
    public void modAdd(int stackNum, int numToAdd){
        //check if i can add
        if(stackNum%2==0){//even
            if(stacks[stackNum].top - 1 + numToAdd <= stacks[stackNum+1].top ){ //|| stacks[stackNum].top >= this.storage.length){
            //handle inserting first item into stack
                if(stacks[stackNum].top == stacks[stackNum].base){
                    storage[ stacks[stackNum].base ] = stackNum;
                    stacks[stackNum].top += 1;
                    numToAdd--;
                }
                for(int i = 0; i < numToAdd; i++){
                    storage[ stacks[stackNum].top ] = stackNum;
                    stacks[stackNum].top += 1;
                }
            }else{
                numOverflows++;
                numMoves += (stacks[stackNum+1].top - stacks[stackNum].top);
                modGarwicksAlgo(stackNum, numToAdd);
            }
        }else{//odd
            if(stacks[stackNum].top + 1 - numToAdd > stacks[stackNum-1].top - 1){ //|| stacks[stackNum].top >= this.storage.length){
            //handle inserting first item into stack
                if(stacks[stackNum].top == stacks[stackNum].base){
                    storage[ stacks[stackNum].base ] = stackNum;
                    stacks[stackNum].top -= 1;
                    numToAdd--;
                }
                for(int i = 0; i < numToAdd; i++){
                    storage[ stacks[stackNum].top ] = stackNum;
                    stacks[stackNum].top -= 1;
                }
            }else{
                numOverflows++;
                numMoves += (stacks[stackNum].top - stacks[stackNum-1].top + 1);
                modGarwicksAlgo(stackNum, numToAdd);
            }
        }
        
        
        //System.out.println("the array after adding is: " + Arrays.toString(storage));
        
    }
    //good
    public void modGetinput(){
        Scanner kb = new Scanner(System.in);
        
        System.out.println("enter nodesfor Modified Garwicks to add in the format \"stack number, number to add\", type \'0000\' to exit");
        while(kb.hasNext()){
            String input = kb.next();
            if( "0000".equals(input)){
                break;
            }
            String[] nums = input.split(",");
            int stacknum = Integer.parseInt(nums[0])-1;
            int numtoadd = Integer.parseInt(nums[1]);
            modAdd(stacknum, numtoadd);
            if(this.spaceRem == 0){
                break;
            }
        }
    }
    
    public int[] report(){
        int ret[] = new int[3];
        ret[0] = numMoves70;
        ret[1] = numMoves;
        ret[2] = numOverflows;
        return ret;
    }
    
    public void generateInputUniform(int size){
        Random rand = new Random();
        while(this.spaceRem>=10){
            int n = rand.nextInt(10);
            modAdd(n, size);
        }
    }
    
    public void generateInputExponential(int size){
        Random rand = new Random();
        while(this.spaceRem>=10){
            int n = rand.nextInt(1024);
            if(n < 512){
                n=0;
            } else if(n < 512 + 256){
                n=1;
            }else if(n < 512 + 256 + 128){
                n=2;
            }else if(n < 512 + 256 + 128 + 64){
                n=3;
            }else if(n < 512 + 256 + 128 + 64 + 32){
                n=4;
            }else if(n < 512 + 256 + 128 + 64 + 32 + 16){
                n=5;
            }else if(n < 512 + 256 + 128 + 64 + 32 + 16 + 8){
                n=6;
            }else if(n < 512 + 256 + 128 + 64 + 32 + 16 + 8 + 4){
                n=7;
            }else if(n < 512 + 256 + 128 + 64 + 32 + 16 + 8 + 4 + 2){
                n=8;
            }else if(n <= 1024){
                n=9;
            }
            
            modAdd(n, size);
        }
    }
    
    public static void main(String[] args) {
        Scanner kb = new Scanner(System.in);
        System.out.println("to initialize Modified Garwicks, please type \'storage length, num stacks, rho\' ");
        String input = kb.next();
        String[] nums = input.split(",");
        int storelength = Integer.parseInt(nums[0]);
        int numstacks = Integer.parseInt(nums[1]);
        double rho = Double.parseDouble(nums[2]);
        
        ModifiedGarwicks instance = new ModifiedGarwicks(storelength, numstacks, rho);
        instance.modGetinput();
    }
    
}
