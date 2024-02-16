package com.daomy.Back.model;

public class Domino {
    private int gauche;
    private int droite;

    public Domino(int gauche, int droite){
        this.gauche=gauche;
        this.droite=droite;
    }
    public String getDomino()
    {
        String s=this.gauche+":"+this.droite;
        return s;
    }

    public int getDroite() {
        return droite;
    }

    public int getGauche() {
        return gauche;
    }
}
