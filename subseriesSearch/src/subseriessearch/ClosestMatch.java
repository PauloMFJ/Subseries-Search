package subseriessearch;

public class ClosestMatch {
    private int p;
    private int sp;
    
    /**
     * Class constructor used to define a new closest match object.
     * 
     * @param p  Double position of array containing closest match.
     * @param sp Double starting point of the position of the closest 
     *           match within the array.
     */
    public ClosestMatch(int p, int sp) {
        this.p = p;
        this.sp = sp;
    }
    
    /**
     * Method to get the position of the array containing the closest
     * match.
     * 
     * @return Double position value. 
     */
    public double getPosition() {
        return this.p;
    }
    
    /**
     * Method to get the starting position of the closest match within
     * the array.
     * 
     * @return Double starting position value. 
     */
    public double getStartingPosition() {
        return this.sp;
    }
    
    /**
     * Method to return details of this closest match in a formatted 
     * string.
     * 
     * @return Formatted string of closest match. 
     */
    @Override
    public String toString() {
        // New string builder
        StringBuilder string = new StringBuilder();
        
        // Append closest match information to string.
        // String.format is used to format text indent
        string.append("T Position Index: ")
                .append(String.format("%12s", getPosition()));
        string.append("\nS Starting Position Index: ")
                .append(getStartingPosition()).append("\n");
     
        // Convert StringBuilder to string
        return string.toString();
    }
}
