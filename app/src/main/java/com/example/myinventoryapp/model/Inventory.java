package com.example.myinventoryapp.model;

public class Inventory {
    private String id, name, qty,loc, status;

    public Inventory(String name, String qty, String loc, String status){
        this.name = name;
        this.qty = qty;
        this.loc = loc;
        this.status = status;
    }

    public String getId(){
        return id;
    }
    public void setId(String id){
        this.id = id;
    }
    public String getName(){
        return name;
    }
    public  void  setName(){
        this.name = name;
    }
    public String getQty(){
        return  qty;
    }
    public void setQty(){
        this.qty = qty;
    }
    public String getLoc(){
        return  loc;
    }
    public void setLoc(){
        this.qty = loc;
    }
    public String getStatus(){
        return  status;
    }
    public void setStatus(){
        this.qty = status;
    }
}
