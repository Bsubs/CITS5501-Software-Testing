sig Movie {directSequel: lone Movie, Sequels : set Movie}

fact noTimeTravel {
	all m : Movie | m.directSequel != m
}

fact noLoopingSequels {
	all a, b : Movie | b in a.directSequel implies b.directSequel != a
}

fact directSequelIsSequel {
	all a, b : Movie | b in a.directSequel implies b in a.Sequels 
}

fact indirectSequels {
	all m, n, a : Movie | (m in a.Sequels and n in m.directSequel) implies n in a.Sequels
}


pred emptyPred () {}

run emptyPred for 2 Movie
