package garwicksAlgorithm;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import java.text.DecimalFormat;

/**
 *
 * @author ComPeter
 */
public class Tester {
    
    int memSize;
    int numStacks;
    
    int[][] growthUnits;
    int[][] growth20;
    int[][] growth50;
    
    public Tester(int memSize, int numStacks){
        this.memSize = memSize;
        this.numStacks = numStacks;
        
    }
    
    public void runAllGrowths(int indicator){
        runGrowthUnits(indicator);
        runGrowth20(indicator);
        runGrowth50(indicator);
    }
    
    public void runGrowthUnits(int indicator){
        if(indicator ==0){
            System.out.println("For normal Garwicks growth in units:");
        }else{
            System.out.println("For modified Garwicks growth in units:");
        }
        
        double Usum70 = 0;
        double Usum = 0;
        double Uoverflows = 0;
        double Esum70 = 0;
        double Esum = 0;
        double Eoverflows = 0;
        
        for(double rho = 1; rho >= 0; rho-=0.5){
            if(indicator == 0){
                for(int i=0; i<10; i++){
                    //System.out.println("Running uniform");
                    Garwicks instance = new Garwicks(memSize, numStacks, rho);
                    instance.generateInputUniform(1);
                    int[] holder = instance.report();
                    Usum70 += holder[0];
                    Usum += holder[1];
                    Uoverflows += holder[2];
                }
                for(int i=0; i<10; i++){
                    //System.out.println("Running exponential");
                    Garwicks instance = new Garwicks(memSize, numStacks, rho);
                    instance.generateInputExponential(1);
                    int[] holder = instance.report();
                    Esum70 += holder[0];
                    Esum += holder[1];
                    Eoverflows += holder[2];
                }
                Usum70 /= 10;
                Usum /= 10;
                Uoverflows /= 10;
                Esum70 /= 10;
                Esum /= 10;
                Eoverflows /= 10;
                DecimalFormat df = new DecimalFormat("#.##");
                
                System.out.println("Rho is: " + rho + " Uniform (70/100/overflows): " + df.format(Usum70) + ", " + df.format(Usum) + ", "+ df.format(Uoverflows) 
                        + " Exponential (70/100/overflows): " + df.format(Esum70) + ", " + df.format(Esum) + ", " + df.format(Eoverflows));
            }else{
                for(int i=0; i<10; i++){
                    //System.out.println("Running uniform");
                    ModifiedGarwicks instance = new ModifiedGarwicks(memSize, numStacks, rho);
                    instance.generateInputUniform(1);
                    int[] holder = instance.report();
                    Usum70 += holder[0];
                    Usum += holder[1];
                    Uoverflows += holder[2];
                }
                for(int i=0; i<10; i++){
                    //System.out.println("Running exponential");
                    ModifiedGarwicks instance = new ModifiedGarwicks(memSize, numStacks, rho);
                    instance.generateInputExponential(1);
                    int[] holder = instance.report();
                    Esum70 += holder[0];
                    Esum += holder[1];
                    Eoverflows += holder[2];
                }
                Usum70 /= 10;
                Usum /= 10;
                Uoverflows /= 10;
                Esum70 /= 10;
                Esum /= 10;
                Eoverflows /= 10;
                DecimalFormat df = new DecimalFormat("#.##");
                System.out.println("Rho is: " + rho + " Uniform (70/100/overflows): " + df.format(Usum70) + ", " + df.format(Usum) + ", "+ df.format(Uoverflows) 
                        + " Exponential (70/100/overflows): " + df.format(Esum70) + ", " + df.format(Esum) + ", " + df.format(Eoverflows));
            }
        }
        
    }
    
