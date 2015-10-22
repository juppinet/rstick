package net.jstick.jersey;

import java.util.ArrayList;
import java.util.List;

import net.jstick.api.Device;
import net.jstick.api.Sensor;
import net.jstick.api.Tellstick;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


public class TellstickService {
	
	private static Tellstick ts = new Tellstick();
	private static Log log = LogFactory.getLog(RestService.class.getName());
	
	public  String sendCommand(int id, String command){
		int status = ts.sendCmd(id, command);
		String rback = "SUCCESS";
		
		if(status != 0){
			String error = ts.getErrorString(status);
			log.info("sendCmd failed with error " + error);
			rback = error;
		}
		return rback;
	}
	
	public  String lastKnownState(int id){
			Device dev = ts.getDevice(id);
			//if (dev == null)
				//throw new DeviceNotFoundException();
			return dev.getLastCmd();
		}

	public  List<Device> getDevices() {
		return ts.getDevices();
	}

	public  Device getDevice(int id) {
		return ts.getDevice(id);
	}

	public  int getLastSentValue(int id) {
		return ts.getLastSentValue(id);
	}
	
	public List<SensorData> getSensors(){
		ArrayList<Sensor> lista = ts.getSensors();
		ArrayList<SensorData> newlist = new ArrayList<>();
		
		for(Sensor sens : lista){
			if(sens.getTimeStampAge()<30){
				if(sens.getDataTypes()==1 ||sens.getDataTypes()==3 ){
					newlist.add(new SensorData(sens.getId(),0,"temp",String.valueOf(sens.getTemperature())));
				}
			
				if(sens.getDataTypes()==2 ||sens.getDataTypes()==3  ){
					newlist.add(new SensorData(sens.getId(),1,"hum",String.valueOf(sens.getHumidity())));
				}
			}
		}
		return newlist;
	}

	public List<SensorData> getSensorDataList(int id) {
		Sensor sens = ts.getSensor(id);
		ArrayList<SensorData> newlist = new ArrayList<>();
		if(sens.getDataTypes()==1 ||sens.getDataTypes()==3 ){
			newlist.add(new SensorData(sens.getId(),0,"temp",String.valueOf(sens.getTemperature())));
		}
		
		if(sens.getDataTypes()==2 ||sens.getDataTypes()==3  ){
			newlist.add(new SensorData(sens.getId(),1,"hum",String.valueOf(sens.getHumidity())));
		}

		return newlist;
	}

	public SensorData getSensorData(int id, int sid) {
		ArrayList<SensorData> lista = (ArrayList<SensorData>) getSensorDataList(id);
		SensorData back = null;
		for (SensorData data: lista){
			if(data.getSensor() == sid)
				back = data;
		}
		return back;
	}


}
