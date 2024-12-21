package ibs.steps;

import io.cucumber.java.ru.Допустим;
import io.cucumber.java.ru.И;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.sql.*;

public class addNewProduct {
    static Connection connection;
    static ResultSet resultSet;
    static Statement statement;

    @Допустим("К стенду подключена БД")
    public void к_стенду_подключена_бд() throws SQLException {
        connection = DriverManager.getConnection("jdbc:h2:tcp://localhost:9092/mem:testdb","user","pass");
        statement = connection.createStatement();
    }

    @Допустим("Товара с названием {string} в таблице не существует")
    public void товара_с_названием_в_таблице_не_существует(String string) throws SQLException {
        resultSet = statement.executeQuery("SELECT COUNT(*) FROM FOOD WHERE FOOD_NAME = '" + string + "'");
        if (resultSet.next()) {
            if(resultSet.getInt("COUNT(*)") != 0){
                throw new IllegalStateException("Такой товар существует");
            }
        }
    }

    @И("Проверить существование товара {string} в БД")
    public void проверить_существование_в_бд(String string) throws SQLException {
        resultSet = statement.executeQuery("SELECT COUNT(*) AS count_name FROM FOOD \n" +
                "WHERE FOOD_NAME = '" + string + "' AND FOOD_TYPE = 'FRUIT' AND FOOD_EXOTIC ='1.00'");
        if (resultSet.next()) {
            if(resultSet.getInt("count_name") != 1){
                throw new IllegalStateException("Количество " + string + " больше 1");
            }
        }
    }

    @И("Удаление товара {string} через запрос в БД")
    public void удаление_товара_через_запрос_в_бд(String string) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM FOOD WHERE FOOD_NAME = '" + string + "'");
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println(string + " удален");
        } else {
            System.out.println("Что-то пошло не так");
        }
    }

    @И("Проверить отсутствие {string} через запрос в БД")
    public void проверить_отсутствие_в_бд(String string) throws SQLException {
        resultSet = statement.executeQuery("SELECT COUNT(*) FROM FOOD WHERE FOOD_NAME = '" + string + "';");
        if (resultSet.next()) {
            if(resultSet.getInt("COUNT(*)") != 0){
                throw new IllegalStateException(string + " все еще в таблице");
            }
        }

        resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM FOOD");
        if (resultSet.next()) {
            if(resultSet.getInt("row_count") != 4){
                throw new IllegalStateException("Таблица не в начальном состоянии");
            }
        }
    }

    @Допустим("Товар с названием {string} в таблице существует")
    public void товар_с_названием_в_таблице_существует(String string) throws SQLException {
        resultSet = statement.executeQuery("SELECT COUNT(*) FROM FOOD WHERE FOOD_NAME = '" + string + "'");
        while (resultSet.next()) {
            if(resultSet.getInt("COUNT(*)") == 0){
                throw new IllegalStateException("Такого товара не существует");
            }
        }
    }

    @И("Проверить существование нескольких товаров {string} в БД")
    public void проверить_существование_нескольких_в_бд(String string) throws SQLException {
        resultSet = statement.executeQuery("SELECT COUNT(*) AS count_name FROM FOOD \n" +
                "WHERE FOOD_NAME = '" + string + "' AND FOOD_TYPE = 'FRUIT' AND FOOD_EXOTIC ='1.00'");
        if (resultSet.next()) {
            if(resultSet.getInt("count_name") < 2){
                throw new IllegalStateException("Количество " + string + " меньше 2");
            }
        }
    }

    @И("Удаление последнего товара {string} через запрос в БД")
    public void удаление_последнего_товара_через_запрос_в_бд(String string) throws SQLException {
        PreparedStatement ps = connection.prepareStatement("DELETE FROM FOOD WHERE FOOD_ID = (SELECT \n" +
                "MAX(FOOD_ID) FROM FOOD) AND FOOD_NAME = 'Апельсин'");
        int rowsAffected = ps.executeUpdate();
        if (rowsAffected > 0) {
            System.out.println("Последний добавленный " + string + " удален");
        } else {
            System.out.println("Что-то пошло не так");
        }
    }

    @И("Проверить отсутствие последнего добавленного {string} через запрос в БД")
    public void проверить_последнего_добавленного_отсутствие_в_бд(String string) throws SQLException {
        resultSet = statement.executeQuery("SELECT COUNT(*) AS row_count FROM FOOD");
        if (resultSet.next()) {
            if(resultSet.getInt("row_count") != 4){
                throw new IllegalStateException("Таблица не в начальном состоянии");
            }
        }
    }
}
