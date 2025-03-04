package ca.cal.tp2.repository;

import java.sql.*;
import java.util.List;

public abstract class ParentRepository<T> implements IBaseRepository<T> {
    protected static final String JDBC_DRIVER = "org.h2.Driver";
    protected static final String DB_URL = "jdbc:h2:mem:TP2;DB_CLOSE_DELAY=-1";
    protected static final String USER = "sa";
    protected static final String PASS = "1";

    public ParentRepository() {
        try {
            Class.forName(JDBC_DRIVER);
            createTables();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    protected Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DB_URL, USER, PASS);
    }

    protected abstract void createTables();

    protected boolean executeUpdate(String sql) {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    protected boolean executeBatch(List<String> sqlStatements) {
        Connection conn = null;
        Statement stmt = null;

        try {
            conn = getConnection();
            conn.setAutoCommit(false);
            stmt = conn.createStatement();

            for (String sql : sqlStatements) {
                stmt.addBatch(sql);
            }

            stmt.executeBatch();
            conn.commit();
            return true;
        } catch (SQLException e) {
            try {
                if (conn != null) conn.rollback();
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
            e.printStackTrace();
            return false;
        } finally {
            try {
                if (stmt != null) stmt.close();
                if (conn != null) {
                    conn.setAutoCommit(true);
                    conn.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Abstract method to save an entity to the database.
     *
     * @param entity The entity to save
     * @return true if save succeeded, false otherwise
     */
    @Override
    public abstract boolean save(T entity);

    /**
     * Abstract method to retrieve an entity by ID.
     *
     * @param id The ID of the entity to retrieve
     * @return The entity if found, null otherwise
     */
    @Override
    public abstract T get(Long id);

    /**
     * Abstract method to retrieve all entities.
     *
     * @return A list of all entities
     */
    @Override
    public abstract List<T> findAll();

    /**
     * Abstract method to update an entity.
     *
     * @param entity The entity to update
     * @return true if update succeeded, false otherwise
     */
    @Override
    public abstract boolean update(T entity);
}