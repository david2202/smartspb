package au.com.auspost.smartspb.dao;


import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.RemoteConfiguration;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.domain.Temperature;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

@Component
public class ReadingDao {
    private static final String SQL_INSERT_READING =
            "INSERT INTO reading " +
                    "(street_posting_box_id, date_time, grams, degreesC, latestInd) " +
                    "VALUES " +
                    "(:streetPostingBoxId, :dateTime, :grams, :degreesC, :latestInd);";
    private static final String SQL_UPDATE_LATEST_READING =
            "UPDATE reading " +
                    "SET latestInd = :notLatestInd " +
                    "WHERE street_posting_box_id = :streetPostingBoxId AND latestInd = :latestInd;";
    private static final String SQL_LIST =
            "SELECT r.id,r.street_posting_box_id, r.date_time, r.grams, r.degreesC, r.latestInd, " +
                    "spb.imei, spb.timezone, spb.api_key, spb.prev_api_key, spb.version " +
                    "FROM reading r " +
                    "JOIN street_posting_box spb on r.street_posting_box_id = spb.id " +
                    "WHERE r.date_time >= :dateTime " +
                    "ORDER BY r.date_time DESC;";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Reading reading) {
        reading.makeLatest();
        MapSqlParameterSource updateParams = new MapSqlParameterSource()
                .addValue("streetPostingBoxId", reading.getStreetPostingBox().getId())
                .addValue("notLatestInd", false)
                .addValue("latestInd", true);

        jdbcTemplate.update(SQL_UPDATE_LATEST_READING, updateParams);

        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("streetPostingBoxId", reading.getStreetPostingBox().getId())
                .addValue("dateTime", reading.getDateTime().toDate())
                .addValue("grams", reading.getGrams())
                .addValue("degreesC", reading.getDegreesC().getValue())
                .addValue("latestInd", reading.isLatest());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(SQL_INSERT_READING, params, keyHolder, new String[]{"id"});

        reading.setId(keyHolder.getKey().intValue());
    }

    public List<Reading> list(DateTime dateTime) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("dateTime", dateTime.toDate());
        final Map<Integer, StreetPostingBox> spbs = new HashMap<>();

        return jdbcTemplate.query(SQL_LIST, params, (resultSet, i) -> {
            if (!spbs.containsKey(resultSet.getInt("street_posting_box_id"))) {
                StreetPostingBox spb = new StreetPostingBox();
                spb.setId(resultSet.getInt("street_posting_box_id"));
                spb.setImei(resultSet.getString("imei"));
                spb.setTimezone(resultSet.getString("timezone"));
                spb.setApiKey(resultSet.getString("api_key"));
                spb.setPrevApiKey(resultSet.getString("prev_api_key"));
                spb.setVersion(resultSet.getInt("version"));
                spbs.put(spb.getId(), spb);
            }
            StreetPostingBox spb = spbs.get(resultSet.getInt("street_posting_box_id"));
            Reading r = new Reading(
                    resultSet.getInt("id"),
                    spb,
                    new DateTime(resultSet.getTimestamp("date_time"), DateTimeZone.forTimeZone(TimeZone.getDefault())),
                    resultSet.getInt("grams"),
                    new Temperature(resultSet.getBigDecimal("degreesc")),
                    resultSet.getBoolean("latestInd"));
            spb.addReading(r);
            return r;
        });
    }
}
