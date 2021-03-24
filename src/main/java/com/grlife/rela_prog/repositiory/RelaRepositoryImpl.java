package com.grlife.rela_prog.repositiory;

import com.grlife.rela_prog.utils.MessageException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.*;

public class RelaRepositoryImpl implements RelaRepository {

    private final NamedParameterJdbcTemplate jdbcTemplate;
    public RelaRepositoryImpl(DataSource dataSource) {
        jdbcTemplate = new NamedParameterJdbcTemplate(dataSource);
    }

    @Override
    public List<Map<String, Object>> selectDiagram() {
        String sql = "SELECT MAIN_NAME AS KEY_NAME, '' AS MAIN_NAME, MAIN_NAME AS SUB_NAME, SUB_CODE, REL_NAME FROM RELATION_GROUP WHERE MAIN_NAME NOT IN (SELECT SUB_NAME FROM RELATION_GROUP) GROUP BY KEY_NAME " +
                "UNION ALL " +
                "SELECT SUB_NAME AS KEY_NAME, MAIN_NAME, SUB_NAME, SUB_CODE, REL_NAME FROM RELATION_GROUP";

        MapSqlParameterSource params = new MapSqlParameterSource();

        return jdbcTemplate.query(sql, params, itemGroupMapper());
    }

    private RowMapper<Map<String, Object>> itemGroupMapper() {
        return (rs, rowNum) -> {
            Map<String, Object> map = new HashMap<String, Object>();

            map.put("key", rs.getString("KEY_NAME"));
            map.put("parent", rs.getString("MAIN_NAME"));
            map.put("name", rs.getString("SUB_NAME"));

            map.put("code", rs.getString("SUB_CODE"));

            map.put("to", rs.getString("KEY_NAME"));
            map.put("from", rs.getString("MAIN_NAME"));
            map.put("text", rs.getString("REL_NAME"));

            return map;
        };
    }

    @Override
    public List<Map<String, Object>> selectRelationGroups(Map<String, Object> data) {
        String sql = "SELECT IDX, MAIN_CODE, MAIN_NAME, SUB_CODE, SUB_NAME, REL_NAME, RGST_DTTM, CHNG_DTTM  FROM RELATION_GROUP ORDER BY MAIN_NAME ASC, SUB_NAME ASC";

        MapSqlParameterSource params = new MapSqlParameterSource();

        return jdbcTemplate.query(sql, params, relationGroupMapper());
    }

    private RowMapper<Map<String, Object>> relationGroupMapper() {
        return (rs, rowNum) -> {

            String mainCode = rs.getString("MAIN_CODE");

            if(!"".equals(mainCode) && mainCode != null) mainCode = "[" + mainCode + "]";

            Map<String, Object> map = new HashMap<String, Object>();
            map.put("idx", rs.getString("IDX"));
            map.put("mainCode", mainCode);
            map.put("mainName", rs.getString("MAIN_NAME"));
            map.put("subCode", rs.getString("SUB_CODE"));
            map.put("subName", rs.getString("SUB_NAME"));
            map.put("relName", rs.getString("REL_NAME"));
            map.put("rgstDttm", rs.getString("RGST_DTTM"));
            map.put("chngDttm", rs.getString("CHNG_DTTM"));
            return map;
        };
    }

    @Override
    public void saveRelationGroup(Map<String, Object> data) throws MessageException, IOException {

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idx", UUID.randomUUID().toString());
        params.addValue("mainCode", data.get("mainCode"));
        params.addValue("mainName", data.get("mainName"));
        params.addValue("subCode", data.get("subCode"));
        params.addValue("subName", data.get("subName"));
        params.addValue("relName", data.get("relName"));
        
        String existSql = "SELECT COUNT(1) FROM RELATION_GROUP WHERE MAIN_NAME = :mainName AND SUB_NAME = :subName";
        int cnt = jdbcTemplate.queryForObject(existSql, params, Integer.class);

        if(cnt > 0) {
            throw new MessageException("이미 등록되어 있습니다.");
        }

        String sql = "INSERT INTO RELATION_GROUP(IDX, MAIN_CODE, MAIN_NAME, SUB_CODE, SUB_NAME, REL_NAME) VALUES(:idx, :mainCode, :mainName, :subCode, :subName, :relName)";
        int result = jdbcTemplate.update(sql, params);
    }

    @Override
    public void deleteRelationGroup(Map<String, Object> data) throws MessageException, IOException {
        String sql = "DELETE FROM RELATION_GROUP WHERE IDX = :idx";

        MapSqlParameterSource params = new MapSqlParameterSource();
        params.addValue("idx", data.get("idx"));

        int r = jdbcTemplate.update(sql, params);

        if(r <= 0) {
            throw new MessageException("삭제에 실패하였습니다.");
        }
    }

}
