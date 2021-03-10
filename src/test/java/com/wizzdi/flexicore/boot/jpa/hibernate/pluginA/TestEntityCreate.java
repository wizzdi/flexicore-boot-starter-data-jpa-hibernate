package com.wizzdi.flexicore.boot.jpa.hibernate.pluginA;

public class TestEntityCreate {

	private String name;
	private String longText;

	public String getName() {
		return name;
	}

	public <T extends TestEntityCreate> T setName(String name) {
		this.name = name;
		return (T) this;
	}

	public String getLongText() {
		return longText;
	}

	public <T extends TestEntityCreate> T setLongText(String longText) {
		this.longText = longText;
		return (T) this;
	}
}
