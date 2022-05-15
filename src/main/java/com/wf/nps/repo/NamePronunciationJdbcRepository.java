package com.wf.nps.repo;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

@Repository
public class NamePronunciationJdbcRepository {

	@Autowired
	JdbcTemplate jdbcTemplate;

	class NamePronunciationRowMapper implements RowMapper<NamePronunciation> {
		@Override
		public NamePronunciation mapRow(ResultSet rs, int rowNum) throws SQLException {
			NamePronunciation namePronunciation = new NamePronunciation();
			namePronunciation.setId(rs.getLong("id"));
			namePronunciation.setuId(rs.getString("uId"));
			namePronunciation.setLegalFName(rs.getString("legalFName"));
			namePronunciation.setLegalLName(rs.getString("legalLName"));
			namePronunciation.setPrefName(rs.getString("prefName"));
			namePronunciation.setPrefPronunciation(rs.getBytes("prefPronunciation"));
			namePronunciation.setCreatedBy(rs.getString("createdBy"));
			namePronunciation.setCreatedOn(rs.getTimestamp("createdOn"));
			namePronunciation.setUpdatedBy(rs.getString("updatedBy"));
			namePronunciation.setUpdatedOn(rs.getTimestamp("updatedOn"));
			return namePronunciation;
		}

	}

	public List<NamePronunciation> findAll() {
		return jdbcTemplate.query("select id, uId, legalFName, legalLName, prefName from NamePronunciation",
				BeanPropertyRowMapper.newInstance(NamePronunciation.class));
	}

	public NamePronunciation findById(long id) {
		return jdbcTemplate.queryForObject(
				"select * from NamePronunciation where id=?",
				BeanPropertyRowMapper.newInstance(NamePronunciation.class), id);
	}

	public int deleteById(long id) {
		return jdbcTemplate.update("delete from NamePronunciation where id=?", new Object[] { id });
	}

	public int insert(NamePronunciation namePronunciation) {
		return jdbcTemplate.update(
				"insert into NamePronunciation (id, uId, legalFName, legalLName, prefName, prefPronunciation, createdBy, createdOn) "
						+ "values(?, ?, ?, ?, ?, ?, ?, ?)",
				new Object[] { namePronunciation.getId(), namePronunciation.getuId(), namePronunciation.getLegalFName(),
						namePronunciation.getLegalLName(), namePronunciation.getPrefName(),
						namePronunciation.getPrefPronunciation(), namePronunciation.getCreatedBy(),
						namePronunciation.getCreatedOn() });
	}

	public int update(NamePronunciation namePronunciation) {
		return jdbcTemplate.update(
				"update NamePronunciation " + " set prefPronunciation = ?, updatedBy = ?, updatedOn = ?"
						+ " where id = ?",
				new Object[] { namePronunciation.getPrefPronunciation(), namePronunciation.getUpdatedBy(),
						namePronunciation.getUpdatedOn(), namePronunciation.getId() });
	}

}
