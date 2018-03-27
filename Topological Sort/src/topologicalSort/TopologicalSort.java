package topologicalSort;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.util.Queue;

/**
 *
 * @author ComPeter (AKA tuc48831 as I program both in the computer labs and on my home machine)
 */
public class TopologicalSort {

    /**
     * @param args the command line arguments
     */
    private int numPrint;
    private int[] countArray;
    public int numPerms;
    private LinkedList[] succArray;
    private LinkedList output;
    private LinkedList bag; //I chose a linked list for the bag as it is O(c) for checking if it's empty, and inserting/removing items to the head and tail
    
    public TopologicalSort(String input){ //an initializer that takes a string of input and sets up the problem
        String[] lines = input.split("\n"); //convert the line seperated input into arrays
        initializeListsArraysAndNums(Integer.parseInt(lines[0])); //get the number of vertices from the first line
        for(int i = 1; i < lines.length; i++){  //after the first line, read line by line
            int toDecode = Integer.parseInt(lines[i]);  //decode the input into the first digit and second digit
            int firstDigit = toDecode / 10;
            int secondDigit = toDecode % 10;
            if(firstDigit == 0 && secondDigit == 0){ //if the 00 is reached, break
                break;
            }
            succArray[firstDigit].addNoDupes(secondDigit); //access the succArray and add the item to the list with my noDupes function
            countArray[secondDigit]++; //increase the predecessor count for the successor that was just added
        }
        for(int i = 0; i < countArray.length ; i++){ //add items with no predecessor into the bag
            if(countArray[i]==0){ //this iterates and is O(n) but it's in the setup
                bag.add(i);
            }
        }
    }
    
    
    public void initializeListsArraysAndNums(int numVertex){ //this is O(c)
        this.numPrint = 10;
        this.countArray = new int[numVertex]; //this initializes an array of all 0's numVertex long
        this.succArray = new LinkedList[numVertex]; //this initializes an array of my linkedlist class with their heads as null
        for(int i = 0; i < numVertex; i++){
            succArray[i] = new LinkedList();
        }
        this.output = new LinkedList();
        this.bag = new LinkedList();
    }
    
    public int reducePredCountAndAddToBag(int vertNum){ //this both reduces the pred count and adds it to the bag if the pred count hits 0, i need to do these at the same time to keep it on the same order of the size of the successor array, not the whole n-space
        int countStored = 0;        //a return variable for how many items i added to the bag
        if(succArray[vertNum].head == null){    //if the head is null, the object has no successors
            return countStored; //return 0 without decrementing any numbers
        }else{
            Node temp = succArray[vertNum].head; //temp node
            while(temp != null){    //traverse the linked list
                int store = (int)temp.getElement(); //capture the nodes from the list
                countArray[store]--;    //decrement their countArray entry
                if(countArray[store] == 0){ //if their countArray entry went to 0
                    //System.out.println(store + ": added to bag");
                    bag.add(store);         //add them to the bag
                    countStored++;          //increment the number of items stored
                }
                temp = temp.getNext();   //iterate
            }
        }
        return countStored;     //return the number of items stores
    }
    
    public void increasePredCountAndRemoveFromBag(int vertNum, int numAdded){ //this both increases the pred count and removes it from the bag if predcount > 0
        if(succArray[vertNum].head == null){    //if the object has no successors, just end
            return;
        }else{  //else
            Node temp = succArray[vertNum].head; //temp to iterate
            while(temp != null){ //iterate over the list
                int store = (int)temp.getElement(); //capture the node from the linked list
                countArray[store]++;            //increment their countArray entry
                temp = temp.getNext();               //move on to the next
            }
        }
        for(int i=0; i<numAdded; i++){   //remove the vertNum items from the end of the bag
            bag.removeTail();           //this will be passed the number of items that was stored in the bag on that recursive call of the algorithm
        }
        return;
    }
    
    public void topsorts(){
        if (bag.head != null){ //this is an O(c) check on if the bag is empty
            int size = bag.getSize(); //this is O(c) since I'm just accessing a field of the linkedlist
            //System.out.println("bag size is: " + bag.size());
            int counter = 0; //this is O(c)
            
        
            while (counter < size){ //i believe this is O(n)
                //Take it out of the Bag, put it in the output,
                //traverse its succ list, reduce the pred count for
                //each successor, and if it goes to zero, put it in 
                //the Bag
                int tempInt = (int)bag.removeHead(); //the creation and assignment of this variable is O(c) //I am casting as an int because I know I will be inserting ints, but my linked list is of generic <E> type
                output.add(tempInt); //add the vertex to the output list
                int numAdded = reducePredCountAndAddToBag(tempInt);
                
                //System.out.println(output.toString());
                topsorts();
                //System.out.println("tempInt is: "+tempInt + "and numAdded is: " + numAdded);
                
                //Reverse the above is equivalent to
                //increment the successors, if the count goes above 0, remove an element from the tail
                //remove the item from the output
                //put it back in the bag
                increasePredCountAndRemoveFromBag(tempInt, numAdded);
                output.removeTail(); //I'm only ever adding 1 thing to the output at a time so I can just remove the tail
                bag.add(tempInt); //adds the removed item back into the bag at the tail
                        
                counter++; //this counter allows me to cycle through the linked list without repeating anything
            }
        }else{
            if(this.numPrint>0){
                System.out.println("Iteration " + numPrint + " is: " + output.toString() );
                this.numPrint--;
            }
            numPerms++;
            /*if(numPerms % 100 == 0){
                System.out.println(numPerms);
            }*/
        }
    }
    
    public static void main(String[] args) {
        // TODO code application logic here
        
        //these are some sample problems I came up with, the first 2 are small and doable hand to verify my work
        //the second 2 are larger ones that show my code properly creates the numbers of permutations for even large sets
        TopologicalSort one = new TopologicalSort(
                "5\n"
                + "01\n"
                + "12\n"
                + "23\n"
                + "24\n"
                + "00");
        System.out.println("initialized one");
        one.topsorts();
        System.out.println("ran one, total number of permutations: " + one.numPerms);
        
        TopologicalSort two = new TopologicalSort(
                "4\n"
                + "00");
        System.out.println("initialized two");
        two.topsorts();
        System.out.println("ran two, total number of permutations: " + two.numPerms);
        
        TopologicalSort three = new TopologicalSort(
                "11\n"
                + "00");
        System.out.println("initialized three");
        three.topsorts();
        System.out.println("ran three, total number of permutations: " + three.numPerms);
        //11! is 39916800 and it takes ~2 seconds to run on my intel i5 2500k quad core @ 3.3Ghz
        
        TopologicalSort four = new TopologicalSort(
                "10\n"
                + "13\n" + "15\n"
                + "17\n" + "19\n"
                + "24\n" + "26\n"
                + "28\n" + "35\n"
                + "37\n" + "39\n"
                + "46\n" + "48\n"
                + "57\n" + "59\n"
                + "68\n" + "79\n"
                + "00");
        System.out.println("initialized four");
        four.topsorts();
        System.out.println("ran four, total number of permutations: " + four.numPerms);
        
        
    }
    
}