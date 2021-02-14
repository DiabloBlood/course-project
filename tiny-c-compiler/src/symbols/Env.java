package symbols;

import java.util.HashMap;
import java.util.Map;

import inter.Id;
import lexer.Token;

public class Env {
	private Map<Token, Id> symTable;
	protected Env prev;
	public Env(Env n) {
		symTable = new HashMap<>();
		prev = n;
	}
	public void put(Token w, Id i) {
		symTable.put(w, i);
	}
	public Id get(Token w) {
		for(Env e = this; e != null; e = e.prev) {
			Id found = (Id)(e.symTable.get(w));
			if(found != null) {
				return found;
			}
		}
		return null;
	}
}
