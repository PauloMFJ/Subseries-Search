package subseriessearch;
import java.lang.Math;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Random;

public class SubseriesSearch {
    /**
     * Method to generate a random query.
     * 
     * @param s Integer length value of this query.
     * @return  Query array of random double values.
     */
    public static double[] generateQuery(int s) {
        double[] q = new double[s];
        Random r = new Random();
        for (int i = 0; i < s; i++) {
            q[i] = r.nextDouble();
        }
        return q;
    }
    
    /**
     * Method to generate a random data set of equal lengths in
     * the form of n x n.
     * 
     * @param n Integer length value of this data set.
     * @return  Data set array of random double values.
     */
    public static double[][] generateDataSet(int n) {
        double[][] data = new double[n][n];
        Random r = new Random();
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                data[i][j] = r.nextDouble();
            }
        }

        return data;
    }
    
    // Used to keep track of lowest distance between methods
    private static double lD = 0;
    private static boolean first = true;
    
    /**
     * Method to return the starting point{s} of the position with the  
     * closest distance between 2 arrays.
     * 
     * @param S Series array of doubles.
     * @param Q Query array of doubles to check against S.
     * @return  Array containing single starting point, or array 
     *          containing multiple starting points where distance 
     *          is a tie.
     */
    public static ArrayList<Integer> closestDistance(double S[], 
        double Q[]) { 
        // Get size of arrays
        double n = S.length;
        double k = Q.length;
        
        // New starting point(s) array lists
        ArrayList<Integer> sP = new ArrayList<>();
        
        // Loop number of possible combinations in series
        for (int i = 0; i < (n - k + 1); i++) {
            // Current elements distance
            double d = 0;

            // While distance is lower than the current lowest 
            // distance calculate distance
            int x = 0;                    
            while (x < k && (d <= lD || first)) {
                d = d + Math.pow((Q[x] - S[x + i]), 2);                        
                x++;
            }
           
            // Set lowest distance on first iteration
            if (first) {
                first = false;
                lD = d;
            }
                    
            // If distance is less than equal to previously lowest 
            // distance then proceed
            if (d <= lD) {
                // Clear previous matches and update new lowest distance
                if (d < lD) {
                    lD = d;
                    sP.clear();
                }
                // Add this start poisition to array
                sP.add(i);
            }
        }
        return sP;
    }
    
    /**
     * Method to perform subseries search on a n x n matrix with a 
     * query array to find the closest match(s).
     * 
     * @param T Matrix of size n x n containing sequences of 
     *          doubles.
     * @param S Query subseries array of double values.
     * @return  ArrayList containing ClosestMatch objects with the index 
     *          of the subseries in T with the closest match to S and 
     *          the starting point of the closest match.
     */
    public static ArrayList<ClosestMatch> subseriesSearch(double T[][], 
            double S[]) {
        // Get size of arrays
        double n = T.length;
        double k = S.length;
        
        // Reset lowest distance to 0
        lD = 0;
        first = true;
        
        // Array containing single closest match, or array containing 
        // ties in distance where closest match is the same
        ArrayList<ClosestMatch> matches = new ArrayList<>();
        
        // Ensure that T is greater than S
        if(n > k) {
            // Loop all series of T
            for (int i = 0; i < n; i++) {
                // Update current distance before distance calculations
                double cD = lD;

                // Get starting poistion from closest distance 
                // method containing 1 or multiple starting positions
                // if multiple ties are found
                ArrayList<Integer> sP = closestDistance(T[i], S);
                for(int x = 0; x < sP.size(); x++) {
                    // If this match is not a tie reset list
                    if (lD < cD)
                        matches.clear();
                    // Add this closest match to list
                    matches.add(new ClosestMatch(i, sP.get(x)));
                }
            }
        }
        return matches;
    }

    /**
     * Method to print out the average time taken to complete a 
     * subseries search.
     * 
     * @param tSize Integer size of n x n subsequences T matrix.
     * @param sSize Integer size of series S array.
     * @param times Times algorithm will be tested
     */
    public static void SubseriesSearchMean(int tSize, int sSize, 
            int times) {
        // Used to calculate total sum in order to get mean
        double sum = 0;
        
        // Perform algorithm a set number of times to get an average
        // time to perform algorithm
        for (int i = 0; i < times; i++) {
            // Randomly generate series of numerical values
            double[][] T = generateDataSet(tSize);
            double[] S = generateQuery(sSize);
            
            // Get current time, perform algorithm then get the time
            // taken based on the new time minus time before algorithm
            long t1 = System.nanoTime();
            // Perform subseries search algorithm
            subseriesSearch(T, S);
            long t2 = System.nanoTime() - t1;
            
            // Recorded in milliseconds to make time more interprettable
            sum += (double) t2 / 1000000.0;
        }
        // Get average time
        double mean = sum / times;
        
        // Used to format output time
        DecimalFormat df = new DecimalFormat("#.####");
        
        // Print iteration information
        System.out.println("Size of n: " + tSize 
                + "\nTime taken: " + df.format(mean) + "s.\n");

    }
    
    public static void main(String[] args) {
        // ------ Subseries Search Algorithm Test ------
        // Randomly generate series of numerical values
        //double[][] T = generateDataSet(100);
        //double[] S = generateQuery(10);
               
        // Print all closest matches found (Atleast 1 or mutilple if 
        // their was ties in closest match)
        //for (ClosestMatch match : subseriesSearch(T, S))
        //    System.out.println(match.toString());        
        
        // ------ Subseries Search Time Experiment ------
        // Get average time taken to perform subseries search across
        // multiple sizes of a T array
        for(int i = 0; i <= 500; i += 50)
            SubseriesSearchMean(i, 10, 100);
    }
}