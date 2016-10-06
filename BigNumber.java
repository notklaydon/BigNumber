/**
 *
 * @author Klaydon Balicanta
 * 
 */
package bignumber;
import java.util.ArrayList;
import java.util.Arrays;

public class BigNumber {
    int[] bigArray;
    //ArrayList<Integer> bigArray = new ArrayList<Integer>();
    //int sign = 0;     //sign variable to indicate positivity/negativity for
                        //a sign and magnitude BigInteger. This variable is
                        //commented as the current representation of this 
                        //object is 10's complement and can easily be un-
                        //commented to represent a sign and magnitude
                        //representation of a BigInteger
    
                        //will have to rename this is one needs to convert
                        //back to full sign and magnitude representation as
                        //the field conflicts with the method sign() below
    
   /**
    *   constructor - takes a String input and parses the value to be stored
    * in a primitive integer array, 1-(n-1) of the array representing the
    * left-to-right reading of natural decimal numbers, in 10's complement
    * with the 0th value representing the sign value of a 10's complement
    * number
    */
    public BigNumber(String input) {
        /*The following commented out lines were meant to serve as
        the assignment of the sign for a sign and magnitude BigNumber
        and are commented out for the current iteration of the BigNumber
        class as it is currently being represented as 10s complement.*/
        
        /*if (input.startsWith("-", 0))
        {
            sign = 1;
        } else {
            sign = 0;
        }*/
        bigArray = new int[input.length()];
        for(int i = 0; i < input.length(); i++) {
            bigArray[i] = Character.getNumericValue(input.charAt(i));
        }
    }
    
   /**
    *   negate - 10's complement negation
    *   @return BigNumber object that is negated in 10's complement
    */
    public BigNumber negate() {//public void negate()

        BigNumber negated;
        String temp = "";
        int i = 0;
        while(i < (bigArray.length - 1)) {
            temp += (9 - this.bigArray[i]);
            i++;
        }
        temp += (10 - this.bigArray[i]);
        return negated = new BigNumber(temp);
    }
    
    /*
    /**
    *   negate - sign negation for sign and magnitude BigNumber representation
    * /
    public void negate() {
        if(this.sign = 0)
            this.sign = 1;
        else 
            this.sign = 0;
    }
    */
    
   /**
    *   toString - converts BigNumber into a printable String and returns 
    * that String
    *   @return String representation of BigNumber object
    */
    @Override
    public String toString() {
        String result = "";
        for(int i = 0; i < this.bigArray.length; i++) {
            result += this.bigArray[i];
        }
        return result;
    }

   /**
    *   Equals - determines whether compared BigNumber is equal to an
    * input BigNumber
    *   @return boolean value of equality check
    */
    public boolean Equals(BigNumber bn) {
        this.normalize();
        bn.normalize();
        
        if((bn.sign() != this.sign()) || (bn.bigArray.length != this.bigArray.length))
            return false;
        else {
            for(int i = 0; i < this.bigArray.length; i++) {
                if(this.bigArray[i] != bn.bigArray[i])
                    return false;
            }
            return true;
        }
    }

   /**
    *   compareTo - compares BigNumber object to input BigNumber
    * Compares this object with the specified object for order. 
    * Returns a negative integer, zero, or a positive integer 
    * as this object is less than, equal to, or greater than 
    * the specified object.
    *
    */
    public int compareTo(BigNumber bn) {
        if(this.sign() < bn.sign()) {
            return 1;   /*will return positive integer if compared BigNumber
                        is greater than this BigNumber Object*/
        } else if (this.sign() > bn.sign()) {
            return -1;  /*will return negative integer if compared BigNumber
                        is lesser than this BigNumber object*/
        }
        else
            return 0;   /*will return 0 if compared BigNumber and this BigNumber
                        object are equal sign*/
    }


   /**
    *   sign - returns the value of a BigNumber as a 0 for positive of 1
    * for negative
    * 10's complement
    * @return int sign value of a BigNumber
    */
    public int sign() {
        if(this.bigArray[0] < 4)
            return 0;   // positive
        else
            return 1;   // negative
    }
    
    /* *
    *   sign - returns the value of a BigNumber as a 0 for positive of 1
    * for negative
    * sign and magnitude
    * @return int sign value of a BigNumber
    * /
    public int sign() {
        return this.sign;
    }*/

   /**
    * normalize - removes any unnecessary leading sign representations
    */
    public void normalize() {
        int indexHalt = this.bigArray[0];
            while(bigArray[indexHalt] == bigArray[indexHalt + 1])
            {indexHalt += 1;}
            this.bigArray = Arrays.copyOfRange(bigArray, indexHalt, bigArray.length - 1);
    }
    
    /**
     *  reversify - takes an ArrayList of Integers and reverses the order
     * of the list and returns the reversed ArrayList. This method is used
     * only in the normalize() method and is therefore private
     * 
     * @param toBeReversed
     * @return 
     */
    private int[] reversify(int[] toBeReversed) {
        int[] reversed = new int[toBeReversed.length];
        for(int i = 0; i < toBeReversed.length; i++) {
            reversed[i] = toBeReversed[toBeReversed.length - 1];
        }
        return reversed;
    }
}