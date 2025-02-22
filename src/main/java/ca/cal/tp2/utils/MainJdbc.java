package ca.cal.tp2.utils;

import java.sql.SQLException;
import ca.cal.tp2.JDBCclass;
import ca.cal.tp2.TcpServer;

public class MainJdbc {
    public static void main(String[] args) throws InterruptedException, SQLException {
        TcpServer.startTcpServer();
        JDBCclass.createDatabase();
        JDBCclass.insertRecords();
        JDBCclass.selectRecords();
        JDBCclass.updateRecord();
        JDBCclass.deleteRecord();
        JDBCclass.whereClause();
        JDBCclass.prepareStatement();
        Thread.currentThread().join();
    }
}
