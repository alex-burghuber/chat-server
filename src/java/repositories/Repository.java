package repositories;

/**
 * @author Alexander Burghuber
 */
public class Repository {

    private static Repository ourInstance = new Repository();
    public static Repository getInstance() {
        return ourInstance;
    }

    private Repository() {
    }


}
