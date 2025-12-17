package comunication.message;

public class ServerMessage {

    public String name;      // プレイヤー名
    public int bananas;
    public String handName;// 所持バナナ
    public String result;    // WIN / LOSE / NONE
    public String hand;      // 役名（なければ null）
    public String phase;     // GamePhase
}
