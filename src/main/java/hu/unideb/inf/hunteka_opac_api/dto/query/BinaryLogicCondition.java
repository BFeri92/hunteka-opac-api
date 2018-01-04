package hu.unideb.inf.hunteka_opac_api.dto.query;

public class BinaryLogicCondition implements Condition {
	public enum OperatorValue {AND, OR, AND_NOT};
	private Condition firstOperand;
	private OperatorValue operator;
	private Condition secondOperand;
	
	public BinaryLogicCondition(Condition firstOperand, OperatorValue operator, Condition secondOperand) {
		super();
		this.firstOperand = firstOperand;
		this.operator = operator;
		this.secondOperand = secondOperand;
	}

	public Condition getFirstOperand() {
		return firstOperand;
	}

	public OperatorValue getOperator() {
		return operator;
	}

	public Condition getSecondOperand() {
		return secondOperand;
	}

	public void setFirstOperand(Condition firstOperand) {
		this.firstOperand = firstOperand;
	}

	public void setOperator(OperatorValue operator) {
		this.operator = operator;
	}

	public void setSecondOperand(Condition secondOperand) {
		this.secondOperand = secondOperand;
	}
	
	@Override
	public String toString()
	{
		return firstOperand.toString()+" "+operator.toString()+" "+secondOperand.toString();
	}
	
}
