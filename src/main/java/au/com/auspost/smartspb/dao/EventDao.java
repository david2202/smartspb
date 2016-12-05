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

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

@Component
public class EventDao {
    private static final String SQL_INSERT_EVENT =
            "INSERT INTO event " +
                    "(street_posting_box_id, type_cd, date_time, degrees_c, latest_ind) " +
                    "VALUES " +
                    "(:streetPostingBoxId, :typeCd, :dateTime, :degreesC, :latestInd);";

    private static final String SQL_UPDATE_LATEST_EVENT =
            "UPDATE event " +
                    "SET latest_ind = :notLatestInd " +
                    "WHERE street_posting_box_id = :streetPostingBoxId AND type_cd = :typeCd AND latest_ind = :latestInd;";

    private static final String SQL_LIST =
            "SELECT e.id AS e_id,e.street_posting_box_id, e.id AS event_id, e.type_cd, e.date_time, e.degrees_c, e.latest_ind, " +
                    "r.id AS r_id, r.grams, r.total_grams, r.article_count, " +
                    "spb.imei, spb.timezone, spb.address, spb.api_key, spb.prev_api_key, spb.version " +
                    "FROM event e " +
                    "LEFT OUTER JOIN reading r ON r.event_id = e.id " +
                    "JOIN street_posting_box spb ON e.street_posting_box_id = spb.id " +
                    "WHERE e.date_time >= :dateTime " +
                    "ORDER BY e.date_time DESC," +
                    "r.id DESC;";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public void clearLatest(Integer spbId, Event.Type type) {
        MapSqlParameterSource updateParams = new MapSqlParameterSource()
                .addValue("streetPostingBoxId", spbId)
                .addValue("typeCd", type.toString())
                .addValue("notLatestInd", false)
                .addValue("latestInd", true);

        int i = jdbcTemplate.update(SQL_UPDATE_LATEST_EVENT, updateParams);
        System.out.println(i);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public void save(Event event) {
        MapSqlParameterSource params = new MapSqlParameterSource()
                .addValue("streetPostingBoxId", event.getStreetPostingBox().getId())
                .addValue("typeCd", event.getType().toString())
                .addValue("dateTime", event.getDateTime().toDate())
                .addValue("degreesC", event.getDegreesC().getValue())
                .addValue("latestInd", event.isLatestForType());

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(SQL_INSERT_EVENT, params, keyHolder, new String[]{"id"});

        event.setId(keyHolder.getKey().intValue());
    }

    public List<Event> list(DateTime dateTime) {
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
            Event e = null;
            Reading r = new Reading(
                    resultSet.getInt("id"),
                    e,
                    resultSet.getInt("grams"),
                    resultSet.getInt("total_grams"),
                    resultSet.getInt("article_count"));
            e = new Event(
                    resultSet.getInt("event_id"),
                    spb,
                    Event.Type.valueOf(resultSet.getString("type_cd")),
                    new DateTime(resultSet.getTimestamp("date_time"), DateTimeZone.forTimeZone(TimeZone.getDefault())),
                    new Temperature(resultSet.getBigDecimal("degrees_c")),
                    resultSet.getBoolean("latest_ind"));
            e.makeReading(r);
            spb.addReading(r);
            return e;
        });
    }
}
