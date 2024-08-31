package oz.yamyam_map.batch.config;

import org.springframework.batch.core.Step;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.PlatformTransactionManager;

import lombok.RequiredArgsConstructor;
import oz.yamyam_map.batch.domain.RowSeoulRestaurant;
import oz.yamyam_map.batch.dto.SeoulRestaurantDto;

@Configuration
@RequiredArgsConstructor
public class SeoulDataPipelineJobConfig {

	/**
	 * Step 1. OpenApi로 받아 온 데이터를 row table에 저장
	 * RowSeoulDataApiReader: HTTP 요청을 보내고 데이터를 받아오는 객체
	 * RowSeoulDataProcessor: 추가/변경된 데이터만 필터링하는 객체
	 * RowSeoulDataWriter: 추가/변경 된 데이터를 row table에 반영하는 객체
	 */
	@Bean
	public Step rowSeoulDataStoreStep(
		JobRepository jobRepository,
		PlatformTransactionManager platformTransactionManager,
		ItemReader<SeoulRestaurantDto> rowSeoulDataApiReader,
		ItemProcessor<SeoulRestaurantDto, RowSeoulRestaurant> rowSeoulDataProcessor,
		ItemWriter<RowSeoulRestaurant> rowSeoulDataWriter
	) {
		return new StepBuilder("RowSeoulDataStoreStep", jobRepository)
			.<SeoulRestaurantDto, RowSeoulRestaurant>chunk(100, platformTransactionManager)
			.reader(rowSeoulDataApiReader)
			.processor(rowSeoulDataProcessor)
			.writer(rowSeoulDataWriter)
			.build();
	}

}
