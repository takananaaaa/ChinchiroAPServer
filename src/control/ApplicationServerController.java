package control;

import comunication.GameResultListener;
import entity.Player;
import entity.Hand;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * アプリケーションサーバ側でゲーム全体を制御するクラス
 */
public class ApplicationServerController {

    private GameResultListener listener;
    private List<Player> players;
    private Player currentDealer;

    private Map<Player, BetController> betControllers;
    private Map<Player, DiceController> diceControllers;

    private Settlement settlement;

    // ===== コンストラクタ =====
    public void setGameResultListener(GameResultListener listener) {
        this.listener = listener;
    }


    public ApplicationServerController(List<Player> players) {
        this.players = players;
        this.currentDealer = players.get(0); // 最初は先頭を親にする

        this.betControllers = new HashMap<>();
        this.diceControllers = new HashMap<>();
        this.settlement = new Settlement();

        // Player ごとに Controller を用意
        for (Player p : players) {
            Timer timer = new Timer(30); // 制限時間30秒
            betControllers.put(p, new BetController(p, timer));
            diceControllers.put(p, new DiceController(p, timer));
        }

        currentDealer.setDealer(true);
    }

    // ==============================
    // ゲーム開始
    // ==============================
    public void startGame() {
        notifyAllPlayers(GamePhase.PLAYING, null, null);
        startBetPhase();
    }
    // ==============================
    // 賭けフェーズ
    // ==============================
    private void startBetPhase() {

        notifyAllPlayers(GamePhase.BET, null, null);

        for (Player p : players) {
            if (p == currentDealer) continue;
            betControllers.get(p).startTimer();
        }
    }

    private void finishBetPhase() {
        notifyAllPlayers(GamePhase.BET_FINISHED, null, null);
        startDicePhase();
    }

    // ==============================
    // サイコロフェーズ
    // ==============================
    public void startDicePhase() {

        notifyAllPlayers(GamePhase.ROLL, null, null);

        for (Player p : players) {

            DiceController dc = diceControllers.get(p);
            dc.startTimer();
            dc.rollDice();

            Hand hand = dc.judgeHand();
            p.setCurrentHand(hand);
        }

        finishDicePhase();
    }

    private void finishDicePhase() {

        for (Player p : players) {

            String result = p.getCurrentHand().getHandStrength() > 0
                    ? "WIN" : "LOSE";

            notifySinglePlayer(
                    p,
                    GamePhase.ROLL_FINISHED,
                    result,
                    p.getCurrentHand().getHandName()
            );
        }

        startSettlementPhase();
    }


    // ==============================
    // 精算フェーズ
    // ==============================
    private void startSettlementPhase() {

        System.out.println("=== 精算フェーズ ===");

        Player winner = settlement.judgeWinner(currentDealer, players);
        System.out.println("勝者: " + winner.getName());

        settlement.settle(winner, players);

        checkGameEnd();
    }

    // ==============================
    // 親交代・終了判定
    // ==============================
    private void checkGameEnd() {

        for (Player p : players) {
            if (p.getOwnedBananas() <= 0) {

                notifyAllPlayers(GamePhase.GAME_END, null, null);
                return;
            }
        }

        rotateDealer();
        startBetPhase();
    }


    private void rotateDealer() {

        int idx = players.indexOf(currentDealer);
        currentDealer.setDealer(false);

        idx = (idx + 1) % players.size();
        currentDealer = players.get(idx);
        currentDealer.setDealer(true);

        System.out.println("新しい親: " + currentDealer.getName());
    }

    // ==============================
    // 結果表示
    // ==============================
    private void showResult() {

        System.out.println("=== 結果 ===");

        for (Player p : players) {
            System.out.println(
                    p.getName() + " : " + p.getOwnedBananas() + " バナナ"
            );
        }
    }


    private void notifyAllPlayers(
            GamePhase phase,
            String result,
            String hand
    ) {
        if (sendListener == null) return;

        for (Player p : players) {
            sendListener.sendUpdate(
                    p.getName(),
                    p.getOwnedBananas(),
                    result,
                    hand,
                    phase
            );
        }
    }

    private void notifySinglePlayer(
            Player p,
            GamePhase phase,
            String result,
            String hand
    ) {
        if (sendListener == null) return;

        sendListener.sendUpdate(
                p.getName(),
                p.getOwnedBananas(),
                result,
                hand,
                phase
        );
    }

}
