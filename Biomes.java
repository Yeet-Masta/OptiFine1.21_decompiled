import java.util.LinkedHashSet;
import java.util.Set;
import net.minecraft.src.C_1629_;
import net.minecraft.src.C_256686_;
import net.minecraft.src.C_5264_;
import net.minecraft.src.C_1629_.C_1634_;
import net.optifine.reflect.ReflectorClass;
import net.optifine.reflect.ReflectorField;

public abstract class Biomes {
   private static Set<C_5264_<C_1629_>> biomeKeys = new LinkedHashSet();
   public static final C_5264_<C_1629_> a = a("the_void");
   public static final C_5264_<C_1629_> b = a("plains");
   public static final C_5264_<C_1629_> c = a("sunflower_plains");
   public static final C_5264_<C_1629_> d = a("snowy_plains");
   public static final C_5264_<C_1629_> e = a("ice_spikes");
   public static final C_5264_<C_1629_> f = a("desert");
   public static final C_5264_<C_1629_> g = a("swamp");
   public static final C_5264_<C_1629_> h = a("mangrove_swamp");
   public static final C_5264_<C_1629_> i = a("forest");
   public static final C_5264_<C_1629_> j = a("flower_forest");
   public static final C_5264_<C_1629_> k = a("birch_forest");
   public static final C_5264_<C_1629_> l = a("dark_forest");
   public static final C_5264_<C_1629_> m = a("old_growth_birch_forest");
   public static final C_5264_<C_1629_> n = a("old_growth_pine_taiga");
   public static final C_5264_<C_1629_> o = a("old_growth_spruce_taiga");
   public static final C_5264_<C_1629_> p = a("taiga");
   public static final C_5264_<C_1629_> q = a("snowy_taiga");
   public static final C_5264_<C_1629_> r = a("savanna");
   public static final C_5264_<C_1629_> s = a("savanna_plateau");
   public static final C_5264_<C_1629_> t = a("windswept_hills");
   public static final C_5264_<C_1629_> u = a("windswept_gravelly_hills");
   public static final C_5264_<C_1629_> v = a("windswept_forest");
   public static final C_5264_<C_1629_> w = a("windswept_savanna");
   public static final C_5264_<C_1629_> x = a("jungle");
   public static final C_5264_<C_1629_> y = a("sparse_jungle");
   public static final C_5264_<C_1629_> z = a("bamboo_jungle");
   public static final C_5264_<C_1629_> A = a("badlands");
   public static final C_5264_<C_1629_> B = a("eroded_badlands");
   public static final C_5264_<C_1629_> C = a("wooded_badlands");
   public static final C_5264_<C_1629_> D = a("meadow");
   public static final C_5264_<C_1629_> E = a("cherry_grove");
   public static final C_5264_<C_1629_> F = a("grove");
   public static final C_5264_<C_1629_> G = a("snowy_slopes");
   public static final C_5264_<C_1629_> H = a("frozen_peaks");
   public static final C_5264_<C_1629_> I = a("jagged_peaks");
   public static final C_5264_<C_1629_> J = a("stony_peaks");
   public static final C_5264_<C_1629_> K = a("river");
   public static final C_5264_<C_1629_> L = a("frozen_river");
   public static final C_5264_<C_1629_> M = a("beach");
   public static final C_5264_<C_1629_> N = a("snowy_beach");
   public static final C_5264_<C_1629_> O = a("stony_shore");
   public static final C_5264_<C_1629_> P = a("warm_ocean");
   public static final C_5264_<C_1629_> Q = a("lukewarm_ocean");
   public static final C_5264_<C_1629_> R = a("deep_lukewarm_ocean");
   public static final C_5264_<C_1629_> S = a("ocean");
   public static final C_5264_<C_1629_> T = a("deep_ocean");
   public static final C_5264_<C_1629_> U = a("cold_ocean");
   public static final C_5264_<C_1629_> V = a("deep_cold_ocean");
   public static final C_5264_<C_1629_> W = a("frozen_ocean");
   public static final C_5264_<C_1629_> X = a("deep_frozen_ocean");
   public static final C_5264_<C_1629_> Y = a("mushroom_fields");
   public static final C_5264_<C_1629_> Z = a("dripstone_caves");
   public static final C_5264_<C_1629_> aa = a("lush_caves");
   public static final C_5264_<C_1629_> ab = a("deep_dark");
   public static final C_5264_<C_1629_> ac = a("nether_wastes");
   public static final C_5264_<C_1629_> ad = a("warped_forest");
   public static final C_5264_<C_1629_> ae = a("crimson_forest");
   public static final C_5264_<C_1629_> af = a("soul_sand_valley");
   public static final C_5264_<C_1629_> ag = a("basalt_deltas");
   public static final C_5264_<C_1629_> ah = a("the_end");
   public static final C_5264_<C_1629_> ai = a("end_highlands");
   public static final C_5264_<C_1629_> aj = a("end_midlands");
   public static final C_5264_<C_1629_> ak = a("small_end_islands");
   public static final C_5264_<C_1629_> al = a("end_barrens");
   public static ReflectorClass Biome = new ReflectorClass(C_1629_.class);
   public static ReflectorField Biome_climateSettings = Biome.makeField(C_1634_.class);

   private static C_5264_<C_1629_> a(String nameIn) {
      C_5264_<C_1629_> key = C_5264_.a(C_256686_.f_256952_, ResourceLocation.b(nameIn));
      biomeKeys.add(key);
      return key;
   }

   public static Set<C_5264_<C_1629_>> getBiomeKeys() {
      return new LinkedHashSet(biomeKeys);
   }

   public static float getDownfall(C_1629_ biome) {
      C_1634_ bcs = (C_1634_)Biome_climateSettings.getValue(biome);
      return bcs == null ? 0.0F : bcs.f_47683_();
   }
}
