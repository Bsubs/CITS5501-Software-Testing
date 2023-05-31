sig DomAnimal {}
sig Canine {}
sig Dog{}

fact DogIsDog {
	DomAnimal & Canine = Dog
}

// As before -- the above will give warnings.
//
// Code that runs without warnings:
//
sig Animal {}
sig DomesticatedAnimal in Animal {}
sig Canine in Animal {}
sig Dog in Canine {}
//
 fact { Dog = DomesticatedAnimal & Canine }
