package Entity;

/**
 * プレイヤーの「役」を表すクラス。
 * UML:
 *  - handName : String
 *  - handStrength : int
 *  - multiplier : double
 *  - getHandName() : String
 *  - getHandStrength() : int
 *  - getMultiplier() : double
 */
public class Hand {

    // ===== フィールド =====
    /** 役の名前（例: "ナガザル", "クロザル", "テナガザル", "フクロテナガザル"） */
    private String handName;

    /** 役の強さ（大きいほど強い） */
    private int handStrength;

    /** 掛け金に対する倍率（例: 1.0, 2.0, 3.0 など） */
    private double multiplier;

    // ===== コンストラクタ =====

    /**
     * 役の名前・強さ・倍率を直接指定するコンストラクタ。
     */
    public Hand(String handName, int handStrength, double multiplier) {
        this.handName = handName;
        this.handStrength = handStrength;
        this.multiplier = multiplier;
    }

    /**
     * MonkeyName から Hand を作るための便利コンストラクタ。
     * （倍率は呼び出し側で決めるバージョン）
     */
    public Hand(MonkeyName monkeyName, double multiplier) {
        this.handName = monkeyName.getDisplayName();
        this.handStrength = monkeyName.getStrength();
        this.multiplier = multiplier;
    }

    // ===== Getter（UMLで指定されている公開メソッド） =====

    public String getHandName() {
        return handName;
    }

    public int getHandStrength() {
        return handStrength;
    }

    public double getMultiplier() {
        return multiplier;
    }

    // ===== 必要なら追加で使える Setter / 補助メソッド =====

    public void setMultiplier(double multiplier) {
        this.multiplier = multiplier;
    }

    @Override
    public String toString() {
        return "Hand{" +
                "handName='" + handName + '\'' +
                ", handStrength=" + handStrength +
                ", multiplier=" + multiplier +
                '}';
    }
}
