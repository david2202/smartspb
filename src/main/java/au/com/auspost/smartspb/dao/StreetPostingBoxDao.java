package au.com.auspost.smartspb.dao;


import au.com.auspost.smartspb.domain.StreetPostingBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class StreetPostingBoxDao {
    private static final String SQL_LOAD_BY_IMEI =
            "SELECT id, imei, timezone, api_key, prev_api_key, version " +
                    "FROM street_posting_box " +
                    "WHERE imei = :imei;";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public StreetPostingBox load(Integer id) {
        return null;
    }

    public void save(StreetPostingBox spb) {

    }

    public StreetPostingBox load(String imei) {
        SqlParameterSource parameterSource = new MapSqlParameterSource("imei", imei);
        return jdbcTemplate.queryForObject(SQL_LOAD_BY_IMEI, parameterSource, (resultSet, i) -> {
            StreetPostingBox spb= new StreetPostingBox();
            spb.setId(resultSet.getInt("id"));
            spb.setImei(resultSet.getString("imei"));
            spb.setTimezone(resultSet.getString("timezone"));
            spb.setApiKey(resultSet.getString("api_key"));
            spb.setPrevApiKey(resultSet.getString("prev_api_key"));
            spb.setVersion(resultSet.getInt("version"));
            return spb;
        });
    }
}
