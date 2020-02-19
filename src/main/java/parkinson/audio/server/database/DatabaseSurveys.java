package parkinson.audio.server.database;

import java.util.List;

import javax.annotation.PostConstruct;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.support.JdbcDaoSupport;
import org.springframework.stereotype.Repository;

import parkinson.audio.server.utils.ConstantsFCR;
import parkinson.audio.server.utils.Survey;
import parkinson.audio.server.utils.SurveyMapper;

@Repository
public class DatabaseSurveys extends JdbcDaoSupport {

	@Autowired
	DataSource datasource;

	Logger log = LoggerFactory.getLogger(DatabaseSurveys.class);

	@PostConstruct
	private void initialize() {
		setDataSource(datasource);
	}

	public int insertRilevazioni(Double fcr1, Double fcr2) {
		log.info("Save datas on db . . .");
		log.debug(ConstantsFCR.FCR_1_SESSION + ": " + fcr1.toString());
		log.debug(ConstantsFCR.FCR_2_SESSION + ": " + fcr2.toString());
		return getJdbcTemplate().update("INSERT INTO rilevazioni(fcr1, fcr2) VALUES (?,?)", fcr1, fcr2);
	}

	public List<Survey> list() {
		log.info("Extact datas on db . . .");
		return getJdbcTemplate().query("SELECT * FROM rilevazioni", new SurveyMapper());
	}
}
