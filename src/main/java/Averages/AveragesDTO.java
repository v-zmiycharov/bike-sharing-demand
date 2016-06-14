package Averages;

public class AveragesDTO {
	public int sum;
	public int count;
	
	public int getAverage() {
		return sum / count;
	}
	
	public AveragesDTO() {
		this.sum = 0;
		this.count = 0;
	}
}
