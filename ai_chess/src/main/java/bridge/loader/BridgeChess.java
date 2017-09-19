package bridge.loader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by didi on 17/9/6.
 *
 * 标题：东马 邓祥年 胜 德国 克纳
 分类：
 来源：
 赛事类型：
 赛事：2011年第12届世界象棋锦标赛
 轮次：第01轮
 组别：
 台次：第22台
 日期：2011-11-21 09:00-12:00
 地点：印度尼西亚首都雅加达酒店
 时间规则：60分＋30秒
 裁判员：
 记录员：
 红方：邓祥年
 红等级分：
 红方用时：
 红队：东马
 黑方：克纳
 黑等级分：
 黑方用时：
 黑队：德国
 解说：(Email:)
 录入：(Email:)
 创建日期：2011-11-23 19:24:00
 修改日期：2011-11-23 19:24:00
 ECCO：A30 飞相对左过宫炮
 棋谱类型：实战全局/开局
 对局种类：慢棋
 结果：红胜
 结束方式：
 FEN ：rnbakabnr/9/1c5c1/p1p1p1p1p/9/9/P1P1P1P1P/1C5C1/9/RNBAKABNR w - - 0 1
 */
public class BridgeChess {

    private String title;
    private String category;
    private String org;
    private String matchKind;
    private String matchName;
    private String rank;
    private String group;
    private String times;
    private String date;
    private String address;
    private String timeRules;
    private String judge;
    private String recorder;
    private String redPlayer;
    private String redRank;
    private String redTime;
    private String redGroup;
    private String blackPlayer;
    private String blackRank;
    private String blackTime;
    private String blackGroup;
    private String comment;
    private String record;
    private String createTime;
    private String modifyTime;
    private String ecco;
    private String chessType;
    private String playKind;
    private String result;
    private String endType;
    private String fen;

    private List<String> redSteps  = new ArrayList<String>();

    private List<String> blackSteps = new ArrayList<String>();


    private int step = 0;

    private char color = 'b';

    public String getTitle() {
        return title;
    }

    public String getCategory() {
        return category;
    }

    public String getOrg() {
        return org;
    }

    public String getMatchKind() {
        return matchKind;
    }

    public String getMatchName() {
        return matchName;
    }

    public String getRank() {
        return rank;
    }

    public String getGroup() {
        return group;
    }

    public String getTimes() {
        return times;
    }

    public String getDate() {
        return date;
    }

    public String getAddress() {
        return address;
    }

    public String getTimeRules() {
        return timeRules;
    }

    public String getJudge() {
        return judge;
    }

    public String getRecorder() {
        return recorder;
    }

    public String getRedPlayer() {
        return redPlayer;
    }

    public String getRedRank() {
        return redRank;
    }

    public String getRedTime() {
        return redTime;
    }

    public String getRedGroup() {
        return redGroup;
    }

    public String getBlackPlayer() {
        return blackPlayer;
    }

    public String getBlackRank() {
        return blackRank;
    }

    public String getBlackTime() {
        return blackTime;
    }

    public String getBlackGroup() {
        return blackGroup;
    }

    public String getComment() {
        return comment;
    }

    public String getRecord() {
        return record;
    }

    public String getCreateTime() {
        return createTime;
    }

    public String getModifyTime() {
        return modifyTime;
    }

    public String getEcco() {
        return ecco;
    }

    public String getChessType() {
        return chessType;
    }

    public String getPlayKind() {
        return playKind;
    }

    public String getResult() {
        return result;
    }

    public String getEndType() {
        return endType;
    }

    public String getFen() {
        return fen;
    }

    public List<String> getRedSteps() {
        return redSteps;
    }

    public List<String> getBlackSteps() {
        return blackSteps;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setOrg(String org) {
        this.org = org;
    }

    public void setMatchKind(String matchKind) {
        this.matchKind = matchKind;
    }

    public void setMatchName(String matchName) {
        this.matchName = matchName;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public void setGroup(String group) {
        this.group = group;
    }

    public void setTimes(String times) {
        this.times = times;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setTimeRules(String timeRules) {
        this.timeRules = timeRules;
    }

    public void setJudge(String judge) {
        this.judge = judge;
    }

    public void setRecorder(String recorder) {
        this.recorder = recorder;
    }

    public void setRedPlayer(String redPlayer) {
        this.redPlayer = redPlayer;
    }

    public void setRedRank(String redRank) {
        this.redRank = redRank;
    }

    public void setRedTime(String redTime) {
        this.redTime = redTime;
    }

    public void setRedGroup(String redGroup) {
        this.redGroup = redGroup;
    }

    public void setBlackPlayer(String blackPlayer) {
        this.blackPlayer = blackPlayer;
    }

    public void setBlackRank(String blackRank) {
        this.blackRank = blackRank;
    }

    public void setBlackTime(String blackTime) {
        this.blackTime = blackTime;
    }

    public void setBlackGroup(String blackGroup) {
        this.blackGroup = blackGroup;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public void setRecord(String record) {
        this.record = record;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public void setModifyTime(String modifyTime) {
        this.modifyTime = modifyTime;
    }

    public void setEcco(String ecco) {
        this.ecco = ecco;
    }

    public void setChessType(String chessType) {
        this.chessType = chessType;
    }

    public void setPlayKind(String playKind) {
        this.playKind = playKind;
    }

    public void setResult(String result) {
        this.result = result;
    }

    public void setEndType(String endType) {
        this.endType = endType;
    }

    public void setFen(String fen) {
        this.fen = fen;
    }

    public void addRedSteps(String step) {
        this.redSteps.add(step);
    }

    public void addBlackSteps(String step) {
        this.blackSteps.add(step);
    }


    public String play(){
        if(this.color == 'b'){
            this.color = 'r';
            if(this.redSteps.size() <= this.step){
                return null;
            }
            return this.redSteps.get(this.step);
        }else{
            this.step += 1;
            this.color = 'b';

            if(this.blackSteps.size() < this.step){
                return null;
            }
            return this.blackSteps.get(this.step - 1);
        }
    }

    public char getColor() {
        return color;
    }

    public void setColor(char color) {
        this.color = color;
    }
}
