package control;

import entity.Hand;
import entity.Player;

import java.util.List;

/**
 * 精算処理を行うクラス
 */
public class Settlement {

    /**
     * 親と子を含むプレイヤー全員から勝者を判定する
     */
    public Player judgeWinner(Player dealer, List<Player> players) {

        Player winner = dealer;
        Hand strongestHand = dealer.getCurrentHand();

        for (Player p : players) {
            if (p.getCurrentHand().getHandStrength() > strongestHand.getHandStrength()) {
                winner = p;
                strongestHand = p.getCurrentHand();
            }
        }

        return winner;
    }

    /**
     * 勝者・敗者間でバナナの精算を行う
     */
    public void settle(Player winner, List<Player> players) {

        Hand winnerHand = winner.getCurrentHand();
        double multiplier = winnerHand.getMultiplier();

        for (Player p : players) {
            if (p == winner) {
                continue;
            }

            int bet = p.getCurrentBetBananas();
            int payment = (int) (bet * multiplier);

            // 勝者はもらう
            winner.addBananas(payment);

            // 敗者は払う
            p.subtractBananas(payment);
        }

        // 掛け金をリセット
        resetBets(players);
    }

    /**
     * 掛け金・手役・リロール回数をリセット
     */
    private void resetBets(List<Player> players) {
        for (Player p : players) {
            p.setCurrentBetBananas(0);
            p.setCurrentHand(null);
            p.resetRerollCount();
        }
    }
}
