# MinigameAPI
Flexible, modern and easy to use library to creating minigames in minecraft. <br>
Library works from 1.13.2 to 1.20.x.
<br>
This version is pre-release, not for commercial use, maybe it has bugs. 
Please report every bugs in issues section.<br>

JavaDocs: https://timsixth.pl/javadocs/minigame_api/1.0.0-rc6/

## Library features:
- Arena management system
- Game management system
- Game cosmetics system
- Teams in game
- Advanced loading system from SQL databases (MySQL or SQLite) or YAML files
- Game statistics system
- Coins system
- Simple minigame configuration
- Integration with T-DataBasesAPI (Thanks to this plugin management of SQL databases is really simple)
- Simple saving, deleting and updating system (You don't have to write query to database or use YAML spigot API to execute these actions)
- Commands API (You can create parent commands and subcommands in separate classes)
- Addons system (You can create addon will works with every mini game which is using this library)

## Addons
- [Boosters](https://github.com/timsixth/BoostersMiniGameAddon)

## Getting started

Maven
```xml
<repositories>
  <repository>
   <id>jitpack.io</id>
   <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
	<groupId>com.github.timsixth.MinigameAPI</groupId>
	<artifactId>library</artifactId>
	<version>Tag</version>
</dependency>
```
Gradle
```gradle
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
  
 dependencies {
	 implementation 'com.github.timsixth:MinigameAPI:Tag'
}
```
