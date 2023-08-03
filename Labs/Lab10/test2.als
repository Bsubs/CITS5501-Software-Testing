sig LectureTheatre {}

sig Venue {}

fact evertLtIsVenue {
	LectureTheatre in Venue
}

// For no warning:

sig LectureTheatre1 extends Venue {}

// or

sig Lecture in Venue {}
