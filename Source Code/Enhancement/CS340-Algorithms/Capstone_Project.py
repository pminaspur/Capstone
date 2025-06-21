"""

@author: pushpalaxman_snhu
* Date					: 06/21/2025
 * Developer Name		: Pushpa Laxman
 * Program Detail		: This is the main method to instantiate Animal Shelter class to add
 *                        read, update and delete animal object.
 */
"""
#Import animal shelter module
from animal_module import AnimalShelter
#import the time module
import time

class Animal:

    def __init__(self):
        # Local variable for mongodb connections    
        DB = 'AAC'
        COL = 'animals'
        
        #Instantiate animal shelter 
        self.animalShelterClient = AnimalShelter(DB, COL)

    
    # Defining findAnimal function
    def findAnimal(self, animal):
         # Call read animal method to find the inserted animal object
        find_animal = { "animal_id": animal }
        found_animals = self.animalShelterClient.read(find_animal)
        if len(found_animals) > 0 :
            return "Animal found - " + str(find_animal)
        else:
            return "Animal not found - " + str(find_animal)


    # Defining findAnimals function
    def findAnimals(self, animal):
        if animal == 'test':
            return 'Animal found - ' + animal
        else:
             return "Animal not found - "  + animal
            

    # Defining insertAnimal function
    def insertAnimal(self, animal):        
        #Call animal create method to add new animal object 
        inserted_animal = self.animalShelterClient.create(animal)
        if inserted_animal:
            return "Animal Inserted Successfully"
        else:
            return "Fail to Insert Animal"

    # Defining udpateAnimal function
    def updateAnimal(self, animal_id, animal_color):
        # Call update animal method to update animal object
        update_animal = { "animal_id":animal_id  }
        data_update = {
            'color': animal_color,
        }
        no_of_animals_updated = self.animalShelterClient.update(update_animal, data_update)
        if no_of_animals_updated > 0 :
            return "Number of animals updated - "+str(no_of_animals_updated)
        else:
             return "Animal not found"

    # Defining deleteAnimal function
    def deleteAnimal(self, animal):
        # Call delete method to remove animal object
        delete_animal = { "animal_id": animal }
        no_of_animals_deleted = self.animalShelterClient.delete(delete_animal)
        if no_of_animals_deleted > 0 :
            return "Number of animals deleted - "+str(no_of_animals_deleted)
        else:
            return "Animal not found"

    # Defining Dog report function
    def dogReport(self):
         # Call read animal method to find the inserted animal object
        dog_report_query = [{"$match": {"animal_type": "Dog" , "breed": {"$in": ["Labrador Retriever Mix","Black Mouth Cur Mix", "Toy Fox Terrier Mix"]}}} , { "$group" : {"_id": "$breed", "totalDogBreeds": {"$sum": 1}}} , { "$sort": {"breed": 1 } }]
        dog_report = self.animalShelterClient.generate_dog_report(dog_report_query)
        if len(dog_report) > 0 :
            return dog_report
        else:
            return "Dog Report failed" 



    # Defining getUserInput function
    def getUserInput(self):

        # Prompting the user to enter operation to perform
        while True:
            lang = input("Please specify operation to perform(search, insert, update, delete, report, exit ) - ")
            match lang:
                case "search":
                    animal = input("Enter Animal Id to search - ")
                    animal_detail = self.findAnimal(animal)
                    print(animal_detail)
                case "insert":
                    animal_type = input("Enter Animal Type - ")
                    if animal_type not in ["Cat", "Dog"]:
                        print("Invalid Animal Type")
                        animal_type = input("Enter Valid  Animal Type - ")
                    animal_id = input("Enter Animal ID - ")
                    animal_name = input("Enter Animal Name - ")
                    animal_breed = input("Enter Animal Breed - ")
                    animal_color = input("Enter Animal Color - ")
                    animal_dob = input("Enter Animal Date Of Birth (yyyy-mm-dd)- ")
                    animal_sex = input("Enter Animal Sex - ")
                    if animal_sex not in ["Male", "Female"]:
                        print("Invalid Animal Sex")
                        animal_sex = input("Enter Valid  Animal Sex - ")
                    
                    new_animal = {
                        'rec_num': 52,
                        'age_upon_outcome': '1 weeks',
                        'animal_id': animal_id,
                        'animal_type': animal_type,
                        'breed': animal_breed,
                        'color': animal_color,
                        'date_of_birth': animal_dob,
                        'datetime': '2016-03-25 18:49:00',
                        'monthyear': '2016-03-25T18:49:00',
                        'name': animal_name,
                        'outcome_subtype': 'Partner',
                        'outcome_type': 'Transfer',
                        'sex_upon_outcome': animal_sex,
                        'location_lat': 30.3557252644792,
                        'location_long': -97.5743300659894,
                        'age_upon_outcome_in_weeks': 1.96914682539683
                    }
                    animal_detail = self.insertAnimal(new_animal)
                    print(animal_detail)
                case "update":
                    animal_id = input("Enter Animal Id to update - ")
                    animal_color = input("Enter Color to update - ")
                    animal_detail = self.updateAnimal(animal_id, animal_color)
                    print(animal_detail)
                case "delete":
                    animal = input("Enter Animal Id to delete - ")
                    animal_detail = self.deleteAnimal(animal)
                    print(animal_detail)
                case "report":
                    dogReport = self.dogReport()
                    print("Generating report")
                    for report in dogReport:
                        print( report)
                case _:
                    print("Thank you.")
                    break

   
# Using the special variable 
# __name__
if __name__=="__main__":
    try:
        animal_program = Animal()
        animal_program.getUserInput()
    except Exception as error:
        print("An Exception as Occured - ", error)



