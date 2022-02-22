package UAW.content.blocks;

import UAW.content.*;
import UAW.graphics.*;
import UAW.world.blocks.gas.LiquidBoiler;
import UAW.world.blocks.production.*;
import arc.graphics.Color;
import gas.GasStack;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.effect.MultiEffect;
import mindustry.graphics.*;
import mindustry.type.*;
import mindustry.world.Block;
import mindustry.world.blocks.production.*;
import mindustry.world.draw.*;
import mindustry.world.meta.Attribute;

import static UAW.Vars.tick;
import static mindustry.Vars.tilesize;
import static mindustry.type.ItemStack.with;

/** Contains Production structures, such as factories, drills, pumps, etc */
public class UAWBlocksProduction implements ContentList {
	public static Block placeholder,
	// Drills
	oilDerrick, rotodynamicPump, steamDrill, steamBore, steamThumper,
	// Item Crafter
	gelatinizer, carburizingFurnace, petroleumSmelter, petrochemicalSeperator, plastaniumForge,
	// Liquid Mixer
	cryofluidDistillery, surgeMixer,
	// Gas
	steamKettle, coalBoiler, stirlingBoiler, geothermalKettle, solarBoiler;

	@Override
	public void load() {
		oilDerrick = new Fracker("oil-derrick") {{
			requirements(Category.production, with(
				Items.titanium, 150,
				Items.plastanium, 125,
				Items.thorium, 115,
				Items.metaglass, 85,
				Items.silicon, 85
			));
			size = 4;
			result = Liquids.oil;
			updateEffect = new MultiEffect(
				UAWFxD.smokeCloud(40, Layer.effect, Color.gray),
				Fx.oily
			);
			updateEffectChance = 0.1f;
			pumpAmount = 1.5f;
			liquidCapacity = 360f;
			attribute = Attribute.oil;
			baseEfficiency = 0.5f;
			rotateSpeed = -2.5f;

			squareSprite = false;

			consumes.liquid(Liquids.cryofluid, pumpAmount / 2.5f);
			consumes.power(3.5f);
		}};
		rotodynamicPump = new RotatingLiquidPump("rotodynamic-pump") {{
			requirements(Category.liquid, with(
				Items.lead, 150,
				Items.metaglass, 60,
				Items.silicon, 60,
				Items.plastanium, 60,
				UAWItems.titaniumCarbide, 40,
				Items.thorium, 70
			));
			size = 3;
			pumpAmount = 0.5f;
			liquidCapacity = 540f;
			rotateSpeed = -3f;

			consumes.item(UAWItems.cryogel, 4);
			consumes.power(2f);
		}};

		gelatinizer = new GenericCrafter("gelatinizer") {{
			requirements(Category.crafting, with(
				Items.lead, 45,
				Items.graphite, 30,
				Items.thorium, 20
			));
			consumes.items(
				new ItemStack(
					Items.sand, 2
				));
			consumes.liquid(Liquids.cryofluid, 0.25f);
			consumes.power(0.5f);
			outputItem = new ItemStack(
				UAWItems.cryogel, 1
			);
			craftTime = 45f;
			hasItems = true;
			hasLiquids = true;
			size = 2;
			drawer = new DrawLiquid();
			craftEffect = Fx.freezing;
			updateEffect = Fx.wet;
		}};
		cryofluidDistillery = new AdvancedGenericCrafter("cryofluid-distillery") {{
			requirements(Category.crafting, with(
				Items.lead, 150,
				Items.silicon, 90,
				Items.titanium, 120
			));
			size = 3;
			squareSprite = false;
			liquidCapacity = 240f;
			outputsLiquid = true;
			hasItems = true;
			hasLiquids = true;
			drawer = new DrawSmelter(UAWPal.cryoFront);
			craftTime = 2f * tick;
			updateEffect = new MultiEffect(Fx.wet, UAWFxD.smokeCloud(45, Layer.effect, Color.white));
			updateEffectChance = 0.01f;
			consumes.items(with(
				Items.titanium, 4,
				Items.thorium, 1
			));
			consumes.liquid(Liquids.water, 1.2f);
			outputLiquid = new LiquidStack(Liquids.cryofluid, 60f);
			consumes.power(3f);
		}};

		carburizingFurnace = new AdvancedGenericCrafter("carburizing-furnace") {{
			requirements(Category.crafting, with(
				Items.titanium, 150,
				Items.thorium, 125,
				Items.metaglass, 95,
				Items.silicon, 95,
				Items.graphite, 95
			));
			consumes.items(
				new ItemStack(Items.titanium, 4),
				new ItemStack(Items.coal, 8)
			);
			consumes.liquid(Liquids.slag, 1.5f);
			outputItem = new ItemStack(
				UAWItems.titaniumCarbide, 1
			);
			consumes.power(5.5f);
			hasItems = true;
			hasLiquids = true;
			size = 3;
			itemCapacity = 30;
			craftTime = 3.5f * tick;
			drawer = new DrawSmelter();
			craftEffect = new MultiEffect(
				UAWFxD.burstSmelt(3.5f * tilesize, Pal.missileYellow, Pal.missileYellowBack),
				Fx.flakExplosionBig
			);
			updateEffect = new MultiEffect(Fx.melting, Fx.burning, Fx.fireSmoke);
			craftShake = 8;
		}};
		surgeMixer = new GenericCrafter("surge-mixer") {{
			requirements(Category.crafting, with(
				Items.thorium, 30,
				Items.lead, 45,
				Items.silicon, 35,
				Items.metaglass, 20
			));
			size = 3;
			liquidCapacity = 120f;
			outputsLiquid = true;
			hasItems = true;
			hasLiquids = true;
			drawer = new DrawLiquid(true);
			craftTime = 2f * tick;
			updateEffect = new MultiEffect(Fx.shieldBreak, Fx.hitBulletSmall);
			consumes.items(with(
				Items.copper, 1,
				Items.titanium, 1,
				Items.silicon, 1
			));
			consumes.liquid(Liquids.oil, 0.25f);

			outputLiquid = new LiquidStack(UAWLiquids.surgeSolvent, 30f);
		}};

		plastaniumForge = new AdvancedGenericCrafter("plastanium-forge") {{
			requirements(Category.crafting, with(
				Items.lead, 225,
				Items.silicon, 160,
				Items.graphite, 160,
				Items.titanium, 300,
				Items.plastanium, 250
			));
			size = 3;
			health = 500 * size;
			squareSprite = false;
			hasItems = true;
			itemCapacity = 48;
			liquidCapacity = 280f;
			craftTime = 5.5f * tick;
			outputItem = new ItemStack(Items.plastanium, 14);
			hasPower = hasLiquids = true;
			craftEffect = new MultiEffect(
				UAWFxD.burstSmelt(2.5f * tilesize, Pal.plastaniumFront, Pal.plastaniumBack),
				Fx.plasticExplosion
			);
			craftShake = 6f;
			updateEffect = new MultiEffect(Fx.plasticburn, Fx.burning, Fx.fireSmoke);
			drawer = new DrawSmelter();

			consumes.liquid(Liquids.oil, 2.5f);
			consumes.items(
				new ItemStack(Items.titanium, 7),
				new ItemStack(UAWItems.anthracite, 2)
			);
		}};
		petroleumSmelter = new AdvancedGenericCrafter("petroleum-smelter") {{
			requirements(Category.crafting, with(
				Items.titanium, 125,
				Items.plastanium, 100,
				Items.thorium, 110,
				Items.metaglass, 85,
				Items.silicon, 85,
				Items.graphite, 95
			));
			size = 4;
			hasItems = true;
			hasLiquids = true;
			itemCapacity = 60;
			liquidCapacity = 360f;
			craftTime = 2.5f * tick;
			squareSprite = false;
			drawer = new DrawSmelter();
			craftEffect = new MultiEffect(
				UAWFxD.burstSmelt(4 * tilesize, Pal.plastaniumFront, Pal.plastaniumBack),
				Fx.flakExplosionBig
			);
			updateEffect = new MultiEffect(
				UAWFxS.plastHit,
				Fx.burning,
				Fx.fireSmoke
			);
			craftSoundVolume = 1.2f;
			craftShake = 15f;
			consumes.items(
				new ItemStack(Items.sand, 12),
				new ItemStack(Items.lead, 4),
				new ItemStack(UAWItems.anthracite, 3)
			);
			consumes.liquid(Liquids.oil, 3);
			outputItems = with(
				Items.silicon, 13,
				Items.metaglass, 13
			);
		}};
		petrochemicalSeperator = new EffectSeparator("petrochemical-seperator") {{
			requirements(Category.crafting, with(
				Items.lead, 120,
				Items.titanium, 100,
				Items.plastanium, 80,
				Items.silicon, 80
			));
			results = with(
				UAWItems.anthracite, 2,
				Items.coal, 2,
				Items.coal, 2
			);
			size = 3;
			craftTime = 2.5f * tick;
			itemCapacity = 30;
			squareSprite = false;
			liquidCapacity = 240f;
			updateEffect = new MultiEffect(Fx.fireHit, UAWFxS.plastHit, Fx.oily);

			consumes.liquid(Liquids.oil, 1.5f);
			consumes.item(Items.sand, 3);
		}};

		// Gasses
		steamKettle = new LiquidBoiler("steam-kettle") {{
			requirements(Category.power, with(
				Items.copper, 12,
				Items.lead, 12
			));
			size = 1;
			steamSize = 4f;
			steamEffectMult = 0.8f;
			warmupSpeed = 0.01f;
			waterAmount = 15f;
			consumes.items(new ItemStack(
				Items.coal, 1
			));
		}};
		coalBoiler = new LiquidBoiler("coal-boiler") {{
			requirements(Category.power, with(
				Items.copper, 35,
				Items.graphite, 25,
				Items.lead, 30
			));
			size = 2;
			warmupSpeed = 0.0015f;
			steamSize = 5f;
			steamEffectMult = 0.8f;
			waterAmount = 30;
			consumes.items(new ItemStack(
				Items.coal, 4
			));
		}};
		stirlingBoiler = new LiquidBoiler("stirling-boiler") {{
			requirements(Category.power, with(
				Items.copper, 150,
				Items.lead, 125,
				Items.titanium, 55,
				Items.graphite, 50
			));
			size = 4;
			warmupSpeed = 0.0002f;
			steamSize = 8f;
			steamEffectMult = 0.8f;
			waterAmount = 90;
			consumes.items(with(
				Items.coal, 6,
				Items.pyratite, 2
			));
		}};

	}
}