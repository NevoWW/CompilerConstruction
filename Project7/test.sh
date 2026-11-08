# RUN THE FILE WITH chmod +x test.sh THEN ./test.sh
# Can run with bash test.sh
#!/bin/bash
make clean
make
# TEST CASES START WITH ~brylow/cosc4400/Projects/tests/
# could end with BinarySearch.java | BubbleSort.java | LinearSearch.java | QuickSort.java | TreeVisitor.java
# | BinaryTree.java | Factorial.java | LinkedList.java | ThreadGood.java
# OR ~brylow/cosc4400/Projects/
# NQueens-broken.java | NQueens.java 

# REFRENCES IMPLEMENTATION
# ~brylow/cosc4400/Projects/mjparser
# ~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/  FillInTestCase | ~brylow/cosc4400/Projects/mjchecker - 

# OUR IMPLEMENTATION
# java Semant.Main < program.java
# java Parse.Main < program.java

# SAVE OUTPUT IN FILE 
# OUR PROJECT > proj.out
# REFRENCE > ref.out

# java Semant.Main < testcases/NQueens.java > proj.out
# ~brylow/cosc4400/Projects/mjparser testcases/NQueens.java | ~brylow/cosc4400/Projects/mjchecker - > ref.out

echo
echo Running test 1

java Semant.Main < testcases/NQueens.java > proj.out
~brylow/cosc4400/Projects/mjparser testcases/NQueens.java | ~brylow/cosc4400/Projects/mjchecker - > ref.out

diff -u proj.out ref.out
rm proj.out ref.out
echo

echo Done test 1

echo
echo Running test 1

java Semant.Main < ~brylow/cosc4400/Projects/tests/TreeVisitor.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/TreeVisitor.java | ~brylow/cosc4400/Projects/mjchecker - > ref.out

diff -u proj.out ref.out
rm proj.out ref.out
echo

echo Done test 1

echo
echo Running test 1

java Semant.Main < ~brylow/cosc4400/Projects/tests/BinarySearch.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/BinarySearch.java | ~brylow/cosc4400/Projects/mjchecker - > ref.out

diff -u proj.out ref.out
rm proj.out ref.out
echo

echo Done test 1

echo
echo Running test 1

java Semant.Main < ~brylow/cosc4400/Projects/tests/BubbleSort.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/BubbleSort.java | ~brylow/cosc4400/Projects/mjchecker - > ref.out

diff -u proj.out ref.out
rm proj.out ref.out
echo

echo Done test 1

echo
echo Running test 1

java Semant.Main < ~brylow/cosc4400/Projects/tests/LinearSearch.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/LinearSearch.java | ~brylow/cosc4400/Projects/mjchecker - > ref.out

diff -u proj.out ref.out
rm proj.out ref.out
echo

echo Done test 1

echo
echo Running test 1

java Semant.Main < ~brylow/cosc4400/Projects/tests/BinaryTree.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/BinaryTree.java | ~brylow/cosc4400/Projects/mjchecker - > ref.out

diff -u proj.out ref.out
rm proj.out ref.out
echo

echo Done test 1





