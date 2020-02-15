package parkinson.audio.server.database;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import parkinson.audio.server.utils.ConstantsFCR;

@Component
public class DatabaseSurveys {

	@Autowired
	JdbcTemplate jdbc;

	Logger log = LoggerFactory.getLogger(DatabaseSurveys.class);

	public int insertRilevazioni(Double fcr1, Double fcr2) {
		log.info("Save datas on db . . .");
		log.debug(ConstantsFCR.FCR_1_SESSION + ": " + fcr1.toString());
		log.debug(ConstantsFCR.FCR_2_SESSION + ": " + fcr2.toString());
		return jdbc.update("INSERT INTO rilevazioni(fcr1, fcr2) VALUES (?,?)", fcr1, fcr2);
	}
}
