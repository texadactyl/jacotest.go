// Hacked from https://cs.android.com/android/platform/superproject/+/master:packages/modules/Wifi/service/tests/wifitests/src/com/android/server/wifi/util/KalmanFilterTest.java
// Comments supplied by texadactyl@github

// Kalman Filter tutorial: https://www.kalmanfilter.net/
// Kalman Filter "in pictures": https://www.bzarg.com/p/how-a-kalman-filter-works-in-pictures/
// Kalman Filter "explained simply": https://thekalmanfilter.com/kalman-filter-explained-simply/

/*
 * Copyright (C) 2017 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

public class main {

    /**
     * Set up and use a Kalman filter to behave as as a phase-locked loop.
     *
     * This is a 2D model that generates sinusoidal output at a fixed frequency.
     * The state transformation is just a rotation by a fixed angle in radians.
     * The output matrix projects one of the dimensions. 
     */
     
    final static double PI = 3.14159265;

	// Model parameters:
    final static double mAmplitude = 10.0;	// Maximum ideal signal value
    final static double mStepSizeRadians = PI / 17.01; // Step angle in radians
    final static int mTransitionPoint = 1000; // Every transition point, do an abrupt phase shift
    final static double mTransitionFactor = 0.75; // Phase shift factor, multiplied by Pi
    final static double mNoiseAmplitude = 1000000.0; // Maximum noise value, initial measurement variance value in KalmanFilter.mR matrix
    final static int mSteps = 20000; // Number of loop steps
    final static int mSeed = 42; // Random seed if nonzero
    final static double mModelVariance = 0.5; // Initial variance value for the KalmanFilter.mQ matrix
    final static double mInitialStateVariance = 10000.0; // // Initial variance value for the KalmanFilter.mP matrix

	/**
	 * Return 0 if the input expression is true; else, return 1.
	 */
	private static int assertTrue(String label, boolean boolExpr) {
		Helpers hh = new Helpers();
		if (boolExpr) {
			hh.printLabeledString(label, "true");
			return 0;
		}
		System.out.print("*** ERROR, ");
		hh.printLabeledString(label, "false");
		return 1;
	}

    /**
     * Initialise the Kalman Filter matrices. 
     */
    private static KalmanFilter initializePLL(double stepSizeRadians,
                                       double modelVariance,
                                       double noiseAmplitude,
                                       double initialStateVariance) {
                                       
        KalmanFilter kf = new KalmanFilter();
        Helpers hh = new Helpers();
        
        // Initialize state transition matrix
        double cos = Math.cos(stepSizeRadians);
        double sin = Math.sin(stepSizeRadians);
        System.out.print("initializePLL: cosine: ");
        System.out.print(cos);
        System.out.print(", sine: ");
        System.out.println(sin);
        System.out.print("initializePLL: modelVariance: ");
        System.out.print(modelVariance);
        System.out.print(", noiseAmplitude: ");
        System.out.print(noiseAmplitude);
        System.out.print(", initialStateVariance: ");
        System.out.println(initialStateVariance);
        kf.mF = new Matrix( 2, new double [] { cos, sin, -sin, cos } );
        
        // Initialize process noise covariance matrix
        kf.mQ = new Matrix( 2, new double [] { modelVariance, 0.0, 0.0, modelVariance } );
        
        // Initialize state-to-measurement matrix
        kf.mH = new Matrix( 2, new double [] {1.0, 0.0} );
        
        // Initialize measurement covariance matrix
        kf.mR = new Matrix( 1, new double [] { noiseAmplitude * noiseAmplitude } );
        
        // Initialize state covariance matrix
        kf.mP = new Matrix( 2, new double [] { initialStateVariance, 0.0, 0.0, initialStateVariance } );
        
        // Initialize state matrix to zeros
        kf.mx = new Matrix(2, 1);
        
        hh.printLabeledString("initializePLL: Initialised Kalman Filter matrices", kf.toString());
        
        return kf;
    }

    /**
     * Generates the ideal signal at time step ii.
     * 
     * Essentially, the ideal signal function is a sinusoid.
     * An abrupt phase shift at every transition point occurs to test transient response.
     */
    private static double idealSignal(int ii) {
    
    	// phi = angle in radians for the next step
        double phi = mStepSizeRadians * ii;
        
        // At a transition point?
        if (ii % mTransitionPoint == 0) {
            phi = phi + PI * mTransitionFactor; // Yes - abrupt adjustment!
        }
        return mAmplitude * Math.sin(phi);
    }

    /** 
     * Using a phase locked loop, the Kalman filter results in a residual that should be
     * a lot smaller than the noise in the signal.
     *
     * The number of errors (integer) are returned to caller.
     */
    public static int testPhaseLockedLoop() {
    	Helpers hh = new Helpers();
    	int errorCount = 0;
    	Random random = new Random(mSeed);
        double [] filtered = new double[mSteps];
        double [] residual = new double[mSteps];
        
        // Generate the noise array
        double [] noise = new double[mSteps];
        for (int ii = 0; ii < mSteps; ii++) {
            noise[ii] = random.nextDouble() * mNoiseAmplitude;
        }
        
        // Initialise the Kalman Filter
        KalmanFilter kf = initializePLL(mStepSizeRadians, mModelVariance, mNoiseAmplitude, mInitialStateVariance);
        if (kf == null) {
        	System.out.println("*** ERROR, initializePLL() returned a null KalmanFilter");
        	return ++errorCount;
        }
        
        // Run the loop
        System.out.print("testPhaseLockedLoop: mSteps: ");
        System.out.println(mSteps);
        for (int ii = 0; ii < mSteps; ii++) {
            kf.predict();
            kf.update(new Matrix(1, new double[] {idealSignal(ii) + noise[ii]}));
            filtered[ii] = kf.mx.get(0, 0);
            residual[ii] = filtered[ii] - idealSignal(ii);
        }
        System.out.print("testPhaseLockedLoop: residual array (1st 3): ");
        System.out.print(residual[0]);
        System.out.print(" ");
        System.out.print(residual[1]);
        System.out.print(" ");
        System.out.println(residual[2]);
        
        // Compute residual mean and variance
        double sum = 0.0;
        double sumSquares = 0.0;
        for (int ii = 0; ii < mSteps; ii++) {
            sum += residual[ii];
            sumSquares += residual[ii] * residual[ii];
        }
        double dSteps = (double) mSteps;
        double mean = sum / dSteps;
        double variance = (sumSquares - sum * sum) / (dSteps * dSteps);
        System.out.print("Residual mean: ");
        System.out.print(mean);
        System.out.print(", variance: ");
        System.out.println(variance);
        errorCount += assertTrue("abs(mean) < 0.5", Math.abs(mean) < 0.5);
        errorCount += assertTrue("abs(variance) < 0.5", Math.abs(variance) < 0.5);
        
        return errorCount;
    }

    /**
     * Entry point
     */
	public static void main(String [] args) {
	
		System.out.println("Exercise Kalman filtering");
		
		// Execute the phase-locked loop
		int errorCount = testPhaseLockedLoop();
		
		// How did we do?
        assert (errorCount == 0);
        
	}
	
}
