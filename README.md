# MinigameAPI
Flexible, modern and easy to use library to creating minigames in minecraft. <br>
Library works from 1.13.2 to 1.20.x.
<br>
This version is pre-release, not for comercial use, may be has bugs. 
Please report every bugs in issues section.<br>

JavaDocs: https://timsixth.pl/javadocs/minigame_api/0.5.2/

## Library features:
- Arena managment system
- Game managment system
- Game cosmetics system
- Teams in game
- Advenced loading system from SQL databases (MySQL or SQLite) or YAML files
- Game statiscics system
- Coins system
- Simple minigame configuration
- Integration with T-DataBasesAPI (Thanks to this plugin, you can manage SQL databases is really simple)
- Simple saving, deleting and updating system (You don't have write query to database or use YAML spigot API to execute these actions) 

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
