/**
 * //Create version2 package of this class
 * @author Eliakah Kakou
 * @author Erin Quigley
 * @author Klaydon Balicanta
 * 
 */
package bignumber;
import java.util.ArrayList;
import java.util.Arrays;

public class BigNumber {
    static final int POS = 0; //change to booleans or enum maybe?
    static final int NEG = 1; //change to booleans or enum maybe?

    int[] bigNumberDigits;
    private int sign = 0;   //indicates positivity (0) or negativity (1)

                    //will have to rename this is one needs to convert
                    //back to full sign and magnitude representation as
                    //the field conflicts with the method sign() below

    /**
     * overloaded constructor
     *
     * modification: took entire body of method call and made new body of constructor
     * @author Eliakah Kakou
     * @param number
     */
    public BigNumber(String number) {
        int[] numList new int[num.length()]; //determines array size
        int index = 0; //char to start populating from

        if (num.charAt(0) == '-') { //checks if negative
            sign = 1;
            index = 1;
        } else if (num.charAt(0) == '+')  //check if positive
            index = 1;


        int numSize = num.length() - index; //substract 1 if there is a sign
        for (int i = index; i < numSize; i++) { //populates array
            int digit = Integer.parseInt("" + num.charAt(i));
            numList[i] = digit;
        }


        this.bigNumberDigits = numList; /scans number number and returns populated list
    }

    /**
     * overloaded constructor
     *
     * @author Erin Quigley
     * @param input
     */
    public BigNumber(String input)
    {
        //put a pattern checker in here later to validate correct input

        //get the digits from the string
        char[] inputNums = input.toCharArray();

        //check sign of number
        if(inputNums[0] == '-') { //if the input string starts with a - sign, set sign to negative
            sign = NEG;

            bigNumberDigits = new int[inputNums.length - 1]; //initialize the array of digits

            for(int i = 1; i < inputNums.length; i++) //skip first character because it was a - sign, get rest of digits
                bigNumberDigits[i - 1] = inputNums[i] - 48; //character encoding offset...for now
        } else {
            sign = POS;

            bigNumberDigits = new int[inputNums.length] //initialize the array of digits

            for(int i = 0; i < inputNums.length; i++) //get digits
                bigNumberDigits[i] = inputNums[i] - 48; //character encoding offset...for now
        }
    }

    /**
     * negate
     *
     * sign negation for sign and magnitude BigNumber representation
     * @author Klaydon Balicanta
     */
    public void negate() {
        if(this.sign = 0)
            this.sign = 1;
        else 
            this.sign = 0;
    }

    
   /**
    * toString
    *
    * converts BigNumber into a printable String and returns that String
    * @return String representation of BigNumber object
    */
    @Override
    public String toString() {
        String result = "";
        if(sign == neg)
            result += "-";

        for(int i = 0; i < this.bigNumberDigits.length; i++) {
            result += this.bigNumberDigits[i];
        }
        return result;
    }

