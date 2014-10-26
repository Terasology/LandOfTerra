/*
 * Copyright 2014 MovingBlocks
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
package org.landofterra.world.generator.facetProviders;

import javax.vecmath.Vector3f;

import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.PerlinNoise;
import org.terasology.utilities.procedural.SubSampledNoise3D;
import org.terasology.world.generation.Facet;
import org.terasology.world.generation.FacetProvider;
import org.terasology.world.generation.GeneratingRegion;
import org.terasology.world.generation.Updates;
import org.terasology.world.generation.facets.InfiniteGenFacet;

@Updates(@Facet(InfiniteGenFacet.class))
public class Perlin3DTerainProvider implements FacetProvider {

    private SubSampledNoise3D surfaceNoise;
    
    private Vector3f zoom;
    private int octaves;
    private int seedOffSet;
    
    private double modulus;
    private double multificator;
    private double increase;

    public Perlin3DTerainProvider(){
    	/* some presets for zoom:
    	 * 0.00085f, 0.0007f, 0.00085f big and mighty mountains, add freg 0.5 and mult 2 to get some character
    	 * 0.0025f, 0.01f, 0.0025f plate mountains
    	 */
    	zoom=new Vector3f(0.00085f, 0.0007f, 0.00085f);
    	octaves=10;
    	seedOffSet=0;
    	
    	modulus=0;
    	multificator=1;
    	increase=0;
    }
    
    public Perlin3DTerainProvider(int seedOffSet,Vector3f zoom,int octaves,double modulus, double multificator,double increase){
    	this.zoom=zoom;
    	this.octaves=octaves;
    	this.seedOffSet=seedOffSet;
    	
    	this.modulus=modulus;
    	this.multificator=multificator;
    	this.increase=increase;
    }
    
    
    @Override
    public void setSeed(long seed) {
        surfaceNoise = new SubSampledNoise3D(new BrownianNoise3D(new PerlinNoise(seed+seedOffSet),octaves), zoom, 4);
    }
    
    @Override
    public void process(GeneratingRegion region) {
        InfiniteGenFacet facet =  region.getRegionFacet(InfiniteGenFacet.class);
        float[] noise = surfaceNoise.noise(facet.getWorldRegion());
       
        float[] orginalData = facet.getInternal();
        for(int i=0;orginalData.length>i;i++){
        	noise[i]*=multificator;
        	if(modulus!=0){
        		noise[i]=(float) (noise[i] %modulus);
        	}
        	noise[i]+=increase;
        	orginalData[i]+=noise[i];
        }
    }


	/**
	 * @return the zoom
	 */
	public Vector3f getZoom() {
		return zoom;
	}


	/**
	 * @param zoom the zoom to set
	 */
	public void setZoom(Vector3f zoom) {
		this.zoom = zoom;
	}


	/**
	 * @return the octaves
	 */
	public int getOctaves() {
		return octaves;
	}


	/**
	 * @param octaves the octaves to set
	 */
	public void setOctaves(int octaves) {
		this.octaves = octaves;
	}


	/**
	 * @return the frequency
	 */
	public double getFrequency() {
		return modulus;
	}


	/**
	 * @param frequency the frequency to set
	 */
	public void setFrequency(double frequency) {
		this.modulus = frequency;
	}


	/**
	 * @return the multificator
	 */
	public double getMultificator() {
		return multificator;
	}


	/**
	 * @param multificator the multificator to set
	 */
	public void setMultificator(double multificator) {
		this.multificator = multificator;
	}
}