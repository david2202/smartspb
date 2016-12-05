package au.com.auspost.smartspb.dao;


import au.com.auspost.smartspb.domain.Event;
import au.com.auspost.smartspb.domain.Reading;
import au.com.auspost.smartspb.domain.StreetPostingBox;
import au.com.auspost.smartspb.domain.Temperature;
import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;

@Component
public class ReadingDao {
    private static final String SQL_INSERT_READING =
            "INSERT INTO reading " +
                    "(event_id, grams, total_grams, article_count) " +
                    "VALUES " +
                    "(:eventId, :grams, :totalGrams, :articleCount);";

    private static final String SQL_LIST =
            "SELECT r.id,e.street_posting_box_id, e.id AS event_id, e.type_cd, e.date_time, r.grams, r.total_grams, r.article_count, e.degrees_c, e.latest_ind, " +
                    "spb.imei, spb.timezone, spb.address, spb.api_key, spb.prev_api_key, spb.version " +
                    "FROM reading r " +
                    "JOIN event e ON r.event_id = e.id " +
                    "JOIN street_posting_box spb ON e.street_posting_box_id = spb.id " +
                    "WHERE e.date_time >= :dateTime " +
                    "ORDER BY e.date_time DESC," +
                             "r.id DESC;";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Reading reading) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("eventId", reading.getEvent().getId())
                .addValue("grams", reading.getGrams())
                .addValue("totalGrams", reading.getTotalGrams())
                .addValue("articleCount", reading.getArticleCount());

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
                spb.setAddress(resultSet.getString("address"));
                spb.setApiKey(resultSet.getString("api_key"));
                spb.setPrevApiKey(resultSet.getString("prev_api_key"));
                spb.setVersion(resultSet.getInt("version"));
                spbs.put(spb.getId(), spb);
            }
            StreetPostingBox spb = spbs.get(resultSet.getInt("street_posting_box_id"));
            Event e = new Event(
                    resultSet.getInt("event_id"),
                    spb,
                    Event.Type.valueOf(resultSet.getString("type_cd")),
                    new DateTime(resultSet.getTimestamp("date_time"), DateTimeZone.forTimeZone(TimeZone.getDefault())),
                    new Temperature(resultSet.getBigDecimal("degrees_c")),
                    resultSet.getBoolean("latest_ind"));

            Reading r = new Reading(
                    resultSet.getInt("id"),
                    e,
                    resultSet.getInt("grams"),
                    resultSet.getInt("total_grams"),
                    resultSet.getInt("article_count"));
            spb.addReading(r);
            return r;
        });
    }
}
