package arbitraryarithmetic ;
import java.util.InputMismatchException;
import java.lang.ArithmeticException;

public class AInteger{
    // stores the integer value as a string
    private String value;
    public AInteger(){
         // Default constructor: initializes to 0
        this.value="0";
    }
     // Getter for value
    public String getvalue(){
        return this.value;
    }
    // Removes leading zeros and handles negative signs properly
    private String TrimZeroes(String s){
        if(s.equals("0")|| s.equals("-0")){
            return "0";
        }
        boolean isNeg=false;
        if(s.startsWith("-")){
            isNeg=true;
            s=s.substring(1);
        }
        int i=0;
        while(i<s.length() && s.charAt(i)=='0'){
            i++;
        }
        s=s.substring(i);
        if(s.isEmpty()){
            return "0";
        }
        if(isNeg){
            s="-"+s;
        }
        return s;
    }
     // Constructor from String
    public AInteger(String s){
        s=TrimZeroes(s);
        this.value=s;
    }
    // Copy constructor
    public AInteger(AInteger other){
        if(other!=null){
            this.value=other.value;
        }
    }
    // Parses a string and returns AInteger if valid; else throws exception
    public static AInteger parse(String s){
        try{
            if (s.startsWith("-")&& (s.substring(1)).matches("\\d+")){
                return new AInteger(s);
            }else if((!s.startsWith("-"))&& s.matches("\\d+")){
                return new AInteger(s);
            }else{
                throw new InputMismatchException("Invalid number format:Input is expected to be Integer");
            }
        }catch (InputMismatchException e){
            System.out.println(e);
            System.exit(1);
            return null;
        }
    }
    // Check if number is negative
    private boolean isNeg(){
        return this.value.charAt(0)=='-';
    }
     // Check if number is zero
    private boolean isZero(){
        this.value=TrimZeroes(this.value);
        return (this.value.equals("0")|| this.value.equals("-0"));
    }
     // Returns positive part of a negative number
    private AInteger retPos(){
        return new AInteger(this.value.substring(1));
    }
    // Adds two positive strings digit by digit
    private String addStr(String x1,String x2){
        StringBuilder sb1 =new StringBuilder(x1).reverse();
        StringBuilder sb2 =new StringBuilder(x2).reverse();
        while(sb1.length()<sb2.length()){
            sb1.append("0");
        }while(sb2.length()<sb1.length()){
            sb2.append("0");
        }
        //add corresponding digits and if sum>10 carry=su%10
        int carry=0;
        StringBuilder result=new StringBuilder();
        for(int i=0;i<sb1.length();i++){
            int c1=sb1.charAt(i)-'0';
            int c2=sb2.charAt(i)-'0';
            int sum=(c1+c2+carry);
            carry=sum/10;
            result.append(sum%10);                
        }
        if(carry>0){
            result.append(carry);
        }
        return TrimZeroes(result.reverse().toString());
        
    }
    // Subtracts x2 from x1 assuming x1 >= x2
    private String SubtractStr(String x1,String x2){
        StringBuilder sb1 =new StringBuilder(x1);
        StringBuilder sb2 =new StringBuilder(x2);
        while(sb1.length()<sb2.length()){
            sb1.insert(0,"0");
        }while(sb2.length()<sb1.length()){
            sb2.insert(0,"0");
        }
        //Subtract each digit from corresponding digit if borrowing is required then carry=-1
        int carry=0;
        StringBuilder result=new StringBuilder();
        for(int i=sb1.length()-1;i>=0;i--){
            int c1=sb1.charAt(i)-'0'+carry;
            int c2=sb2.charAt(i)-'0';
            if(c1<c2 && i>0){
                c1+=10;
                carry=-1;
            }else {              
                carry=0;
            }
            result.insert(0,c1-c2);
        }
        //TrimZeroes i.e, remove leading zeroes
        return TrimZeroes(result.toString());
    }
    // Multiplies two strings
    private String multiplyStr(String x1,String x2){
        int l1=x1.length();
        int l2=x2.length();
        String result="0";
        // Multiply each digit of the first number with the i'th digit of the second. Store the result in currProduct.
        for(int i=l2-1;i>=0;i--){
            StringBuilder sum = new StringBuilder();
            int carry=0;
            int d2=x2.charAt(i)-'0';
            for(int j=l1-1;j>=0;j--){
                int c=x1.charAt(j)-'0';
                int prod=(c*d2)+carry;
                sum.insert(0,prod%10);
                carry=prod/10;
                
            }
            //By appending /required number of zeroes to sum
            for(int k=0;k<l2-i-1;k++){
                sum.append("0");
            }
            if(carry>0){
                sum.insert(0,carry);
            }
            // add sumto result using addStr method
            result=addStr(result,sum.toString());            
        }
        return TrimZeroes(result);
    }
    private boolean FirstIsGreater(String x1,String x2){
        String y1=TrimZeroes(x1);
        String y2=TrimZeroes(x2);
        if(y1.length()>y2.length()){
            return true;
        }else if(y1.length()==y2.length()){
            if (y1.compareTo(y2)>0){
                return true;
            }else{
                return false;
            }
        }else{
            return false;
        }
    }
    // Divides x1 by x2 using repeated subtraction
    private String divideStr(String x1,String x2){
        StringBuilder currDividend=new StringBuilder();
        StringBuilder result=new StringBuilder();
        for(int i=0;i<x1.length();i++){
            currDividend.append(x1.charAt(i));
            // add digit by digit to currDividend
            String curr =TrimZeroes(currDividend.toString());
            int quoti=0;
            //keep subtracting x2 from curr until it becomes less than x2
            while(FirstIsGreater(curr, x2)||curr.equals(x2)){
                curr=SubtractStr(curr, x2);
                curr=TrimZeroes(curr);
                quoti++;
            }
            currDividend=new StringBuilder(curr);
            result.append(quoti);
        }  
        return TrimZeroes(result.toString());                   
    }
    // Adds two AInteger objects to give another AInteger sum.
    public AInteger add(AInteger other){
        if(this.isZero() && other.isZero()){
            return AInteger.parse("0");
        }
        if (!(other.isNeg()||this.isNeg())){
            return AInteger.parse(addStr(this.value,other.value));//(a+b)
        }else if(!other.isNeg() && this.isNeg()){
            return other.subtract(this.retPos());//(-a+b)
        }else if(other.isNeg() && !this.isNeg()){
            return this.subtract(other.retPos());//(a+(-b))
        }else{
            return AInteger.parse("-"+(this.retPos().add(other.retPos())).value);//((-a)+(-b))
        }
    }
    // Subtracts two AInteger objects to give their AInteger difference.
    public AInteger subtract(AInteger other){
        if(this.isZero() && other.isZero()){
            return AInteger.parse("0");
        }
        if (!(other.isNeg()||this.isNeg())){
            if(FirstIsGreater(this.value,other.value)){
                return AInteger.parse(SubtractStr(this.value,other.value));//(a-b),a>b
            }else{
                return AInteger.parse("-"+SubtractStr(other.value,this.value));//-(b-a),b>a
            }
            
        }else if(!other.isNeg() && this.isNeg()){
            return AInteger.parse("-"+(this.retPos().add(other)).value);//((-a)-b)
        }else if(other.isNeg() && !this.isNeg()){
            return AInteger.parse((this.add(other.retPos())).value);//(a-(-b))
        }else{
            return AInteger.parse("-"+(this.retPos().subtract(other.retPos())).value);//(-a)-(-b)
        }
        
    }
    // Multiplies two AIntegers to give their AInteger product.
    public AInteger multiply(AInteger other){
        if(this.isZero()||other.isZero()){
            return AInteger.parse("0");
        }  
        if(!(this.isNeg()^other.isNeg())){// to find same sign using xor operator
            if(this.isNeg()){
                return AInteger.parse((multiplyStr(this.retPos().value,other.retPos().value)));//(-a*-b)
            }
            else{
                return AInteger.parse((multiplyStr(this.value,other.value)));//(a*b)
            }
        } 
        else{
            if(this.isNeg()){
                return AInteger.parse("-"+(multiplyStr(this.retPos().value,other.value)));//(-a*b)
            }
            else{
                return AInteger.parse("-"+(multiplyStr(this.value,other.retPos().value)));//(a*(-b))
            }
        }
    }
    // Divides two AIntegers to give their AInteger quotient.
    public AInteger Divide(AInteger other){
        try{
            if(other.isZero()){
            throw new ArithmeticException("Division by zero error");
        }       
      } catch(ArithmeticException e){
        System.out.println(e); 
        System.exit(1);       
      }
        if(this.isZero()){
            return AInteger.parse("0");
        }  
        if(!(this.isNeg()^other.isNeg())){// to find same sign using xor operator
            if(this.isNeg()){
                return AInteger.parse((divideStr(this.retPos().value,other.retPos().value)));//(-a/-b)
            }
            else{
                return AInteger.parse((divideStr(this.value,other.value)));//(a/b)
            }
        } 
        else{
            if(this.isNeg()){
                return AInteger.parse("-"+(divideStr(this.retPos().value,other.value)));//(-a/b)
            }
            else{
                return AInteger.parse("-"+(divideStr(this.value,other.retPos().value)));//(a/-b)
            }
        }
    }  

}