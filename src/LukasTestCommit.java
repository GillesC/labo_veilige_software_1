/**
 * Created by Lukas on 20-Feb-16.
 */
public class LukasTestCommit {
    private static LukasTestCommit ourInstance = new LukasTestCommit();

    public static LukasTestCommit getInstance() {
        return ourInstance;
    }

    private LukasTestCommit() {
        System.out.println("Hallo");
    }
}
