#!/usr/bin/env python3
# -*- coding: utf-8 -*-
"""
Created on Fri Jul 26 07:58:13 2024
@author: pushpalaxman_snhu
* Date					: 08/04/2024
 * Developer Name		: Pushpa Laxman
 * Class Detail			: This class is for Animal shelter to add, read, update 
 *                        and delete the animal object to mongodb 
 */
"""

#Import mongoclient and object module
from pymongo import MongoClient
from bson.objectid import ObjectId
#create a new instance for animal shelter
class AnimalShelter(object):
    """ CRUD operations for Animal collection in MongoDB """

    def __init__(self,mongo_user, mongo_password, mongo_host, mongo_port, mongo_database, mongo_collection):
        # Initializing the MongoClient. This helps to 
        # access the MongoDB databases and collections.
       
        # Initialize Connection
        #
        self.client = MongoClient('mongodb://%s:%s@%s:%d/?authSource=%s' % (mongo_user,mongo_password,mongo_host,mongo_port,mongo_database))
        self.database = self.client['%s' % (mongo_database)]
        self.collection = self.database['%s' % (mongo_collection)]
        
# this create method to implement the C in CRUD and insert the date using insert one method
#if inserted will return true otherwise will return false
    def create(self, data):
        if data is not None:
            animal_id = self.database.animals.insert_one(data)  # data should be dictionary 
            if animal_id != 0:
                return True
            return False
        else:
            raise Exception("Nothing to save, because data parameter is empty")

# Method to read animals list from mongodb.
    def read(self,data):
        #creating an array of animal list
        animal_lists = []
        for animal in self.database.animals.find(data):
            #adding the animals in the list
            animal_lists.append(animal)            
        return animal_lists
    
# this update method to implement the U in CRUD.
    def update(self,animal_to_update, data):
        if data is not None and animal_to_update is not None:
            found_animals = self.read(animal_to_update)
            #if the only 1 animal is found then using update one method
            if len(found_animals) == 1 :
                animal_updated = self.database.animals.update_one(animal_to_update, { "$set": data})
                return animal_updated.modified_count
            #if more than 1 animal is found using update many method
            elif len(found_animals) > 1 :
                animal_updated = self.database.animals.update_many(animal_to_update, { "$set": data})
                return animal_updated.matched_count
            #if no animal found then return 0
            else:
                return 0
        # raise exception in case of no data or animal to update parameter is empty 
        else:
            raise Exception("Nothing to update, because data or animal to update parameter is empty")
            
# this delete method to implement the D in CRUD.
    def delete(self,animal_to_delete):
        if animal_to_delete is not None:
            found_animals = self.read(animal_to_delete)
            #if animal found is 1 then delete using delete one method
            if len(found_animals) == 1 :
                animal_deleted = self.database.animals.delete_one(animal_to_delete)
                return animal_deleted.deleted_count
            #if animal found is moret than 1 then delete using delete many method
            elif len(found_animals) > 1 :
                animal_deleted = self.database.animals.delete_many(animal_to_delete)
                return animal_deleted.deleted_count
            #if animal is not found then return 0
            else:
                return 0
            #raise excpetion in case no animal to delete
        else:
            raise Exception("Nothing to delete, because animal to delete parameter is empty")       