package pds.dao;

import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.persist.Transactional;
import pds.dto.ArticlesDto;
import pds.models.Article;
import ninja.jpa.UnitOfWork;

import javax.persistence.*;
import java.util.List;

@Transactional
public class ArticleDao {

    @Inject
    Provider<EntityManager> provider;

    public Article findByUrl(String url){
        EntityManager em = provider.get();
        TypedQuery<Article> q = em.createQuery("select x from Article x where x.url = :curl",Article.class);
        q.setParameter("curl", url);
        List<Article> articles = q.getResultList();
        if (articles.size()==1){
            return articles.get(0);
        }
        return null;
    }

    public Article save(Article article){
            EntityManager em = provider.get();
            em.persist(article);
            em.flush();
            return article;

    }

    public ArticlesDto getAllArticles() {

        EntityManager entityManager = provider.get();

        TypedQuery<Article> query= entityManager.createQuery("SELECT x FROM Article x", Article.class);
        List<Article> articles = query.getResultList();

        ArticlesDto articlesDto = new ArticlesDto();
        articlesDto.articles = articles;

        return articlesDto;

    }


    public List listArticles() {

        EntityManager entityManager = provider.get();

        TypedQuery<Article> query= entityManager.createQuery("SELECT x FROM Article x", Article.class);
        List<Article> articles = query.getResultList();
        return articles;

    }

    public Article getFirstArticleForFrontPage() {

        EntityManager entityManager = provider.get();

        Query q = entityManager.createQuery("SELECT x FROM Article x ORDER BY x.created DESC");
        Article article = (Article) q.setMaxResults(1).getSingleResult();

        return article;
    }

    @UnitOfWork
    public List<Article> getOlderArticlesForFrontPage() {

        EntityManager entityManager = provider.get();

        Query q = entityManager.createQuery("SELECT x FROM Article x ORDER BY x.created DESC");
        List<Article> articles = (List<Article>) q.setFirstResult(1).setMaxResults(10).getResultList();

        return articles;


    }

    @UnitOfWork
    public Article getArticle(Long id) {

        EntityManager entityManager = provider.get();

        Query q = entityManager.createQuery("SELECT x FROM Article x WHERE x.id = :idParam");
        Article article = (Article) q.setParameter("idParam", id).getSingleResult();

        return article;


    }



}
