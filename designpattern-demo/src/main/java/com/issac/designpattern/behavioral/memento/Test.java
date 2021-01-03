package com.issac.designpattern.behavioral.memento;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class Test {
    public static void main(String[] args) {
        ArticleMementoManager mementoManager = new ArticleMementoManager();
        Article article = new Article("设计模式", "收集内容a", "图片a");
        ArticleMemento articleMemento = article.saveToMemento();
        mementoManager.addArticleMemento(articleMemento);
        System.out.println(article);

        article.setTitle("架构师");
        mementoManager.addArticleMemento(article.saveToMemento());
        System.out.println(article);

        article.setContent("修改内容b");
        mementoManager.addArticleMemento(article.saveToMemento());
        System.out.println(article);

        article.undoFromMemento(mementoManager.getMemento());
        System.out.println(article);

        article.undoFromMemento(mementoManager.getMemento());

        System.out.println(article);

    }
}
