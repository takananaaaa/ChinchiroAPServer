package control;

import entity.Player;

public class BetController {

    private Player player;
    private Timer timer;
    private int betBananas;

    // ===== コンストラクタ =====
    public BetController(Player player, Timer timer) {
        this.player = player;
        this.timer = timer;
        this.betBananas = 0;
    }

    // ===== 賭け金を設定する =====
    public boolean setBetBananas(int bet) {
        this.betBananas = bet;

        // 所持金以内かどうか検証
        if (!validateBetWithinOwned()) {
            System.out.println("エラー: 賭け金が所持金を超えています。");
            return false;
        }

        // プレイヤーに設定
        player.setCurrentBetBananas(betBananas);
        return true;
    }

    // ===== 賭け金が所持金を超えていないかを検証 =====
    public boolean validateBetWithinOwned() {
        return betBananas <= player.getOwnedBananas();
    }

    // ===== 全員が賭け終わったかを判定（UML上のメソッド） =====
    // ※実際には ApplicationServerController で複数人管理するが、
    //    BetController 単体では true を返す形にしておく。
    public boolean isAllBetCompleted() {
        return player.getCurrentBetBananas() > 0;
    }

    // ===== タイマーを開始 =====
    public Timer startTimer() {
        timer.start();
        return timer;
    }

    // ===== タイムアウト時の自動設定 =====
    public void autoSetting() {

        // 仕様書では “自動的にサイコロを振る” があるが、
        // 賭け金フェーズでは "ランダム or 固定バナナ" が妥当。
        // ここでは以下のルールとする：
        // ・所持金の10%（最低100バナナ）
        int autoBet = Math.max(100, player.getOwnedBananas() / 10);

        this.betBananas = autoBet;
        player.setCurrentBetBananas(autoBet);

        System.out.println("タイムアウトのため自動で賭け金 " + autoBet + " を設定しました。");
    }

    // ===== 賭け金を取得 =====
    public int getBetBananas() {
        return betBananas;
    }
}
