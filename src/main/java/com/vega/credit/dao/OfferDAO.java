package com.vega.credit.dao;

import com.vega.credit.enums.LimitType;
import com.vega.credit.enums.OfferStatus;
import com.vega.credit.model.Offer;
import com.vega.credit.server.SQL;
import com.vega.credit.util.DateTimeUtil;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static com.vega.credit.constants.DBConstants.OFFER_TABLE_NAME;

@Repository
public class OfferDAO {
    private final Statement statement;

    private final String tableName = OFFER_TABLE_NAME;

    public OfferDAO() throws SQLException {
        SQL sql = new SQL();
        Connection connection = sql.getDBConnection();
        this.statement = connection.createStatement();
    }


    public List<Offer> getActiveOffers(String accountId, LocalDate activationDate) {
        ResultSet resultSet = null;
        try {
            List<Offer> activeOffers = new ArrayList<>();
            String query = String.format("select * from %s where account_id = '%s' and " +
                    "offer_activation_time = '%s';", tableName, accountId, activationDate.toString());
            resultSet = statement.executeQuery(query);

            while(resultSet.next()) {
                LocalDate retrievedActivationDate =
                        DateTimeUtil.stringToLocalDate(resultSet.getString("offer_activation_time"));
                LocalDate retrievedExpiryDate =
                        DateTimeUtil.stringToLocalDate(resultSet.getString("offer_expiry_time"));
                /* Shows only live offers */
                if(activationDate.isBefore(retrievedActivationDate) || activationDate.isAfter(retrievedExpiryDate)) {
                    continue;
                }

                Offer activeOffer = Offer.builder().
                        accountId(accountId).
                        offerId(resultSet.getString("offer_id")).
                        limitType(LimitType.valueOf(resultSet.getString("limit_type"))).
                        newLimit(resultSet.getInt("new_limit")).
                        offerActivationTime(resultSet.getString("offer_activation_time")).
                        offerExpiryTime(resultSet.getString("offer_expiry_time")).
                        offerStatus(OfferStatus.valueOf(resultSet.getString("offer_status"))).
                        build();
                activeOffers.add(activeOffer);
            }
            return activeOffers;
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public void addOffer(Offer offer) {
        try {
            String insertQuery =
                    String.format("insert into %s(account_id, offer_id, limit_type, new_limit, offer_activation_time, " +
                                    "offer_expiry_time, offer_status) values('%s','%s','%s','%s','%s','%s','%s');",
                            tableName, offer.getAccountId(), offer.getOfferId(), offer.getLimitType().getLimitType(),
                            offer.getNewLimit(), offer.getOfferActivationTime(), offer.getOfferExpiryTime(),
                            offer.getOfferStatus().getOfferStatus());
            statement.executeUpdate(insertQuery);
            System.out.println("Row Inserted");
        } catch (Exception e) {
            System.out.printf("Insertion in db fails for account id: %s", offer.getAccountId());
            throw new RuntimeException(e.getMessage());
        }
    }

    public void modifyOfferStatus(String offerId, OfferStatus offerStatus) {
        try {
            String updateQuery =  String.format("update %s set offer_status='%s' where offer_id='%s'",
                    tableName, offerStatus.getOfferStatus(), offerId);
            statement.executeUpdate(updateQuery);
            System.out.println("Status Updated");
        } catch (Exception e){
            System.out.printf("Status cannot be updated for offer id: %s", offerId);
            throw new RuntimeException(e.getMessage());
        }
    }

    public String getOfferDetails(String offerId, String fieldName) {
        ResultSet resultSet = null;
        try {
            String query = String.format("select '%s' from %s where offer_id = '%s';", fieldName, tableName, offerId);
            resultSet = statement.executeQuery(query);
            if(resultSet.next()) {
                return resultSet.getString(fieldName);
            }
            return null;
        } catch (SQLException e) {
            throw new RuntimeException(e.getMessage());
        }
    }
}
