package hu.unideb.inf.hunteka_opac_api;

import java.util.List;

import org.restlet.Server;
import org.restlet.data.Protocol;
import org.restlet.engine.Engine;
import org.restlet.engine.converter.ConverterHelper;

class Main {

	static {
		System.setProperty("org.restlet.engine.loggerFacadeClass", "org.restlet.ext.slf4j.Slf4jLoggerFacade");
	}

	public static void main(String[] args) {
		List<ConverterHelper> converters = Engine.getInstance().getRegisteredConverters();
		for (ConverterHelper converterHelper : converters) {
			System.out.println("- " + converterHelper);
		}
		Server server = new Server(Protocol.HTTP, 8111, new ApiServer());
		try {
			server.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
