package hu.szgyf.android.tt;

public class ScoreListItem {
    private Integer id;
    private String name;
    private int score;

    public ScoreListItem(Integer id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public ScoreListItem(String name, int score) {
        this(null, name, score);
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return ""+id+","+name+","+score;
    }
}
