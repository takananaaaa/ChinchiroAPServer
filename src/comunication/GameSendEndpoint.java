package comunication;

import com.google.gson.Gson;
import control.ApplicationServerController;
import control.GamePhase;
import websocket.message.ServerMessage;

import jakarta.websocket.Session;
import java.io.IOException;

/**
 * 送信専用クラス
 * ・WebSocket Session に JSON を送るだけ
 */
public class GameSendEndpoint implements GameSendListener {

    private final Session session;
    private static final Gson gson = new Gson();

    public GameSendEndpoint(Session session) {
        this.session = session;
    }

    @Override
    public void sendUpdate(
            String name,
            int bananas,
            String result,
            String hand,
            GamePhase phase
    ) {

        ServerMessage msg = new ServerMessage();
        msg.name = name;
        msg.bananas = bananas;
        msg.result = result;
        msg.hand = hand;
        msg.phase = phase.name();

        try {
            session.getBasicRemote().sendText(gson.toJson(msg));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void onOpen(Session session) {

        controller = new ApplicationServerController(players);
        controller.setSendListener(new GameSendEndpoint(session));
        controller.startGame();
    }

}
