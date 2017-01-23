package mickael.koc.channelmessaging2;

/**
 * Created by kocm on 20/01/2017.
 */
public class Jsonlogin {
    private String response;
    private String code;
    private String accesstoken;

    Jsonlogin()
    {}

    public String getaccess()
    {
        return this.accesstoken.toString();
    }
}
