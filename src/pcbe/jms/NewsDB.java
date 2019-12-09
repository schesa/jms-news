package pcbe.jms;

import java.util.concurrent.ConcurrentHashMap;

public class NewsDB {

    public static ConcurrentHashMap<String, NewsDomain> domains = new ConcurrentHashMap<>();

    public static void init() {
        NewsDomain sport = new NewsDomain(Domains.SPORT.name());
        Article meci = new Article("INCREDIBIL DAR ADEVARAT","schesa","content");
        sport.addArticle("FOTBAL", meci);
        meci = new Article("INCREDIBIL 2", "schesa", "content");
        sport.addArticle("FOTBAL", meci);
        meci = new Article("INCREDIBIL 3", "schesa", "content");
        sport.addArticle("FOTBAL", meci);

        NewsDomain vreme = new NewsDomain(Domains.VREME.name());
        Article a = new Article("TITLE", "schesa", "content");
        sport.addArticle("LUNI", a);

        NewsDomain pol = new NewsDomain(Domains.POLITIC.name());
        Article a2 = new Article("PARTIDE TITLU", "schesa", "content");
        sport.addArticle("PARTIDE", a2);

        domains.put(Domains.SPORT.name(), sport);
        domains.put(Domains.VREME.name(), vreme);
        domains.put(Domains.POLITIC.name(), pol);
    }

}
