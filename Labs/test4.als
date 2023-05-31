sig Activity {}
sig Person { hobbies: set Activity }
sig ComputerScientist extends Person {}
sig Economists extends Person {}
sig Student extends Person {}
sig Bots {}

fact csNoLife{
    all cs : ComputerScientist | #cs.hobbies = 0
}

fact econBoring {
	all ec :Economists | lone ec.hobbies
}

fact studentsInteresting {
	all st:Student | some st.hobbies
}




