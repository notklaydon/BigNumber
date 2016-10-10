/**
 * BigNumber
 *
 * Description of object go hear.
 *
 * @author Eliakah Kakou
 * @author Erin Quigley
 * @author Klaydon Balicanta
 * Date Due : 11 October 2016
 * Class : Computer Cryptography (CS 07350 1)
 * Professor Bergmann
 */
import java.util.ArrayList;
import java.util.Arrays;

public class BigNumber {
    private static final int POS = 0; //change to booleans or enum maybe?
    private static final int NEG = 1; //change to booleans or enum maybe?

    private int[] bigNumberDigits;
    private int sign = 0;   //indicates positivity (0) or negativity (1)

    /**
     * constructor
     *
     * author Erin Quigley
     * @param input string to parse into array instantiated to hold digits of BigNumber
     */
    public BigNumber(String input) {
        //put a pattern checker in here later to validate correct input

        //get the digits from the string
        char[] inputNums = input.toCharArray();

        //check sign of number
        if (inputNums[0] == '-') { //if the input string starts with a - sign, set sign to negative
            sign = NEG;
            bigNumberDigits = new int[inputNums.length - 1]; //initialize the array of digits
            for (int i = 1; i < inputNums.length; i++) //skip first character because it was a - sign, get rest of digits
                bigNumberDigits[i - 1] = inputNums[i] - 48; //character encoding offset...for now
        } else {
            sign = POS;
            bigNumberDigits = new int[inputNums.length]; //initialize the array of digits
            for (int i = 0; i < inputNums.length; i++) //get digits
                bigNumberDigits[i] = inputNums[i] - 48; //character encoding offset...for now
        }
    }

    /**
     * weird private constructor because this method of making a BigNumber should not be public,
     * but I need to use this elsewhere currently
     *
     * author Erin Quigley
     * @param newsign
     * @param newdigits
     */
    private BigNumber(int newsign, int[] newdigits) {
        this.setSign(newsign);
        this.setNumber(newdigits);
    }

