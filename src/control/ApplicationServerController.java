package control;

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

    private List<Player> players;
    private Player currentDealer;

    private Map<Player, BetController> betControllers;
    private Map<Player, DiceController> diceControllers;

    private Settlement settlement;

    // ===== コンストラクタ =====
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
        System.out.println("=== ゲーム開始 ===");
        startBetPhase();
    }

    // ==============================
    // 賭けフェーズ
    // ==============================
    private void startBetPhase() {

        System.out.println("=== 賭けフェーズ開始 ===");

        for (Player p : players) {
            if (p == currentDealer) continue;

            BetController bc = betControllers.get(p);
            bc.startTimer();
            // 実際の入力はクライアント側
        }
    }

    // ==============================
    // サイコロフェーズ
    // ==============================
    public void startDicePhase() {

        System.out.println("=== サイコロフェーズ開始 ===");

        for (Player p : players) {
            DiceController dc = diceControllers.get(p);
            dc.startTimer();
            dc.rollDice();

            Hand hand = dc.judgeHand();
            p.setCurrentHand(hand);

            System.out.println(p.getName() + " の役: " + hand.getHandName());
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
                System.out.println("=== ゲーム終了（所持金0） ===");
                showResult();
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
}
