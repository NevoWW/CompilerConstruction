# RUN THE FILE WITH chmod +x test.sh THEN ./test.sh
#!/bin/bash

echo
echo Running test 1
make clean
make
java Parse.Main < ~brylow/cosc4400/Projects/tests/TreeVisitor.java > proj.out
~brylow/cosc4400/Projects/mjparser ~brylow/cosc4400/Projects/tests/TreeVisitor.java > ref.out
diff -u proj.out ref.out
rm proj.out ref.out
make clean





