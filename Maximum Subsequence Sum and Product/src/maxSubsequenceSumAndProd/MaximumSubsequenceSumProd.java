package maxSubsequenceSumAndProd;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.util.Arrays;
import java.lang.Math;

/**
 *
 * @author ComPeter
 */
public class MaximumSubsequenceSumProd {
    
    public void modifiedMSS4(int[] array, int p1, int p2){
        
        //a simple print statement to show the supplied array and the start and end positions supplied
        System.out.println("The supplied Array is: " + Arrays.toString(array) + 
                            "\nand the input start and end positions are: " + Integer.toString(p1) + 
                            " and " + Integer.toString(p2) + "\n");
        
        //variables needed
        int maxsum = 0, sum = 0;
        int startPos = p1, endPos = p1;
        //linear loop from the supplied input to the end input
        for(int i = p1; i <= p2; i++){
            sum += array[i];            //add the current item to the array
            if(sum > maxsum){           //if the current sum is larger than the previous sum
                endPos = i;             //remember the new end position
                maxsum = sum;           //set the max sum to the new sum
            }else if(sum < 0){          //else if the sum goes below 0
                startPos = i+1;         //the new start pos will be the next element
                endPos = i+1;           //the new end position will be the same (aka a "null" string)
                sum = 0;                //the new sum will be 0
            }
        }
        
        int[] slice = Arrays.copyOfRange(array, startPos, endPos);
        System.out.println("The final maximum subsequence sum is: " + maxsum +
                            "\nThe slice of the array is: " + Arrays.toString(slice) +
                            "\nThe start index is: " + Integer.toString(startPos) + 
                            " and the end index is: " + Integer.toString(endPos) + "\n");
    }
    
    public int MSP3(int[] array, int p1, int p2){
        
        System.out.println("The supplied Array is: " + Arrays.toString(array) + 
                            "\nand the input start and end positions are: " + Integer.toString(p1) + 
                            " and " + Integer.toString(p2) + "\n");
        
        int maxProd = 0;
        if(p1 == p2){  //if p1 was p2 the rule was that it's supposed to be 0 but i can't return a 0 up the stack or recursive calls or it will trash any results
            //System.out.println("p1 == p2 case, num is: " + Integer.toString(array[p1]));
            return array[p1];
        } else {
            int m = (p1+p2)/2;                  //this is an int so it will automatically do the floor function in weird cases like 11/2 = 5
            int L = this.MSP3(array, p1, m);    //left half of the problem
            int R = this.MSP3(array, m+1, p2);  //right half of the problem
            int prodlt = 1, prodrt = 1;         //variables i will need
            int templ = 1, tempr = 1;
            int negl = 0, negr = 0;
            int holderl = 0, holderr = 0;
            
            //double checking left to right to see if there is a maximum subproduct that is better
            for(int i = m; i >= p1; i--){      
                if(array[i] < 0 && negl < 0){       //iterate through the array and if you hit a negative while already seeing another negative
                    holderl = templ *negl * prodlt;
                    prodlt = templ * negl * array[i] * prodlt;  //multiply everything together
                    negl = 0;                       //reset the variables
                    templ = 1;
                    continue;                       //continue
                }else if(array[i] < 0){             //if its the first time you see a negative
                    templ = prodlt;                     //capture the current product
                    negl = array[i];                    //remember the negative
                    prodlt = 1;                         //reset the current product
                    continue;                       //continue
                }
                prodlt = prodlt*array[i];           //normal iterative case, multiply and move on
            }
            //prodlt = Math.max(prodlt, templ);       //choose the larger of the temp versus the stored product
            
            for(int i = m+1; i <= p2; i++){      //double checking the right half to see if there is a maximum subproduct that is better
                if(array[i] < 0 && negr < 0){   //iterate through the array and if you hit a negative while already seeing another negative
                    holderr = tempr * negr * prodrt;
                    prodrt = tempr * negr * array[i] * prodrt; //multiply everything together
                    negr = 0;                   //reset the variables
                    tempr = 1;
                    continue;                    //continue
                }else if(array[i] < 0){          //if its the first time you see a negative
                    tempr = prodrt;                 //capture the current product
                    negr = array[i];                //remember the negative
                    prodrt = 1;                     //reset the current product
                    continue;                   //continue
                }
                prodrt = prodrt*array[i];       //normal iterative case, multiply and move on
            }
            //prodrt = Math.max(prodrt, tempr);    //choose the larger of the temp versus the stored product
            
            //3 cases, left has neg, right has neg, both have neg
            //System.out.println("maxProd is: " + maxProd + "negl and negr are: " + negl + " " + negr);
            int temp;
            int extra;
            if(negr < 0 && negl < 0){       //if there are 2 negative numbers left, one on the left side and one on the right side
                temp = prodrt * tempr * negr * prodlt * templ * negl;   //multiply everything together because they will cancel
                maxProd = Math.max(maxProd, temp);  //choose the max between the max and the new value
            }else if(negr < 0){
                temp = tempr * prodlt;      //if the right hand side had the negative
                extra = holderl * negr * tempr * prodrt;    //check the left hand side of the right hand side and multiply it to the lefthand side
                temp = Math.max(temp, extra);   //chose the max
            }else if(negl < 0){
                temp = templ * prodrt;  //the left hand side reverse case of the above
                extra = holderr * negl * templ * prodlt;
                temp = Math.max(temp, extra);
            }else{
                temp = prodrt * prodlt; //else final check
            }
            
            maxProd = Math.max(Math.max(L, R), temp); //choose max
        }
        //System.out.println("Maxprod is: " + maxProd);
        System.out.println("The final maximum subsequence product is: " + maxProd);
        return maxProd;
    }
    
    public static void main(String[] args) {
        
        int testArray1[] = {1, 1, 4, -9, 6, -5, 3, -9, 6, -8, 10}; //13,996,800
        int testArray2[] = {1, 2, -3, 4, -5, 6}; //720
        int testArray3[] = {2, -1, 1, -2, 3, -4, 3, 2, 2}; //288
        int testArray4[] = {99, -1, 3, 2, 4}; //99
        int testArray5[] = {2,2,2,2,2,-1,-1,-1,3,3,3,3,3}; //243
        int testArray6[] = {2,2,2,2,2,-1,5,5,5,5,5,-1,3,3,3,3,3}; //2^5 * 3^5 = 7776, 2^5*5^5*3^5 = 24300000
        
        
        MaximumSubsequenceSumProd test = new MaximumSubsequenceSumProd();
        test.modifiedMSS4(testArray1, 0, 10);
        test.modifiedMSS4(testArray1, 4, 10);
        
        System.out.println("the return amount is: "+ Integer.toString(test.MSP3(testArray1, 0, 10)));
        System.out.println("the return amount is: "+ Integer.toString(test.MSP3(testArray2, 0, 5)));
        System.out.println("the return amount is: "+ Integer.toString(test.MSP3(testArray3, 0, 8)));
        System.out.println("the return amount is: "+ Integer.toString(test.MSP3(testArray4, 0, 4)));
        System.out.println("the return amount for testArray5 is: "+ Integer.toString(test.MSP3(testArray5, 0, 12)));
        System.out.println("the return amount for testArray6 is: "+ Integer.toString(test.MSP3(testArray6, 0, 16)));
    }
    
}
