# ClaimFly [GriefPrevention Addon]

- Plugin Available on [SpigotMC](https://www.spigotmc.org/resources/claimfly-griefprevention-addon.122058/)
- View Plugin Stats [bStats](https://bstats.org/plugin/bukkit/ClaimFly%20-%20GriefPrevention%20Addon/24525)

Features:

- Choose where players can fly by setting permissions:
  - Claims they own
  - Claims where they have Access Trust
  - Admin claims
  - Outside of claimed areas
> [!WARNING] 
> `Experimental Feature - has potential to cause lag?`
> - Automatically enable or disable flight mode when players enter or leave a claimed area so that players don't need to use the /claimfly command
This feature can be enabled in the settings.yml file
If you enable this feature, players will still be able to use the command as well

Dependancies:
- [GriefPrevention](https://github.com/GriefPrevention)
- [Foundation](https://github.com/kangarko/Foundation)

Commands:
- /claimfly | /cfly | /fly

>[!IMPORTANT]
> Permissions:
> - `claimfly.use` - allows use of the command and flight in own claims
> - `claimfly.claims.admin` - allows flight in admin claims
> - `claimfly.claims.others` - allows flight in other players claims if they have Access Trust
> - `claimfly.claims.unclaimed` - allows flight outside of claimed areas

Configuration:
No additional config needed. Drop in, Set permissions and it just works.
Additional features and all messages can be customized in the config file though.
