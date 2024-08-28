package lk.ijse.model;

import lk.ijse.dto.ScoreDto;
import lk.ijse.util.SQLUtil;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ScoreModel {
    public boolean save(ScoreDto dto) throws SQLException, ClassNotFoundException {
        return SQLUtil.execute("Insert into score values (?,?)",
                dto.getDate(),
                dto.getScore()
        );

    }
    public ArrayList<ScoreDto> getAll() throws SQLException, ClassNotFoundException {

        ResultSet resultSet = SQLUtil.execute("SELECT * FROM score");
        ArrayList<ScoreDto> scores = new ArrayList<>();

        while (resultSet.next()){
            ScoreDto dto = new ScoreDto(
                    resultSet.getDate(1).toLocalDate(),
                    resultSet.getInt(2)
            );
            scores.add(dto);
        }
        return scores;
    }
}
