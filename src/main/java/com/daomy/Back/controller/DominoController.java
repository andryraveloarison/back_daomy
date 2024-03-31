package com.daomy.Back.controller;

import com.daomy.Back.model.Domino;
import com.daomy.Back.model.Message;

import java.util.Collections;
import java.util.Vector;

public class DominoController {


    public Vector<Domino>[] InitialiseDomino() {

        Vector<Domino> listeDomino = new Vector<>();
        Vector <Domino> dominosJoueurs[] = new Vector[4];

        // Creation du domy
        for(int i=0;i<7;i++){
            for (int j=i;j<7;j++)
            {
                Domino x = new Domino(i,j);
                listeDomino.add(x);
            }
        }

        // pasoka
        Collections.shuffle(listeDomino);

        // liste dominoJoueur
        for (int i=0;i<dominosJoueurs.length;i++)
        {
            Vector <Domino> liste = new Vector<>();
            dominosJoueurs[i] = liste;
        }

        //Mampiditra liste domino
        dominosJoueurs= distribuer(dominosJoueurs,listeDomino);


        return dominosJoueurs;
    }


    public Vector <Domino>[] distribuer(Vector <Domino> dominosJoueurs[],Vector<Domino> listeDomino)
    {
        int i=0;
        for(int j=0;j<4;j++)
        {
            for(int k=0;k< 7;k++)
            {
                dominosJoueurs[j].add(listeDomino.get(i));
                i++;
            }
        }

        return dominosJoueurs;

    }



    public String[] traitement(String reference[], Message message){
        String ref[]=reference;
        if(message.getAction().equalsIgnoreCase("initialise")){
            ref=init(reference,message.getMessage());
        }else {
            ref=update(reference,message.getMessage(),message.getAction());
        }
        return ref;
    }



    public String[] update(String reference[], String domino, String action){
        String updated[]=reference;
        String dom[] = domino.split(":");

        if(action.equalsIgnoreCase("addGauche")){
            updated[0]=dom[0];
        }else if(action.equalsIgnoreCase("addReverseGauche")){
            updated[0]=dom[1];
        }else if(action.equalsIgnoreCase("addDroite")){
            updated[1]=dom[1];
        }else if(action.equalsIgnoreCase("addReverseDroite")){
            updated[1]=dom[0];
        }
        return updated;
    }

    public String[] init(String reference[], String domino){
        String dom[] = domino.split(":");
        return dom;
    }

    public  Vector<Domino>[] removeDomino( Vector<Domino> dominosJoueurs[], int tour,String domino, int nbJoueurs){
        Vector<Domino> dominoRemoved[] = dominosJoueurs;

        for(int j=0; j<dominosJoueurs[(tour+2)% nbJoueurs].size(); j++) {

            if(dominosJoueurs[(tour+2)% nbJoueurs].get(j).getDomino().equalsIgnoreCase(domino))
            {
                dominosJoueurs[(tour+2)% nbJoueurs].remove(j);
                break;
            }

        }

        return dominoRemoved;
    }


}
