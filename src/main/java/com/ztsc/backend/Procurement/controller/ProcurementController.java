package com.ztsc.backend.Procurement.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/procurement")
public class ProcurementController {

    @GetMapping("/dashboard")
    public String dashboard() {
        return "Welcome to Procurement Dashboard";
    }
}