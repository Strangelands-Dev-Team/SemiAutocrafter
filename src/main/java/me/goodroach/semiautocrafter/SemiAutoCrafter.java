package me.goodroach.semiautocrafter;

import org.bukkit.Material;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;

public final class SemiAutoCrafter extends JavaPlugin {

    public static SemiAutoCrafter instance;
    public static HashMap<String, AutocraftRecipe> Recipes = new HashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        getConfig().options().copyDefaults();
        saveDefaultConfig();
        PluginManager pm = getServer().getPluginManager();
        AutocrafterCraftingRecipe recipe = new AutocrafterCraftingRecipe(this);
        pm.registerEvents(new Listeners(recipe), this);
        addData();
    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }

    public void addData() {
        for(String x : getConfig().getConfigurationSection("Recipes").getKeys(false)){
            String recipePath = "Recipes." + x ;
            System.out.println(x);
            int amount = getConfig().getInt(recipePath + ".Amount");
            System.out.println(amount);
            Material output = Material.getMaterial(getConfig().getString(recipePath + ".Output").toUpperCase());
            System.out.println(output.toString());
            List<String> l = getConfig().getStringList(recipePath + ".Input");
            HashMap<Material,Integer> input = new HashMap<>();
            for(String y : l){
                String[] a = y.split(",");
                input.put(Material.getMaterial(a[0].toUpperCase()),Integer.parseInt(a[1]));
            }
            AutocraftRecipe recipe = new AutocraftRecipe(x,input,output,amount);
            Recipes.put(x.toLowerCase(Locale.ENGLISH), recipe);
            System.out.println("The plugin has successfully registered a recipe.");
        }
    }
}
