package inter;

import lexer.Tag;
import lexer.Token;
import lexer.Word;
import symbols.Type;

public class main {
	public static void main(String[] args) {
		Id start = new Id(new Word("start", Tag.ID), Type.Int, 0);
		Id left = new Id(new Word("left", Tag.ID), Type.Int, 0);
		Id right = new Id(new Word("right", Tag.ID), Type.Int, 0);
		
		Arith arith2 = new Arith(new Token('*'), left, right);
		Arith arith1 = new Arith(new Token('+'), start, arith2);
		
		//System.out.println(arith1.reduce());
		//System.out.println(start.reduce());
		
		Access array = new Access(start, arith2, Type.Int);
		//System.out.println(array.reduce());
		
		Id a = new Id(new Word("a", Tag.ID), Type.Bool, 0);
		Id b = new Id(new Word("b", Tag.ID), Type.Bool, 0);
		Or or = new Or(new Token(Tag.OR), a, b);
		or.gen();
	}
}












