package com.daomy.Back.model;
import com.daomy.Back.model.FinalResult;
public class FinalResult {
    Result Result[];
    String winnerName;
    int winnerPoints;
    private Status status;
    String lastSender;
    String lastDomino;
    String lastAction;
    private String[][] newListeDominos;
    private int tete;

    private int pointJoueurs[];
    private boolean isFinished;

    public boolean isFinished() {
        return isFinished;
    }

    public int getTete() {
        return tete;
    }

    public String getLastAction() {
        return lastAction;
    }

    public String getLastSender() {
        return lastSender;
    }

    public String getLastDomino() {
        return lastDomino;
    }

    public int[] getPointJoueurs() {
        return pointJoueurs;
    }

    public Status getStatus() {
        return status;
    }

    public String[][] getNewListeDominos() {
        return newListeDominos;
    }

    public int getWinnerPoints() {
        return winnerPoints;
    }

    public com.daomy.Back.model.Result[] getResult() {
        return Result;
    }

    public String getWinnerName() {
        return winnerName;
    }

    public FinalResult(com.daomy.Back.model.Result[] result, String winnerName, int winnerPoints, Status status, String lastSender, String lastDomino, String lastAction, String[][] newListeDominos, int tete, int[] pointJoueurs, boolean isFinished) {
        Result = result;
        this.winnerName = winnerName;
        this.winnerPoints = winnerPoints;
        this.status=status;
        this.lastDomino=lastDomino;
        this.lastSender=lastSender;
        this.lastAction=lastAction;
        this.newListeDominos=newListeDominos;
        this.tete=tete;
        this.pointJoueurs=pointJoueurs;
        this.isFinished=isFinished;
    }
}
