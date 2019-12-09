package pcbe.jms;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NewsDomain{

    private String domain;
    private Map<String, List<Article>> news = new HashMap<String, List<Article>>();

    public NewsDomain(String domain){
        this.domain = domain;
    }

    public void addArticle(String subDomain, Article article){
        List<Article> articleList = news.get(subDomain);

        if(articleList == null) {
            articleList = new ArrayList<Article>();
            articleList.add(article);
            news.put(subDomain, articleList);
        } else {
            if(!articleList.contains(article)) articleList.add(article);
        }
    }

    public String readArticle(String subDomain, String articleName){
        String article = null;
        List<Article> articles = news.get(subDomain);
        if (articles == null || articles.size() == 0)
            return "There are no articles in this domain";
        else
        {
            for (Article a : articles) {
                if (a.getArticleName().equals(articleName)) article = a.toString();
                break;
            }
        }
        return article;
    }
}