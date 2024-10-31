# Item Rename Mod

## Description

The Item Rename mod for Fabric Modloader allows players to edit various properties of items in the game, such as their
name, lore, and enchantment glow. With support for color and formatting, you can personalize your items like a piece of
cake.

This mod is heavily inspired by the plugin: [EpicRename](https://www.spigotmc.org/resources/epicrename.4341/)

This mod was originally made by FaeWulf, backported to 1.19 by Murren.

Also you can use my tools to easily make your own custom name/lore: https://faewulf.xyz/projects/epicrename

## Commands and Permissions

| Command                                     | Description                                                   | Permission        |
|---------------------------------------------|---------------------------------------------------------------|-------------------|
| `/rename <name>`                            | Renames the item in hand.                                     | itemrename.rename |
| `/setloreline <lineNumber> <text>`          | Sets a certain line of lore on the item in hand.              | itemrename.lore   |
| `/addloreline <text>`                       | Adds a line of lore on the item in hand.                      | itemrename.lore   |
| `/removeloreline <lineNumber>`              | Remove a certain line of lore on the item in hand.            | itemrename.lore   |
| `/insertloreline <beforeLineNumber> <text>` | Insert a certain line of lore on the item in hand.            | itemrename.lore   |
| `/glow`                                     | Adds an enchantment glow to the item in hand.                 | itemrename.glow   |
| `/removeglow`                               | Removes the enchantment glow from the item.                   | itemrename.glow   |
| `/removename`                               | Removes any custom name from the item in hand.                | itemrename.rename |
| `/removelore`                               | Removes any custom lore from the item in hand.                | itemrename.lore   |
| `/lockitem`                                 | Lock item edit, allowing only you to modify selected items.   | itemrename.lock   |
| `/unlockitem`                               | Unlock item edit, allowing everyone to modify selected items. | itemrename.lock   |

## Additional Permissions

- **itemrename.admin:** Allows bypassing lock check for item editing.

## Color and Format Support

The mod supports color and formatting using the EssentialsEX color format, with the prefix `&`.

## Feedback and Support

If you encounter any issues or have suggestions for improvement, please don't hesitate to reach out on the mod's GitHub
repository.

Thank you so much 🛠️✨