    /**
     * add
     *
     * will change to BigNumber instead of String later
     * get the general idea down now
     *
     * author Erin Quigley
     * @param BNtoAdd BigNumber argument to add to this BigNumber making method call
     * @return BigNumber sum of BigNumber addition
     */
    public BigNumber add(BigNumber BNtoAdd) {
        BigNumber finalAnswer = new BigNumber("0"); /*initialize to 0 BigInteger, to satisfy final return at the
                                                    end of this block. will be overwritten later. */
        int[] sum;
        int[] tempSum;
        int sumSign = 2; //garbage initialization

        //check which number has more digits, pad the shorter number accordingly with 0's
        //consider invoking normalize() after computation is finished

        if(bigNumberDigits.length > BNtoAdd.getNumber().length) //first number is bigger than second number
        {
            int[] newDigits = new int[bigNumberDigits.length];
            for(int i = 0; i < BNtoAdd.getNumber().length; i++)
            {
                newDigits[i + (bigNumberDigits.length - BNtoAdd.getNumber().length)] = BNtoAdd.getNumber()[i];
            }
            BNtoAdd.setNumber(newDigits);
            tempSum = new int[bigNumberDigits.length];
        } else if(bigNumberDigits.length < BNtoAdd.getNumber().length) { //second number is bigger than first number
            int[] newDigits = new int[BNtoAdd.getNumber().length];
            for(int i = 0; i < bigNumberDigits.length; i++) {
                newDigits[i + (BNtoAdd.getNumber().length - bigNumberDigits.length)] = bigNumberDigits[i];
            }
            this.setNumber(newDigits);
            tempSum = new int[BNtoAdd.getNumber().length];
        } else { //both have same number of digits
            tempSum = new int[bigNumberDigits.length];
        }

        //NOTE: if both are negative or both are positive, we can add as normal and set the sign afterwards
        //NOTE: if one number is negative and the other is positive.......more work

        //now that we've padded with 0's if necessary, let's check the sign of each number
        if(sign == BNtoAdd.getSign()) { //both have the same sign
            //adding two **positive** numbers of the **same length**
            int carry = 0;

            for(int i = bigNumberDigits.length - 1; i >= 0; i--) {
                //add the two digits and the carry together
                int temp = bigNumberDigits[i] + BNtoAdd.getNumber()[i] + carry;

                if(temp > 9) {//carry the 1

                    if(i == 0) {//carry the 1 AND no more digits left
                        carry = 1;
                        //make a new space for the carryover
                        sum = new int[tempSum.length + 1];
                        //copy the temp sum over into the new one
                        for(int j = 0; j < tempSum.length; j++) {
                            sum[j+1] = tempSum[j];
                        }
                        //put the rest of the digits in
                        sum[1] = temp % 10;
                        sum[0] = carry;

                        //assign the sign of the sum appropriately
                        //we already know both have the same sign here, so just check against the first number
                        if(sign == POS) {
                            sumSign = POS;
                        } else if(sign == NEG) {
                            sumSign = NEG;
                        }

                        //slap that shit in a BigNumber and toString it for the results
                        finalAnswer = new BigNumber(sumSign, sum);
                    } else { //carry the 1, more digits left to add
                        carry = 1;
                        tempSum[i] = temp % 10;
                    }

                } else { //no carry
                    carry = 0;
                    tempSum[i] = temp;

                    if(i == 0) {//no carry AND no more digits left

                        sum = tempSum;

                        //set the sign of the sum appropriately
                        //we already know both have the same sign here, so just check against the first number
                        if(sign == POS) {
                            sumSign = POS;
                        } else if(sign == NEG) {
                            sumSign = NEG;
                        }

                        //slap that shit in a new BigNumber & toString it for the results
                        finalAnswer = new BigNumber(sumSign, sum);
                    }
                }
            }
        }
        else if(sign != BNtoAdd.getSign()) {//different signs
            //numbers are opposite signs but same magnitude? answer = 0
            //but first we need to check the magnitude of each number
            //we'll look at them digit by digit and count how many matches there are
            int sameMag = 0;
            for(int a = 0; a < bigNumberDigits.length; a++) {
                if(bigNumberDigits[a] == BNtoAdd.getNumber()[a]) {
                    sameMag++;
                }
            }

            //if we had the same number of matches as we do digits, then they're the same
            if(sameMag == bigNumberDigits.length) {
                finalAnswer = new BigNumber(POS, new int[1]);
                return finalAnswer;
            }

            //at this point we know that the numbers have different signs and magnitudes,
            //so we have to subtract now

            //figure out which one has greater magnitude
            boolean firstIsGreater = false;
            for(int c = 0; c < bigNumberDigits.length; c++) {
                if(bigNumberDigits[c] > BNtoAdd.getNumber()[c]) {
                    firstIsGreater = true;
                }
            }

            if(firstIsGreater) {//if the first number is greater than the second
                int carry = 0;

                for(int i = bigNumberDigits.length - 1; i >= 0 ; i--) {
                    int temp = 0;

                    if((bigNumberDigits[i] + carry) < BNtoAdd.getNumber()[i]) {
                        temp = (10 + bigNumberDigits[i] + carry) - BNtoAdd.getNumber()[i];
                        carry = -1;
                    } else if((bigNumberDigits[i] + carry) > BNtoAdd.getNumber()[i]) {
                        temp = (bigNumberDigits[i] + carry) - BNtoAdd.getNumber()[i];
                        carry = 0;
                    } else {
                        carry = 0;
                    }

                    tempSum[i] = temp;
                }

                sumSign = sign;
                sum = tempSum;
                finalAnswer = new BigNumber(sumSign, sum);
                finalAnswer.normalize();

            } else { //the second number was greater than the first number
                int carry = 0;

                for(int i = BNtoAdd.getNumber().length - 1; i >= 0 ; i--) {
                    int temp = 0;

                    if((BNtoAdd.getNumber()[i] + carry) < bigNumberDigits[i]) {
                        temp = (10 + BNtoAdd.getNumber()[i] + carry) - bigNumberDigits[i];
                        carry = -1;
                    } else if((BNtoAdd.getNumber()[i] + carry) > bigNumberDigits[i]) {
                        temp = (BNtoAdd.getNumber()[i] + carry) - bigNumberDigits[i];
                        carry = 0;
                    } else {
                        carry = 0;
                    }

                    tempSum[i] = temp;
                }
                sumSign = BNtoAdd.getSign();
                sum = tempSum;
                finalAnswer = new BigNumber(sumSign, sum);
                finalAnswer.normalize();
            }
        }
        return finalAnswer; //may need to get rid of because Erin covers all clauses when getting to this point
    }

