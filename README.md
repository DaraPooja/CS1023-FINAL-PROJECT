# Arbitrary Precision Arithmetic Calculator
This project implements an arbitrary-precision arithmetic library in Java. It supports addition, subtraction, multiplication, and division for both integer and floating-point numbers with arbitrary precision. The library allows users to handle calculations that exceed the precision of standard Java numeric types like int, long, float, and double.
Project Overview
In this project, an arbitrary-precision arithmetic calculator is implemented in Java. The primary goal is to allow users to perform calculations with integers and floating-point numbers of arbitrary size and precision. The calculations are performed using strings to avoid overflow issues that occur with Java’s native data types.
The project includes:

- AInteger Class for handling arbitrary-precision integers.
- AFloat Class for handling arbitrary-precision floating-point numbers.
- MyInfArith Class to interact with the user and perform the required operations based on input.

The library also includes:

- Ant Build Script to compile and run the project.
- Python Script for automating the project build and run process.
- Docker Support for containerized deployment.

## LIMITATIONS OF BUILT-IN TYPES

| DATA TYPE   | MINIMUM VALUE                         | MAXIMUM VALUE                           |
|-------------|---------------------------------------|-----------------------------------------|
| `int`       | -2^31 (-2,147,483,648)                | 2^31 - 1 (2,147,483,647)                |
| `long`      | -2^63 (-9,223,372,036,854,775,808)    | 2^63 - 1 (9,223,372,036,854,775,807)    |
| `float`     | -3.4028235e^38                        | 3.4028235e^38                           |
| `double`    | -1.7976931348623157e^308              | 1.7976931348623157e^308                 |

- This library can handle numbers that exceed the range of these standard types.

# Classes
## AInteger Class
The AInteger class allows handling arbitrarily large integers. The class supports:

- Constructors for initializing the instance with 0 or a given string.
- Methods for parsing strings into AInteger objects.
- Overloaded arithmetic operators: addition, subtraction, multiplication, and division.

## AFloat Class
The AFloat class supports high-precision floating-point arithmetic. It provides:

- Constructors for initializing with 0.0 or a string representation of the floating-point number.
- Methods to parse strings into AFloat objects.
- Overloaded arithmetic operators: addition, subtraction, multiplication, and division.

## MyInfArith Class
The MyInfArith class is the main driver class. It:

- Accepts user inputs.
-Performs the specified arithmetic operations on two operands.

# Installation
## Prerequisites
Before running the project, ensure that the following tools are installed:

- Java (JDK 8 or later)
- Ant (for building the project)
- Python (for running the Python script)

If you don't want to install Java, Ant, or Python locally, you can use Docker to run the project.
### EXECUTING USING MYINFARITH
To perform addition, subtraction, multiplication, and division, run the following commands:
- command MyInfArith <int/float> <add/sub/mul/div> <operand1> <operand2>
## Runner.py
- It is a python script which run the program with command lines arguments using MyInfarith.java
## Executing using runner.py
To perform addition, subtraction, multiplication, and division, run the following commands:
- command python runner.py <int/float> <add/sub/mul/div> <operand1> <operand2>
## Executing using JAR file
To perform addition, subtraction, multiplication, and division, run the following command:
- command: java -cp arbitraryarithmetic/aarithmetic.jar:. MyInfArith <int/float> <add/sub/mul/div> <operand1> <operand2>

## Conclusion
### Key Learning
- Arbitrary Precision Arithmetic: Implementing custom data types for handling large numbers, which standard Java data types can't manage due to their fixed size and precision limits.
- String-based Representation: Using strings to represent large integers and floating-point numbers ensures that precision is maintained across arithmetic operations.
- Learned How to use ant build tool and using Python to run commands in command line.
### Veification approch
- Each arithmetic operation (addition, subtraction, multiplication, division) was tested with both small and large numbers to ensure the results are accurate.
- Special attention was given to operations with negative numbers, zero, and very large numbers.
- The results from the library were compared against the built-in Java BigInteger and BigDecimal classes to verify correctness.
### Limitations
- It does not have other operations exception these four.
- The string-based arithmetic operations may be slower compared to built-in data types, especially for very large numbers.
- Handling large numbers using strings can consume a significant amount of memory, which may be a concern for extremely large values.
