package ru.azor;

import java.sql.*;
import java.util.Random;

public class DataBaseService {
    private static final String DB_URL = "jdbc:sqlite:words.db";
    private static final String GET_WORD_REQUEST = "SELECT word FROM words WHERE id = ?";
    private static final String GET_NUMBER_OF_ROWS_REQUEST = "SELECT count(*) FROM words";
    private static final String SET_WORD = "INSERT INTO words (id, word) VALUES (?, ?)";
    private static Connection connection;
    private static final Random random = new Random();
    private static PreparedStatement getNumberOfRowsStatement;
    private static PreparedStatement getWordStatement;
    private static PreparedStatement setWord;

    private DataBaseService(){
    }

    public static void setPrepareStatements() {
        try {
            getNumberOfRowsStatement = connection.prepareStatement(GET_NUMBER_OF_ROWS_REQUEST);
            getWordStatement = connection.prepareStatement(GET_WORD_REQUEST);
            setWord = connection.prepareStatement(SET_WORD);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void createConnection(){
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(DB_URL);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static String getWord() {
        String word = null;
        try {
            int count = getCountRows();
            int rand = random.nextInt(count) + 1;
            getWordStatement.setInt(1, rand);
            ResultSet wordSet = getWordStatement.executeQuery();
            while (wordSet.next()){
                word = wordSet.getString("word");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return word;
    }

    private static int getCountRows() throws SQLException {
        ResultSet rowsSet = getNumberOfRowsStatement.executeQuery();
        return rowsSet.getInt("count(*)");
    }

    public static void setWord(String word) {
        try {
            int id = getCountRows() +1;
            setWord.setInt(1, id);
            setWord.setString(2, word);
            setWord.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void closeConnection() {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
