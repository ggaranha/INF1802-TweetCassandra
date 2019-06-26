import com.datastax.driver.core.LocalDate;
import java.util.Date;

public class Tweet {

    private String username;

    private String tweetText;

    private LocalDate tweetDate;
    //private String tweetDate;

    public Tweet()
    {

    }

    public Tweet(LocalDate td, String un, String tt)
    {
        username = un;
        tweetText = tt;
        tweetDate = td;
    }

    public String getTweetText() {
        return tweetText;
    }

    public LocalDate getTweetDate() {
        return tweetDate;
    }

    public String getUsername() {
        return username;
    }

    public void setTweetText(String tt) {
        tweetText = tt;
    }

    public void getTweetDate(LocalDate td) {
        tweetDate = td;
    }

    public void getUsername(String un) {
        username = un;
    }
}