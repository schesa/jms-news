package pcbe.jms;

import java.util.Date;

public class Article{

    private String author;
    private String content;
    private String infoSource;
    private String articleName;
    private Date creationDate;
    private Date lastModification;

    public Article(String articleName, String author, String content){
        this.articleName = articleName;
        this.author = author;
        this.content = content;
        creationDate = new Date();
    }

    public String getArticleName(){
        return articleName;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
        lastModification = new Date();
    }

    public String getInfoSource() {
        return infoSource;
    }

    public void setInfoSource(String infoSource) {
        this.infoSource = infoSource;
        lastModification = new Date();
    }

    public String printArticle(){
        return articleName + "\n\t" + content + "\n\t\t" + author;
    }
}