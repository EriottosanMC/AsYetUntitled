package asyetuntitled.common.entity;

import asyetuntitled.AsYetUntitled;
import asyetuntitled.common.entity.backpack.EntityBackpack;
import asyetuntitled.common.entity.livingchest.LivingChest;
import asyetuntitled.common.entity.livingchest.Staff;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class EntityRegistry {
	
	public static final String[] NAMES = {"Acceptor", "Affecter", "Arrester", "Assessor", "Bester", "Begetter", "Bisector", "Chester", "Cirencester", 
			"Compressor", "Confessor", "Connector", "Corrector", "Defector", "Deflector", "Dexter", "Ector", "Esther", "Ejector", "Elector", 
			"Fester", "Fermentor", "Flexer", "Forgetter", "Guesture", "Getter", "Guesser", "Hester", "Hector", "Hessler", "Impressor", "Injector",
			"Inspector", "Interceptor", "Inventor", "Investor", "Jester", "Kesler", "Kester", "Leicester", "Lecter", "Lessor", "Letter", "Mentor", "Mester",
			"Messner", "Midwester", "Nester", "Nestor", "Nectar", "Northwester", "Objector", "Oppressor", "Pecker", "Pester", "Petter", "Petr", "Polyester",
			"Possessor", "Preceptor", "Presenter", "Prester", "Preventer", "Professor", "Projector", "Protector", "Quester", "Reassesser", "Receptor", 
			"Reflector", "Regressor", "Representer", "Requestor", "Respector", "Resurrector", "Rhetor", "Sceptre", "Sector", "Selector", "Semester",
			"Sensor", "Senter", "Sequester", "Setter", "Silvester", "Stentor", "Stressor", "Successor", "Suppressor", "Sweater", "Sylvester",
			"Tesla", "Texter", "Tester", "Textor", "Transgressor", "Trimester", "Undresser", "Upsetter", "Vector", "Vester", "Vesper", "Vetter", "Webster",
			"Wester", "Wetter", "Xester", "Yester", "Zester"};

	private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(ForgeRegistries.ENTITIES, AsYetUntitled.MODID);
	
	public static final RegistryObject<EntityType<EntityBackpack>> BACKPACK = ENTITIES.register("backpack",
					() -> EntityType.Builder.<EntityBackpack>of(EntityBackpack::new, MobCategory.MISC)
					.sized(0.7F, 0.8F)
					.clientTrackingRange(10)
					.build("backpack"));
	
	public static final RegistryObject<EntityType<LivingChest>> LIVING_CHEST = ENTITIES.register("living_chest", 
					() -> EntityType.Builder.<LivingChest>of(LivingChest::new, MobCategory.CREATURE)
					.sized(0.8F, 0.8F)
					.clientTrackingRange(10)
					.build("living_chest"));
	
	public static final RegistryObject<EntityType<Staff>> STAFF = ENTITIES.register("staff", 
					() -> EntityType.Builder.<Staff>of(Staff::new, MobCategory.MISC)
					.sized(0.2F, 0.8F)
					.clientTrackingRange(10)
					.build("staff"));
	
	public static final RegistryObject<EntityType<ShadowSpider>> SHADOW_SPIDER = ENTITIES.register("shadow_spider", 
			() -> EntityType.Builder.<ShadowSpider>of(ShadowSpider::new, MobCategory.MONSTER)
			.sized(0.8F, 0.8F)
			.clientTrackingRange(10)
			.build("shadow_spider"));
	
	public static final RegistryObject<EntityType<ShadowChicken>> SHADOW_CHICKEN = ENTITIES.register("shadow_chicken", 
			() -> EntityType.Builder.<ShadowChicken>of(ShadowChicken::new, MobCategory.MONSTER)
			.sized(0.8F, 0.8F)
			.clientTrackingRange(10)
			.build("shadow_chicken"));
	
	public static void registerEntities(IEventBus bus) 
	{
		ENTITIES.register(bus);
	}
	
	
}
