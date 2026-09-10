javac Scanner.java
echo
echo Running test 00. fact.java
java Scanner test/fact.java > proj.out
~brylow/cosc4400/Projects/scanner test/fact.java > ref.out
diff proj.out ref.out > test/diff_0.txt
diff proj.out ref.out
rm proj.out ref.out
echo
echo Running test 01. misc.java
java Scanner test/misc.java > proj.out
#~brylow/cosc4400/Projects/scanner test/misc.java > ref.out
./scanner test/misc.java > ref.out
diff proj.out ref.out > test/diff_1.txt
diff proj.out ref.out
rm proj.out ref.out
echo
echo Running test 02. nums.java
java Scanner test/nums.java > nums.out
#~brylow/cosc4400/Projects/scanner test/nums.java > ref.out
./scanner test/nums.java > ref.out
diff proj.out ref.out > test/diff_2.txt
diff proj.out ref.out
rm proj.out ref.out