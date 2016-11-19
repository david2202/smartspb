package au.com.auspost.smartspb.dao;


import au.com.auspost.smartspb.domain.LatLong;
import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.domain.Temperature;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.TimeZone;

@Component
public class StreetPostingBoxDao {
    private static final String SQL_COLUMNS = "spb.id AS s_id, spb.imei, spb.timezone, spb.api_key, spb.prev_api_key, " +
            "spb.address, spb.postcode, spb.latitude, spb.longitude, spb.version, " +
            "r.id AS r_id, r.date_time, r.grams, r.total_grams, r.degrees_c, r.latest_ind ";
    private static final String SQL_LOAD_BY_IMEI =
            "SELECT  " + SQL_COLUMNS +
                    "FROM street_posting_box spb " +
                    "LEFT OUTER JOIN reading r ON r.street_posting_box_id = spb.id AND r.latest_ind = true " +
                    "WHERE imei = :imei;";
    private static final String SQL_LIST =
            "SELECT  " + SQL_COLUMNS  +
                    "FROM street_posting_box spb " +
                    "LEFT OUTER JOIN reading r ON r.street_posting_box_id = spb.id AND r.latest_ind = true ";

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
            StreetPostingBox spb = mapResultSet(resultSet);
            return spb;
        });
    }

    public List<StreetPostingBox> list() {
        return jdbcTemplate.query(SQL_LIST, (resultSet, i) -> {
            StreetPostingBox spb = mapResultSet(resultSet);
            return spb;
        });
    }

    private StreetPostingBox mapResultSet(ResultSet resultSet) throws SQLException {
        StreetPostingBox spb= new StreetPostingBox();
        spb.setId(resultSet.getInt("id"));
        spb.setImei(resultSet.getString("imei"));
        spb.setTimezone(resultSet.getString("timezone"));
        spb.setApiKey(resultSet.getString("api_key"));
        spb.setPrevApiKey(resultSet.getString("prev_api_key"));
        spb.setAddress(resultSet.getString("address"));
        spb.setPostCode(resultSet.getInt("postcode"));
        spb.setLatLong(new LatLong(resultSet.getBigDecimal("latitude"), resultSet.getBigDecimal("longitude")));
        Reading r = new Reading(
                resultSet.getInt("r_id"),
                spb,
                new DateTime(resultSet.getTimestamp("date_time"), DateTimeZone.forTimeZone(TimeZone.getDefault())),
                resultSet.getInt("grams"),
                resultSet.getInt("total_grams"),
                new Temperature(resultSet.getBigDecimal("degrees_c")),
                resultSet.getBoolean("latest_ind"));
        spb.setLatestReading(r);
        spb.addReading(r);
        spb.setVersion(resultSet.getInt("version"));
        return spb;
    }
}
