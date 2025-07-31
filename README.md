# Mob-Spawner-Highlight-for-Fabric-1.21.1


Client-side Minecraft mod for **Fabric 1.21.1** that visually highlights **mob spawners** whose internal value (`spawnDelay`) is not at the default resting state (`2`). Used for identifying if a player has been within 12 blocks of a spawner or for regular debugging.

---

## basic info

- Highlights mob spawners with a `spawnDelay` value **not equal to `2`** (tells you if there was a player within 12 blocks of a spawner)
- Client-side only does **not** require server installation
- Uses Fabric


---

## Installation

1. Install [Fabric Loader](https://fabricmc.net/use/) and [Fabric API](https://modrinth.com/mod/fabric-api) for Minecraft 1.21.1.
2. Download and compile into a .jar
3. Place the .jar in your Minecraft mods folder:


4. Launch the game in Fabric 1.21.1.

---

## How It Works

The mod scans all loaded `MobSpawnerBlockEntity` instances and checks the value of their internal `spawnDelay`. If it's **not equal to `2`**, the block is rendered with a **red outline box** for visibility.

This internal value is 2 when idle or reset, but starts counting up to a value of 200 and looping back down to 0, after a player at any point is within 12 blocks of it pausing when they leave, as a result, if the value is anything other than 2, it is assumed that a player has been at the spawner before.

---

## Technical Notes

- The mod uses a **Mixin accessor** to retrieve the private `spawnDelay` field from `MobSpawnerLogic`.
- The highlight is rendered using Fabric’s `WorldRenderEvents` on the client.
- You can customise the colour or behaviour by editing the source code (I was too lazy to make a UI).

---

## License

MIT License – feel free to use or modify <3.



