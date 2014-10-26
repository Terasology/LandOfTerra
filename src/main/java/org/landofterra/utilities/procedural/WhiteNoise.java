/*
 * Copyright 2013 MovingBlocks
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
package org.landofterra.utilities.procedural;

import org.landofterra.utilities.random.BitScrampler;
import org.terasology.utilities.procedural.Noise2D;
import org.terasology.utilities.procedural.Noise3D;

/**
 * Deterministic white noise generator
 * @author Esereja
 */
public class WhiteNoise implements Noise2D, Noise3D {
	///TODO Broken
	
	long seed;
    /**
     * Initialize permutations with a given seed
     *
     * @param seed a seed value used for permutation shuffling
     */
    public WhiteNoise(long seed) {
       this.seed=seed;
    }


    /**
     * 2D semi white noise
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @return a noise value in the interval [-1,1]
     */
    @Override
    public float noise(float xin, float yin) {
        return 1;
    }

    /**
     * 3D semi white noise
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @param zin the z input coordinate
     * @return a noise value in the interval [-1,1]
     */
    @Override
    public float noise(float xin, float yin, float zin) {
    	int x=Float.floatToRawIntBits(xin);
    	int y=Float.floatToRawIntBits(yin);
    	int z=Float.floatToRawIntBits(zin);
    	int s=Float.floatToRawIntBits(seed);
    	
    	int i1 =BitScrampler.ScrampleInt(x+s)^BitScrampler.ScrampleInt(y+s)^BitScrampler.ScrampleInt(z+s);
    	//int i1 = s^(x>>8)^(y>>16)^(~z>>24);
    	
    	//scrample(i1);

    	float result = Float.intBitsToFloat(i1);
    	
    	if(result <= 1 && result >= -1){
    		return result;
        }else{
        	return 1/result;
        }
    }


    /**
     * 4D semi white noise
     *
     * @param xin the x input coordinate
     * @param yin the y input coordinate
     * @param zin the z input coordinate
     * @return a noise value in the interval [-1,1]
     */
    public float noise(float xin, float yin, float zin, float win) {
        return 1;
    }

}
