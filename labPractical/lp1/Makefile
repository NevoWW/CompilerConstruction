# Copyright (C) 1997-2003, Purdue Research Foundation of Purdue
# University. All rights reserved.
JFLAGS=-cp .
JAVA=java
JAVAC=javac
DIFF=diff
DIFFOPT=

SCANNER=Recognizer
SRC = $(SCANNER).java

TESTIN=test.txt
TESTOUT=test.out
EXPECTED=test-expected.txt

all: $(SRC:.java=.class)

%.class : %.java
	${JAVAC} ${JFLAGS} $<

clean:
	rm -f $(SRC:.java=.class) *.class ${TESTOUT}

test: all
	@ ${JAVA} ${SCANNER} < ${TESTIN} > ${TESTOUT}
	${DIFF} ${DIFFOPT} ${EXPECTED} ${TESTOUT}

realclean: clean
	rm -f */*.class *~ .??* */*~ */.??*


