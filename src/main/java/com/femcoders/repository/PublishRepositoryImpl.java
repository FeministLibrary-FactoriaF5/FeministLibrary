package com.femcoders.repository;

import com.femcoders.config.DBManager;
import com.femcoders.model.Publisher;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PublishRepositoryImpl implements PublisherRepository {

    Connection connection;
    PreparedStatement statement;

    @Override
    public Publisher createPublisher(Publisher publisher) {
        String sql = "INSERT INTO publishers (name) VALUES (?)";

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);

            statement.setString(1, publisher.getName());
            statement.executeUpdate();

            ResultSet generatedKeys = statement.getGeneratedKeys();
            if (generatedKeys.next()) {
                publisher.setId(generatedKeys.getInt(1));
            }

            System.out.println("Publisher created successfully.");
            return publisher;
        } catch (Exception e) {
            System.out.println("Publisher creation failed.");
            System.out.println(e.getMessage());
            return null;
        } finally {
            DBManager.closeConnection();
        }
    }

    @Override
    public Publisher readPublisherByName(String name) {
        String sql = "SELECT id, name FROM publishers WHERE LOWER(name) = LOWER(?)";

        try {
            connection = DBManager.getConnection();
            statement = connection.prepareStatement(sql);

            statement.setString(1, name);

            ResultSet resultSetPublishers = statement.executeQuery();

            if (resultSetPublishers.next()) {
                int id = resultSetPublishers.getInt("id");
                String publisherName = resultSetPublishers.getString("name");
                return new Publisher(id, publisherName);
            }

            return null;

        } catch (Exception e) {
            System.out.println("Publisher not found.");
            System.out.println(e.getMessage());
            return null;
        } finally {
            DBManager.closeConnection();{
            }
        }
    }
}