   /**
    * equals
    *
    * determines whether compared BigNumber is equal to an
    * input BigNumber
    * @return boolean value of equality check
    */
    public boolean equals(BigNumber bn) {
        this.normalize();
        bn.normalize();
        
        if((bn.sign() != this.sign()) || (bn.bigNumberDigits.length != this.bigNumberDigits.length))
            return false;
        else {
            for(int i = 0; i < this.bigNumberDigits.length; i++) {
                if(this.bigNumberDigits[i] != bn.bigNumberDigits[i])
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
     * sign
     *
     * @author Klaydon Balicanta
     * @return the value of a BigNumber as a 0 (positive) or 1 (negative)
     * */
    public int sign() {
        return this.sign;
    }

   /**
    * normalize
    *
    * @author Klaydon Balicanta
    * removes front set of excess padded signs (mainly used for if 10's complement number)
    */
    public void normalize() {
        int indexHalt = this.bigNumberDigits[0];
            while(bigNumberDigits[indexHalt] == bigNumberDigits[indexHalt + 1])
            {indexHalt += 1;}
            this.bigNumberDigits = Arrays.copyOfRange(bigNumberDigits, indexHalt, bigNumberDigits.length - 1);
    }

    /**
     * multiply //made by Eliakah
     *
     * This method uses shifting and addition to get the multiplication result
     * as illustrated here: https://www.youtube.com/watch?v=bxAxTMbQii4
     *
     * @param bN
     */
    public BigNumber multiply(BigNumber bN) {
        int zeros = 0; //number of zeros being padded
        int carry = 0; //carry from addition
        int mltpl_result = 0;
        int[] numTwo = bN.getNumber(); //list of number to multiply by
        int[] numOne = getNumber(); // list of our digits
        ArrayList<ArrayList<Integer>> rows = new ArrayList<>(); //list of rows being added
        int current_list = -1;

        if (numTwo.length > 0) { //checks if the number has digits in the list

            for (int i = numTwo.length - 1; i >= 0; i--) {//this goes through each digit in the second number

                rows.add(new ArrayList<Integer>());//create the new list to be added to the lists of lists
                current_list++;//add one to current list counter

                for (int k = 0; k < zeros; k++) { //this will add the pads before we add the other numbers
                    if (rows.get(current_list).size() > 0)
                        rows.get(current_list).add(0, 0);//sets zeros at the begining
                    else
                        rows.get(current_list).add(0);
                }

                for (int j = numOne.length - 1; j >= 0; j--) { //this will go through the digits in the first number
                    mltpl_result = numOne[j] * numTwo[i] + carry;
                    int[] rslt_BN = new BigNumber("" + mltpl_result).getNumber();

                    if (rslt_BN.length == 2 && j != 0) {
                        carry = rslt_BN[0]; //set the carry for the next addition
                        rslt_BN[0] = rslt_BN[1]; // and set the result to the second digit
                        rslt_BN = new int[]{rslt_BN[1]}; // set the extra digit to null
                    } else {

                        carry = 0; //set carry to zero if result is less than 2 digits
                    }
                    for (int k = rslt_BN.length - 1; k >= 0; k--) {
                        if (rows.get(current_list).size() > 0)
                            rows.get(current_list).add(0, rslt_BN[k]);//sets zeros at the begining
                        else
                            rows.get(current_list).add(rslt_BN[k]);//sets zeros at the begining
                    }
                }
                zeros++;//padd with an extra zero
            }
        }
        //repetitive addition step
        BigNumber finalBig;
        finalBig = new BigNumber(""+concatinateNumber(toIntArray(rows.get(0)))); //set result value to first number in row
        for (int i = 1; i < rows.size(); i++) {
            finalBig = finalBig.add(new BigNumber(""+concatinateNumber(toIntArray(rows.get(i)))) );
        }

        return finalBig;

    }

    /**
     * divide //made by Eliakah
     *
     * This method checks if a big number is less than, more than, or equal to the current number
     * if it's more than or equals to , it exits the loop and returns the value accumulated
     * @param bN
     * @return
     */
    BigNumber divide(BigNumber bN){
        int value = 0;
        //if the divider is bigger just return 0
        if(compareTo(bN) > 0)
            return new BigNumber("0");

        //otherwise
        BigNumber currentVal = bN;
        Boolean flag = true;

        while(flag){//while flag is true
            if(compareTo(currentVal.add(currentVal)) >= 0) { //if it's equal to or less than
                if (compareTo(currentVal.add(currentVal)) > 0) { //if less than
                    currentVal = currentVal.add(currentVal);
                    value++;
                }else { //if equal to
                    currentVal = currentVal.add(currentVal);
                    value++;
                    flag = false;
                }
            }else{ //if more than
                flag = false;
            }
        }

        return new BigNumber(""+value); //for now
    }

    /**
     * factor //made by Eliakah
     *
     * This method returns a list of factors of the current number
     * @return
     */
    public ArrayList<BigNumber> factor(){
        BigNumber remainder = this; // number used to deduce factors until = to 1
        BigNumber tryFactor = new BigNumber("2");
        BigNumber soFar = new BigNumber("0");
        int count = 1;
        ArrayList<BigNumber> factors= new ArrayList<>(); //holds the factors
        factors.add(new BigNumber("1"));

        while(soFar.compareTo(this) != 0){//while the number so far != this
            for (int i = 0; i <factors.size() ; i++) { //multiplies all factors to result bignumber
                soFar = soFar.add(factors.get(i));
            }

            if (soFar.add(remainder.divide(tryFactor)).compareTo(this) == 0 && !(soFar.add(remainder.divide(tryFactor)).compareTo(this) > 0) ){ //if the number with the new try factor + remainer add up to this add tryfactor
                factors.add(tryFactor);
                remainder = remainder.divide(tryFactor); //update remainder
                tryFactor = new BigNumber("2");
            }else{
                if((soFar.add(remainder.divide(tryFactor)).compareTo(this) > 0)){ //if the factor multiplication is too big
                    factors.add(remainder);
                    break;
                }else {
                    tryFactor = tryFactor.add(new BigNumber("1"));
                }
            }
        }
        return factors;
    }

    //

    /**
     * add
     *
     * will change to BigNumber instead of String later
     * get the general idea down now
     * @param otherNum
     * @return
     */
    public String add(BigNumber otherNum)
    {
        String result = "";

        int[] sum;
        int[]tempSum;
        int sumSign = 2; //garbage initialization

        //check which number has more digits, pad the shorter number accordingly with 0's
        //consider invoking normalize() after computation is finished

        if(digits.length > otherNum.getDigits().length) //first number is bigger than second number
        {
            int[] newDigits = new int[digits.length];
            for(int i = 0; i < otherNum.getDigits().length; i++)
            {
                newDigits[i + (digits.length - otherNum.getDigits().length)] = otherNum.getDigits()[i];
            }
            otherNum.setDigits(newDigits);
            tempSum = new int[digits.length];
        }
        else if(digits.length < otherNum.getDigits().length) //second number is bigger than first number
        {
            int[] newDigits = new int[otherNum.getDigits().length];
            for(int i = 0; i < digits.length; i++)
            {
                newDigits[i + (otherNum.getDigits().length - digits.length)] = digits[i];
            }
            this.setDigits(newDigits);
            tempSum = new int[otherNum.getDigits().length];
        }
        else //both have same number of digits
        {
            tempSum = new int[digits.length];
        }

        //NOTE: if both are negative or both are positive, we can add as normal and set the sign afterwards
        //NOTE: if one number is negative and the other is positive.......more work

        //now that we've padded with 0's if necessary, let's check the sign of each number
        if(sign == otherNum.getSign()) //both have the same sign
        {
            //adding two **positive** numbers of the **same length**
            int carry = 0;

            for(int i = digits.length - 1; i >= 0; i--)
            {
                //add the two digits and the carry together
                int temp = digits[i] + otherNum.getDigits()[i] + carry;

                if(temp > 9) //carry the 1
                {
                    if(i == 0)//carry the 1 AND no more digits left
                    {
                        carry = 1;
                        //make a new space for the carryover
                        sum = new int[tempSum.length + 1];
                        //copy the temp sum over into the new one
                        for(int j = 0; j < tempSum.length; j++)
                        {
                            sum[j+1] = tempSum[j];
                        }
                        //put the rest of the digits in
                        sum[1] = temp % 10;
                        sum[0] = carry;

                        //assign the sign of the sum appropriately
                        //we already know both have the same sign here, so just check against the first number
                        if(sign == pos)
                        {
                            sumSign = pos;
                        }
                        else if(sign == neg)
                        {
                            sumSign = neg;
                        }

                        //slap that shit in a BigNumber and toString it for the results
                        BigNumber answer = new BigNumber(sumSign, sum);
                        result = answer.toString();
                    }
                    else //carry the 1, more digits left to add
                    {
                        carry = 1;
                        tempSum[i] = temp % 10;
                    }

                }
                else //no carry
                {
                    carry = 0;
                    tempSum[i] = temp;

                    if(i == 0)//no carry AND no more digits left
                    {
                        sum = tempSum;

                        //set the sign of the sum appropriately
                        //we already know both have the same sign here, so just check against the first number
                        if(sign == pos)
                        {
                            sumSign = pos;
                        }
                        else if(sign == neg)
                        {
                            sumSign = neg;
                        }

                        //slap that shit in a new BigNumber & toString it for the results
                        BigNumber answer = new BigNumber(sumSign, sum);
                        result = answer.toString();
                    }
                }
            }
        }
        else if(sign != otherNum.getSign()) //different signs
        {
            //numbers are opposite signs but same magnitude? answer = 0
            //but first we need to check the magnitude of each number
            //we'll look at them digit by digit and count how many matches there are
            int sameMag = 0;
            for(int a = 0; a < digits.length; a++)
            {
                if(digits[a] == otherNum.getDigits()[a])
                {
                    sameMag++;
                }
            }

            //if we had the same number of matches as we do digits, then they're the same
            if(sameMag == digits.length)
            {
                BigNumber answer = new BigNumber(pos, new int[1]);
                result = answer.toString();
                return result;
            }

            //at this point we know that the numbers have different signs and magnitudes,
            //so we have to subtract now

            //figure out which one has greater magnitude
            boolean firstIsGreater = false;
            for(int c = 0; c < digits.length; c++)
            {
                if(digits[c] > otherNum.getDigits()[c])
                {
                    firstIsGreater = true;
                }
            }

            if(firstIsGreater == true) //if the first number is greater than the second
            {
                int carry = 0;

                for(int i = digits.length - 1; i >= 0 ; i--)
                {
                    int temp = 0;

                    if((digits[i] + carry) < otherNum.getDigits()[i])
                    {
                        temp = (10 + digits[i] + carry) - otherNum.getDigits()[i];
                        carry = -1;
                    }
                    else if((digits[i] + carry) > otherNum.getDigits()[i])
                    {
                        temp = (digits[i] + carry) - otherNum.getDigits()[i];
                        carry = 0;
                    }
                    else
                    {
                        carry = 0;
                    }

                    tempSum[i] = temp;
                }

                sumSign = sign;
                sum = tempSum;
                BigNumber answer = new BigNumber(sumSign, sum);
                //normalize answer here when normalize() is written
                result = answer.toString();

            }
            else //the second number was greater than the first number
            {
                int carry = 0;

                for(int i = otherNum.getDigits().length - 1; i >= 0 ; i--)
                {
                    int temp = 0;

                    if((otherNum.getDigits()[i] + carry) < digits[i])
                    {
                        temp = (10 + otherNum.getDigits()[i] + carry) - digits[i];
                        carry = -1;
                    }
                    else if((otherNum.getDigits()[i] + carry) > digits[i])
                    {
                        temp = (otherNum.getDigits()[i] + carry) - digits[i];
                        carry = 0;
                    }
                    else
                    {
                        carry = 0;
                    }

                    tempSum[i] = temp;
                }

                sumSign = otherNum.getSign();
                sum = tempSum;
                BigNumber answer = new BigNumber(sumSign, sum);
                //normalize answer here when normalize() is written
                result = answer.toString();
            }
        }

        return result;
    }

    //placeholder
    public String subtract(BigNumber otherNum)
    {

        if(sign != otherNum.getSign()) //numbers have different signs
        {
            //match the signs up and then add them together
            if(sign == neg) //first number negative, second number positive
            {
                otherNum.setSign(neg);
                return add(otherNum);
            }
            else //first number positive, second number negative
            {
                otherNum.setSign(pos);
                return add(otherNum);
            }
        }
        else //numbers have the same sign
        {
            //numbers are same sign and magnitude? answer = 0
            //add()'s 0 padding hasn't happened yet, so check the number of digits first
            //if they both have the same number of digits, they MAY have the same magnitude
            //if they don't, then we know they don't have the same magnitude
            if(digits.length == otherNum.getDigits().length)
            {
                //now we need to check them digit by digit and count how many matches there are
                int sameMag = 0;
                for(int a = 0; a < digits.length; a++)
                {
                    if(digits[a] == otherNum.getDigits()[a])
                    {
                        sameMag++;
                    }
                }

                //if we had the same number of matches as we do digits, then they're the same
                if(sameMag == digits.length)
                {
                    BigNumber answer = new BigNumber(pos, new int[1]);
                    return answer.toString();
                }
            }

            //so, they aren't the same magnitude but have the same signs?
            //change the sign of the second number according to the first one's sign and send it to add()
            if(sign == neg) //first number negative, set second number to positive
            {
                otherNum.setSign(pos);
                return add(otherNum);
            }
            else //first number positive, set second number to negative
            {
                otherNum.setSign(neg);
                return add(otherNum);
            }
        }
    }

    /**
     * weird private constructor because this method of making a BigNumber should not be public,
     * but I need to use this elsewhere currently
     * @param newsign
     * @param newdigits
     */
    private BigNumber(int newsign, int[] newdigits)
    {
        this.setSign(newsign);
        this.setDigits(newdigits);
    }

    /*GETTERS AND SETTERS*/

    /**
     *
     * @return
     */
    public int[] getNumber() {
        return this.bigNumberDigits;
    }

    /**
     *
     */
    public void setNumber(int[] input) {
        this.bigNumberDigits = input;
    }

    /**
     *
     * @return
     */
    public int getSign() {
        return this.sign;
    }

    /**
     *
     * @param input
     */
    private void setSign(int input) {
        if(input == 0 || input == 1)
            this.sign = input;
    }
}