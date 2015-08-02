package main.java.controllers.interfaces;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.InetSocketAddress;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.SocketTimeoutException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import org.apache.log4j.Logger;
import org.jsoup.nodes.Document;

import main.java.model.interfaces.WebPage;

public abstract class WpParser {
	Logger logger = Logger.getLogger("main.java.controllers.interfaces.WpParser");

	private List<Proxy> proxies = new ArrayList<Proxy>();

	public abstract WebPage parse(URL url) throws IOException;

	public WpParser(boolean initProxies) {
		if (initProxies)
			this.initProxies();
		else
			proxies.add(Proxy.NO_PROXY);
	}

	private boolean isValid(Proxy proxy) throws IOException {
		URL url = new URL("http://www.google.com");
		String str = null;
		StringBuilder buffer = new StringBuilder();
		HttpURLConnection urlConnect = (HttpURLConnection) url.openConnection(proxy);
		urlConnect.setConnectTimeout(30000);
		// trying to retrieve data from the source. If offline, this line will
		// fail:
		urlConnect.connect();
		// -- Download the website into a buffer
		BufferedReader br = new BufferedReader(new InputStreamReader(urlConnect.getInputStream()));

		while ((str = br.readLine()) != null) {
			logger.debug(str);
			buffer.append(str);
		}
		
		logger.info("... proxy : " + proxy.toString() + " is valid");
		return true;
	}

	private void initProxies() {
		// TODO Auto-generated method stub
		logger.debug("default Java proxy " + System.getProperty("http.proxyPort") + ":" + System.getProperty("http.proxyHost"));
		logger.info("... loading proxies");
		List<Proxy> proxiesCandidates = new ArrayList<Proxy>();
		
		proxiesCandidates.add(new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("184.69.82.226", 1080)));
		proxiesCandidates.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("213.136.79.124", 80)));
		proxiesCandidates.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("24.132.85.114", 80)));
		proxiesCandidates.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("86.62.76.51", 3128)));
		proxiesCandidates.add(new Proxy(Proxy.Type.HTTP, new InetSocketAddress("82.101.250.44", 80)));
		proxiesCandidates.add(Proxy.NO_PROXY);
		
		for (Proxy proxy : proxiesCandidates) {
			try {
				if (isValid(proxy))
					proxies.add(proxy);
				else
					logger.info("... proxy : " + proxy.toString() + " is NOT valid");
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				logger.error(e);
			}
		}
		
		logger.info("... number of valid proxies : " + proxies.size());
	}

	public String download(URL url) throws IOException {
		// -- Setup connection through proxy
		String dl = null;

		for (Proxy proxy : proxies) {
			dl = download(proxy, url);

			if (dl != null)
				break;
		}

		if (dl == null) {
			throw new IOException("... ERROR : URL " + url.toString() + " impossible to download");
		}

		return dl;
	}

	private String download(Proxy proxy, URL url) {
		String str = null;
		StringBuilder buffer = new StringBuilder();

		try {
			HttpURLConnection httpUrlConnection = (HttpURLConnection) url.openConnection(proxy);
			httpUrlConnection.setConnectTimeout(10000);
			httpUrlConnection.connect();
			// -- Download the website into a buffer
			BufferedReader br = new BufferedReader(new InputStreamReader(httpUrlConnection.getInputStream()));

			while ((str = br.readLine()) != null) {
				buffer.append(str);
			}
		} catch (SocketTimeoutException e) {
			logger.error(e);
			buffer = null;
		} catch (IOException e) {
			logger.error(e);
			buffer = null;
		}

		if (buffer != null) {
			str = buffer.toString();
		}

		return str;
	}
}
