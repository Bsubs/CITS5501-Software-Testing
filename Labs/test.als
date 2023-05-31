sig Door  { house: one House }
sig House { doors: some Door }

fact HouseDoorInverse {
  all h: House, d: Door | d in h.doors implies d.house = h
}

pred example() {
}

run example for exactly 2 House, 2 Door
