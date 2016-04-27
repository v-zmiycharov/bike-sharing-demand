package Data;

import java.util.Date;

import Enums.Season;
import Enums.Weather;
import Utils.DateHelper;

public class DataRow {
	private Date datetime;
	private int season;
	private boolean isHoliday;
	private boolean isWorkingDay;
	private int weather;
	private double temp;
	private double feelsLikeTemp;
	private double humidity;
	private double windspeed;
	private int casualCount;
	private int registeredCount;
	private int totalCount;
	
	public Date getDatetime() {
		return datetime;
	}
	public void setDatetime(Date datetime) {
		this.datetime = datetime;
	}
	public int getSeason() {
		return season;
	}
	public void setSeason(int season) {
		this.season = season;
	}
	public boolean isHoliday() {
		return isHoliday;
	}
	public void setHoliday(boolean isHoliday) {
		this.isHoliday = isHoliday;
	}
	public boolean isWorkingDay() {
		return isWorkingDay;
	}
	public void setWorkingDay(boolean isWorkingDay) {
		this.isWorkingDay = isWorkingDay;
	}
	public int getWeather() {
		return weather;
	}
	public void setWeather(int weather) {
		this.weather = weather;
	}
	public double getTemp() {
		return temp;
	}
	public void setTemp(double temp) {
		this.temp = temp;
	}
	public double getFeelsLikeTemp() {
		return feelsLikeTemp;
	}
	public void setFeelsLikeTemp(double feelsLikeTemp) {
		this.feelsLikeTemp = feelsLikeTemp;
	}
	public double getHumidity() {
		return humidity;
	}
	public void setHumidity(double humidity) {
		this.humidity = humidity;
	}
	public double getWindspeed() {
		return windspeed;
	}
	public void setWindspeed(double windspeed) {
		this.windspeed = windspeed;
	}
	public int getCasualCount() {
		return casualCount;
	}
	public void setCasualCount(int casualCount) {
		this.casualCount = casualCount;
	}
	public int getRegisteredCount() {
		return registeredCount;
	}
	public void setRegisteredCount(int registeredCount) {
		this.registeredCount = registeredCount;
	}
	public int getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(int totalCount) {
		this.totalCount = totalCount;
	}
	
	public DataRow() { }

	public DataRow(String line) throws Exception {
		String[] splitted = line.split(",");
		
		if(splitted.length != 9 && splitted.length != 12) {
			throw new Exception("Invalid line");
		}
		
		int currentIndex = 0;
		this.datetime = DateHelper.getDate(splitted[currentIndex++]);
		this.setSeason(Integer.parseInt(splitted[currentIndex++]));
		this.setHoliday(Integer.parseInt(splitted[currentIndex++]) == 1);
		this.setWorkingDay(Integer.parseInt(splitted[currentIndex++]) == 1);
		this.setWeather(Integer.parseInt(splitted[currentIndex++]));
		this.setTemp(Double.parseDouble(splitted[currentIndex++]));
		this.setFeelsLikeTemp(Double.parseDouble(splitted[currentIndex++]));
		this.setHumidity(Double.parseDouble(splitted[currentIndex++]));
		this.setWindspeed(Double.parseDouble(splitted[currentIndex++]));
		
		// train case
		if(splitted.length > 9) {
			this.setCasualCount(Integer.parseInt(splitted[currentIndex++]));
			this.setRegisteredCount(Integer.parseInt(splitted[currentIndex++]));
			this.setTotalCount(Integer.parseInt(splitted[currentIndex++]));
		}
	}

	public String toSubmissionString() {
		return String.join(","
				, DateHelper.formatDate(this.datetime)
				, Integer.toString(this.totalCount));
	}
	
	public String toLongString() {
		return String.join(","
				, DateHelper.formatDate(this.datetime)
				, Integer.toString(this.season)
				, Integer.toString(this.isHoliday ? 1 : 0)
				, Integer.toString(this.isWorkingDay ? 1 : 0)
				, Integer.toString(this.weather)
				, Double.toString(this.temp)
				, Double.toString(this.feelsLikeTemp)
				, Double.toString(this.humidity)
				, Double.toString(this.windspeed)
				, Integer.toString(this.casualCount)
				, Integer.toString(this.registeredCount)
				, Integer.toString(this.totalCount));
	}
}
