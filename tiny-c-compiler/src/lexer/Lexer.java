package lexer;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import symbols.Type;

public class Lexer {

	public static int line = 1;
	char peek = ' ';
	Map<String, Word> wordsMap = new HashMap<>();
	
	void reserve(Word w) {
		wordsMap.put(w.lexeme, w);
	}
	
	public Lexer() {
		reserve(new Word("if", 		Tag.IF));
		reserve(new Word("else", 	Tag.ELSE));
		reserve(new Word("while", 	Tag.WHILE));
		reserve(new Word("do", 		Tag.DO));
		reserve(new Word("break", 	Tag.BREAK));
		
		reserve(Word.True);
		reserve(Word.False);
		
		reserve(Type.Int);
		reserve(Type.Char);
		reserve(Type.Bool);
		reserve(Type.Float);
	}
	
	void readch() throws IOException {
		peek = (char)System.in.read();
	}
	
	boolean readch(char c) throws IOException {
		readch();
		if(peek != c) {
			return false;
		}
		peek = ' ';
		return true;
	}
	
	public Token scan() throws IOException {
		while(true) {
			if(peek == ' ' || peek == '\t') {
				continue;
			} else if (peek == '\n') {
				line = line + 1;
			} else {
				break;
			}
		}
		
		switch(peek) {
		//and &&
		case '&':
			if(readch('&')) {
				return Word.and;
			} else {
				return new Token('&');
			}
		//or ||
		case '|':
			if(readch('|')) {
				return Word.or;
			} else {
				return new Token('|');
			}
		//equal ==
		case '=':
			if(readch('=')) {
				return Word.eq;
			} else {
				return new Token('=');
			}
		//not equal !=
		case '!':
			if(readch('=')) {
				return Word.ne;
			} else {
				return new Token('!');
			}
		//less equal <=
		case '<':
			if(readch('=')) {
				return Word.le;
			} else {
				return new Token('<');
			}
		//great equal
		case '>':
			if(readch('=')) {
				return Word.ge;
			} else {
				return new Token('>');
			}
		}
		// process num and real
		if(Character.isDigit(peek)) {
			int v = 0;
			do {
				v = 10 * v + Character.digit(peek, 10);
				readch();
			} while(Character.isDigit(peek));
			if(peek != '.') {
				return new Num(v);
			}
			float x = v;
			float d = 10;
			while(true) {
				readch();
				if(!Character.isDigit(peek)) {
					break;
				}
				x = x + Character.digit(peek, 10) / d;
				d *= 10;
			}
			return new Real(x);
		}
		// process identifier
		if(Character.isLetter(peek)) {
			StringBuilder sb = new StringBuilder();
			do {
				sb.append(peek);
				readch();
			} while(Character.isLetterOrDigit(peek));
			String token = sb.toString();
			Word word = (Word)wordsMap.get(token);
			if(word != null) {
				return word;
			}
			word = new Word(token, Tag.ID);
			wordsMap.put(token, word);
			return word;
		}
		
		Token tok = new Token(peek);
		peek = ' ';
		return tok;
	}
}
















