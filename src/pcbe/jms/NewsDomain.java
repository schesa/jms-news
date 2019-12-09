package pcbe.jms;

import java.util.Date;

public class NewsDomain{

    private String newsName;
    private Date publication;
    private Map<String, List<Article>> news = new Map<String, List<Article>>();

    public NewsDomain(String newsName){
        this.newsName = newsName;
        publication = new Date();
    }

    public void addArticle(String domain, Article article){
        List<Article> articleList = news.get(domain);

        if(articleList == null) {
            articleList = new ArrayList<Article>();
            articleList.add(article);
            items.put(domain, articleList);
        } else {
            if(!articleList.contains(article)) articleList.add(article);
        }
    }

    public String readArticle(String domain, String articleName){
        List<Article> articles = news.get(domain);
        if(articles == null)
            return "There are no articles in this domain";
        else
        {
            for (Article article: articles) {
                if(article.getArticleName().equals(articleName))
                    return article.printArticle();
            }
        }

    }
}