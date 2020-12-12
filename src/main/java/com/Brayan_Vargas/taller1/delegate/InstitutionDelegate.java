package com.Brayan_Vargas.taller1.delegate;

import com.Brayan_Vargas.taller1.model.Institution;

public interface InstitutionDelegate {

    public void GET_Institution(long id);
    public void GET_Institutions();
    public Institution POST_Institution(Institution institution);
    public void PUT_Institution(Institution institution);
    public void DELETE_Institution(Institution institution);
}