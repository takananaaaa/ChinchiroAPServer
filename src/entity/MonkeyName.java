package entity;

/**
 * チンチロにおける役（サルの名前）
 */
public enum MonkeyName {

    NAGAZARU("ナガザル", 1),
    KUROZARU("クロザル", 1),
    TENAGAZARU("テナガザル", 2),
    FUKURO_TENAGAZARU("フクロテナガザル", 3);

    // ===== フィールド =====
    private final String displayName;
    private final int strength;

    // ===== コンストラクタ =====
    MonkeyName(String displayName, int strength) {
        this.displayName = displayName;
        this.strength = strength;
    }

    // ===== Getter =====
    public String getDisplayName() {
        return displayName;
    }

    public int getStrength() {
        return strength;
    }
}
