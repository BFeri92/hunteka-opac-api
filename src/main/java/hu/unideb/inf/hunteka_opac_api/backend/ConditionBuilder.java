package hu.unideb.inf.hunteka_opac_api.backend;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import hu.unideb.inf.hunteka_opac_api.dto.query.BinaryLogicCondition;
import hu.unideb.inf.hunteka_opac_api.dto.query.BinaryLogicCondition.OperatorValue;
import hu.unideb.inf.hunteka_opac_api.dto.query.Condition;
import hu.unideb.inf.hunteka_opac_api.dto.query.EmptyCondition;
import hu.unideb.inf.hunteka_opac_api.dto.query.MatchCondition;
import hu.unideb.inf.hunteka_opac_api.dto.query.Query;

public class ConditionBuilder {
	
	private Map<String, String> optionNameToOptionIdMap = new HashMap<String, String>();
	private Map<BinaryLogicCondition.OperatorValue, Integer> logicOperatorToOperatorIdMap = new HashMap<BinaryLogicCondition.OperatorValue, Integer>();
	
	public void specifyOptionId(String option, String optionId)
	{
		this.optionNameToOptionIdMap.put(option, optionId);
	}
	
	public void SpecifyLogicOperatorId(BinaryLogicCondition.OperatorValue operator, int operatorId)
	{
		this.logicOperatorToOperatorIdMap.put(operator, operatorId);
	}
	
	private String buildConditionPart(Condition condition, int termPosition) throws UnsupportedEncodingException
	{
		if (condition instanceof MatchCondition)
		{
			MatchCondition matchCondition = (MatchCondition)condition;
			return "bib1ext="+optionNameToOptionIdMap.get(matchCondition.getProperty())+"&search_term__"+termPosition+"="+URLEncoder.encode(matchCondition.getValue(), "UTF-8");
		}
		
		if (condition instanceof BinaryLogicCondition)
		{
			BinaryLogicCondition logicCondition = (BinaryLogicCondition)condition;
			return buildConditionPart(logicCondition.getFirstOperand(),termPosition-1)+
					"&logic="+logicOperatorToOperatorIdMap.get(logicCondition.getOperator())+
					"&"+buildConditionPart(logicCondition.getSecondOperand(), termPosition);
		}
		return "";
	}
	
	private int getConditionTreeDepth(Condition subtree)
	{
		if (subtree instanceof BinaryLogicCondition) {
			BinaryLogicCondition logicCondition = (BinaryLogicCondition)subtree;
			return Math.max(
					getConditionTreeDepth(logicCondition.getFirstOperand()), 
					getConditionTreeDepth(logicCondition.getSecondOperand())
					)+1;
		}
		return 1;
	}
	
	public String buildCondition(Query query) throws UnsupportedEncodingException
	{
		Condition condition = query.getCondition();
		String result = "";
		if (condition instanceof EmptyCondition)
		{
			return "bib1ext=1003-0&search_term__0=&logic=1&"
			+ "bib1ext=65-0&search_term__1=&logic=1&"
			+ "bib1ext=21-0&search_term__2=&logic=1&"
			+ "bib1ext=31-0&search_term__3=&logic=1&"
			+ "bib1ext=1018-0&search_term__4=&logic=1&"
			+ "bib1ext=1001-0&search_term__5=&logic=1&"
			+ "bib1ext=2009-0&search_term__6=&logic=1&"
			+ "bib1ext=2030-0&search_term__7=&";	
		}
		
		return buildConditionPart(condition, getConditionTreeDepth(condition))+"&logic="+logicOperatorToOperatorIdMap.get(OperatorValue.AND);
	}
}
