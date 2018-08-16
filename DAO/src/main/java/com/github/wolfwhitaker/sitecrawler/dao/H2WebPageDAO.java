package com.github.wolfwhitaker.sitecrawler.dao;

import com.github.wolfwhitaker.sitecrawler.dao.dto.WebPage;
import com.github.wolfwhitaker.sitecrawler.dao.exception.DAOException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class H2WebPageDAO implements WebPageDAO {



    /* Private constants */

    private static final String SQL_FIND_BY_ID =
            "SELECT id, content, plain_text FROM web_pages WHERE id = ?";
    private static final String SQL_LIST_ORDER_BY_ID =
            "SELECT id, content, plain_text FROM web_pages ORDER BY id";
    private static final String SQL_SEARCH_LIST =
            "SELECT id, content, plain_text FROM web_pages WHERE " +
                    "to_tsvector('simple', plain_text) @@ plainto_tsquery('simple', ?);";
    private static final String SQL_INSERT =
            "INSERT INTO web_pages (content, plain_text) VALUES (?, ?)";
    private static final String SQL_UPDATE =
            "UPDATE web_pages SET content = ?, plain_text = ? WHERE id = ?";
    private static final String SQL_DELETE =
            "DELETE FROM web_pages WHERE id = ?";
    private static final String SQL_CLEAR_TABLE = "TRUNCATE ONLY web_pages";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS web_pages " +
            "(id         BIGSERIAL PRIMARY KEY NOT NULL," +
            " content    TEXT                  NOT NULL," +
            " plain_text TEXT                  NOT NULL)";


    /* Methods */

    /**
     * @see WebPageDAO#find(Long).
     */
    public WebPage find(Long id) throws DAOException {
        return find(SQL_FIND_BY_ID, id);
    }

    /**
     * @see WebPageDAO#listById().
     */
    public List<WebPage> listById() throws DAOException {
        List<WebPage> pages = new ArrayList<>();

        try (
                Connection con = H2DAOFactory.getConnection();
                PreparedStatement stmt = con.prepareStatement(SQL_LIST_ORDER_BY_ID);
                ResultSet rs = stmt.executeQuery()
        ){
            while (rs.next()) {
                pages.add(map(rs));
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }

        return pages;
    }

    /**
     * @see WebPageDAO#searchList(String).
     */
    public List<WebPage> searchList(String searchQuery) throws DAOException {
        List<WebPage> webPages = new ArrayList<>();

        try (
                Connection con = H2DAOFactory.getConnection();
                PreparedStatement stmt = prepareStatement(con, SQL_SEARCH_LIST,
                        false, searchQuery);
                ResultSet rs = stmt.executeQuery()
        ){
            while (rs.next()) {
                webPages.add(map(rs));
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }

        return webPages;
    }

    /**
     * @see WebPageDAO#create(WebPage).
     */
    public void create(WebPage page) throws IllegalArgumentException, DAOException {
        if (page.getId() != null) {
            throw new IllegalArgumentException("The page is already created, the page ID is not null.");
        }

        Object[] values = {
                page.getContent(),
                page.getPlainText()
        };

        try (
                Connection con = H2DAOFactory.getConnection();
                PreparedStatement stmt = prepareStatement(con, SQL_INSERT, true, values)
        ){
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("The WebPage Creating failed, no rows affected.");
            }

            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    page.setId(generatedKeys.getLong(1));
                } else {
                    throw new DAOException("The WebPage Creating failed, no generated key obtained");
                }
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * @see WebPageDAO#update(WebPage).
     */
    public void update(WebPage page) throws IllegalArgumentException, DAOException {
        if (page.getId() == null) {
            throw new IllegalArgumentException("The WebPage is not created yet, the WebPage ID is null");
        }

        Object[] values = {
                page.getContent(),
                page.getPlainText()
        };

        try (
                Connection con = H2DAOFactory.getConnection();
                PreparedStatement stmt = prepareStatement(con, SQL_UPDATE, false, values)
        ){
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("WebPage updating failed, no rows affected.");
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * @see WebPageDAO#delete(WebPage).
     */
    public void delete(WebPage page) throws DAOException {
        Object[] values = {
                page.getId()
        };

        try (
                Connection con = H2DAOFactory.getConnection();
                PreparedStatement stmt = prepareStatement(con, SQL_DELETE, false, values)
        ){
            int affectedRows = stmt.executeUpdate();
            if (affectedRows == 0) {
                throw new DAOException("The WebPage deleting failed, no rows affected.");
            } else {
                page.setId(null);
            }
        } catch (SQLException ex) {
            throw new DAOException(ex);
        }
    }

    /**
     * Returns the WebPage from the database matching the given SQL query with the given values.
     * @param sql The SQL query to be executed in the database.
     * @param values The PreparedStatement values to be set.
     * @return The WebPage from the database matching the given SQL query with the given values.
     * @throws DAOException If something fails at database level.
     */
    private WebPage find(String sql, Object... values) throws DAOException {
        WebPage webPage = null;

        try (
                Connection con = H2DAOFactory.getConnection();
                PreparedStatement stmt = prepareStatement(con, sql, false, values);
                ResultSet rs = stmt.executeQuery()
        ){
            if (rs.next()) {
                webPage = map(rs);
            }
        } catch (SQLException ex) {
            throw new DAOException("The WebPage object searching failed: " + ex);
        }

        return webPage;
    }

    /**
     * @see WebPageDAO#clearTable().
     */
    public void clearTable() {
        try (
                Connection con = H2DAOFactory.getConnection();
                PreparedStatement stmt = con.prepareStatement(SQL_CLEAR_TABLE)
        ){
            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException("The table clearing failed: " + ex);
        }
    }

    /**
     * @see WebPageDAO#createTable().
     */
    public void createTable() {
        try (
                Connection con = H2DAOFactory.getConnection();
                PreparedStatement stmt = con.prepareStatement(SQL_CREATE_TABLE)
        ){
            stmt.executeUpdate();

        } catch (SQLException ex) {
            throw new DAOException("The table creation failed: " + ex);
        }
    }

    /**
     * Returns a PreparedStatement of the given connection, set with the given SQL query and the
     * given parameter values.
     * @param con The Connection to create the PreparedStatement from.
     * @param sql The SQL query to construct the PreparedStatement with.
     * @param returnGeneratedKeys Set whether to return generated keys or not.
     * @param values The parameter values to be set in the created PreparedStatement.
     * @throws SQLException If something fails during creating the PreparedStatement.
     */
    private static PreparedStatement prepareStatement(Connection con, String sql,
                                                      boolean returnGeneratedKeys, Object... values)
            throws SQLException {
        PreparedStatement stmt = con.prepareStatement(sql,
                returnGeneratedKeys ? Statement.RETURN_GENERATED_KEYS : Statement.NO_GENERATED_KEYS);
        setValues(stmt, values);
        return stmt;
    }

    /**
     * Set the given parameter values in the given PreparedStatement.
     * @param stmt The PreparedStatement to set in the created PreparedStatement.
     * @param values The parameter values to be set in the created PreparedStatement.
     * @throws SQLException If something fails during setting the PreparedStatement values.
     */
    private static void setValues(PreparedStatement stmt, Object... values) throws SQLException {
        for (int i = 0; i < values.length; i++) {
            stmt.setObject(i + 1, values[i]);
        }
    }

    /**
     * Map the current entry of the given ResultSet to a WebPage object.
     * @param rs The ResultSet of which the current entry is to be mapped to a WebPage object.
     * @return The mapped WebPage from the current row of the given ResultSet.
     * @throws SQLException If something fails at database level.
     */
    private static WebPage map(ResultSet rs) throws SQLException {
        WebPage webPage = new WebPage();
        webPage.setId(rs.getLong("id"));
        webPage.setContent(rs.getString("content"));
        webPage.setPlainText(rs.getString("plain_text"));
        return webPage;
    }

}
