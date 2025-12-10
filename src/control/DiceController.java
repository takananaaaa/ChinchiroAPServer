package control;

import entity.Hand;
import entity.MonkeyName;
import entity.Player;

import java.util.Random;

public class DiceController {

    private Player playerRollingDice;
    private Timer timer;
    private String dice;  // "フクテロ..." のように文字列で保持

    private static final String[] DICE_FACES = {"フ", "ク", "ロ", "テ", "ナ", "ガ"};
    private Random rand = new Random();

    // ===== コンストラクタ =====
    public DiceController(Player player, Timer timer) {
        this.playerRollingDice = player;
        this.timer = timer;
        this.dice = "";
    }

    // ===== サイコロを６つ振る =====
    public void rollDice() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            sb.append(DICE_FACES[rand.nextInt(DICE_FACES.length)]);
        }
        this.dice = sb.toString();
    }

    // ===== 最大2回までの振り直し =====
    public void reRoll() {
        if (playerRollingDice.getRerollCount() >= 2) {
            return; // 振り直し上限
        }
        playerRollingDice.incrementRerollCount();
        rollDice();
    }

    // ===== タイマー開始（UML準拠） =====
    public Timer startTimer() {
        timer.start();
        return timer;
    }

    // ===== 出た目から役を判定する =====
    public Hand judgeHand() {

        // 出現回数カウント
        int countフ = countOf("フ");
        int countク = countOf("ク");
        int countロ = countOf("ロ");
        int countテ = countOf("テ");
        int countナ = countOf("ナ");
        int countガ = countOf("ガ");

        // ▼ 役判定ロジック ▼

        // フクロテナガザル
        if (countフ >= 1 && countク >= 1 && countロ >= 1 && countテ >= 1 && countナ >= 1 && countガ >= 1) {
            return new Hand(MonkeyName.FUKURO_TENAGAZARU, 3.0);
        }

        // テナガザル
        if (countテ >= 1 && countナ >= 1 && countガ >= 1) {
            return new Hand(MonkeyName.TENAGAZARU, 2.0);
        }

        // ナガザル
        if (countナ >= 1 && countガ >= 1) {
            return new Hand(MonkeyName.NAGAZARU, 1.0);
        }

        // クロザル
        if (countク >= 1 && countロ >= 1) {
            return new Hand(MonkeyName.KUROZARU, 1.0);
        }

        // 役なし
        return new Hand("役なし", 0, 0.0);
    }

    // ===== 特別役（UMLの judgeSpecialHand） =====
    public Hand judgeSpecialHand() {
        // 例：フクロテナガザル成立時は即勝利など
        Hand hand = judgeHand();
        if (hand.getHandStrength() == 3) {  // 最強役
            return hand;
        }
        return null; // 特別役なし
    }

    // ===== 補助メソッド：文字数カウント =====
    private int countOf(String ch) {
        return (int) dice.chars().filter(c -> c == ch.charAt(0)).count();
    }

    // サイコロ結果を取得
    public String getDice() {
        return dice;
    }
}
