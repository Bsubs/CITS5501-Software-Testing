
.PHONY: build clean test

SHELL=bash

build:
	javac -d bin -cp bin:'lib/*' src/*java
	javac -d bin -cp bin:'lib/*' test/*java

clean:
	-rm -rf bin/*.class

JUNIT_SELECTION =  --select-package ""

test:
	java -jar lib/junit-platform-console-standalone-1.9.3.jar --class-path bin $(JUNIT_SELECTION)

gladius-source.zip: Makefile lib/*jar src/*.java test/*java
	set -x && \
	orig=`readlink -f .` && \
	tmp=`mktemp -d` && \
	mkdir -p $$tmp/gladius-source && \
	cd $$tmp && \
	cp -a $$orig/{lib,Makefile,src,test} ./gladius-source && \
	zip -r - gladius-source > $$orig/$@ && \
	strip-nondeterminism $$orig/$@