    /**
     * subtract
     *
     * author Erin Quigley
     * @param otherNum BigNumber argument to subtract from this BigNumber making method call
     * @return BigNumber difference of BigNumber subtraction
     */
    public BigNumber subtract(BigNumber otherNum) {
        if(sign != otherNum.getSign()) {  //numbers have different signs
            //match the signs up and then add them together
            if(sign == NEG) { //first number negative, second number positive
                otherNum.setSign(NEG);
                return add(otherNum);
            } else { //first number positive, second number negative
                otherNum.setSign(POS);
                return add(otherNum);
            }
        } else { //numbers have the same sign
            //numbers are same sign and magnitude? answer = 0
            //add()'s 0 padding hasn't happened yet, so check the number of digits first
            //if they both have the same number of digits, they MAY have the same magnitude
            //if they don't, then we know they don't have the same magnitude
            if(bigNumberDigits.length == otherNum.getNumber().length) {
                //now we need to check them digit by digit and count how many matches there are
                int sameMag = 0;
                for(int a = 0; a < bigNumberDigits.length; a++) {
                    if(bigNumberDigits[a] == otherNum.getNumber()[a]) {
                        sameMag++;
                    }
                }

                //if we had the same number of matches as we do digits, then they're the same
                if(sameMag == bigNumberDigits.length) {
                    BigNumber answer = new BigNumber(POS, new int[1]);  //makes call to the weird private constructor
                    return answer;
                }
            }

            //so, they aren't the same magnitude but have the same signs?
            //change the sign of the second number according to the first one's sign and send it to add()
            if(sign == NEG) { //first number negative, set second number to positive
                otherNum.setSign(POS);
                return add(otherNum);
            } else { //first number positive, set second number to negative
                otherNum.setSign(NEG);
                return add(otherNum);
            }
        }
    }

