
//var baseUrl = "http://stick:8080";
var baseUrl = "/api";
//var user = "user";
//var password = "";
//var baseUrl = "";


function printAlert(msg){
	$("#alert").append('<div class="alert alert-danger" role="alert">' + msg + '</div>');
}
            
function printSensorList(dev){
		$("#sensortable").append('<tr><th scope="row">' + dev.id + '</th><td><div id="metric' + dev.id + dev.sensor + '"></div></td>' +            
				'<td><div id="value' + dev.id + dev.sensor + '"></div></td><td><div id="time' + dev.id + dev.sensor + '"></div></td></tr>')    
}

function printDeviceList(dev){
	$("#devtable").append('<tr><th scope="row">' + dev.id + '</th><td>' + dev.name + '</td><td><div id="ls' + dev.id + '"></div></td>' +            
			'<td><button type="button" data-press="buttonAjax" data-id="' + dev.id + '" data-cmd="ON" class="btn btn-xs btn-warning">ON</button>' +        
			'<button type="button" data-press="buttonAjax" data-id="' + dev.id + '" data-cmd="OFF" class="btn btn-xs btn-default">OFF</button></td></tr>');            
}


function listDevs(){
	console.log("Call to listDevs()");
	$.ajax({
		url: baseUrl + "/device",
		error: function( xhr, status, errorThrown ) {
			printAlert( xhr.responseText );
		}    
	}).then(function(data) {
		data.forEach( printDeviceList );
	});               
}

function listSensors(){
	console.log("Call to listsensors()");
	$.ajax({
		url: baseUrl + "/sensor",
		error: function( xhr, status, errorThrown ) {
			printAlert( xhr.responseText );
		}    
	}).then(function(data) {
		data.forEach( printSensorList );
	});               
}

function printStatus(element){
	console.log(element.id + " " + element.lastCmd); 
	if(element.lastCmd == 'ON') {       
		$("#ls" + element.id).html('<img src="lightbulb.png">');
	} else {
		$("#ls" + element.id).html('<img src="lightbulb_off.png">');
	}  
}


function sensorsUpdate(element){
	$("#metric" + element.id + element.sensor).html(element.metric);
	if (element.metric == 'temp'){
		$("#value" + element.id + element.sensor).html(element.value +'&deg;C');
	} else if (element.metric == 'hum'){
		$("#value" + element.id + element.sensor).html(element.value + '%');
	} else {
		$("#value" + element.id + element.sensor).html(element.value );
	}	
	$("#time" + element.id + element.sensor).html(element.update);
}

function lightStatus(){
	console.log("Call to lightStatus()");
	$.ajax({
		url: baseUrl + "/device",
	}).then(function(data) {
		data.forEach( printStatus );
	});               
}

function sensorStatus(){
	console.log("Call to sensorStatus()");
	$.ajax({
		url: baseUrl + "/sensor",
	}).then(function(data) {
		data.forEach( sensorsUpdate );
	});               
}



// Call when doc is loaded
$(document).ready(function()
		{
	listDevs();
	listSensors();
	lightStatus();
	sensorStatus();
	// Update page every 1min
	var refreshId = setInterval( function() { lightStatus(); }, 60000);
	var refreshId2 = setInterval( function() { sensorStatus(); }, 60000);

	$('#devtable').on('click', 'button', function (event) {
		event.preventDefault();
		var id = $(this).attr('data-id')
		var cmd = $(this).attr('data-cmd')
		console.log( "toggel " + id + " " + cmd );

		$.ajax({
			url: baseUrl + "/device/" + id +"/" + cmd,
			type: "GET",
			dataType : "text",

			success: function( json ) {
				
				if(json == 'FAILED') {
					console.log ("call failed " + id + " " + cmd );
				}
				// check status
				//setTimeout(function(){
				//	lightStatus();
				//}, 500)
				
				if(json == 'SUCCESS') {
					console.log ("success call " + id + " " + cmd );
				}
				
			},

			error: function( xhr, status, errorThrown ) {
				//printAlert( xhr.responseText );
				console.log ("call failed " + id + " " + cmd );
			},

			complete: function( xhr, status ) {
				// alert( "The request is complete!" );
				lightStatus();
			}
		});
	});


		});