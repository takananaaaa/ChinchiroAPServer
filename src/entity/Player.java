package entity;

public class Player {

    // ===== フィールド =====
    private int id;
    private String userName;
    private int ownedBananas;
    private int currentBetBananas;
    private Hand currentHand;
    private boolean isDealer;
    private int rerollCount;

    // ===== コンストラクタ =====
    public Player(int id, String userName, int ownedBananas, boolean isDealer) {
        this.id = id;
        this.userName = userName;
        this.ownedBananas = ownedBananas;
        this.isDealer = isDealer;
        this.currentBetBananas = 0;
        this.currentHand = null;
        this.rerollCount = 0;
    }

    // ===== Getter =====
    public int getId() {
        return id;
    }

    public String getName() {
        return userName;
    }

    public int getOwnedBananas() {
        return ownedBananas;
    }

    public int getCurrentBetBananas() {
        return currentBetBananas;
    }

    public Hand getCurrentHand() {
        return currentHand;
    }

    public boolean isDealer() {
        return isDealer;
    }

    public int getRerollCount() {
        return rerollCount;
    }

    // ===== Setter / 状態変更用メソッド（今後よく使う） =====
    public void setCurrentHand(Hand currentHand) {
        this.currentHand = currentHand;
    }

    public void setCurrentBetBananas(int bananas) {
        this.currentBetBananas = bananas;
    }

    public void addBananas(int bananas) {
        this.ownedBananas += bananas;
    }

    public void subtractBananas(int bananas) {
        this.ownedBananas -= bananas;
    }

    public void incrementRerollCount() {
        this.rerollCount++;
    }

    public void resetRerollCount() {
        this.rerollCount = 0;
    }

    public void setDealer(boolean isDealer) {
        this.isDealer = isDealer;
    }
}
