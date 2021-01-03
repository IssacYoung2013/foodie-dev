package com.issac.designpattern.behavioral.memento;

import java.util.Stack;

/**
 * @author: ywy
 * @date: 2020-12-29
 * @desc:
 */
public class ArticleMementoManager {
    private final Stack<ArticleMemento> ARTICLE_MEMENTO_STACK = new Stack<>();

    public ArticleMemento getMemento() {
        return ARTICLE_MEMENTO_STACK.pop();
    }

    public void addArticleMemento(ArticleMemento articleMemento) {
        ARTICLE_MEMENTO_STACK.push(articleMemento);
    }

}
