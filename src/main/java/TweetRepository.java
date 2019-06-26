import com.datastax.driver.core.ResultSet;
import com.datastax.driver.core.Row;
import com.datastax.driver.core.Session;
import java.util.ArrayList;
import java.util.List;
import com.datastax.driver.core.LocalDate;


public class TweetRepository {

    private static final String TABLE_NAME = "tweets";

    private static final String TABLE_NAME_BY_TITLE = TABLE_NAME + "ByDate";

    private Session session;

    public TweetRepository(Session session)
    {
        this.session = session;
    }

    public void createTable()
    {
        StringBuilder sb = new StringBuilder("CREATE TABLE IF NOT EXISTS ")
                .append(TABLE_NAME).append("(")
                .append("tweetDate date PRIMARY KEY,")
                .append("tweetUsername text,")
                .append("tweetText text);");

        final String query = sb.toString();
        session.execute(query);
    }

    public void insertTweet(Tweet tw)
    {
        StringBuilder sb = new StringBuilder("INSERT INTO ")
                .append(TABLE_NAME).append("(tweetDate, tweetUsername, tweetText) ")
                .append("VALUES ('").append(tw.getTweetDate()).append("', '")
                .append(tw.getUsername()).append("', '")
                .append(tw.getTweetText()).append("');");

        final String query = sb.toString();
        session.execute(query);
    }

    public List<Tweet> selectAll() {
        StringBuilder sb = new StringBuilder("SELECT * FROM ").append(TABLE_NAME);

        final String query = sb.toString();
        ResultSet rs = session.execute(query);

        List<Tweet> tweets = new ArrayList<Tweet>();

        for (Row r : rs) {
            Tweet twt = new Tweet(r.getDate("tweetDate"), r.getString("tweetUsername"), r.getString("tweetText"));
            System.out.println("Tweet = " + r.getDate("tweetDate") + ", "
                    + r.getString("tweetUsername") + ", "
                    + r.getString("tweetText"));

            tweets.add(twt);
        }

        return tweets;
    }

    public void deleteTweetByDate(LocalDate dt)
    {
        StringBuilder sb = new StringBuilder("DELETE FROM ")
                .append(TABLE_NAME)
                .append(" WHERE tweetDate = '")
                .append(dt.toString()).append("';");

        final String query = sb.toString();
        session.execute(query);
    }

    public void deleteTable(String tableName)
    {
        StringBuilder sb = new StringBuilder("DROP TABLE IF EXISTS ").append(tableName);

        final String query = sb.toString();
        session.execute(query);
    }
}
