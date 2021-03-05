package com.example.springboot;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class HelloController {
	/*
	@Autowired
	TelemetryClient telemetryClient;
	*/

	@RequestMapping("/")
	public String index() {
/*
		// send event
		telemetryClient.trackEvent("URI /is triggered");

		// measure DB query benchmark
		long startTime = System.nanoTime();
		long endTime = System.nanoTime();

		MetricTelemetry benchmark = new MetricTelemetry();
		benchmark.setName("Default URL");
		benchmark.setValue(endTime - startTime);
		telemetryClient.trackMetric(benchmark);
*/
		return "Greetings from Spring Boot!";
	}

}
