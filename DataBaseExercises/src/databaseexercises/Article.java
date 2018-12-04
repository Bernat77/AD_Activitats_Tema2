/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package databaseexercises;

/**
 *
 * @author windeveloper
 */
public class Article {

    private String id;
    private String descripcio;

    public Article(String id, String desc) {
        this.id = id;
        descripcio = desc;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getDescripcio() {
        return descripcio;
    }

    public void setDescripcio(String descripcio) {
        this.descripcio = descripcio;
    }

}
