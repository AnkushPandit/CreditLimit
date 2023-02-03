package com.vega.credit.dao;

import com.vega.credit.enums.LimitType;
import com.vega.credit.model.Account;
import com.vega.credit.server.SQL;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import static com.vega.credit.constants.DBConstants.ACCOUNT_TABLE_NAME;

@Repository
public class AccountDAO {

    private final Statement statement;

    private final String tableName = ACCOUNT_TABLE_NAME;

    public AccountDAO() throws SQLException {
        SQL sql = new SQL();
        Connection connection = sql.getDBConnection();
        this.statement = connection.createStatement();
    }


    public Account getAccount(String accountId) {
        ResultSet resultSet = null;
        try {
            String query = String.format("select * from %s where account_id = '%s';", tableName, accountId);
            resultSet = statement.executeQuery(query);
            if (!resultSet.isBeforeFirst()) {
                return null;
            }
            resultSet.next();
            Account newAccount = Account.builder().
                    accountId(accountId).
                    customerId(resultSet.getString("customer_id")).
                    accountLimit(resultSet.getInt("account_limit")).
                    perTransactionLimit(resultSet.getInt("per_transaction_limit")).
                    lastAccountLimit(resultSet.getInt("last_account_limit")).
                    lastPerTransactionLimit(resultSet.getInt("last_per_transaction_limit")).
                    accountLimitUpdateTime(resultSet.getString("account_limit_update_time")).
                    perTransactionLimitUpdateTime(resultSet.getString("per_transaction_limit_update_time")).
                    build();
            if (resultSet.next()) {
                throw new RuntimeException(
                        String.format("More than one account information for account id: %s",accountId));
            }
            return newAccount;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void addAccount(Account account) {
        try {
            String insertQuery =
                    String.format("insert into %s(account_id, customer_id, account_limit, per_transaction_limit, " +
                            "last_account_limit, last_per_transaction_limit, account_limit_update_time, " +
                            "per_transaction_limit_update_time) values('%s','%s','%s','%s','%s','%s','%s','%s');",
                            tableName, account.getAccountId(), account.getCustomerId(), account.getAccountLimit(),
                            account.getPerTransactionLimit(), account.getLastAccountLimit(),
                            account.getLastPerTransactionLimit(), account.getAccountLimitUpdateTime(),
                            account.getPerTransactionLimitUpdateTime());
            statement.executeUpdate(insertQuery);
            System.out.println("Row Inserted");
        } catch (Exception e) {
            System.out.printf("Insertion in db fails for customer id: %s", account.getCustomerId());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void updateAccountLimit(String accountId, LimitType limitType, int newLimit) {
        try {
            String queryLimitType = limitType.getLimitType().toLowerCase();
            String updateQuery = String.format("update %s set %s=%s;", tableName, queryLimitType, newLimit);
            statement.executeUpdate(updateQuery);
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
