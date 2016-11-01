package au.com.auspost.smartspb.dao;

import au.com.auspost.smartspb.domain.RemoteConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.Property;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.PreparedStatementCallback;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.rmi.Remote;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class RemoteConfigurationDao {
    private static final String SQL_SELECT_CONFIGURATION =
            "SELECT rc.version, rcp.name, rcp.value " +
                    "FROM remote_config rc " +
                    "JOIN remote_config_prop rcp ON rcp.remote_config_id = rc.id " +
                    "WHERE rc.id = (SELECT MAX(id) FROM remote_config);";

    @Autowired
    private NamedParameterJdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.SUPPORTS)
    public RemoteConfiguration load() {
        final List<RemoteConfiguration> remoteConfigurations = new ArrayList<>();

        jdbcTemplate.query(SQL_SELECT_CONFIGURATION, new RowMapper<Object>() {
            @Override
            public Object mapRow(ResultSet resultSet, int i) throws SQLException {
                if (remoteConfigurations.isEmpty()) {
                    RemoteConfiguration remoteConfiguration = new RemoteConfiguration(resultSet.getInt("version"));
                    remoteConfigurations.add(remoteConfiguration);
                }
                remoteConfigurations.get(0).getProperties().setProperty(
                        resultSet.getString("name"), resultSet.getString("value"));
                return null;
            }
        });
        return remoteConfigurations.get(0);
    }
}
