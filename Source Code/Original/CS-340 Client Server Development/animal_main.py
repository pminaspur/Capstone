#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Jul 26 08:00:34 2024

@author: pushpalaxman_snhu
* Date					: 08/04/2024
 * Developer Name		: Pushpa Laxman
 * Program Detail		: This is the main method to instantiate Animal Shelter class to add
 *                        read, update and delete animal object.
 */
"""
#Import animal shelter module
from animal_module import AnimalShelter
#import the time module
import time

if __name__ == "__main__":
    #try block to handle the code and raise exception
    try:
        # Local variable for mongodb connections    
        USER = 'aacuser' 
        PASS = 'SNHU1234' 
        HOST = 'nv-desktop-services.apporto.com'
        PORT = 30920
        DB = 'AAC'
        COL = 'animals'
        
        #Instantiate animal shelter 
        animal = AnimalShelter(USER, PASS, HOST, PORT, DB, COL)
        
        # New key value collection for animal 
        new_animal = {
            'rec_num': 52,
            'age_upon_outcome': '1 weeks',
            'animal_id': 'A723334',
            'animal_type': 'Cat',
            'breed': 'Domestic Shorthair Mix',
            'color': 'Brown Tabby',
            'date_of_birth': '2016-03-12',
            'datetime': '2016-03-25 18:49:00',
            'monthyear': '2016-03-25T18:49:00',
            'name': '',
            'outcome_subtype': 'Partner',
            'outcome_type': 'Transfer',
            'sex_upon_outcome': 'Intact Male',
            'location_lat': 30.3557252644792,
            'location_long': -97.5743300659894,
            'age_upon_outcome_in_weeks': 1.96914682539683
        }
        
        #Call animal create method to add new animal object 
        #and display the message through the print function
        inserted_animal = animal.create(new_animal)
        if inserted_animal:
            print("Animal Inserted Successfully")
        else:
            print("Fail to Insert Animal")
            
         #Creating a time delay of 3 seconds    
        time.sleep(3)
         
        # Call read animal method to find the inserted animal object
        #and display the message through the print function
        find_animal = { "animal_id": "A723334" }
        found_animals = animal.read(find_animal)
        if len(found_animals) > 0 :
            print("Animal found - " + str(find_animal))
        else:
            print("Animal not found - " + str(find_animal))
            
        #Creating a time delay of 3 seconds     
        time.sleep(3)
        
        # Call update animal method to update animal object
        #and display the message through the print function
        update_animal = { "animal_id": "A723334" }
        data_update = {
            'color': 'White',
            'name': 'Test_animal',
        }
        no_of_animals_updated = animal.update(update_animal, data_update)
        if no_of_animals_updated > 0 :
            print("Number of animals updated - "+str(no_of_animals_updated))
        else:
            print("Animal not found")
            
        #Creating a time delay of 3 seconds 
        time.sleep(3)
        
        # Call update animal method to update multi animal object
        #and display the message through the print function
        update_multi_animal = { "animal_type":"Cat","color": "Brown Tabby","outcome_type": "Euthanasia" }
        data_update = {
            'color': 'White',
            'name': 'Test_animal',
        }
        no_of_animals_updated = animal.update(update_multi_animal, data_update)
        if no_of_animals_updated > 0 :
            print("Number of animals updated - "+str(no_of_animals_updated))
        else:
            print("Animal not found")
        #Creating a time delay of 3 seconds     
        time.sleep(3)    
            
            
        # Call delete method to remove animal object
        #and display the message through the print function
        delete_animal = { "animal_id": "A723334" }
        no_of_animals_deleted = animal.delete(delete_animal)
        if no_of_animals_deleted > 0 :
            print("Number of animals deleted - "+str(no_of_animals_deleted))
        else:
            print("Animal not found")
            
        #Creating a time delay of 3 seconds 
        time.sleep(3)   
        
        #Call animal create method to handle exception
        #and display the message through the print function
        inserted_animal = animal.create(None)
        if inserted_animal:
            print("Animal Inserted Successfully")
        else:
            print("Fail to Insert Animal")
            
         #Creating a time delay of 3 seconds   
        time.sleep(3)
      #except block to handle the exception      
    except Exception as error:
        print("An Exception as Occured - ", error)
    
    