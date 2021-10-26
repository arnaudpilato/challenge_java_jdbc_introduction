package com.wildcodeschool.challengeJDBCIntroduction.repository;

import com.wildcodeschool.challengeJDBCIntroduction.entity.Peoples;
import com.wildcodeschool.challengeJDBCIntroduction.util.JdbcUtils;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PeoplesRepository implements CrudDao<Peoples> {
    private final static String DB_URL = "jdbc:mysql://localhost:3306/challenge_jdbc_introduction?serverTimezone=GMT";
    private final static String DB_USER = "h4rryp0tt3r";
    private final static String DB_PASSWORD = "Horcrux4life!";

    @Override
    public Peoples save(Peoples people) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );

            statement = connection.prepareStatement(
                    "INSERT INTO peoples (firstName, lastName,  age, country) VALUES (?, ?, ?, ?)",
                    Statement.RETURN_GENERATED_KEYS
            );

            statement.setString(1, people.getFirstName());
            statement.setString(2, people.getLastName());
            statement.setLong(3, people.getAge());
            statement.setString(4, people.getCountry());

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to insert data");
            }

            resultSet = statement.getGeneratedKeys();

            if (resultSet.next()) {
                Long id = resultSet.getLong(1);
                people.setId(id);
                return people;
            } else {
                throw new SQLException("failed to get inserted id");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    @Override
    public Peoples findById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );

            statement = connection.prepareStatement(
                    "SELECT * FROM peoples WHERE id = ?"
            );

            statement.setLong(1, id);

            resultSet = statement.executeQuery();

            if (resultSet.next()) {
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                Long age = resultSet.getLong("age");
                String country = resultSet.getString("country");
                return new Peoples(id, firstName, lastName, age, country);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeResultSet(resultSet);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeConnection(connection);
        }
        return null;
    }

    @Override
    public List<Peoples> findAll() {
        Connection connection = null;
        PreparedStatement statement = null;
        ResultSet resultSet = null;

        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );

            statement = connection.prepareStatement(
                    "SELECT * FROM peoples"
            );

            resultSet = statement.executeQuery();

            List<Peoples> peoples = new ArrayList<>();

            while (resultSet.next()) {
                Long id = resultSet.getLong("id");
                String firstName = resultSet.getString("firstName");
                String lastName = resultSet.getString("lastName");
                Long age = resultSet.getLong("age");
                String country = resultSet.getString("country");
                peoples.add(new Peoples(id, firstName, lastName, age, country));
            }

            return peoples;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(connection);
            JdbcUtils.closeStatement(statement);
            JdbcUtils.closeResultSet(resultSet);
        }
        return null;
    }

    @Override
    public Peoples update(Peoples peoples) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );

            statement = connection.prepareStatement(
                    "UPDATE peoples SET firstName = ?, lastName = ?, age = ?, country = ? WHERE id = ?"
            );

            statement.setString(1, peoples.getFirstName());
            statement.setString(2, peoples.getLastName());
            statement.setLong(3, peoples.getAge());
            statement.setString(4, peoples.getCountry());
            statement.setLong(5, peoples.getId());

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to update data");
            }

            return peoples;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(connection);
            JdbcUtils.closeStatement(statement);
        }
        return null;
    }

    @Override
    public void deleteById(Long id) {
        Connection connection = null;
        PreparedStatement statement = null;

        try {
            connection = DriverManager.getConnection(
                    DB_URL, DB_USER, DB_PASSWORD
            );

            statement = connection.prepareStatement(
                    "DELETE FROM peoples WHERE id = ?"
            );

            statement.setLong(1, id);

            if (statement.executeUpdate() != 1) {
                throw new SQLException("failed to delete data");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            JdbcUtils.closeConnection(connection);
            JdbcUtils.closeStatement(statement);
        }
    }
}
