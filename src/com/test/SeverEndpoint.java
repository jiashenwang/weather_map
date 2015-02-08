package com.test;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.StringWriter;
import java.security.InvalidParameterException;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonWriter;
import javax.websocket.OnClose;
import javax.websocket.OnError;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint("/severendpoint")
public class SeverEndpoint{
	
	static Set<Session> users = Collections.synchronizedSet(new HashSet<Session>());
	
	String host = "172.16.42.86";
	String group = "233.7.117.107";
	int port = 4034;
	
	@OnOpen
	public void handleOpen(Session userSession){
		System.out.println("Server get connected");
		users.add(userSession);
		//System.out.println(ConnectToMulticastListener());
	}
	@OnClose
	public void handleClose(Session userSession){
		System.out.println("Client is now disconnected!");
		users.remove(userSession);
	}
	@OnMessage
	public void handleMessage(String message, Session userSession) throws IOException{
		
		String name = (String) userSession.getUserProperties().get("username");
		if(name == null){
			userSession.getUserProperties().put("username", message);
			userSession.getBasicRemote().sendText(buildJsonData("System", "you are now connect as "+message));
		}else{
			Iterator<Session> it = users.iterator();
			while(it.hasNext())
				it.next().getBasicRemote().sendText(buildJsonData(name, message));
		}
		
		System.out.println("Input: "+message);
		//String replyMessage = "echo "+message;
		//System.out.println("Send to Client: "+replyMessage);
		//return replyMessage;
	}
	
	private String buildJsonData(String name, String message) {
		// TODO Auto-generated method stub
		JsonObject jo = Json.createObjectBuilder().add("message", name+":"+message).build();
		StringWriter sw = new StringWriter();
		try(JsonWriter jw = Json.createWriter(sw)){
			jw.write(jo);
		}
		return sw.toString();
	}
	@OnError
	public void handleError(Throwable t){
		t.printStackTrace();
	}
	
	/*
	public String ConnectToMulticastListener() {
		String data = "";
		try {
			OutputStream os = new ByteArrayOutputStream();
			MulticastListenerThread mlt= new MulticastListenerThread(host, group, port, os);
			System.out.println("MulticastListenerThread is now connected!");
			mlt.start();
			data = os.toString();
		} catch (InvalidParameterException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return data;
	}*/
}
