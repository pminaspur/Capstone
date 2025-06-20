import unittest
from Capstone_Project import Animal


class TestCapstoneProject(unittest.TestCase):


    def test_findAnimal(self):
        animal_program = Animal()
        self.assertEqual(animal_program.findAnimal("A746874"), "Animal found - {'animal_id': 'A746874'}", 'Animal not found .')
        

    def test_insertAnimal(self):
        animal_program1 = Animal()
        new_animal = {
            'rec_num': 52,
            'age_upon_outcome': '1 weeks',
            'animal_id': 'A729999',
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
        self.assertEqual(animal_program1.insertAnimal(new_animal), "Animal Inserted Successfully", "Fail to Insert Animal")

if __name__ == '__main__':
    unittest.main()