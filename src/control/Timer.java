package control;

/**
 * 各フェーズ（賭け・サイコロ）で使用するタイマークラス
 */
public class Timer {

    // ===== フィールド =====
    /** 制限時間（秒） */
    private int duration;

    /** 開始時刻（ミリ秒） */
    private long startTime;

    /** 終了したかどうか */
    private boolean finished;

    // ===== コンストラクタ =====
    public Timer(int duration) {
        this.duration = duration;
        this.finished = false;
    }

    // ===== タイマー開始 =====
    public void start() {
        this.startTime = System.currentTimeMillis();
        this.finished = false;
        System.out.println("タイマー開始（制限時間：" + duration + "秒）");
    }

    // ===== タイマー終了 =====
    public void finish() {
        this.finished = true;
        System.out.println("タイマー終了");
    }

    // ===== 制限時間を取得 =====
    public int getDuration() {
        return duration;
    }

    // ===== 経過時間（秒）を取得 =====
    public int getElapsedSeconds() {
        long now = System.currentTimeMillis();
        return (int) ((now - startTime) / 1000);
    }

    // ===== 時間切れかどうか =====
    public boolean isTimeUp() {
        if (finished) {
            return true;
        }
        return getElapsedSeconds() >= duration;
    }
}
