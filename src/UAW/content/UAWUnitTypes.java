package UAW.content;

import UAW.entities.abilities.RazorRotorAbility;
import UAW.entities.bullet.AntiBuildingBulletType;
import UAW.entities.bullet.TorpedoBulletType;
import UAW.graphics.*;
import UAW.type.HelicopterUnitType;
import UAW.type.TankUnitType;

import arc.graphics.Color;
import mindustry.content.*;
import mindustry.ctype.ContentList;
import mindustry.entities.bullet.*;
import mindustry.entities.effect.MultiEffect;
import mindustry.gen.Sounds;
import mindustry.gen.UnitWaterMove;
import mindustry.graphics.Pal;
import mindustry.type.UnitType;
import mindustry.type.Weapon;
import mindustry.type.ammo.ItemAmmoType;
import mindustry.type.weapons.PointDefenseWeapon;
import mindustry.world.meta.BlockFlag;

import static mindustry.Vars.tilesize;
import static mindustry.content.Bullets.*;

public class UAWUnitTypes implements ContentList {
    public static UnitType
    // Air
        // Attack Helicopters
        aglovale, bedivere, calogrenant, dagonet, esclabor,
        // Bombers

    //Naval
        // Monitors [5]
        clurit, kujang, beladau, alamang, kerambit,
        // Torpedo Boats [4]
        hatsuharu, shiratsuyu, kagero, shimakaze,
    //Ground
        // Tanks
        gardlacz, arkabuz, muszkiet, karabin, armata;

