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
package org.landofterra.world.generator.worldGenerators;

import javax.vecmath.Vector3f;

import org.landofterra.utilities.procedural.Turbulence3DAdapter;
import org.landofterra.world.generator.facetProviders.InfiniteGenDensityProvider;
import org.landofterra.world.generator.facetProviders.Noise3DBaseTerainProvider;
import org.landofterra.world.generator.rasterizers.DebugSolidRasterizer;
import org.terasology.core.world.generator.facetProviders.BiomeProvider;
import org.terasology.core.world.generator.facetProviders.PerlinHumidityProvider;
import org.terasology.core.world.generator.facetProviders.PerlinSurfaceTemperatureProvider;
import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;
import org.terasology.engine.SimpleUri;
import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;

@RegisterWorldGenerator(id = "test", displayName = "Test")
public class TestWorldGenerator extends BaseFacetedWorldGenerator {

    public TestWorldGenerator(SimpleUri uri) {
        super(uri);
    }

    @Override
    protected WorldBuilder createWorld(long seed) {
    	InfiniteGenDensityProvider densityProv =new InfiniteGenDensityProvider();
    	densityProv.setOrigoOffSet(+500);
    	densityProv.setUpHeightMultiplifier(0.001);
    	densityProv.setUpDensityFunction(2);
    	densityProv.setDownHeightMultiplifier(0.008);
    	densityProv.setDownDensityFunction(1);
    	densityProv.setDensityMultifier(30);
    	densityProv.setDensityFunction(1);
        return new WorldBuilder(seed)
                .addProvider(new SeaLevelProvider(32))
                //.addProvider(new Simplex3DBaseTerainProvider(1,new Vector3f(0.013f, 0.013f, 0.013f),5,0,1,+0.2))
                .addProvider( 
                		new Noise3DBaseTerainProvider(
                				new BrownianNoise3D(new Turbulence3DAdapter(new SimplexNoise(seed),new SimplexNoise(seed+1),new SimplexNoise(seed+2),new SimplexNoise(seed+3)),2)
                				,new Vector3f(0.005f, 0.005f, 0.005f),0,1,0
                				)
                		)
                /*.addProvider( 
                		new Noise3DBaseTerainProvider(
                				new BrownianNoise3D(new SimpleTurbulenceAdapter(new SimplexNoise(seed)),2)
                				,new Vector3f(0.005f, 0.005f, 0.005f),0,1,0
                				)
                		)
                */		
                //.addProvider(new Simplex3DTerainProvider(2,new Vector3f(0.0025f, 0.01f, 0.0025f),10,0,0.7,0))
                //.addProvider(new Cubic3DTerainProvider(2,new Vector3f(0.001f, 0.001f, 0.001f),12,0,1,-0.2))
                //.addProvider(new Perlin3DTerainProvider(4,new Vector3f(0.00085f, 0.0085f, 0.00085f),9,0,1,0))
                //.addProvider(new Perlin3DNoiseProvider(5,new Vector3f(0.0012f, 0.0012f, 0.0012f),0,-0.5,0))
                //.addProvider(new Perlin3DNoiseProvider(6,new Vector3f(0.0001f, 0.0001f, 0.0001f),0,-0.3,0))
                	//.addProvider(new Functional3DRemoverProvider(new Vector3f(0f, 0f, 0f),new Vector3f(100f, 100f, 100f),false,0))
                .addProvider(new PerlinHumidityProvider())
                .addProvider(new PerlinSurfaceTemperatureProvider())
                .addProvider(new BiomeProvider())
                .addProvider(densityProv)
                .addRasterizer(new DebugSolidRasterizer())
                //.addRasterizer(new InfiniteGenSolidRasterizer())
                .addPlugins();
    }
}