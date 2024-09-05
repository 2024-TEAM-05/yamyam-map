package oz.yamyam_map.batch.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("batch.seoul")
public record SeoulDataPipelineProperties(
	String host,
	String serviceName,
	String responseFormat,
	String authKey,
	int chunkSize
) {
}
