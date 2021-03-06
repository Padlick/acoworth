package net.yasfu.acoworth.ShopListeners;


import org.bukkit.Material;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.bukkit.configuration.file.FileConfiguration;
import net.yasfu.acoworth.Storage;
import net.yasfu.acoworth.AcoWorthPlugin;
import org.maxgamer.quickshop.Shop.Shop;
import org.maxgamer.quickshop.Shop.ShopType;
import org.maxgamer.quickshop.event.ShopSuccessPurchaseEvent;

/*
QuickShop Reremake
https://www.spigotmc.org/resources/quickshop-reremake-1-15-ready-bees-bees-bee.62575/
 */

public class QuickshopListener implements Listener {

    AcoWorthPlugin plugin;

    public QuickshopListener(AcoWorthPlugin pl) {
        plugin = pl;
    }

    @EventHandler (priority = EventPriority.MONITOR)
    public void onQuickShopSale(ShopSuccessPurchaseEvent shopPurchaseEvent) {
        if (shopPurchaseEvent.isCancelled()) {
            return;
        }

        FileConfiguration cfg = plugin.getConfig();
        String type = cfg.getString("trackSaleTypes");

        Shop shop = shopPurchaseEvent.getShop();
        ShopType shopType = shop.getShopType();

        if (type == null) {
            type = "BUY";
        }

        switch (type) {
            case "BUY":
                if (shopType == ShopType.BUYING) { // this is if the SHOP is buying, not the player.
                    return;
                }
                break;
            case "SELL":
                if (shopType == ShopType.SELLING) { // this is if the SHOP is selling, not the player.
                    return;
                }
                break;
        }

        ItemStack item = shop.getItem();
        Material mat = item.getType();

        int amt = shopPurchaseEvent.getAmount();
        double cost = shopPurchaseEvent.getBalanceWithoutTax();

        cost /= amt;

        Storage.addSale(mat, cost);
    }

}
