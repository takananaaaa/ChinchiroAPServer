package comunication;

import control.GamePhase;

public interface GameSendListener {

    void sendUpdate(
            String name,
            int bananas,
            String result,
            String hand,
            GamePhase phase
    );
}