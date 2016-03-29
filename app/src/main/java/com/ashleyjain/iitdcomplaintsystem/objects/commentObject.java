package com.ashleyjain.iitdcomplaintsystem.objects;

/**
 * Created by chandudasari on 27/03/16.
 */
public class commentObject {

    private String description;
    private String created_by;
    private String created_at;
    private Integer id;


    public void setDescription(String description){
        this.description = description;
    }
    public void setCreated_by(String created_by){
        this.created_by = created_by;
    }
    public void setCreated_at(String created_at){
        this.created_at = created_at;
    }
    public void setId(Integer id){
        this.id = id;
    }


    public commentObject(String description, String created_by, String created_at, Integer id){
        this.description = description;
        this.created_by = created_by;
        this.created_at= created_at;
        this.id= id;
    }

    public String getDescription(){
        return description;
    }
    public String getCreated_by(){
        return created_by;
    }
    public String getCreated_at(){
        return created_at;
    }
    public Integer getId(){
        return id;}
}
