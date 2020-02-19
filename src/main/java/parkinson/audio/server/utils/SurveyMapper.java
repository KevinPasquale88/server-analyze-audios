package parkinson.audio.server.utils;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class SurveyMapper implements RowMapper<Survey> {

	@Override
	public Survey mapRow(ResultSet rs, int rowNum) throws SQLException {
		return new Survey(rs.getDouble("fcr1"), rs.getDouble("fcr2"));
	}

}
