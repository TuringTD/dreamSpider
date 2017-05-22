package ds.command;

import com.google.inject.Injector;
import ds.base.AbstractCliCommand;
import ds.dao.ArticleDao;
import ds.models.Article;
import ds.service.SpiderService;
import org.slf4j.Logger;

import java.util.Iterator;
import java.util.List;

public class DataSpiderCommand extends AbstractCliCommand {

    @Override
    protected void command(Injector injector) {
        Logger logger = (Logger)injector.getProvider(Logger.class).get();
        ArticleDao articleDao = injector.getProvider(ArticleDao.class).get();
        SpiderService spiderService = injector.getProvider(SpiderService.class).get();
        //step: define url
        String url = "http://www.hc3i.cn/php/search.php?t=news&q=%2C&s=stime&ie=utf-8&p=";

        //forloop
        for(int i = 1;i<1406;i++){
            String currentUrl = url+String.valueOf(i);
            //step: check existed
            if(null==articleDao.findByUrl(currentUrl)){
                //step: get remote content
                logger.info("---------------------------------");
                String source = spiderService.getRemoteSource(currentUrl);//@TODO
                logger.info("---------------------------------");
                //step: get single article url
                List<String> articleUrls = spiderService.getArticleUrlsFromSource(source);//@TODO
                //step: loop single article url
                Iterator<String> articleUrlsIt = articleUrls.iterator();
                while(articleUrlsIt.hasNext()){
                    //step: get single article source
                    String articleUrl = articleUrlsIt.next();
                    //step: clean html
                    Article article = spiderService.getArticleFromSource(articleUrl);
                    //step: save
                    articleDao.save(article);
                }
            }
        }
        //endforloop
        logger.debug("it works");
    }

    public static void main(String[] args){
        AbstractCliCommand ac = new DataSpiderCommand();
        ac.run();
    }
}
