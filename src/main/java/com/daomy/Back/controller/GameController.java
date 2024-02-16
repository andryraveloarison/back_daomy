package com.daomy.Back.controller;

import com.daomy.Back.model.Domino;
import com.daomy.Back.controller.DominoController;
import com.daomy.Back.model.Message;
import com.daomy.Back.model.Status;
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
    int i =0;
    Vector<Domino> listeDomino = new Vector<>();
    Vector <Domino> dominosJoueurs[] = new Vector[4];

    String[][] sDominosJoueurs = new String[3][3];
    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @MessageMapping("/message")
    @SendTo("/chatroom/public")
    public Message receiveMessage(@Payload Message message){

        Message reponse = message;

        if(message.getStatus().toString().equalsIgnoreCase("JOIN")) {
            reponse = testParticipation(message);
        }else{
            if(nbJoueurs<i){
                tour = tour % nbJoueurs;

                Message newMessage = new Message(reponse.getSenderName(), nomJoueurs[tour],message.getMessage(),Status.MESSAGE,null);
                reponse=newMessage;
                tour++;
            }
        }

        if(i==nbJoueurs){

            DominoController dominoController = new DominoController();
            dominosJoueurs = dominoController.InitialiseDomino();

            for(int j=0; j<nbJoueurs; j++) {
                //System.out.println("Vos dominos/"+enChaine(dominosJoueurs[j]));
                sDominosJoueurs[j]=enChaine(dominosJoueurs[j]);
            }

            Message newMessage = new Message(reponse.getSenderName(), nomJoueurs[0],"Vous commencer le jeux",Status.JOIN,this.sDominosJoueurs);
            reponse=newMessage;
            i++;

        }


        return reponse;
    }

    @MessageMapping("/private-message")
    public Message recMessage(@Payload Message message){
        simpMessagingTemplate.convertAndSendToUser(message.getReceiverName(),"/private",message);
        System.out.println(message.toString());
        return message;
    }

    public Message testParticipation(Message message){
        Message reponse = message;
        Boolean isAccepted = true;
        if(i< nbJoueurs){

            for(int j=0;j<nbJoueurs;j++) {
                if(nomJoueurs[j] != null && nomJoueurs[j].equalsIgnoreCase(message.getSenderName())){
                    isAccepted = false;
                }
            }

        }else{
            isAccepted = false;
        }

        if(!isAccepted){
            Message newMessage = new Message(message.getSenderName(), "","",Status.REFUSED,null);
            reponse = newMessage;
        } else {
            nomJoueurs[i]= message.getSenderName();
            i++;
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

}