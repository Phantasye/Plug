package com.plug.util.io;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import com.google.gson.Gson;
import com.plug.util.Log;

public class Resource {

	private final Gson parse = new Gson();

	public Resource(Loadable loaded, String resource) {
		loadResource(loaded, resource);
	}

	public void loadResource(Loadable loaded, String resource) {
		try {
			Path src = Paths.get(resource);
			InputStream in = Files.newInputStream(src);
			BufferedReader input = new BufferedReader(new InputStreamReader(in));
			loaded.initialize(parse.fromJson(input, loaded.getClass()));
		} catch (Exception e) {
			Log.printError(e);
		}
	}
}
