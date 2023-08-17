package main

import (
	"fmt"
	"sync"
	"time"
)

const IMAX = 10000000000

func main() {
	var mutex sync.Mutex
	var elapsedSecs float64

	tStart := time.Now()
	for ii := 0; ii < IMAX; ii++ {
		mutex.Lock()   // Is the map still being built by initialisation?
		mutex.Unlock() // Immediately unlock.
	}
	tStop := time.Now()
	elapsedMillies := tStop.Sub(tStart).Milliseconds()
	elapsedSecs = float64(elapsedMillies) / 1000.0
	fmt.Printf("Finished in %0.3f seconds after %d million loops\n", elapsedSecs, IMAX/1000000)
	fmt.Printf("Cost = %0.3f seconds per loop\n", elapsedSecs/float64(IMAX))

}
