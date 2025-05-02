package arbitraryarithmetic ;
import java.util.InputMismatchException;
import java.lang.ArithmeticException;
public class AFloat {
    private String value;// Private String to store the value of the current object.
    private int divisionPre=30;// Store the max number of decimal places we want to go till during division.
     // Default constructor: initializes to 0.0
     public AFloat(){
        this.value="0.0";
    }
    //Constructor from String and checking whether the input is correct or not
    public AFloat(String s){
        try{
            if (s == null) {
                throw new InputMismatchException("Input cannot be null");
            }
        
            boolean isNeg = false;
            if (s.startsWith("-")) {
                isNeg = true;
                s = s.substring(1);
            }
        
            int decimal_point = 0;
            for (int i = 0; i < s.length(); i++) {
                char ch = s.charAt(i);
                if (ch == '.') {
                    decimal_point++;
                } else if (!Character.isDigit(ch)) {
                    throw new InputMismatchException("Invalid character in number: " + ch);
                }
            }
        
            if (decimal_point > 1) {
                throw new InputMismatchException("Invalid number: more than one decimal point");
            }
        
            if (s.isEmpty()) {
                throw new InputMismatchException("Invalid number: empty input");
            }
        
            s = addZeroes(s);
            s = TrimZeroes(s);
        
            if (isNeg) {
                s = "-" + s;
            }
        
            this.value = s;

        }
        catch (InputMismatchException e){
            System.out.println(e);
        }

    }
    //copy constructor
    public AFloat(AFloat other){
        if(other!=null){
            this.value=other.value;
        }
    }
    //If int is given converts into float
    private String addZeroes(String val){
        if(!(val.contains("."))){
            val += ".0";
        }
        return val;
    }
    // Getter for value
    public String getValue(){
        return this.value;
    }
    public static AFloat parse(String s){
        return new AFloat(s);
    }
    private boolean isZero(){
        String s=TrimZeroes(this.value);
        return (s.equals("0.0")||s.equals("-0.0"));
    }
    //Trim leading zeroes of flaot
    private String TrimZeroes(String x){
        String[] s=x.split("\\.");
        String result="";
        boolean isNeg=false;
        if(s[0].equals("0")){
            result="0";
        }else{
            if(s[0].charAt(0)=='-'){
                isNeg=true;
                s[0]=s[0].substring(1);
            }
            int index=0;
            while(index < s[0].length() && s[0].charAt(index)=='0'){
                index++;
            }
            if(index==s[0].length()){
                result = "0";
            }
            else{
                result = s[0].substring(index);
            }
        }

        result += "."; 
        result+=s[1];
        return(isNeg && !result.equals("0.0")?"-":"")+result;
        
    }
    //checks if the number is negative
    private boolean isNeg(){
        return this.value.charAt(0)=='-';
    }
    // Returns positive part of a negative number
    private AFloat retPos(){
        return AFloat.parse(this.value.substring(1));
    }
    // Adds two positive strings digit by digit by splitting and doing as int for both intger and fractional part
    private String addDecimal(String x,String y){
        String[] s1=x.split("\\.");
        String[] s2=y.split("\\.");
        StringBuilder fracSum=new StringBuilder();
        String I1=s1[1];
        String I2=s2[1];
        while(I1.length()<I2.length()){
            I1+="0";
        }while(I1.length()>I2.length()){
            I2+="0";
        }
        
        int carry=0;
        for(int i=(I1.length())-1;i>=0;i--){
            int sum=(I1.charAt(i)-'0')+(I2.charAt(i)-'0')+(carry);
            carry=sum/10;
            fracSum.insert(0,sum%10);                
        }
        StringBuilder IntSum=new StringBuilder();
        I1=s1[0];
        I2=s2[0];
        while(I1.length()<I2.length()){
            I1+="0";
        }while(I1.length()>I2.length()){
            I2+="0";
        }
        for(int i=(I1.length()-1);i>=0;i--){
            int sum=(I1.charAt(i)-'0')+(I2.charAt(i)-'0')+(carry);
            carry=sum/10;
            IntSum.insert(0,sum%10);                 
        }
        if(carry>0){
            IntSum.insert(0,carry);
        }


        return TrimZeroes(IntSum+"."+fracSum);

    }// Subtracts two positive strings digit by digit by splitting and doing as int for both intger and fractional part
    private String SubtractDecimal(String x,String y){
        String[] s1=x.split("\\.");
        String[] s2=y.split("\\.");
        StringBuilder sb1 =new StringBuilder(s1[1]);
        StringBuilder sb2 =new StringBuilder(s2[1]);
        while(sb1.length()<sb2.length()){
            sb1.insert(0,"0");
        }while(sb2.length()<sb1.length()){
            sb2.insert(0,"0");
        }
        int carry=0;
        StringBuilder fracdiff=new StringBuilder();
        for(int i=sb1.length()-1;i>=0;i--){
            int c1=sb1.charAt(i)-'0'+carry;
            int c2=sb2.charAt(i)-'0';
            if(c1<c2 && i>=0){
                c1+=10;
                carry=-1;
            }else {              
                carry=0;
            }
            fracdiff.insert(0,c1-c2);
        }
        
        StringBuilder intdiff=new StringBuilder();
        StringBuilder sbi1 =new StringBuilder(s1[0]);
        StringBuilder sbi2 =new StringBuilder(s2[0]);
        while(sbi1.length()<sbi2.length()){
            sbi1.insert(0,"0");
        }while(sbi2.length()<sbi1.length()){
            sbi2.insert(0,"0");
        }
        for(int i=sbi1.length()-1;i>=0;i--){
            int c1=sbi1.charAt(i)-'0'+carry;
            int c2=sbi2.charAt(i)-'0';
            if(c1<c2 && i>0){
                c1+=10;
                carry=-1;
            }else {              
                carry=0;
            }
            intdiff.insert(0,c1-c2);
        }
        return(intdiff+"."+fracdiff);
        
    }
    //This is required when doing multuplication 
    private String addIntString(String x1,String x2){
        StringBuilder sb1 =new StringBuilder(x1).reverse();
        StringBuilder sb2 =new StringBuilder(x2).reverse();
        while(sb1.length()<sb2.length()){
            sb1.append("0");
        }while(sb2.length()<sb1.length()){
            sb2.append("0");
        }
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
        return TrimIntZeroes(result.reverse().toString());
        
    }
    //for addIntString and Subtract int String TrimInZeroes is required
    private String TrimIntZeroes(String s){
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
    }//This is required when doing division
    private String SubtractIntString(String x1,String x2){
        StringBuilder sb1 =new StringBuilder(x1);
        StringBuilder sb2 =new StringBuilder(x2);
        while(sb1.length()<sb2.length()){
            sb1.insert(0,"0");
        }while(sb2.length()<sb1.length()){
            sb2.insert(0,"0");
        }
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
        return TrimIntZeroes(result.toString());
    }
    // sum of  length of fractional parts and multiplying the decimals by removing point and then adding point 
    private String MultiplyDecimal(String x,String y){
        String[] x1=x.split("\\.");
        String[] x2=y.split("\\.");
        int decimal_points=x1[1].length()+x2[1].length();
        String s1=x1[0]+x1[1];
        String s2=x2[0]+x2[1];
        int l1=s1.length();
        int l2=s2.length();
        String result="0";
        for(int i=l2-1;i>=0;i--){
            StringBuilder sum=new StringBuilder();
            int carry=0;
            int d2=s2.charAt(i)-'0';
            for(int j=l1-1;j>=0;j--){
                int c=s1.charAt(j)-'0';
                int prod=(c*d2)+carry;
                sum.insert(0,prod%10);
                carry=prod/10;
                
            }
            if(carry>0){
                sum.insert(0,carry);
            }
            for(int k=l2-1;k>i;k--){
                sum.append("0");
            }
            
            result=addIntString(result,sum.toString());            
        }
        while(result.length()<=decimal_points){
            result="0"+result;
        }
        int l=result.length();
        result=result.substring(0,l-decimal_points)+"."+result.substring(l-decimal_points);
        return TrimZeroes(result);
    } 
    private boolean isIntgreater(String x1,String x2){
        String y1=TrimIntZeroes(x1);
        String y2=TrimIntZeroes(x2);
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
    //similar to multiplication removing point and there fractional part lengths difference is calculated
    public String DivideDecimals(String x,String y){
        String[] x1=x.split("\\.");
        String[] x2=y.split("\\.");
        int diff=x1[1].length()-x2[1].length();
        String s1=x1[0]+x1[1];
        String s2=x2[0]+x2[1];
        StringBuilder currDividend=new StringBuilder();
        StringBuilder result=new StringBuilder();
        for(int i=0;i<s1.length();i++){
            currDividend.append(s1.charAt(i));
            String curr=TrimIntZeroes(currDividend.toString());
            int quoti=0;
            while(isIntgreater(curr, s2)||curr.equals(s2)){
                curr=SubtractIntString(curr, s2);
                curr=TrimIntZeroes(curr);
                quoti++;
            }
            currDividend=new StringBuilder(curr);
            result.append(quoti);
        }
        int index = 0;      
        for(; index<this.divisionPre-diff && !currDividend.toString().equals("0");index++){
            currDividend.append("0");
            String curr= TrimIntZeroes(currDividend.toString());
            int q = 0;
            while(isIntgreater(curr, s2) || curr.equals(s2)){
                curr = SubtractIntString(curr, s2);
                curr = TrimIntZeroes(curr);
                q++;
            }
            currDividend=new StringBuilder(curr);
            result.append(q);
            
        }

        int n = result.length();
        int decimalPoints = index + diff;       
        
        if(decimalPoints>0 && n>decimalPoints){
            result = result.insert(n-decimalPoints,".");   
            return TrimZeroes(result.toString());
        }
        else{
            while(decimalPoints < 0){
                result.append("0");
                decimalPoints++;
            }
        }
        
        
        return (result.toString());
    }
    private boolean isgreater(String s1,String s2){
        String[] x1=s1.split("\\.");
        String[] x2=s2.split("\\.");  
        x1[0]=TrimIntZeroes(x1[0]); 
        x2[0]=TrimIntZeroes(x2[0]); 
        if(x1[0].length()>x2[0].length()){
            return true;
        }else if(x1[0].length()<x2[0].length()){
            return false;
        }
        for(int i = x1[0].length()-1;i>=0;i--){
            if(x1[0].charAt(i) > x2[0].charAt(i)){
                return true;
            }
            else if(x2[0].charAt(i) > x1[0].charAt(i)){
                return false;
            }
        }
        int min=Math.min(x1[1].length(),x2[1].length());
        for(int i = 0;i<min;i++){
            if(x1[1].charAt(i) > x2[1].charAt(i)){
                return true;
            }
            else if(x2[1].charAt(i) > x1[1].charAt(i)){
                return false;
            }
        }
        return (min==x1[1].length()?false:true);

        
    }
    // Adds two AFloat objects to give another AFloat sum
    public AFloat add(AFloat other){
        if(this.isZero() && other.isZero()){
            return AFloat.parse("0");
        }
        if (!(other.isNeg()||this.isNeg())){
            return AFloat.parse(addDecimal(this.value,other.value));//(a+b)
        }else if(!other.isNeg() && this.isNeg()){
            return other.subtract(this.retPos());//(-a+b)
        }else if(other.isNeg() && !this.isNeg()){
            return this.subtract(other.retPos());//(a+(-b))
        }else{
            return AFloat.parse("-"+(this.retPos().add(other.retPos())).value);//(-a+(-b))
        }
    }
    // Subtracts two AFloat objects to give their AFloat difference.
    public AFloat subtract(AFloat other){
        if(this.isZero() && other.isZero()){
            return AFloat.parse("0");
        }
        if (!(other.isNeg()||this.isNeg())){
            if(isgreater(this.value,other.value)){
                return AFloat.parse(SubtractDecimal(this.value,other.value));//(a-b),a>b
            }else{
                return AFloat.parse("-"+SubtractDecimal(other.value,this.value));//-(b-a),b>a
            }
            
        }else if(!other.isNeg() && this.isNeg()){
            return AFloat.parse("-"+(this.retPos().add(other)).value);//(-a-b)
        }else if(other.isNeg() && !this.isNeg()){
            return AFloat.parse((this.add(other.retPos())).value);//(a-(-b))
        }else{
            return AFloat.parse("-"+(this.retPos().subtract(other.retPos())).value);//(-a-(-b))
        }
        
    }
    // Multiplies two AFloats to give their AFloat product.
    public AFloat multiply(AFloat other){
        if(this.isZero()||other.isZero()){
            return AFloat.parse("0");
        }  
        if(!(this.isNeg()^other.isNeg())){
            if(this.isNeg()){// to find same sign using xor operator
                return AFloat.parse((MultiplyDecimal(this.retPos().value,other.retPos().value)));//(-a*-b)
            }
            else{
                return AFloat.parse((MultiplyDecimal(this.value,other.value)));//(a*b)
            }
        } 
        else{
            if(this.isNeg()){
                return AFloat.parse("-"+(MultiplyDecimal(this.retPos().value,other.value)));//(-a*b)
            }
            else{
                return AFloat.parse("-"+(MultiplyDecimal(this.value,other.retPos().value)));//(a*-b)
            }
        }
    }
    // Divides two AFloats to give their AFloat quotient.
    public AFloat Divide(AFloat other){
        try{
            if(other.isZero()){
            throw new ArithmeticException("Division by zero error");
        }       
      } catch(ArithmeticException e){
        System.out.println(e); 
        System.exit(1);       
      }     
        if(this.isZero()){
            return AFloat.parse("0");
        }  
        if(!(this.isNeg()^other.isNeg())){// to find same sign using xor operator
            if(this.isNeg()){
                return AFloat.parse((DivideDecimals(this.retPos().value,other.retPos().value)));//(-a/-b)
            }
            else{
                return AFloat.parse((DivideDecimals(this.value,other.value)));//(a/b)
            }
        } 
        else{
            if(this.isNeg()){
                return AFloat.parse("-"+(DivideDecimals(this.retPos().value,other.value)));//(-a/b)
            }
            else{
                return AFloat.parse("-"+(DivideDecimals(this.value,other.retPos().value)));//(a/-b)
            }
        }
    } 
}
