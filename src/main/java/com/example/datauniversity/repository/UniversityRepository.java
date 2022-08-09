package com.example.datauniversity.repository;

import com.example.datauniversity.model.University;

import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class UniversityRepository {



    public void saveAll(List<University> universities) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:file:./src/main/resources/data/dataUniversity;AUTO_SERVER=true", "sa", "")) {
            String sql = "INSERT INTO Data_University (State, NameUniversity, InstitutionType, PhoneNumber, Website) VALUES ('";
            Statement statement = connection.createStatement();
            int i = 0;
            for (University university : universities) {
                i++;
                String checkString = "SELECT NAMEUNIVERSITY FROM DATA_UNIVERSITY WHERE NAMEUNIVERSITY='" + university.getNameUniversity() +"';";
                ResultSet resultSet = statement.executeQuery(checkString);
                if (!resultSet.next()) {
                    String resultSql = sql + university.getState() + "', '" + university.getNameUniversity() + "', '" + university.getInstitutionType() + "', '" + university.getPhoneNumber() + "', '" + university.getWebsite() + "');";
                    statement.executeUpdate(resultSql);
                }
            }
            statement.close();
        } catch (Exception e) {
            throw new RuntimeException("exception added universities in database: ", e);
        }
    }

    public List<University> findAll() {
        List<University> universities = new ArrayList<>();
        try (Connection connection = DriverManager.getConnection("jdbc:h2:file:./src/main/resources/data/dataUniversity;AUTO_SERVER=true", "sa", "")) {
            Statement statement = connection.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM DATA_UNIVERSITY");
            while (resultSet.next()) {
                University university = new University(resultSet.getString(2),resultSet.getString(1),resultSet.getString(3),resultSet.getString(4),resultSet.getString(5));
                universities.add(university);
            }
            statement.close();
            return universities;
        } catch (Exception e) {
            throw new RuntimeException("exception find universities in database: ", e);
        }
    }
}
