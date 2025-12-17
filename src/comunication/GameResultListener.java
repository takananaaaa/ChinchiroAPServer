package comunication;

public interface GameResultListener {
    void onGameUpdate(
            String name,
            int bananas,
            String handName,
            String result,
            String hand,
            String phase
    );
}
