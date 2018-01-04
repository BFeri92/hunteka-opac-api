package hu.unideb.inf.hunteka_opac_api.dto.query;

public class MatchCondition implements Condition {
	private String property;
	private String value;

	public MatchCondition(String property, String value) {
		super();
		this.property = property;
		this.value = value;
	}

	public String getProperty() {
		return property;
	}

	public String getValue() {
		return value;
	}

	public String toString() {
		return property+':'+value;
	}
}
