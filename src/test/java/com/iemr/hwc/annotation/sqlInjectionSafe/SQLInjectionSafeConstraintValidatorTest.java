package com.iemr.hwc.annotation.sqlInjectionSafe;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("SQLInjectionSafeConstraintValidator")
class SQLInjectionSafeConstraintValidatorTest {

	private final SQLInjectionSafeConstraintValidator validator = new SQLInjectionSafeConstraintValidator();

	@ParameterizedTest
	@NullSource
	@ValueSource(strings = { "", "Ramesh Kumar", "ANC visit for beneficiary 12345", "village-2, block 7",
			"paracetamol 500mg", "no complaints", "select" })
	@DisplayName("accepts ordinary field values")
	void acceptsOrdinaryValues(String value) {
		assertThat(validator.isValid(value, null)).as("%s should be accepted", value).isTrue();
	}

	@ParameterizedTest
	@ValueSource(strings = { "SELECT name FROM users", "select * from t_benvisitdetail",
			"INSERT INTO users VALUES (1)", "UPDATE users SET name = 'x'", "DELETE FROM users WHERE id = 1",
			"DROP TABLE users", "TRUNCATE TABLE users", "CREATE TABLE t (id int)", "ALTER TABLE users ADD c int",
			"CALL someProcedure()", "ROLLBACK to savepoint", "KILL 1", "UPSERT into t", "SAVEPOINT sp1",
			"DESC users", "DESCRIBE users", "LOCK TABLE users", "UNLOCK TABLE x", "RELEASE SAVEPOINT sp",
			"name'; DROP TABLE users", "name /* comment */", "name -- comment" })
	@DisplayName("rejects a value carrying a SQL statement or a comment marker")
	void rejectsSqlPayloads(String value) {
		assertThat(validator.isValid(value, null)).as("%s should be rejected", value).isFalse();
	}

	@Test
	@DisplayName("matches regardless of the case the statement is written in")
	void isCaseInsensitive() {
		assertThat(validator.isValid("SeLeCt id FrOm users", null)).isFalse();
	}

	@Test
	@DisplayName("initialize accepts the annotation without needing state from it")
	void initializeIsANoOp() {
		validator.initialize(null);

		assertThat(validator.isValid("Ramesh Kumar", null)).isTrue();
	}
}
