import sys
import subprocess

def compile():
    print(" Compiling the project using Ant...")
    subprocess.run(["ant", "compile"], check=True)

def run_operation(args):
    if len(args) != 6:
        print(" Usage: python run_project.py --run <int|float> <add|sub|mul|div> <operand1> <operand2>")
        return

    dtype = args[2]
    operation = args[3]
    op1 = args[4]
    op2 = args[5]

    cmd = ["java", "-cp", "build", "MyInfArith", dtype, operation, op1, op2]
    print("Running:", " ".join(cmd))
    subprocess.run(cmd, check=True)

if __name__ == "__main__":
    if len(sys.argv) < 2:
        print(" Usage:\n  python run_project.py --compile\n  python run_project.py --run int add 123 456")
        sys.exit(1)

    if sys.argv[1] == "--compile":
        compile()
    elif sys.argv[1] == "--run":
        run_operation(sys.argv)
    else:
        print("Unknown command. Use --compile or --run")

