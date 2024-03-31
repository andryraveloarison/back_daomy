package com.daomy.Back.controller;

import com.daomy.Back.model.Domino;
import com.daomy.Back.model.FinalResult;
import com.daomy.Back.model.Result;
import com.daomy.Back.model.Status;

import java.util.Vector;

public class EndGameController {

    public FinalResult makeResult(Vector<Domino> dominosJoueurs[],String  nomJoueurs[], int nbJoueurs, String lastSender, String lastDomino, String lastAction,String[][] newListeDominos, int tete, int[] pointJoueurs, int maxPoint){
        String winnerName= "";
        int winnerPoint=0;
        int tbPoint[] = new int[nbJoueurs];
        Result result[] = new Result[nbJoueurs];
        FinalResult finalResult;
        boolean isWin=false;

        int id=0;
        int somme=0;
        boolean isNotNull=true;
        boolean isFinished=false;

        for(int k=0;k<nbJoueurs;k++)
        {
            int point=0;

            if(dominosJoueurs[k].isEmpty()){
                winnerName=nomJoueurs[k];
                id=k;
                isWin=true;
            }else{
                for (int l=0;l<dominosJoueurs[k].size();l++)
                {
                    point = point+ dominosJoueurs[k].get(l).getGauche()+
                            dominosJoueurs[k].get(l).getDroite();
                }
            }

            tbPoint[k]=point;
        }
        int min=tbPoint[0];

        //Calcule Winner point
        if (isWin) {

            for(int i = 1; i<nbJoueurs;i++){
                winnerPoint=winnerPoint+tbPoint[(id+i)%nbJoueurs];
            }
        }

        for(int i =0; i<nbJoueurs;i++){

            result[i]= new Result(nomJoueurs[i],tbPoint[i]);

                if (!isWin) {

                    if(tbPoint[i]< min ){
                        id=i;
                        somme = somme+min;
                        min = tbPoint[i];

                    }else if(tbPoint[i]>min){

                        somme=somme+tbPoint[i];

                    }else if(i!=0){

                        winnerName = "null";
                        isNotNull=false;

                    }

                }


            }


        if(isNotNull && !isWin) {
            winnerPoint = somme;
            winnerName = nomJoueurs[id];
        }



        int[] newPoint = updatePoint(pointJoueurs,nomJoueurs,winnerName, winnerPoint);
        if(newPoint[id] > maxPoint){
            isFinished =true;
        }

        finalResult = new FinalResult(result,winnerName,winnerPoint, Status.RESULT,lastSender,lastDomino,lastAction,newListeDominos, tete, newPoint, isFinished);

        return finalResult;
    }

    public int[] updatePoint(int[] pointJoueurs, String[] nomJoueurs, String winnerName, int winnerPoint){

        for(int i=0; i<nomJoueurs.length; i++){
            if(nomJoueurs[i].equalsIgnoreCase(winnerName)){
                pointJoueurs[i]=pointJoueurs[i]+winnerPoint;
            }
        }
        return pointJoueurs;
    }

    public int[] initialisePoint(int[] pointJoueurs){

        for(int i=0; i<pointJoueurs.length; i++){
           pointJoueurs[i]=0;
        }
        return pointJoueurs;
    }

}
