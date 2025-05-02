
import arbitraryarithmetic.AFloat;
import arbitraryarithmetic.AInteger;

public class MyInfArith {
    public static void main(String[] args) {
        // To check whether the number of arguments are correct or not
        if (args.length != 4) {
            System.out.println("Usage: java MyInfArith <int/float> <operation> <operand1> <operand2>");
            return;
        }

        // Taking input arguments
        String type = args[0];      // "int" or "float"
        String operation = args[1];  // "add", "sub", "mul", "div"
        String operand1 = args[2];   // First operand (as string)
        String operand2 = args[3];   // Second operand (as string)

        // To Handle integer operations 
        if (type.equals("int")) {
            // Parse operands as AInteger objects
            AInteger num1 = AInteger.parse(operand1);
            AInteger num2 = AInteger.parse(operand2);
            AInteger result = null;

            // Perform the operation based on user input
            switch (operation) {
                case "add":
                    result = num1.add(num2);
                    break;
                case "sub":
                    result = num1.subtract(num2);
                    break;
                case "mul":
                    result = num1.multiply(num2);
                    break;
                case "div":
                    result = num1.Divide(num2);
                    break;
                default:
                    System.out.println("Invalid operation for integers.");
                    return;
            }

            // Print the result as a string 
            System.out.println(result.getvalue());

        } else if (type.equals("float")) {
            // Parse operands as AFloat objects
            AFloat num1 = AFloat.parse(operand1);
            AFloat num2 = AFloat.parse(operand2);
            AFloat result = null;

            // Perform the operation based on user input
            switch (operation) {
                case "add":
                    result = num1.add(num2);
                    break;
                case "sub":
                    result = num1.subtract(num2);
                    break;
                case "mul":
                    result = num1.multiply(num2);
                    break;
                case "div":
                    result = num1.Divide(num2);
                    break;
                default:
                    System.out.println("Invalid operation for floating-point numbers.");
                    return;
            }

            // Print the result 
            // Using result.getValue() 
            System.out.println(result.getValue()); // Print the complete result

        } else {
            System.out.println("Invalid type. Use 'int' or 'float'.");
        }
    }
    
}
