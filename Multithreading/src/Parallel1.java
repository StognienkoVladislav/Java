import java.util.ArrayList;
import java.util.Collections;


class ThreadCacl extends Thread{
    
    ArrayList<Double> vectA = new ArrayList<Double>();
    int startIndex;
    int endIndex;
    double resultMax;
    double resultMin;

    public ThreadCacl(ArrayList<Double> vectA, int startIndex, int endIndex) {
        this.vectA = vectA;
        this.startIndex = startIndex;
        this.endIndex = endIndex;
    }
    
    public double getResultMax(){
    	return resultMax;
    }
    
    public double getResultMin(){
    	return resultMin;
    }
    
    @Override
    public void run(){
    	resultMax=vectA.get(0);
    	resultMin=vectA.get(0);
        for(int i = startIndex; i<endIndex; i++ ){
        	if(resultMax<vectA.get(i))
        		resultMax=vectA.get(i);
        	
        	if(resultMin>vectA.get(i))
        		resultMin=vectA.get(i);
        }
    }
    
}

class ParallelMin extends Thread{
    ArrayList<Double> vectA = new ArrayList<Double>();

    double resultMin;

    public double getResultMin(){
        return resultMin;
    }

    public ParallelMin(ArrayList<Double> vectA){
        this.vectA=vectA;
    }

    @Override
    public void run(){
        resultMin = Collections.min(vectA);
    }
}

class ParallelMax extends Thread{
    ArrayList<Double> vectA = new ArrayList<Double>();

    double resultMax;

    public double getResultMax(){
        return resultMax;
    }

    public ParallelMax(ArrayList<Double> vectA){
        this.vectA=vectA;
    }
    @Override
    public void run(){
        resultMax = Collections.max(vectA);
    }
}

public class Parallel1 {
	
    public static int SIZE = 100000;
    public static int NUNMBER_JOBS = 4;
    
    public static void main(String [] args ) throws InterruptedException{
        
//////////////////////////////////////////////////////////////////////  /////////////////////
    	
        ArrayList<Double> vectA = new ArrayList<Double>();
         
        for(int i =0; i<SIZE; i++){
            vectA.add(Math.random()*100);
        }

        double resultMax = vectA.get(0);
        double resultMin = vectA.get(0);
        
        long startTime = System.currentTimeMillis();
        
        for( int i=0; i< SIZE; i++){
        	if(resultMax<vectA.get(i))
        		resultMax=vectA.get(i);
        	
        	if(resultMin>vectA.get(i))
        		resultMin=vectA.get(i);
        }
        
        long timeSpent = System.currentTimeMillis() - startTime;
        
        System.out.println("resultMax " +resultMax);
        System.out.println("resultMin " + resultMin);
        System.out.println("Time " + timeSpent + " millis");
      
///////////////////////////////////////////////////////////////////////////////////////////        
                  
        long startParallelTime = System.currentTimeMillis();
        
        ThreadCacl[] treadArrray = new ThreadCacl[NUNMBER_JOBS];
            
          for(int i = 0; i < NUNMBER_JOBS; i++){
              treadArrray[i] = new ThreadCacl(vectA ,
                      SIZE/NUNMBER_JOBS * i,
                      i==NUNMBER_JOBS-1 ?SIZE:SIZE/NUNMBER_JOBS * (i + 1) );
              treadArrray[i].start();
          }
          for(int i = 0; i < NUNMBER_JOBS; i++){
              treadArrray[i].join();
          }
            
          for(int i = 0; i < NUNMBER_JOBS; i++){
        	  if(resultMax<treadArrray[i].getResultMax())
          		resultMax=treadArrray[i].getResultMax();
          	
          	if(resultMin>treadArrray[i].getResultMin())
          		resultMin=treadArrray[i].getResultMin();
          }
          
          long timeParallelSpent = System.currentTimeMillis() - startParallelTime;
          
          System.out.println("/////////////////////////////////////");
          System.out.println("parallelResultMax " + resultMax );
          System.out.println("parallelResultMin " + resultMin );
          System.out.println("Time : " + timeParallelSpent + " millis");
   
///////////////////////////////////////////////////////////////////////////////////////////
          long startP2Time = System.currentTimeMillis();
          
          ParallelMin parallelMin = new ParallelMin(vectA);
          ParallelMax parallelMax = new ParallelMax(vectA);

          parallelMin.start();
          parallelMax.start();

          parallelMin.join();
          parallelMax.join();

          long timeP2Spent = System.currentTimeMillis() - startP2Time;

         System.out.println("/////////////////////////////////////");
         System.out.println("parallelResultMax " + parallelMax.getResultMax() );
         System.out.println("parallelResultMin " + parallelMin.getResultMin() );
         System.out.println("Time : " + timeParallelSpent + " millis");
    }
    
}
