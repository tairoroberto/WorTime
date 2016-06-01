package br.com.trmasolucoes.meuponto.domain;

import org.w3c.dom.Document;

/**
 * Created by tairo on 18/04/16.
 */
public class MessageEB {
    private Document result;


    public MessageEB(Document r){
        result = r;
    }


    public Document getResult() {
        return result;
    }


    public void setResult(Document result) {
        this.result = result;
    }
}
