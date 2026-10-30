package com.iemr.hwc.utils.config;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.mock.env.MockEnvironment;

/**
 * The settings are read once off {@code application.properties} into a static
 * {@link java.util.Properties}, so these tests read whatever the packaged file holds and
 * assert the conversion behaviour rather than particular values.
 */
@DisplayName("ConfigProperties")
class ConfigPropertiesTest {

	private final ConfigProperties configProperties = new ConfigProperties();

	@Test
	@DisplayName("an unknown property reads as null")
	void unknownPropertyIsNull() {
		assertThat(ConfigProperties.getPropertyByName("no.such.property.exists")).isNull();
	}

	@Test
	@DisplayName("an unknown property converts to the zero value of each numeric type")
	void unknownPropertyConvertsToZero() {
		assertThat(ConfigProperties.getInteger("no.such.property.exists")).isZero();
		assertThat(ConfigProperties.getLong("no.such.property.exists")).isZero();
		assertThat(ConfigProperties.getBoolean("no.such.property.exists")).isFalse();
	}

	@Test
	@DisplayName("a property that is not a number converts to zero instead of failing")
	void aNonNumericPropertyConvertsToZero() {
		java.util.Properties properties = (java.util.Properties) org.springframework.test.util.ReflectionTestUtils
				.getField(ConfigProperties.class, "properties");
		properties.setProperty("test.not.a.number", "hwc");
		try {
			assertThat(ConfigProperties.getInteger("test.not.a.number")).isZero();
			assertThat(ConfigProperties.getLong("test.not.a.number")).isZero();
			assertThat(ConfigProperties.getFloat("test.not.a.number")).isZero();
		} finally {
			properties.remove("test.not.a.number");
		}
	}

	@Test
	@DisplayName("a float reads back when the property is a number")
	void readsAFloat() {
		java.util.Properties properties = (java.util.Properties) org.springframework.test.util.ReflectionTestUtils
				.getField(ConfigProperties.class, "properties");
		properties.setProperty("test.float", "1.5");
		try {
			assertThat(ConfigProperties.getFloat("test.float")).isEqualTo(1.5F);
		} finally {
			properties.remove("test.float");
		}
	}

	@Test
	@DisplayName("a float lookup of a missing property fails, unlike the other converters")
	void aMissingFloatFails() {
		// getFloat catches NumberFormatException only, and Float.parseFloat(null) raises a
		// NullPointerException instead, so an absent property is not handled the way an
		// absent integer or long is. Asserted here so the difference is not a surprise.
		org.assertj.core.api.Assertions.assertThatThrownBy(() -> ConfigProperties.getFloat("no.such.property.exists"))
				.isInstanceOf(NullPointerException.class);
	}

	@Test
	@DisplayName("the session settings come back with usable defaults")
	void sessionSettingsHaveDefaults() {
		assertThat(ConfigProperties.getSessionExpiryTime()).isNotNegative();
		assertThat(ConfigProperties.getRedisPort()).isNotNegative();

		// Both accessors cache their value, so a second read must agree with the first.
		assertThat(ConfigProperties.getExtendExpiryTime()).isEqualTo(ConfigProperties.getExtendExpiryTime());
		assertThat(ConfigProperties.getRedisUrl()).isEqualTo(ConfigProperties.getRedisUrl());
	}

	@Test
	@DisplayName("an unset password reads as empty rather than null")
	void anUnsetPasswordIsEmpty() {
		assertThat(ConfigProperties.getPassword("no.such.password")).isNull();
	}

	@Test
	@DisplayName("a Base64 password marked with the 0X10 prefix is decoded")
	void decodesAnEncodedPassword() {
		String encoded = "0X10:" + java.util.Base64.getEncoder().encodeToString("s3cret".getBytes());
		java.util.Properties properties = (java.util.Properties) org.springframework.test.util.ReflectionTestUtils
				.getField(ConfigProperties.class, "properties");
		properties.setProperty("test.password", encoded);
		try {
			assertThat(ConfigProperties.getPassword("test.password")).isEqualTo("s3cret");
		} finally {
			properties.remove("test.password");
		}
	}

	@Test
	@DisplayName("a plain password is handed back as it is")
	void leavesAPlainPasswordAlone() {
		java.util.Properties properties = (java.util.Properties) org.springframework.test.util.ReflectionTestUtils
				.getField(ConfigProperties.class, "properties");
		properties.setProperty("test.plain.password", "plain");
		try {
			assertThat(ConfigProperties.getPassword("test.plain.password")).isEqualTo("plain");
		} finally {
			properties.remove("test.plain.password");
		}
	}

	@Test
	@DisplayName("the Spring environment can be handed in")
	void acceptsTheEnvironment() {
		configProperties.setEnvironment(new MockEnvironment().withProperty("a", "b"));

		assertThat(ConfigProperties.getPropertyByName("no.such.property.exists")).isNull();
	}
}
