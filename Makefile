p2 : 
	java -jar jflex-full-1.8.2.jar ./astgenerator/IdLexer.flex
	java -jar java-cup-11b.jar -dump < ./astgenerator/parser2.cup
	javac -cp jflex-full-1.8.2.jar:java-cup-11b.jar:./ ./astgenerator/*.java  *.java ./dfa/*.java ./cfg/*.java

run:
	java -cp ./java-cup-11b.jar:./:./dfa:./astgenerator:./cfg FrontEnd ./input/input2.txt

clean:
	rm *.class ./astgenerator/*.class ./cfg/*.class ./dfa/*.class parser.java ./astgenerator/IdLexer.java sym.java

