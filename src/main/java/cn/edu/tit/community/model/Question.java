package cn.edu.tit.community.model;

public class Question {
    private Integer id;
    private String title;
    private String description;
    private String tag;
    private Integer commentCount = 0;
    private Integer viewCount = 0;
    private Integer likeCount = 0;
    private Long gmtCreate;
    private Long gmtModify;
    private Integer creator;


    public Question() {
    }

    public Question(Integer id, String title, String description, String tag, Integer commentCount, Integer viewCount, Integer likeCount, Long gmtCreate, Long gmtModify, Integer creator) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.tag = tag;
        this.commentCount = commentCount;
        this.viewCount = viewCount;
        this.likeCount = likeCount;
        this.gmtCreate = gmtCreate;
        this.gmtModify = gmtModify;
        this.creator = creator;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public Integer getCommentCount() {
        return commentCount;
    }

    public void setCommentCount(Integer commentCount) {
        this.commentCount = commentCount;
    }

    public Integer getViewCount() {
        return viewCount;
    }

    public void setViewCount(Integer viewCount) {
        this.viewCount = viewCount;
    }

    public Integer getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(Integer likeCount) {
        this.likeCount = likeCount;
    }

    public Long getGmtCreate() {
        return gmtCreate;
    }

    public void setGmtCreate(Long gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Long getGmtModify() {
        return gmtModify;
    }

    public void setGmtModify(Long gmtModify) {
        this.gmtModify = gmtModify;
    }

    public Integer getCreator() {
        return creator;
    }

    public void setCreator(Integer creator) {
        this.creator = creator;
    }

    @Override
    public String toString() {
        return "Question{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", tag='" + tag + '\'' +
                ", commentCount=" + commentCount +
                ", viewCount=" + viewCount +
                ", likeCount=" + likeCount +
                ", gmtCreate=" + gmtCreate +
                ", gmtModify=" + gmtModify +
                ", creator=" + creator +
                '}';
    }
}
