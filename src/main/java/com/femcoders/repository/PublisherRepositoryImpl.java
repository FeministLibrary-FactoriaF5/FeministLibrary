package com.femcoders.repository;

import com.femcoders.config.DBManager;
import com.femcoders.model.Publisher;
import com.femcoders.view.Colors;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

public class PublisherRepositoryImpl implements PublisherRepository {

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

            return publisher;

        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Publisher creation failed: " + e.getMessage() + Colors.RESET);
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
                return new Publisher(
                        resultSetPublishers.getInt("id"),
                        resultSetPublishers.getString("name"));
            }

        } catch (Exception e) {
            System.out.println(Colors.RED + "❌ Error reading publisher by name: " + e.getMessage() + Colors.RESET);

        } finally {
            DBManager.closeConnection();
        }

        return null;
    }

    @Override
    public Publisher validateExistingPublisher(String name) {
        Publisher existingPublisher = readPublisherByName(name);

        if (existingPublisher == null) {
            Publisher newPublisher = new Publisher();
            newPublisher.setName(name);
            Publisher savedPublisher = createPublisher(newPublisher);
            System.out.println(Colors.GREEN + "✅ Publisher not found. A new publisher has been created." + Colors.RESET);
            return savedPublisher;
        }

        System.out.println(Colors.GREEN + "✅ Publisher found: " + existingPublisher.getName() + Colors.RESET);
        return existingPublisher;
    }

    @Override
    public Publisher findById(int id){

        String sql = "SELECT * FROM publishers WHERE id = ?";

         try {
             connection = DBManager.getConnection();
             statement = connection.prepareStatement(sql);
             statement.setInt(1, id);

            ResultSet rs = statement.executeQuery();

            if (rs.next()) {
                return new Publisher(
                        rs.getInt("id"),
                        rs.getString("name"));
            }

        } catch (Exception e) {
             System.out.println(Colors.RED + "❌ Error reading publisher by ID: " + e.getMessage() + Colors.RESET);

        } finally {
             DBManager.closeConnection();
         }

        return null;
    }
}