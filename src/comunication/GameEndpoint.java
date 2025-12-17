package comunication;

import com.google.gson.Gson;
import control.ApplicationServerController;
import entity.Player;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;

import java.util.ArrayList;
import java.util.List;

/**
 * WebSocket 受信専用クラス
 * ・受信のみ担当
 * ・送信はしない
 */
@ServerEndpoint("/game")
public class GameEndpoint {

    private static final Gson gson = new Gson();

    private ApplicationServerController controller;

    // =========================
    // 接続開始
    // =========================
    @OnOpen
    public void onOpen(Session session) {
        System.out.println("接続開始: " + session.getId());

        // 仮プレイヤー生成（本番ではログイン情報を使う）
        List<Player> players = new ArrayList<>();
        players.add(new Player(1, "player1", 1000, false));

        controller = new ApplicationServerController(players);
        controller.startGame(); // ゲーム開始
    }

    // =========================
    // クライアントから受信
    // =========================
    @OnMessage
    public void onMessage(String message) {
        System.out.println("受信: " + message);

        ClientMessage msg = gson.fromJson(message, ClientMessage.class);

        // 受信内容に応じて Controller を呼ぶだけ
        switch (msg.action) {
            case "ROLL":
                controller.startDicePhase();
                break;

            case "BET":
                // 今回は未実装（将来拡張用）
                break;

            default:
                System.out.println("未知のアクション: " + msg.action);
        }
    }

    @OnClose
    public void onClose(Session session) {
        System.out.println("切断: " + session.getId());
    }

    @OnError
    public void onError(Session session, Throwable error) {
        error.printStackTrace();
    }

    // =========================
    // 受信用 JSON クラス
    // =========================
    private static class ClientMessage {
        String action; // "ROLL", "BET" など
    }
}
