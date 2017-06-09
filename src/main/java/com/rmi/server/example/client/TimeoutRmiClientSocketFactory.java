package com.rmi.server.example.client;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.rmi.server.RMIClientSocketFactory;

public class TimeoutRmiClientSocketFactory implements RMIClientSocketFactory {
	private int timeout = 2000;
	
	public TimeoutRmiClientSocketFactory() {
		super();
	}

	public TimeoutRmiClientSocketFactory(int timeout) {
		super();
		this.timeout = timeout;
	}

	public void setTimeout(int timeout) {
	    this.timeout = timeout;
	}

	@Override
	public Socket createSocket(String host, int port) throws IOException {
	    final Socket socket = new Socket();
	    socket.setSoTimeout(timeout);
	    socket.setSoLinger(false, 0);	 
	    
	    socket.connect(new InetSocketAddress(host, port), timeout);
	    
	    System.out.println("CreateSocket : "  + socket +": timeout = " +socket.getSoTimeout());
	    
	    return socket;
	}

 }
