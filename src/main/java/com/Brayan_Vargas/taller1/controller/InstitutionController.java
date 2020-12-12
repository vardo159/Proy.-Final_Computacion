package com.Brayan_Vargas.taller1.controller;

import com.Brayan_Vargas.taller1.model.Institution;
import com.Brayan_Vargas.taller1.service.InstitutionService;
import com.Brayan_Vargas.taller1.validation.newInstitution;
import javassist.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class InstitutionController {

    InstitutionService institutionService;

    @Autowired
    public InstitutionController(InstitutionService institutionService) {
        this.institutionService = institutionService;
    }

    @PreAuthorize("hasRole('admin')")
    @GetMapping("/Institution/")
    public String indexIntitution(Model model){
        model.addAttribute("institutions",institutionService.findAll());
        return"Institution/index";
    }

    @GetMapping("/Institution/add")
    public String addInstitution(Model model){
        model.addAttribute("institution", new Institution());
        return "Institution/add-institution";
    }

    @PostMapping("/Institution/add")
    public String saveInstitution(@Validated(newInstitution.class) Institution institution, BindingResult bindingResult,
                                  @RequestParam(value = "action", required = true) String action, Model model) {

        if(action.equals("Cancel"))
            return "redirect:/Institution/";

        if (bindingResult.hasErrors()) {
/*
            model.addAttribute("instName", institution.getInstName());
            model.addAttribute("instAcademicserverur", institution.getInstAcademicserverurl());
            model.addAttribute("instAcadextradataurl",institution.getInstAcadextradataurl());
            model.addAttribute("instAcadloginurl",institution.getInstAcadloginurl());
            model.addAttribute("instAcadpersoninfodocurl",institution.getInstAcadpersoninfodocurl());
            model.addAttribute("instAcadpersoninfoidurl",institution.getInstAcadpersoninfoidurl());
            model.addAttribute("instAcadphysicalspacesurl",institution.getInstAcadphysicalspacesurl());
            model.addAttribute("instAcadprogrammedcoursesurl",institution.getInstAcadprogrammedcoursesurl());
            model.addAttribute("instLdapurl",institution.getInstLdapurl());*/

            return "Institution/add-institution";
        }

        else if (!action.equals("Cancel")) {
            institutionService.saveInstitution(institution);
        }

        return "redirect:/Institution/";
    }


    @GetMapping("/Institution/edit/{id}")
    public String showUpdateInstitution(@PathVariable("id") long id, Model model) {
        Institution institution = institutionService.getInstitution(id);

        if (institution == null)
            throw new IllegalArgumentException("Invalid user Id:" + id);

        model.addAttribute("institution", institution);
        /*
        model.addAttribute("instName", institution.get().getInstName());
        model.addAttribute("instAcademicserverur", institution.get().getInstAcademicserverurl());
        model.addAttribute("instAcadextradataurl",institution.get().getInstAcadextradataurl());
        model.addAttribute("instAcadloginurl",institution.get().getInstAcadloginurl());
        model.addAttribute("instAcadpersoninfodocurl",institution.get().getInstAcadpersoninfodocurl());
        model.addAttribute("instAcadpersoninfoidurl",institution.get().getInstAcadpersoninfoidurl());
        model.addAttribute("instAcadphysicalspacesurl",institution.get().getInstAcadphysicalspacesurl());
        model.addAttribute("instAcadprogrammedcoursesurl",institution.get().getInstAcadprogrammedcoursesurl());
        model.addAttribute("instLdapurl",institution.get().getInstLdapurl());*/

        return "Institution/update-institution";
    }

    @PostMapping("/Institution/edit/{id}")
    public String updateInstitution(@PathVariable("id") long id,
                             @RequestParam(value = "action", required = true) String action, @Validated(newInstitution.class) Institution institution,
                             BindingResult bindingResult, Model model) {

        if (action.equals("Cancel")) {

            return "redirect:/Institution/";
        }

        if (bindingResult.hasErrors()) {
/*
            model.addAttribute("instName", institution.getInstName());
            model.addAttribute("instAcademicserverur", institution.getInstAcademicserverurl());
            model.addAttribute("instAcadextradataurl",institution.getInstAcadextradataurl());
            model.addAttribute("instAcadloginurl",institution.getInstAcadloginurl());
            model.addAttribute("instAcadpersoninfodocurl",institution.getInstAcadpersoninfodocurl());
            model.addAttribute("instAcadpersoninfoidurl",institution.getInstAcadpersoninfoidurl());
            model.addAttribute("instAcadphysicalspacesurl",institution.getInstAcadphysicalspacesurl());
            model.addAttribute("instAcadprogrammedcoursesurl",institution.getInstAcadprogrammedcoursesurl());
            model.addAttribute("instLdapurl",institution.getInstLdapurl());*/

            return "Institution/update-institution";
        }

        if (action != null && !action.equals("Cancel")) {
            institutionService.editInstitution(institution);
        }

        return "redirect:/Institution/";
    }

    @GetMapping("/Institution/consult")
    public String consultInstitution(Model model) {

        model.addAttribute("institution", new Institution());
        return "Institution/consult-institution";
    }

    @PostMapping("/Institution/consult")
    public String showConsultInstitution(@ModelAttribute Institution institution, Model model) throws NotFoundException {

        Institution inst = institutionService.getInstitution(institution.getInstId());

        if (inst == null) {
            throw new NotFoundException("USU NO ENCONTRADO");
        }

        model.addAttribute("institution", inst);

        return "Institution/update-institution";
    }

}