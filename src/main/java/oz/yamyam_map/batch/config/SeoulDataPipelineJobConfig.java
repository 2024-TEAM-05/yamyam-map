package oz.yamyam_map.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
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
import oz.yamyam_map.module.restaurant.entity.Restaurant;

@Configuration
@RequiredArgsConstructor
public class SeoulDataPipelineJobConfig {

	@Bean
	public Job seoulDataPipelineJob(JobRepository jobRepository, Step rowSeoulDataStoreStep, Step seoulDataUpdateStep) {
		return new JobBuilder("SeoulDataPipelineJob", jobRepository)
			.start(rowSeoulDataStoreStep)
			.next(seoulDataUpdateStep)
			.build();
	}

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

	/**
	 * Step 2. Row table에서 변경/추가된 데이터를 전처리 후 운영 테이블에 반영
	 * RowSeoulDataDBReader: row_seoul_restaurant 테이블에서 데이터를 읽어오는 객체
	 * SeoulDataProcessor: 변경/추가된 RowSeoulRestaurant를 Restaurant 객체로 변환하는 객체 (전처리도 여기서 수행)
	 * RowSeoulDataWriter: Restaurant를 restraunt 테이블에 반영하는 객체
	 */
	@Bean
	public Step seoulDataUpdateStep(
		JobRepository jobRepository,
		PlatformTransactionManager platformTransactionManager,
		ItemReader<RowSeoulRestaurant> rowSeoulDataDbReader,
		ItemProcessor<RowSeoulRestaurant, Restaurant> seoulDataProcessor,
		ItemWriter<Restaurant> seoulDataWriter
	) {
		return new StepBuilder("SeoulDataUpdateStep", jobRepository)
			.<RowSeoulRestaurant, Restaurant>chunk(100, platformTransactionManager)
			.reader(rowSeoulDataDbReader)
			.processor(seoulDataProcessor)
			.writer(seoulDataWriter)
			.build();
	}

}
