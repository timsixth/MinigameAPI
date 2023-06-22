package pl.timsixth.minigameapi.user;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import pl.timsixth.minigameapi.model.Model;

import java.util.UUID;

public interface User  {

    UUID getUuid();

    default Player toPlayer() {
        return Bukkit.getPlayer(getUuid());
    }
}
