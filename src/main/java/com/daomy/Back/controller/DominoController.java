package com.daomy.Back.controller;

import com.daomy.Back.model.Domino;

import java.util.Collections;
import java.util.Vector;

public class DominoController {
    Vector<Domino> listeDomino = new Vector<>();
    Vector <Domino> dominosJoueurs[] = new Vector[4];

    public Vector<Domino>[] InitialiseDomino() {
        // Creation du domy
        for(int i=0;i<7;i++){
            for (int j=i;j<7;j++)
            {
                Domino x = new Domino(i,j);
                this.listeDomino.add(x);
            }
        }

        // pasoka
        Collections.shuffle(this.listeDomino);

        // liste dominoJoueur
        for (int i=0;i<this.dominosJoueurs.length;i++)
        {
            Vector <Domino> liste = new Vector<>();
            this.dominosJoueurs[i] = liste;
        }

        //Mampiditra liste domino
        distribuer(this.dominosJoueurs,this.listeDomino);


        return this.dominosJoueurs;
    }


    public void distribuer(Vector <Domino> dominosJoueurs[],Vector<Domino> listeDomino)
    {
        int i=0;
        for(int j=0;j<4;j++)
        {
            for(int k=0;k< 7;k++)
            {
                this.dominosJoueurs[j].add(listeDomino.get(i));
                i++;
            }
        }

    }


}
