package com.project.taskifier;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.env.Environment;
import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
public class TaskifierApplication {

	private static final Logger log = LoggerFactory.getLogger(TaskifierApplication.class);

	private final Environment env;

	public TaskifierApplication(Environment env) {
		this.env = env;
	}

	public static void main(String[] args) {
		SpringApplication app = new SpringApplication(TaskifierApplication.class);
		Environment env = app.run(args).getEnvironment();
		logApplicationStartup(env);
	}

	private static void logApplicationStartup(Environment env) {
		String serverPort = env.getProperty("server.port");
		String hostAddress = "localhost";
		try {
			// querying our network adapter for our IPv4 address
			hostAddress = InetAddress.getLocalHost().getHostAddress();
		} catch (UnknownHostException e) {
			log.warn("The host name could not be determined, using `localhost` as fallback");
		}
		log.info("\n----------------------------------------------------------\n\t" +
			"Application is running!\n\t" +
			"Local IPv4: \thttp://localhost:{}\n\t" +
			"External IPv4: \thttp://{}:{}\n\t" +
            "Server Version: {}\n\t" +
            "Spring Version: {}\n\t" +
			"Profile(s): \t{}\n" +
			"----------------------------------------------------------\n",
			serverPort,
			hostAddress,
			serverPort,
            env.getProperty("build.version"),
            env.getProperty("parent.version"),
			env.getActiveProfiles());
	}

}

