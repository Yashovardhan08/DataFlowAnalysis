DESCRIPTION:
This program parses the contents of the file input.txt and prints it out on console.

COMPILING AND EXECUTING
Before compiling, please point the $jflex and $cup variables to the JFlex and CUP jars respectively. 

To compile:
	make p2

To execute:
	make run

To clean directories of generated files:
	make clean

Output: Prints the IN and OUT sets for Live Variable Analysis and Reaching Definitions.

Change the input.txt to different files to check the different features:
	* input1.txt : Nested Loops, with a if clause in the first while block.
	* input2.txt : Sequential arithmetic statements.
	* input3.txt : Loop with a nested if else statement.
	* input4.txt : Loop with a if statement followed by an if else block.
	* input5.txt : Nested if else block followed by loop.
	* input6.txt : Highly nested if else.


		