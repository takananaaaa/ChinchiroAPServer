package comunication;

import control.ApplicationServerController;
import entity.Player;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@ServerEndpoint("/game")
public class ClientCommunication implements GameResultListener {

    private static final Gson gson = new Gson();

    private Session session;
    private ApplicationServerController controller;

    @OnOpen
    public void onOpen(Session session) {
        this.session = session;

        // 仮プレイヤー
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "player1", 1000, false));

        controller = new ApplicationServerController(players);
        controller.setGameResultListener(this);

        controller.startGame();
    }

    @OnMessage
    public void onMessage(String msg) {
        // クライアントから「振る」要求が来たら
        controller.startDicePhase();
    }

    // ===== ApplicationServerController からの通知 =====
    @Override
    public void onGameUpdate(
            String name,
            int bananas,
            String handName,
            String result,
            String hand,
            String phase
    ) {
        try {
            ServerMessage m = new ServerMessage();
            m.name = name;
            m.bananas = bananas;
            m.handName = handName;
            m.result = result;
            m.hand = hand;
            m.phase = phase;

            session.getBasicRemote().sendText(gson.toJson(m));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
