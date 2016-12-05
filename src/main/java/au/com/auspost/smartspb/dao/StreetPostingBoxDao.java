package au.com.auspost.smartspb.dao;


import au.com.auspost.smartspb.domain.*;
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
            "e.id AS e_id, e.type_cd, e.date_time, e.degrees_c, e.latest_ind, " +
            "r.id AS r_id, r.grams, r.total_grams, r.article_count ";
    private static final String SQL_LOAD_BY_IMEI =
            "SELECT " + SQL_COLUMNS +
                    "FROM street_posting_box spb " +
                    "LEFT OUTER JOIN event e ON e.street_posting_box_id = spb.id AND e.type_cd = :typeCd AND e.latest_ind = true " +
                    "LEFT OUTER JOIN reading r ON r.event_id = e.id " +
                    "WHERE imei = :imei";
    private static final String SQL_LIST =
            "SELECT " + SQL_COLUMNS  +
                    "FROM street_posting_box spb " +
                    "LEFT OUTER JOIN event e ON e.street_posting_box_id = spb.id AND e.type_cd = :typeCd AND e.latest_ind = true " +
                    "LEFT OUTER JOIN reading r ON r.event_id = e.id";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    public StreetPostingBox load(Integer id) {
        return null;
    }

    public void save(StreetPostingBox spb) {

    }

    public StreetPostingBox load(String imei) {
        SqlParameterSource parameterSource = new MapSqlParameterSource()
                .addValue("imei", imei)
                .addValue("typeCd", Event.Type.READING.toString());
        return jdbcTemplate.queryForObject(SQL_LOAD_BY_IMEI, parameterSource, (resultSet, i) -> {
            StreetPostingBox spb = mapResultSet(resultSet);
            return spb;
        });
    }

    public List<StreetPostingBox> list() {
        SqlParameterSource parameterSource = new MapSqlParameterSource("typeCd", Event.Type.READING.toString());
        return jdbcTemplate.query(SQL_LIST, parameterSource, (resultSet, i) -> {
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
        resultSet.getInt("r_id");
        if (!resultSet.wasNull()) {
            Event e = new Event(
                    resultSet.getInt("e_id"),
                    spb,
                    Event.Type.valueOf(resultSet.getString("type_cd")),
                    new DateTime(resultSet.getTimestamp("date_time"), DateTimeZone.forTimeZone(TimeZone.getDefault())),
                    new Temperature(resultSet.getBigDecimal("degrees_c")),
                    resultSet.getBoolean("latest_ind"));
            Reading r = new Reading(
                    resultSet.getInt("r_id"),
                    e,
                    resultSet.getInt("grams"),
                    resultSet.getInt("total_grams"),
                    resultSet.getInt("article_count"));
            spb.setLatestReading(r);
            spb.addReading(r);
        }
        spb.setVersion(resultSet.getInt("version"));
        return spb;
    }
}
