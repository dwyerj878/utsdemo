package net.dev.jcd.uts.properties;

import org.apache.deltaspike.core.api.config.PropertyFileConfig;

public class UtsProperties implements PropertyFileConfig {

	@Override
	public String getPropertyFileName() {
		return "uts-demo.properties";
	}

	@Override
	public boolean isOptional() {
		return false;
	}

}
