package oz.yamyam_map.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersBuilder;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class SeoulDataPipelineScheduler {

	private final JobLauncher jobLauncher;
	private final Job seoulDataPipelineJob;

	// 매일 새벽 2시에 작업을 실행
	@Scheduled(cron = "0 0 2 * * ?")
	public void runSeoulDataPipelineJob() {
		try {
			JobParameters jobParameters = new JobParametersBuilder()
				.addLong("date", System.currentTimeMillis()) // 중복 실행 방지를 위한 unique 파라미터
				.toJobParameters();

			jobLauncher.run(seoulDataPipelineJob, jobParameters);
			log.info("SeoulDataPipelineJob 실행 완료");

		} catch (Exception e) {
			log.error("SeoulDataPipelineJob 실행 중 오류 발생", e);
		}
	}
}