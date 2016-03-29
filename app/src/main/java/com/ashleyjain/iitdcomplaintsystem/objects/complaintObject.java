package com.ashleyjain.iitdcomplaintsystem.objects;

/**
 * Created by chandudasari on 27/03/16.
 */
public class complaintObject {
    private String title;
    private String description;
    private String no_of_votes;
    private String created_at;
    private Integer department_;

    public void setTitle(String title){
        this.title = title;
    }
    public void setDescription(String description){
        this.description = description;
    }
    public void setVotes(String no_of_votes){
        this.no_of_votes = no_of_votes;
    }
    public void setCreated_at(String created_at){
        this.created_at = created_at;
    }
    public void setDepartment_(Integer department_){
        this.department_ = department_;
    }

    public complaintObject(String title, String description, String no_of_votes, String created_at, Integer department_){
        this.title = title;
        this.description = description;
        this.no_of_votes = no_of_votes;
        this.created_at= created_at;
        this.department_ = department_;
    }
    public String getTitle(){
        return title;
    }
    public String getDescription(){
        return description;
    }
    public String getVotes(){
        return no_of_votes;
    }
    public String getCreated_at(){
        return created_at;
    }
    public Integer getDepartment_(){
        return department_;
    }
}
