package loginApp;

import dbUtil.dbConnection;
import org.apache.commons.codec.digest.DigestUtils;

import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginModel {

    private Connection connection;

    /**
     * Konstruktor ustanawiający połączenie.
     */
    public LoginModel()
    {
        try {
            connection = dbConnection.getConnection();
        }
        catch (SQLException ex)
        {
            ex.printStackTrace();
        }
        if (connection==null)
            System.exit(1);
    }

    /**
     * Metoda sprawdzająca czy połączenie jest ustanowione.
     * @return wartość logizna. True dla ustanowionego połączenia.
     */
    public boolean isDatabaseConnected()
    {
        return connection != null;
    }

    /**
     * Metoda sprawdzająca czy poświadczenia wprowadzone przez użytkownika są poprawne - czy znajdują się w bazie danych.
     * @param user nazwa użytkownika
     * @param password hasło
     * @param position stanowisko
     * @return wartość logiczna. True jeśli dane są poprawne i znajdują się w bazie. False dla każdego innego przypadku.
     * @throws SQLException
     */
    public boolean isLogin (String user, String password, String position) throws SQLException {

        String sql = "select * from public.pracownik where login = ? and haslo = ? and stanowisko = ?";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user);
            preparedStatement.setString(2, password);
            preparedStatement.setString(3, position);

            ResultSet resultSet = preparedStatement.executeQuery();

            if (resultSet.next())
                return true;
            return false;
        }
        catch (SQLException ex) {
            return false;
        }
    }

    /**
     * Szyfrowanie hasła - działanie identyczne jak funkcja PASSWORD() w SZBD MySQL.
     * Podwójne SHA1. Przy ostatniej zamianie zwracany jest ciąg w którym każdy ze znaków jest
     * zamieniany na szystem szesnastkowy - HEX.
     * Przed utowrzonym ciągiem dodawany jest także znak '*'
     * @param plainText
     * @return hash
     * @throws UnsupportedEncodingException
     */
    public String hashPassword(String plainText) throws UnsupportedEncodingException {
        byte[] utf8 = plainText.getBytes("UTF-8");
        return "*" + DigestUtils.sha1Hex(DigestUtils.sha1(utf8)).toUpperCase();
    }
}
