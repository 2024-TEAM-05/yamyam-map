package oz.yamyam_map.data_loader.repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;

import lombok.extern.slf4j.Slf4j;
import oz.yamyam_map.data_loader.RegionRecord;

@Repository
@Slf4j
public class JdbcRegionRepository implements RegionRepository {
	public static final String TRUNCATE_SQL = "TRUNCATE TABLE region";
	public static final String INSERT_SQL = "INSERT INTO region (province, city_district, location) VALUES (?, ?, ST_GeomFromText(?))";
	public static final String POINT_FORMAT = "POINT(%f %f)";

	private final String jdbcUrl;
	private final String jdbcUser;
	private final String jdbcPassword;

	public JdbcRegionRepository(
		@Value("${spring.datasource.url}") String jdbcUrl,
		@Value("${spring.datasource.username}") String jdbcUser,
		@Value("${spring.datasource.password}") String jdbcPassword
	) {
		this.jdbcUrl = jdbcUrl;
		this.jdbcUser = jdbcUser;
		this.jdbcPassword = jdbcPassword;
	}

	@Override
	public void deleteAll() {
		try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
			 Statement statement = connection.createStatement()) {
			statement.executeUpdate(TRUNCATE_SQL);
			log.info("기존 시군구 데이터 삭제 성공");
		} catch (SQLException e) {
			log.info("기존 시군구 데이터 삭제 실패");
		}
	}

	@Override
	public void saveAll(List<RegionRecord> data) {
		try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword);
			 PreparedStatement preparedStatement = connection.prepareStatement(INSERT_SQL)) {

			connection.setAutoCommit(false); // 자동 커밋을 비활성화하여 트랜잭션을 제어

			for (RegionRecord record : data) {
				preparedStatement.setString(1, record.getDosi());
				preparedStatement.setString(2, record.getSgg());

				String point = String.format(POINT_FORMAT, record.getLon(), record.getLat()); // POINT 데이터 생성
				preparedStatement.setString(3, point);

				preparedStatement.addBatch();
			}

			// 배치 실행
			preparedStatement.executeBatch();

			// 트랜잭션 커밋
			connection.commit();

			log.info("새로운 시군구 데이터 저장 성공");

		} catch (SQLException e) {
			log.error("새로운 시군구 데이터 저장 실패", e);

			// 롤백
			try (Connection connection = DriverManager.getConnection(jdbcUrl, jdbcUser, jdbcPassword)) {
				if (connection != null) {
					connection.rollback();
				}
			} catch (SQLException rollbackException) {
				log.error("데이터베이스 롤백 실패", rollbackException);
			}
		}
	}
}
