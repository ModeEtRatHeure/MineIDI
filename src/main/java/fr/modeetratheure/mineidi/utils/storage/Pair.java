package fr.modeetratheure.mineidi.utils.storage;

public class Pair <T, O>{

    private T first;
    private O second;

    public Pair(){
    }

    public Pair(T first, O second){
        this.first = first;
        this.second = second;
    }

    public void setFirst(T first){
        this.first = first;
    }

    public void setSecond(O second){
        this.second = second;
    }

    public T getFirst(){
        return first;
    }

    public O getSecond(){
        return second;
    }

    public void clear(){
        first = null;
        second = null;
    }

}
