package com.ashleyjain.iitdcomplaintsystem;

/**
 * Created by chandudasari on 27/03/16.
 */
public class complaintObject {
    private String title;
    private String description;
    private String created_by;
    private String created_at;

    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setCreated_by(String created_by){
        this.created_by = created_by;
    }
    public void setCreated_at(String created_at){
        this.created_at = created_at;
    }

    complaintObject(String title, String description, String created_by, String created_at){
        this.title = title;
        this.description = description;
        this.created_by = created_by;
        this.created_at= created_at;
    }
    public String getTitle(){
        return title;
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
}
