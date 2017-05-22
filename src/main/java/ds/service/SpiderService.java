package ds.service;

import br.com.starcode.jerichoselector.jerQuery;
import br.com.starcode.parccser.ParserException;
import com.google.inject.Inject;
import com.mashape.unirest.http.HttpResponse;
import com.mashape.unirest.http.Unirest;
import com.mashape.unirest.request.GetRequest;
import ds.models.Article;
import net.htmlparser.jericho.Element;
import org.slf4j.Logger;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static br.com.starcode.jerichoselector.jerQuery.$;

public class SpiderService {

    @Inject
    Logger logger;

    public Article getArticleFromSource(String url){
        String source = this.getRemoteSource(url);
        String title = null;
        String content = null;
        try {
            List<Element> telists =  $(source,".title").getSelectedElements();
            if(telists.size()>0){
                title = telists.get(0).getContent().toString();
            }
        } catch (ParserException e) {
            System.out.println(e.getMessage());
        }

        try {
            List<Element> celists =  $(source,".answer").getSelectedElements();
            if(celists.size()>0){
                content = celists.get(0).getContent().toString();
            }
        }catch (ParserException e) {
            System.out.println(e.getMessage());
        }

        Article a = new Article();
        a.setCreated(new Date());
        a.setUrl(url.trim());
        a.setTitle(title.trim());
        a.setContent(content.trim());
        return a;
    }

    public List<String> getArticleUrlsFromSource(String source){
        List<String> list = new ArrayList<>();
        try {
            jerQuery q = $(source, "h2>a");
            List<Element> elists = q.getSelectedElements();
            for(Element e : elists){
                String url = e.getAttributeValue("href");
                System.out.println(url);
                list.add(url);
            }
        } catch (ParserException e) {
            System.out.println(e.getMessage());
        }
        return list;
    }


    public String getRemoteSource(String url){
            GetRequest request = Unirest.get(url);
            HttpResponse response = null;
            try{
                response = request.asString();
            }catch(Exception ex){
                return "";
            }
            String res = String.valueOf(response.getBody());
            return res;
    }
}
