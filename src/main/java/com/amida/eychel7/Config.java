package com.amida.eychel7;

import ca.uhn.hl7v2.DefaultHapiContext;
import ca.uhn.hl7v2.HapiContext;

public class Config {
	private static Config instance;

	private HapiContext context;
	private int port = 1011;
	private boolean useTls = false;

	private Config() {
		context = new DefaultHapiContext();
	}

	public static synchronized Config get() {
		if (instance == null) {
			instance = new Config();
		}
		return instance;
	}

	public HapiContext getContext() {
		return context;
	}

	public int getPort() {
		return port;
	}

	public boolean getUseTls() {
		return useTls;
	}

	@Override
	protected void finalize() throws Exception {
		context.close();
	}
}
