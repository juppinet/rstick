package net.jstick.jersey;

public class SensorData {
	private int id;
	private int sensor;
	private String metric;
	private String value;
	
	public SensorData(int id, int sensor, String metric, String value) {
		super();
		this.id = id;
		this.sensor = sensor;
		this.metric = metric;
		this.value = value;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getSensor() {
		return sensor;
	}

	public void setSensor(int sensor) {
		this.sensor = sensor;
	}

	public String getMetric() {
		return metric;
	}

	public void setMetric(String metric) {
		this.metric = metric;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	
}
