package hu.unideb.inf.hunteka_opac_api.query_parsers;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import hu.unideb.inf.hunteka_opac_api.dto.query.BinaryLogicCondition.OperatorValue;
import hu.unideb.inf.hunteka_opac_api.dto.query.BinaryLogicCondition;
import hu.unideb.inf.hunteka_opac_api.dto.query.Condition;
import hu.unideb.inf.hunteka_opac_api.dto.query.EmptyCondition;
import hu.unideb.inf.hunteka_opac_api.dto.query.MatchCondition;
import hu.unideb.inf.hunteka_opac_api.dto.query.Query;
import hu.unideb.inf.hunteka_opac_api.query_parsers.exceptions.SyntaxErrorException;

public class QueryParser {

	//TODO: Make a more general tokenizer with no magic numbers, etc.
	private List<String> tokenize(String condition) throws SyntaxErrorException
	{
		List<String> result = new ArrayList<String>();
		String[] tokenTypes = {"atomicCondition", "boolOperator"};
		int exceptedTokenTypeIndex = 0;
		Pattern tokenizerPattern = Pattern.compile("(?:(?<atomicCondition>'[^']*':'[^']*')|(?<boolOperator>AND NOT|OR|AND))");
		Matcher tokenizerMatcher = tokenizerPattern.matcher(condition);
		while (tokenizerMatcher.find())
		{
			try
			{
				String nextToken = tokenizerMatcher.group(tokenTypes[exceptedTokenTypeIndex]);
				if (nextToken==null)
				{
					throw new SyntaxErrorException(condition);
				}
				result.add(nextToken);
			} catch (IllegalStateException|IllegalArgumentException e)
			{
				throw new SyntaxErrorException(condition);
			}
			exceptedTokenTypeIndex=(exceptedTokenTypeIndex+1)%2;
		}
		if (exceptedTokenTypeIndex!=1)
		{
			throw new SyntaxErrorException(condition);
		}
		return result;
	}
	
	private Condition parseAtomicCondition(String token) throws SyntaxErrorException
	{
		Pattern atomicConditionSplitterPattern = Pattern.compile("'(?<property>[^']*)':'(?<value>[^']*)'");
		Matcher atomicConditionSplitterMatcher = atomicConditionSplitterPattern.matcher(token);
		if (!atomicConditionSplitterMatcher.matches())
		{
			throw new SyntaxErrorException(token);
		}
		return new MatchCondition(atomicConditionSplitterMatcher.group("property"), atomicConditionSplitterMatcher.group("value"));
	}
	
	private OperatorValue getOperatorValueForToken(String token) throws SyntaxErrorException
	{
		switch (token) {
		case "AND":
			return OperatorValue.AND;
		case "OR":
			return OperatorValue.OR;
		case "AND NOT":
			return OperatorValue.AND_NOT;
		default:
			throw new SyntaxErrorException(token);
		}
	}
	
	//TODO: Create a more general parser
	//TODO: move the logic associated with each token type to separate class; 
	//		this one should only build the expression tree based on the output of these classes.
	public Query parse(String condition) throws SyntaxErrorException
	{
		List<String> tokens = tokenize(condition);
		Iterator<String> currentTokenIterator = tokens.iterator();
		if (!currentTokenIterator.hasNext())
		{
			return new Query(new EmptyCondition());
		}
		Condition parsedCondition = parseAtomicCondition(currentTokenIterator.next());
		while (currentTokenIterator.hasNext())
		{
			String operator = currentTokenIterator.next();
			String operand = currentTokenIterator.next();
			Condition newCondition = new BinaryLogicCondition(parsedCondition, getOperatorValueForToken(operator), parseAtomicCondition(operand));
			parsedCondition = newCondition;
		}
		Query result = new Query(parsedCondition);
		return result;
	}
}
