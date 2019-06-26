import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;

import com.datastax.driver.core.LocalDate;

public class HelloTweet {

    public static void main(String[] args) {
        System.out.println("Hello, Cassandra!");
        Cluster cluster = null;
        try {
            cluster = Cluster.builder()
                    .addContactPoint("localhost")
                    .build();

            Session session = cluster.connect();

            ResultSet rs = session.execute("select release_version from system.local");
            Row row = rs.one();
            System.out.println(row.getString("release_version"));

            KeyspaceRepository sr = new KeyspaceRepository(session);
            sr.createKeyspace("tweetLibrary", "SimpleStrategy", 1);
            System.out.println("Create repository");

            sr.useKeyspace("tweetLibrary");
            System.out.println("Using repository tweetLibrary");

            TweetRepository tr = new TweetRepository(session);
            tr.createTable();
            System.out.println("Create table tweets");

            LocalDate ld1 = LocalDate.fromYearMonthDay(2019, 2, 4);
            Tweet tweet1 = new Tweet(ld1, "UserT", "Get REKT!");
            System.out.println(tweet1.getTweetDate().toString());
            tr.insertTweet(tweet1);
            LocalDate ld2 = LocalDate.fromYearMonthDay(2019, 2, 5);
            Tweet tweet2 = new Tweet(ld2, "UserT1", "Get lost!");
            tr.insertTweet(tweet2);
            LocalDate ld3 = LocalDate.fromYearMonthDay(2019, 2, 7);
            Tweet tweet3 = new Tweet(ld3, "UserT7", "Hello.");
            tr.insertTweet(tweet3);
            LocalDate ld4 = LocalDate.fromYearMonthDay(2019, 2, 12);
            Tweet tweet4 = new Tweet(ld4, "UserJ", "Goodbye.");
            tr.insertTweet(tweet4);
            LocalDate ld5 = LocalDate.fromYearMonthDay(2019, 2, 17);
            Tweet tweet5 = new Tweet(ld5, "UserL", "Yep.");
            tr.insertTweet(tweet5);
            System.out.println("Insert tweets");

            tr.selectAll();

            tr.deleteTweetByDate(LocalDate.fromYearMonthDay(2019, 2, 5));
            tr.deleteTable("tweets");
            System.out.println("Delete tweets");

            sr.deleteKeyspace("tweetLibrary");
            System.out.println("Delete keyspace tweetLibrary");

        } finally {
            if (cluster != null)
                cluster.close();
        }
    }
}