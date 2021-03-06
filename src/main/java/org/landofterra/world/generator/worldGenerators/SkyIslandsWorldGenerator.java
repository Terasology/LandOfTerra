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

import org.boundlessworlds.world.generator.facetProviders.Noise3DBaseTerainProvider;
import org.boundlessworlds.world.generator.facetProviders.Noise3DTerainProvider;
import org.boundlessworlds.world.generator.facetProviders.SimplePlanetSimulatorProvider;
import org.boundlessworlds.world.generator.rasterizers.DebugSolidRasterizer;
import org.terasology.core.world.generator.facetProviders.BiomeProvider;
import org.terasology.core.world.generator.facetProviders.PerlinHumidityProvider;
import org.terasology.core.world.generator.facetProviders.PerlinSurfaceTemperatureProvider;
import org.terasology.core.world.generator.facetProviders.SeaLevelProvider;
import org.terasology.engine.SimpleUri;
import org.terasology.math.geom.Vector3f;
import org.terasology.utilities.procedural.BrownianNoise3D;
import org.terasology.utilities.procedural.PerlinNoise;
import org.terasology.utilities.procedural.SimplexNoise;
import org.terasology.world.generation.BaseFacetedWorldGenerator;
import org.terasology.world.generation.WorldBuilder;
import org.terasology.world.generator.RegisterWorldGenerator;

@RegisterWorldGenerator(id = "skyislands", displayName = "SkyIslands")
public class SkyIslandsWorldGenerator extends BaseFacetedWorldGenerator {

    public SkyIslandsWorldGenerator(SimpleUri uri) {
        super(uri);
    }

    @Override
    protected WorldBuilder createWorld(long seed) {
    	SimplePlanetSimulatorProvider densityProv =new SimplePlanetSimulatorProvider();
    	densityProv.setOrigoOffSet(-500);
    	densityProv.setUpHeightMultiplifier(0.002f);
    	densityProv.setUpDensityFunction(2);
    	densityProv.setDownHeightMultiplifier(0.008f);
    	densityProv.setDownDensityFunction(1);
    	densityProv.setDensityMultifier(30);
    	densityProv.setDensityFunction(1);
        return new WorldBuilder(seed)
                .addProvider(new SeaLevelProvider(32))
                .addProvider( 
                		new Noise3DBaseTerainProvider(
                				new BrownianNoise3D(new SimplexNoise(seed),5),
                				new Vector3f(0.00085f, 0.0007f, 0.00085f),0,1,0
                				)
                		)
                .addProvider( 
                		new Noise3DTerainProvider(
                				new BrownianNoise3D(new SimplexNoise(seed),10),
                				new Vector3f(0.0025f, 0.01f, 0.0025f),0,1,0
                				)
                		)
                .addProvider( 
                		new Noise3DTerainProvider(
                				new BrownianNoise3D(new PerlinNoise(seed),9),
                				new Vector3f(0.00085f, 0.0007f, 0.00085f),1,2,0
                				)
                		)
                .addProvider( 
                		new Noise3DTerainProvider(
                				new PerlinNoise(seed),
                				new Vector3f(0.0012f, 0.0012f, 0.0012f),0,-3,0
                				)
                		)
                .addProvider( 
                		new Noise3DTerainProvider(
                				new PerlinNoise(seed),
                				new Vector3f(0.0001f, 0.0001f, 0.0001f),0,-1.5,0
                				)
                		)
                .addProvider(densityProv)		
                .addProvider(new PerlinHumidityProvider())
                .addProvider(new PerlinSurfaceTemperatureProvider())
                .addProvider(new BiomeProvider())
                .addRasterizer(new DebugSolidRasterizer())
                //.addRasterizer(new InfiniteGenSolidRasterizer())
                .addPlugins();
    }
}
