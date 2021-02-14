test: lex.yy.o parser.tab.o
	gcc -o test lex.yy.o parser.tab.o
lex.yy.o: lex.yy.c  parser.tab.h
	gcc -c lex.yy.c 
lex.yy.c: lex.l
	flex lex.l
parser.tab.o: parser.tab.c
	gcc -c parser.tab.c
parser.tab.c parser.tab.h: parser.y 
	bison -d parser.y
clean:
	rm -f test parser.output *.o parser.tab.c parser.tab.h lex.yy.c