    /**
     * divide
     *
     * This method checks if a big number is less than, more than, or equal to the current number
     * if it's more than or equals to , it exits the loop and returns the value accumulated
     *
     * author Eliakah Kakou
     * @param bN BigNumber argument to divide by this BigNumber making method call
     * @return quotient of BigNumber division
     */
    public BigNumber divide(BigNumber bN){
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
     * multiply
     *
     * This method uses shifting and addition to get the multiplication result
     * as illustrated here: https://www.youtube.com/watch?v=bxAxTMbQii4
     *
     * author Eliakah Kakou
     * @param bN BigNumber argument to multiply to this BigNumber making method call
     * @return BigNumber product of BigNumber multiplication
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
     * takes an array and concatinates it, returns it as an integer number
     *
     * author Eliakah Kakou
     * @param list
     * @return
     */
    private int concatinateNumber(int[] list) {
        int bigNum = 0;
        String str = "";
        for (int i = 0; i < list.length; i++) {
            str += list[i];
        }

        bigNum = Integer.parseInt(str);
        return bigNum;
    }

    /**
     *
     * @param list
     * @return
     */
    private int[] toIntArray(ArrayList<Integer> list){
        int[] convertedList = new int[list.size()];
        for (int i=0; i < convertedList.length; i++)
        {
            convertedList[i] = list.get(i);
        }

        return convertedList;
    }

    /**
     * factor
     *
     * This method returns a list of factors of the current number
     *
     * author Eliakah Kakou
     * @return ArrayList of factors of BigNumber making method call
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
                    tryFactor = tryFactor.add((new BigNumber("1")));
                }
            }
        }
        return factors;
    }

    /**
     * negate
     *
     * sign negation for a BigNumber object
     *
     * author Klaydon Balicanta
     * @return negated form of calling BigNumber object
     */
    public BigNumber negate() {
        if(this.getSign() == 0)
            this.setSign(1);
        else
            this.setSign(0);

        return this;
    }

    /**
     * normalize
     *
     * author Klaydon Balicanta
     * removes front set of excess padded signs or buffered 0's
     */
    public void normalize() {
        int indexHalt = 0;

        if (this.getSign() != 0)
            this.bigNumberDigits = Arrays.copyOfRange(this.bigNumberDigits, 1, (bigNumberDigits.length - 1));

        for(int i = 0; i < this.bigNumberDigits.length; i++) {
            if(bigNumberDigits[indexHalt] != bigNumberDigits[indexHalt + 1]) {
                indexHalt++;
            } else {
                i = 999999999;    //this is used to break out of the loop
            }
        }
        this.bigNumberDigits = Arrays.copyOfRange(this.bigNumberDigits, (indexHalt), (bigNumberDigits.length - 1));
    }

    /**
     * equals
     *
     * determines whether compared BigNumber is equal to an
     * input BigNumber
     *
     * author Klaydon Balicanta
     * @return boolean value of equality check
     */
    public boolean equals(BigNumber bn) {
        this.normalize();
        bn.normalize();

        if ((bn.getSign() != this.getSign()) || (bn.bigNumberDigits.length != this.bigNumberDigits.length)) {
            return false;
        } else {
            for(int i = 0; i < this.bigNumberDigits.length; i++) {
                if(this.bigNumberDigits[i] != bn.bigNumberDigits[i])
                    return false;
            }
            return true;
        }
    }

    /**
     * compareTo
     *
     * compares BigNumber object to input BigNumber
     * Compares this object with the specified object for order.
     * Returns a negative integer, zero, or a positive integer
     * as this object is less than, equal to, or greater than
     * the specified object.
     *
     * author Klaydon Balicanta
     * @return value of comparison between this BigNumber and BigNumber argument
     * (example:
     * object making call bigger than argument is : 1,
     * object making call equal to argument is : 0,
     * object making call less-than argument is : -1)
     */
    public int compareTo(BigNumber bn) {
        BigNumber toCompare = bn;
        int value = 0; //variable to hold greater(1), equal(0), or less than(-1) value
        if(this.sign() > toCompare.sign()) {
            return 1;   /*return positive integer if this is bigger than argument*/
        } else if (this.sign() < toCompare.sign()) {
            return -1;  /*will return negative integer if this is lesser than argument*/
        } else {
            this.normalize();
            bn.normalize();
            /*NEED TO DOUBLE CHECK FOR SIGN AT BEGINNING AND REMOVE
            FUNCTIONALITY FOR ADDING NEGATIVE (1) BUFFER INCONSISTENT*/
            if(this.getNumber().length < toCompare.getNumber().length) {
                int[] buff = new int[toCompare.getNumber().length - this.getNumber().length];
                for (int a  : buff) {a = 0;}
                int[] both = concat(buff, this.getNumber());
                this.bigNumberDigits = both;
            } else if (this.getNumber().length > toCompare.getNumber().length) {
                int[] buff = new int[this.getNumber().length - toCompare.getNumber().length];
                for (int a  : buff) {a = 0;}
                int[] both = concat(buff, toCompare.getNumber());
                toCompare = new BigNumber(both.toString());
            }
            /**********************************************/

            for(int i = this.bigNumberDigits.length; i>0; i--) {
                //System.out.println(this.bigNumberDigits.length + " " + toCompare.bigNumberDigits.length);
                if(this.bigNumberDigits[i-1] > toCompare.bigNumberDigits[i-1]) {
                    value = 1;
                } else if (this.bigNumberDigits[i-1] < toCompare.bigNumberDigits[i-1]) {
                    value = -1;
                } else {
                    value = 0;
                }
            }
            return value;

            //irrelevant
            /*will return 0 if compared BigNumber and this BigNumber object are equal sign*/
        }
    }

    /**
     *
     * @param a
     * @param b
     * @return
     */
    public int[] concat(int[] a, int[] b) {
        int aLen = a.length;
        int bLen = b.length;
        int[] c= new int[aLen+bLen];
        System.arraycopy(a, 0, c, 0, aLen);
        System.arraycopy(b, 0, c, aLen, bLen);
        return c;
    }

    /**
     * getNumber
     *
     * getter for this objects array of ints holding BigNumber digits
     *
     * @return the number representation of this BigNumber object
     */
    public int[] getNumber() {
        return this.bigNumberDigits;
    }

    /**
     * setNumber
     *
     * setter for this objects array of ints holding BigNumber digits
     *
     * @param input is new array of digits and, thus, the new value of this BigNumber object
     */
    public void setNumber(int[] input) {
        this.bigNumberDigits = input;
    }

    /**
     * getSign
     *
     * getter for this objects sign
     *
     * @return sign of this BigNumber object
     */
    public int getSign() {
        return this.sign;
    }

    /**
     * setSign
     *
     * setter for this objects sign
     *
     * @param input new sign
     */
    private void setSign(int input) {
        if(input == 0 || input == 1)
            this.sign = input;
    }

    /**
     * sign
     *
     * author Klaydon Balicanta
     * @return the value of a BigNumber as a 0 (positive) or 1 (negative)
     * */
    private int sign() {
        return this.sign;
    }

    /**
     * toString
     *
     * converts BigNumber object into a printable String and returns that String
     *
     * author Klaydon Balicanta
     * Made modifications taken from Erin Quigley's code to check if sign is negative
     * @return String representation of BigNumber object
     */
    @Override
    public String toString() {
        String result = "";
        if(sign == NEG)
            result += "-";

        for(int i = 0; i < this.bigNumberDigits.length; i++) {
            result += this.bigNumberDigits[i];
        }
        return result;
    }
}