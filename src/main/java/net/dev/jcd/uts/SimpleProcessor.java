package net.dev.jcd.uts;

import java.util.HashMap;
import java.util.Map;

import org.apache.camel.Exchange;
import org.apache.camel.Processor;
import org.jboss.logging.Logger;

public class SimpleProcessor implements Processor{
	private static final Logger LOG = Logger.getLogger(SimpleProcessor.class);

	@Override
	public void process(Exchange exchange) throws Exception {
		LOG.info("process :"+exchange );
		Map mm = exchange.getIn().getBody(Map.class);
		LOG.info("process :"+mm.get("id"));
		HashMap<String, String> out = new HashMap<>();
		out.put("id", (String) mm.get("id"));
		out.put("status", "complete");
		exchange.getOut().setBody(out);;
	}

}
