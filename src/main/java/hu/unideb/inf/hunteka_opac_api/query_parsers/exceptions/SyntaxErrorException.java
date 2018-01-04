package hu.unideb.inf.hunteka_opac_api.query_parsers.exceptions;

public class SyntaxErrorException extends Exception{
	private String expression;

	public SyntaxErrorException(String expression) {
		super();
		this.expression = expression;
	}

	public String getExpression() {
		return expression;
	}
	
}
