package oz.yamyam_map.batch.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("batch.seoul")
public record SeoulDataPipelineProperties(
	String authKey,
	int chunkSize
) {
}