    @Override
    public void load () {
        // region Air - Helicopters
        aglovale = new HelicopterUnitType("aglovale") {{
            health = 450;
            hitSize = 18;
            speed = 2.8f;
            accel = 0.5f;
            drag = 0.02f;
            ammoType = new ItemAmmoType(Items.graphite);

            flying = true;
            range = 150;
            circleTarget = true;

            offsetY = 2.5f;

            weapons.add(
                    new Weapon("uaw-missile-small-blue") {{
                        rotate = false;
                        mirror = true;
                        shootCone = 90;
                        x = 3.5f;
                        y = 0f;
                        shots = 2;
                        inaccuracy = 4;
                        reload = 12f;
                        shootSound = Sounds.missile;
                        bullet = Bullets.missileExplosive;
                    }},
                    new Weapon("uaw-machine-gun-small-blue") {{
                        rotate = false;
                        mirror = true;
                        shootCone = 90;
                        top = false;
                        x = 3.5f;
                        y = 7f;
                        reload = 8f;
                        shootSound = Sounds.shoot;
                        ejectEffect = Fx.casing1;
                        bullet = UAWBullets.standardMG;
                    }}
            );
        }};
        bedivere = new HelicopterUnitType("bedivere") {{
            health = 900;
            hitSize = 30;
            speed = 2.3f;
            accel = 0.5f;
            drag = 0.03f;
            ammoType = new ItemAmmoType(Items.graphite);

            flying = true;
            range = 280;
            circleTarget = true;
            bladeCount = 4;

            offsetY = 0f;

            weapons.add(
                    new Weapon("uaw-missile-medium-blue") {{
                        shots = 3;
                        rotate = false;
                        mirror = true;
                        shootCone = 100;
                        top = true;
                        x = 6f;
                        y = -2f;
                        shootY = -1;
                        reload = 24f;
                        shootSound = Sounds.missile;
                        bullet = Bullets.missileSurge;
                    }},
                    new Weapon("uaw-machine-gun-small-blue") {{
                        rotate = false;
                        mirror = true;
                        shootCone = 100;
                        top = false;
                        x = 5f;
                        y = 7f;
                        reload = 5f;
                        shootSound = Sounds.shoot;
                        ejectEffect = Fx.casing1;
                        bullet = UAWBullets.standardMG;
                    }}
            );
            abilities.add(new RazorRotorAbility(4, 10, 8 * tilesize));
        }};
        // endregion
        // region Naval - Monitor
        clurit = new UnitType("clurit") {{
            health = 750;
            speed = 0.75f;
            accel = 0.2f;
            rotateSpeed = 1.8f;
            drag = 0.05f;
            hitSize = 20;
            armor = 5;
            range = 30 * tilesize;
            maxRange = range;
            rotateShooting = false;
            ammoType = new ItemAmmoType(Items.graphite,2);

            trailLength = 22;
            trailX = 7f;
            trailY = -9f;
            trailScl = 1.4f;

            constructor = UnitWaterMove::create;

            weapons.add(
                    new PointDefenseWeapon("uaw-point-defense-purple") {{
                        rotate = autoTarget = true;
                        mirror = controllable = false;
                        x = 0;
                        y = 8f;
                        reload = 2.5f;
                        recoil = 0.1f;
                        reload = 5f;
                        targetInterval = 10f;
                        targetSwitchInterval = 15f;
                        ejectEffect = Fx.casing1;

                        bullet = new BulletType() {{
                            shootEffect = Fx.sparkShoot;
                            hitEffect = Fx.pointHit;
                            maxRange = 10f * tilesize;
                            damage = 10f;
                        }};
                    }},
                    new Weapon("uaw-machine-gun-small-purple") {{
                        rotate = mirror = autoTarget = true;
                        controllable = false;
                        x = 5f;
                        y = 0.4f;
                        inaccuracy = 4f;
                        reload = 10f;
                        shootSound = Sounds.shoot;
                        ejectEffect = Fx.casing2;
                        bullet = standardHoming;
                    }},
                    new Weapon("uaw-artillery-small-purple") {{
                        mirror = rotate = alternate = true;
                        x = 5.5f;
                        y = -8f;
                        targetFlags = new BlockFlag[]{BlockFlag.turret, null};
                        inaccuracy = 8f;
                        shootCone = 30;
                        rotateSpeed = 2.2f;
                        reload = 1.5f * 60;
                        recoil = 2.2f;
                        shootSound = Sounds.artillery;
                        shots = 1;
                        bullet =  new AntiBuildingBulletType(2f, 35, 2.5f) {{
                            splashDamageRadius = 6 * tilesize;
                            size = 32;
                            lifetime = range / speed;
                            status = StatusEffects.burning;
                            incendChance = 0.5f;
                            incendSpread = 16f;
                            makeFire = true;

                            frontColor = Pal.sapBullet;
                            backColor = Pal.sapBulletBack;
                            shootEffect = new MultiEffect(Fx.shootBig2, UAWFxStatic.shootSporeFlame);
                            smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.shootLiquid);
                            despawnEffect = hitEffect = new MultiEffect(UAWFxDynamic.crossBombBlast(backColor, splashDamageRadius), Fx.nuclearsmoke);

                            fragBullets = 9;
                            fragBullet = slagShot;
                            fragVelocityMin = 0.3f;
                            fragVelocityMax = fragVelocityMin * 1.2f;
                            fragLifeMin = 0.4f;
                            fragLifeMax = fragLifeMin * 1.8f;
                        }};
                    }}
            );
        }};
        kujang = new UnitType("kujang") {{
            health = 4500;
            hitSize = 30;
            speed = 0.65f;
            drag = 0.05f;
            accel = 0.2f;
            rotateSpeed = 1.5f;
            rotateShooting = false;
            range = 40 * tilesize;
            maxRange = range;
            ammoType = new ItemAmmoType(Items.graphite, 2);

            trailLength = 45;
            trailX = 9f;
            trailY = -9f;
            trailScl = 2.8f;

            constructor = UnitWaterMove::create;

            weapons.add(
                    new PointDefenseWeapon("uaw-point-defense-purple") {{
                        mirror = false;
                        targetAir = true;
                        x = 0f;
                        y = 13f;
                        recoil = 0.5f;
                        reload = 3f;
                        targetInterval = 10f;
                        targetSwitchInterval = 15f;
                        ejectEffect = Fx.casing1;

                        bullet = new BulletType() {{
                            shootEffect = Fx.sparkShoot;
                            hitEffect = Fx.pointHit;
                            maxRange = 15f * tilesize;
                            damage = 10f;
                        }};
                    }},
                    new Weapon("uaw-machine-gun-small-purple") {{
                        rotate = mirror = autoTarget = alternate = true;
                        controllable = false;
                        x = 8f;
                        y = 0.4f;
                        inaccuracy = 4f;
                        reload = 12f;
                        shootSound = Sounds.shoot;
                        ejectEffect = Fx.casing2;
                        bullet = fragGlass;
                    }},
                    new Weapon("uaw-artillery-medium-purple") {{
                        rotate = true;
                        mirror = false;
                        rotateSpeed = 1f;
                        x = 0f;
                        y = -12f;
                        targetFlags = new BlockFlag[]{BlockFlag.turret, null};
                        inaccuracy = 8f;
                        reload = 4f * 60;
                        recoil = 3f;
                        shootSound = Sounds.artillery;
                        shootCone = 30f;
                        bullet =  new AntiBuildingBulletType(2f, 120, 3.5f) {{
                            splashDamageRadius = 12 * tilesize;
                            size = 40;
                            lifetime = range / speed;
                            status = StatusEffects.burning;
                            incendChance = 0.8f;
                            incendSpread = 16f;
                            makeFire = true;

                            frontColor = Pal.sapBullet;
                            backColor = Pal.sapBulletBack;
                            shootEffect = new MultiEffect(Fx.shootBig2, UAWFxStatic.shootSporeFlame);
                            smokeEffect = new MultiEffect(Fx.shootBigSmoke2, Fx.shootLiquid);
                            despawnEffect = hitEffect = new MultiEffect(UAWFxDynamic.crossBombBlast(backColor, splashDamageRadius), Fx.nuclearsmoke);

                            fragBullets = 18;
                            fragBullet = heavySlagShot;
                            fragVelocityMin = 0.35f;
                            fragVelocityMax = fragVelocityMin * 1.2f;
                            fragLifeMin = 0.6f;
                            fragLifeMax = fragLifeMin * 1.8f;
                        }};
                    }}
            );
        }};
        // endregion
        // region naval - Torpedo Destroyers
        hatsuharu = new UnitType("hatsuharu") {{
            health = 800;
            speed = 1.2f;
            accel = 0.2f;
            rotateSpeed = 1.9f;
            drag = 0.05f;
            hitSize = 18;
            range = 32 * tilesize;
            maxRange = range;
            rotateShooting = false;
            ammoType = new ItemAmmoType(Items.graphite,2);

            trailLength = 25;
            trailX = 7f;
            trailY = -9f;
            trailScl = 1.4f;

            constructor = UnitWaterMove::create;

            weapons.add(
                    new PointDefenseWeapon("uaw-point-defense-red") {{
                        rotate = autoTarget = true;
                        mirror = controllable = false;
                        x = 0f;
                        y = 8.5f;
                        reload = 3f;
                        recoil = 0f;
                        targetInterval = 4f;
                        targetSwitchInterval = 4f;
                        ejectEffect = Fx.casing1;
                        bullet = new BulletType() {{
                            shootEffect = Fx.sparkShoot;
                            smokeEffect = Fx.shootSmallSmoke;
                            hitEffect = Fx.pointHit;
                            maxRange = range / 3.5f;
                            damage = 15f;
                        }};
                    }},
                    new Weapon("uaw-torpedo-small-red") {{
                rotate = true;
                mirror = false;
                rotateSpeed = 2;
                x = 0f;
                y = -10f;
                reload = 6 * 60;
                inaccuracy = 1f;
                ammoType = new ItemAmmoType(Items.thorium);

                shootSound = UAWSounds.TorpedoFire1;

                bullet = new TorpedoBulletType(1.8f, 650) {{
                    shootEffect = UAWFxStatic.shootWaterFlame;
                    lifetime = (range + 22 * tilesize) / speed;
                }};
            }}
            );
        }};
        // endregion naval - Torpedo Destroyers
        // region Ground - Tanks
        gardlacz = new TankUnitType("gardlacz") {{
            health = 450;
            armor = 15;
            hitSize = 13;
            speed = 1.5f;
            flying = false;
            ammoType = new ItemAmmoType(Items.graphite);

            accel = 0.05f;
            drag = 0.05f;
            range = 120;
            rotateShooting = false;

            weapons.add(
                    new PointDefenseWeapon("uaw-point-defense-blue") {{
                        rotate = autoTarget = true;
                        mirror = controllable = false;
                        x = 0f;
                        y = 5f;
                        reload = 1.5f;
                        rotateSpeed = 5.5f;
                        recoil = 0.1f;
                        reload = 6f;
                        targetInterval = 6f;
                        targetSwitchInterval = 7.5f;
                        ejectEffect = Fx.casing1;

                        bullet = new BulletType() {{
                            shootEffect = Fx.sparkShoot;
                            hitEffect = Fx.pointHit;
                            maxRange = 10f * tilesize;
                            damage = 10f;
                        }};
                    }},
                    new Weapon(name + "-turret") {{
                        targetFlags = new BlockFlag[]{BlockFlag.extinguisher, null};
                        rotate = true;
                        mirror = false;
                        rotateSpeed = 2.6f;
                        x = 0f;
                        y = -4.3f;
                        shootY = 16f;
                        reload = 40f;
                        recoil = 0f;
                        shootSound = UAWSounds.CannonShot1;
                        ejectEffect = Fx.casing2;
                        bullet = new BasicBulletType(4, 45) {{
                            height = 25f;
                            width = 10f;
                            recoil = 0.05f;
                            buildingDamageMultiplier = 2f;
                            pierceBuilding = true;
                            pierce = true;
                            pierceCap = 2;
                            lifetime = 50f;
                            trailLength = 13;
                            trailWidth = 2.5f;
                            trailColor = backColor;
                            shootSound = Sounds.shootBig;
                            shootEffect = new MultiEffect(UAWFxDynamic.railShoot(backColor, 22f), Fx.shootBigSmoke2);
                            hitEffect = despawnEffect = new MultiEffect(Fx.hitBulletBig, Fx.shootBigSmoke2);
                            fragBullets = 4;
                            fragLifeMin = 0f;
                            fragCone = 30f;
                            fragBullet = new BasicBulletType(7f, 6){{
                                buildingDamageMultiplier = 1.5f;
                                height = width = 8f;
                                pierce = true;
                                pierceBuilding = true;
                                pierceCap = 3;
                                lifetime = 30f;
                                hitEffect = Fx.flakExplosion;
                                splashDamage = damage;
                                splashDamageRadius = 8f;
                            }};
                        }};
                    }}
            );
        }};
        // endregion
    }
}