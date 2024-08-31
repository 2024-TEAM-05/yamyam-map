package oz.yamyam_map.batch.config;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("batch.seoul")
public record SeoulDataPipelineProperties(
	String authKey,
	int chunkSize
) {
}
