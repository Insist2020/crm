package hr.tvz.crm.main;

public class Tip {
	int id;
	String tip;

	public Tip(int id, String tip) {
		this.id = id;
		this.tip = tip;
	}
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getTip() {
		return tip;
	}

	public void setTip(String tip) {
		this.tip = tip;
	}

	@Override
	public String toString() {
		return tip;
	}
	
	
	
}
