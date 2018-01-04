package hu.unideb.inf.hunteka_opac_api.dto.query;

public class Query {
	private Condition condition;

	public Condition getCondition() {
		return condition;
	}

	public void setCondition(Condition condition) {
		this.condition = condition;
	}
	

	public Query() {
		super();
		this.condition = new EmptyCondition();
	}

	public Query(Condition condition) {
		super();
		this.condition = condition;
	}
	
	
}
