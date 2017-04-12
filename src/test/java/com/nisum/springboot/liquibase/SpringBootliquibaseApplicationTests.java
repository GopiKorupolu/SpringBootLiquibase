package com.nisum.springboot.liquibase;

import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.rule.OutputCapture;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.core.NestedCheckedException;
import java.net.ConnectException;

import static org.assertj.core.api.Assertions.assertThat;



public class SpringBootliquibaseApplicationTests {


	@Rule
	public OutputCapture outputCapture = new OutputCapture();

	@Test
	public void testDefaultSettings() throws Exception {
		try {
			SpringBootliquibaseApplication.main(new String[] { "--server.port=0" });
		}
		catch (IllegalStateException ex) {
			if (serverNotRunning(ex)) {
				return;
			}
		}
		String output = this.outputCapture.toString();
		assertThat(output).contains("Successfully acquired change log lock")
				.contains("Creating database history "
						+ "table with name: PUBLIC.DATABASECHANGELOG")
				.contains("Table Employee created")
//				.contains("ChangeSet classpath:/db/"
//						+ "changelog/db.changelog-master.xml::1::"
//						+ "marceloverdijk ran successfully")
//				.contains("New row inserted into person")
//				.contains("ChangeSet classpath:/db/changelog/"
//						+ "db.changelog-master.yaml::2::"
//						+ "marceloverdijk ran successfully")
				.contains("Successfully released change log lock");
	}

	@SuppressWarnings("serial")
	private boolean serverNotRunning(IllegalStateException ex) {
		NestedCheckedException nested = new NestedCheckedException("failed", ex) {
		};
		if (nested.contains(ConnectException.class)) {
			Throwable root = nested.getRootCause();
			if (root.getMessage().contains("Connection refused")) {
				return true;
			}
		}
		return false;
	}

}