    public void runGrowth20(int indicator){
        if(indicator ==0){
            System.out.println("For normal Garwicks growth in spurts 20:");
        }else{
            System.out.println("For modified Garwicks growth in spurts 20:");
        }
        
        double Usum70 = 0;
        double Usum = 0;
        double Uoverflows = 0;
        double Esum70 = 0;
        double Esum = 0;
        double Eoverflows = 0;
        
        for(double rho = 1; rho >= 0; rho-=0.5){
            if(indicator == 0){
                for(int i=0; i<10; i++){
                    //System.out.println("Running uniform");
                    Garwicks instance = new Garwicks(memSize, numStacks, rho);
                    instance.generateInputUniform(20);
                    int[] holder = instance.report();
                    Usum70 += holder[0];
                    Usum += holder[1];
                    Uoverflows += holder[2];
                }
                for(int i=0; i<10; i++){
                    //System.out.println("Running exponential");
                    Garwicks instance = new Garwicks(memSize, numStacks, rho);
                    instance.generateInputExponential(20);
                    int[] holder = instance.report();
                    Esum70 += holder[0];
                    Esum += holder[1];
                    Eoverflows += holder[2];
                }
                Usum70 /= 10;
                Usum /= 10;
                Uoverflows /= 10;
                Esum70 /= 10;
                Esum /= 10;
                Eoverflows /= 10;
                DecimalFormat df = new DecimalFormat("#.##");
                System.out.println("Rho is: " + rho + " Uniform (70/100/overflows): " + df.format(Usum70) + ", " + df.format(Usum) + ", "+ df.format(Uoverflows) 
                        + " Exponential (70/100/overflows): " + df.format(Esum70) + ", " + df.format(Esum) + ", " + df.format(Eoverflows));
            }else{
                for(int i=0; i<10; i++){
                    //System.out.println("Running uniform");
                    ModifiedGarwicks instance = new ModifiedGarwicks(memSize, numStacks, rho);
                    instance.generateInputUniform(20);
                    int[] holder = instance.report();
                    Usum70 += holder[0];
                    Usum += holder[1];
                    Uoverflows += holder[2];
                }
                for(int i=0; i<10; i++){
                    //System.out.println("Running exponential");
                    ModifiedGarwicks instance = new ModifiedGarwicks(memSize, numStacks, rho);
                    instance.generateInputExponential(20);
                    int[] holder = instance.report();
                    Esum70 += holder[0];
                    Esum += holder[1];
                    Eoverflows += holder[2];
                }
                Usum70 /= 10;
                Usum /= 10;
                Uoverflows /= 10;
                Esum70 /= 10;
                Esum /= 10;
                Eoverflows /= 10;
                DecimalFormat df = new DecimalFormat("#.##");
                System.out.println("Rho is: " + rho + " Uniform (70/100/overflows): " + df.format(Usum70) + ", " + df.format(Usum) + ", "+ df.format(Uoverflows) 
                        + " Exponential (70/100/overflows): " + df.format(Esum70) + ", " + df.format(Esum) + ", " + df.format(Eoverflows));
            }
        }
    }
    
    public void runGrowth50(int indicator){
        if(indicator ==0){
            System.out.println("For normal Garwicks growth in spurts 50:");
        }else{
            System.out.println("For modified Garwicks growth in spurts 50:");
        }
        
        double Usum70 = 0;
        double Usum = 0;
        double Uoverflows = 0;
        double Esum70 = 0;
        double Esum = 0;
        double Eoverflows = 0;
        
        for(double rho = 1; rho >= 0; rho-=0.5){
            if(indicator == 0){
                for(int i=0; i<10; i++){
                    //System.out.println("Running uniform");
                    Garwicks instance = new Garwicks(memSize, numStacks, rho);
                    instance.generateInputUniform(50);
                    int[] holder = instance.report();
                    Usum70 += holder[0];
                    Usum += holder[1];
                    Uoverflows += holder[2];
                }
                for(int i=0; i<10; i++){
                    //System.out.println("Running exponential");
                    Garwicks instance = new Garwicks(memSize, numStacks, rho);
                    instance.generateInputExponential(50);
                    int[] holder = instance.report();
                    Esum70 += holder[0];
                    Esum += holder[1];
                    Eoverflows += holder[2];
                }
                Usum70 /= 10;
                Usum /= 10;
                Uoverflows /= 10;
                Esum70 /= 10;
                Esum /= 10;
                Eoverflows /= 10;
                DecimalFormat df = new DecimalFormat("#.##");
                System.out.println("Rho is: " + rho + " Uniform (70/100/overflows): " + df.format(Usum70) + ", " + df.format(Usum) + ", "+ df.format(Uoverflows) 
                        + " Exponential (70/100/overflows): " + df.format(Esum70) + ", " + df.format(Esum) + ", " + df.format(Eoverflows));
            }else{
                for(int i=0; i<10; i++){
                    //System.out.println("Running uniform");
                    ModifiedGarwicks instance = new ModifiedGarwicks(memSize, numStacks, rho);
                    instance.generateInputUniform(50);
                    int[] holder = instance.report();
                    Usum70 += holder[0];
                    Usum += holder[1];
                    Uoverflows += holder[2];
                }
                for(int i=0; i<10; i++){
                    //System.out.println("Running exponential");
                    ModifiedGarwicks instance = new ModifiedGarwicks(memSize, numStacks, rho);
                    instance.generateInputExponential(50);
                    int[] holder = instance.report();
                    Esum70 += holder[0];
                    Esum += holder[1];
                    Eoverflows += holder[2];
                }
                Usum70 /= 10;
                Usum /= 10;
                Uoverflows /= 10;
                Esum70 /= 10;
                Esum /= 10;
                Eoverflows /= 10;
                DecimalFormat df = new DecimalFormat("#.##");
                System.out.println("Rho is: " + rho + " Uniform (70/100/overflows): " + df.format(Usum70) + ", " + df.format(Usum) + ", "+ df.format(Uoverflows) 
                        + " Exponential (70/100/overflows): " + df.format(Esum70) + ", " + df.format(Esum) + ", " + df.format(Eoverflows));
            }
        }
        
    }
}
