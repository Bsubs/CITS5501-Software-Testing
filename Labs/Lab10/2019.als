sig Report {
	submittedBy: one User,
	prerequisite: set Report
}

fact noCycle {
	all r : Report | r not in r.^prerequisite
}

sig User {}

pred atLeast5() {
	
}

run atLeast5 for 5
