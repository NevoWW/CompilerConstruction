# RUN THE FILE WITH chmod +x test.sh THEN ./test.sh
#!/bin/bash

echo
echo Running test 1
make clean
make
java Parse.Main < ~brylow/cosc4400/Projects/tests/BinarySearch.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/BinarySearch.java > ref.out
diff -u proj.out ref.out
rm proj.out ref.out
echo
echo Running test 2
java Parse.Main < ~brylow/cosc4400/Projects/tests/BinaryTree.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/BinaryTree.java > ref.out
diff -u proj.out ref.out
rm proj.out ref.out
echo
echo Running test 3
java Parse.Main < ~brylow/cosc4400/Projects/tests/BubbleSort.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/BubbleSort.java > ref.out
diff -u proj.out ref.out
rm proj.out ref.out
echo
echo Running test 4
java Parse.Main < ~brylow/cosc4400/Projects/tests/Factorial.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/Factorial.java > ref.out
diff -u proj.out ref.out
rm proj.out ref.out
echo
echo Running test 5
java Parse.Main < ~brylow/cosc4400/Projects/tests/LinearSearch.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/LinearSearch.java > ref.out
diff -u proj.out ref.out
rm proj.out ref.out
echo
echo Running test 6
java Parse.Main < ~brylow/cosc4400/Projects/tests/LinkedList.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/LinkedList.java > ref.out
diff -u proj.out ref.out
rm proj.out ref.out
echo
echo Running test 7
java Parse.Main < ~brylow/cosc4400/Projects/tests/QuickSort.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/QuickSort.java > ref.out
diff -u proj.out ref.out
rm proj.out ref.out

echo
echo Running test 8
java Parse.Main < ~brylow/cosc4400/Projects/tests/ThreadGood.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/ThreadGood.java > ref.out
diff -u proj.out ref.out
rm proj.out ref.out
echo
echo Running test 9
java Parse.Main < ~brylow/cosc4400/Projects/tests/TreeVisitor.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/TreeVisitor.java > ref.out
diff -u proj.out ref.out
rm proj.out ref.out
make clean




