package com.daomy.Back.controller;

import com.daomy.Back.model.*;
import com.daomy.Back.controller.DominoController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.util.Vector;

@Controller
public class GameController {
    int tour = 1;
    int nbJoueurs = 3;

    String  nomJoueurs[] = new String[nbJoueurs];
    int  pointJoueurs[] = new int[nbJoueurs];
    int joueursConnecte =0;

    int tete=1;
    Vector<Domino> listeDomino = new Vector<>();
    Vector <Domino> dominosJoueurs[] = new Vector[4];
    Vector <Domino> newDominosJoueurs[] = new Vector[4];
    String[][] sDominosJoueurs = new String[3][3];
    String[][] newSDominosJoueurs = new String[3][3];

    String reference[] = new String[2];
    int passer = 0;

    DominoController dominoController = new DominoController();
    EndGameController endGameController =new EndGameController();

    int maxPoint =0;

    int newParty=0;
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Object receiveMessage(@Payload Message message){
        boolean isMessage = true;
        Message reponse = message;
        Request request=null;
        FinalResult result = null;
        Object res=null;

        if(message.getStatus().toString().equalsIgnoreCase("MESSAGE")){
            if(nbJoueurs<joueursConnecte){

                tour = tour % nbJoueurs;
                String miseAjours="";
                if(message.getMessage().equalsIgnoreCase("passer")){
                    miseAjours = reponse.getSenderName() +" a passé ";
                    passer++;

                }else {
                    reference=dominoController.traitement(reference,message);
                    dominosJoueurs=dominoController.removeDomino(dominosJoueurs,tour,message.getMessage(),nbJoueurs);

                    miseAjours = reponse.getSenderName() +" a envoyé(e) "+message.getMessage();
                    passer=0;
                }

                if(passer==3 || dominosJoueurs[(tour+2)%nbJoueurs].isEmpty()){

                    newDominosJoueurs = dominoController.InitialiseDomino();

                    for(int j=0; j<nbJoueurs; j++) {
                        //System.out.println("Vos dominos/"+enChaine(dominosJoueurs[j]));
                        newSDominosJoueurs[j]=enChaine(newDominosJoueurs[j]);
                    }
                    tour=tete=tete%nbJoueurs;
                    FinalResult resp =  endGameController.makeResult(dominosJoueurs,nomJoueurs,nbJoueurs, message.getSenderName(), message.getMessage(), message.getAction(),this.newSDominosJoueurs,tete, pointJoueurs, maxPoint);

                    res=resp;
                    dominosJoueurs = newDominosJoueurs;
                    sDominosJoueurs = newSDominosJoueurs;
                    tete++;
                    passer = 0;
                    if(resp.isFinished()){reInitialise();}else {
                        tour++;
                    }
                }else{
                    Request newMessage = new Request(reponse.getSenderName(), nomJoueurs[tour],miseAjours,Status.SUCCES,reference[0],reference[1], message.getAction(), message.getMessage());
                    res=newMessage;
                    tour++;
                }
                isMessage=false;

            }
        }else if(message.getStatus().toString().equalsIgnoreCase("LEAVE")) {
            res= new Message(reponse.getSenderName(), nomJoueurs[0]," ",Status.LEAVE,null,null);
            reInitialise();
        }else if(message.getStatus().toString().equalsIgnoreCase("NewParty")) {
            newParty++;
            res= new Message(reponse.getSenderName(), nomJoueurs[0],Integer.toString(newParty),Status.NewParty,null,null);
            if(newParty == nbJoueurs){
                newParty=0;
            }
        }else if(message.getStatus().toString().equalsIgnoreCase("JOIN")) {
                if(maxPoint>0){
                    if(!message.getMessage().equalsIgnoreCase("")){
                        res = new Message(message.getSenderName(), "","Une partie est déjà en cours, veuillez attendre que le jeu soit terminé ou rejoindre si ce n'est pas déjà complet",Status.REFUSED,null,null);
                    }else {
                        res = testParticipation(message);
                    }

                }else if(message.getMessage().equalsIgnoreCase("")){
                    res = new Message(message.getSenderName(), "","Veuillez créer une partie",Status.REFUSED,null,null);
                }else {
                    try {
                        int newMaxPoint = Integer.parseInt(message.getMessage());
                        if(newMaxPoint>0){
                            maxPoint = newMaxPoint;
                            reponse = testParticipation(message);
                            res=reponse;
                        }else {
                            res = new Message(message.getSenderName(), "","Veuillez entrer un maximum de points supérieurs à 0",Status.REFUSED,null,null);
                        }

                    } catch (NumberFormatException e) {
                        res = new Message(message.getSenderName(), "","Veuillez entrer un maximum de points valide",Status.REFUSED,null,null);
                    }
                }

        }


        if(joueursConnecte==nbJoueurs){
            pointJoueurs = endGameController.initialisePoint(pointJoueurs);
            dominosJoueurs = dominoController.InitialiseDomino();

            for(int j=0; j<nbJoueurs; j++) {
                //System.out.println("Vos dominos/"+enChaine(dominosJoueurs[j]));
                sDominosJoueurs[j]=enChaine(dominosJoueurs[j]);
            }
            Message newMessage = new Message(reponse.getSenderName(), nomJoueurs[0],"Vous commencer le jeux",Status.JOIN,this.sDominosJoueurs,null);
            res=newMessage;
            joueursConnecte++;

        }

        return res;


    }

    @MessageMapping("/discussion")
    @SendTo("/chat")
    public Message recMessage(@Payload Message message){
        System.out.println(message.toString());
        return message;
    }

    public Message testParticipation(Message message){
        Message reponse = message;
        Boolean isAccepted = true;

        if(joueursConnecte< nbJoueurs){

            for(int j=0;j<nbJoueurs;j++) {
                if(nomJoueurs[j] != null && nomJoueurs[j].equalsIgnoreCase(message.getSenderName())){
                    isAccepted = false;
                }
            }

        }else{
            isAccepted = false;
        }

        if(!isAccepted){
            Message newMessage = new Message(message.getSenderName(), "","Vous ne pouvez pas jouer pour le moment",Status.REFUSED,null,null);
            reponse = newMessage;
        } else {
            nomJoueurs[joueursConnecte]= message.getSenderName();
            joueursConnecte++;
        }
        return reponse;
    }

    public static String[] enChaine(Vector<Domino> ds)
    {
        String res="";
        String rep[] = new String[ds.size()];

        for(int i=0; i< ds.size();i++)
        {
            String s=ds.get(i).getGauche()+":"+ds.get(i).getDroite();
            rep[i]=s;
        }
        return rep;

    }


    public void  reInitialise (){
        passer = 0;
        joueursConnecte=0;
        tete=1;
        tour=1;
        nomJoueurs =new String[nbJoueurs];
        pointJoueurs = new int[nbJoueurs];
        maxPoint=0;
    }

}