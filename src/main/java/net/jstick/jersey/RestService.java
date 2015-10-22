package net.jstick.jersey;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.Consumes;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import net.jstick.api.Device;


//Base URL set in App.java to /api
@Path("/")
public class RestService {
	private static String version = "1.0";
	private TellstickService ts = new TellstickService();
	
	@GET
	@Path("/version")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,String> version( ) {
		HashMap<String, String> ver = new HashMap<String, String>();
		ver.put("version", version);
		return ver;
	}
	
	@GET
	@Path("/sensor")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SensorData> getSensorDataList( ) {
		return ts.getSensors();
	}
	
	@GET
	@Path("/sensor/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public List<SensorData> getSensorDataList(@PathParam("id") int id ) {
		return ts.getSensorDataList(id);
	}
	
	@GET
	@Path("/sensor/{id}/{sid}")
	@Produces(MediaType.APPLICATION_JSON)
	public SensorData getSensorData(@PathParam("id") int id,@PathParam("sid") int sid  ) {
		return ts.getSensorData(id, sid);
	}

	/*
	 * 
	 * DEVICES
	 * 
	 */
	@GET
	@Path("/device")
	@Produces(MediaType.APPLICATION_JSON)
	public List<Device> getDevices() {
		return ts.getDevices();
	}
	
	@GET
	@Path("/device/{id}")
	@Produces(MediaType.APPLICATION_JSON)
	public Device getDevice(@PathParam("id") int id) {
		return ts.getDevice(id);
	}
	
	@GET
	@Path("/device/{id}/lastcmd")
	@Produces(MediaType.APPLICATION_JSON)
	public String getDeviceState(@PathParam("id") int id) {
		return ts.getDevice(id).getLastCmd();
	}
	
	@GET
	@Path("/device/{id}/state")
	@Produces(MediaType.APPLICATION_JSON)
	public Map<String,String> getLastKnownState(@PathParam("id") int id) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("lastCmd", ts.getDevice(id).getLastCmd());
		return map;
	}
	
	@GET
	@Path("/device/{id}/dimlevel")
	@Produces(MediaType.APPLICATION_JSON)
	public int getDeviceDimLevel(@PathParam("id") int id) {
		return ts.getLastSentValue(id);
	}

	@GET
	@Path("/device/{id}/{cmd}")
	@Produces(MediaType.APPLICATION_JSON)
	public  Map<String,String>  sendCmd(@PathParam("id") int id, @PathParam("cmd") String cmd) {
		HashMap<String,String> map = new HashMap<String,String>();
		map.put("status", ts.sendCommand(id, cmd.toUpperCase()));
		return map;
	}
	
	
}
