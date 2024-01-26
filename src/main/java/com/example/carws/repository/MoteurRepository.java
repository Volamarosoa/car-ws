/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/springframework/Repository.java to edit this template
 */
package com.example.carws.repository;

import com.example.carws.model.primaire.Moteur;
import org.springframework.data.repository.CrudRepository;

/**
 *
 * @author sarobidy
 */
public interface MoteurRepository extends CrudRepository<Moteur, String> {
          
}
