# SQL Module

> [!CAUTION]
> Module must be shaded to your JAR file <br>
> The SQL Module by default are used [T-DataBaseAPI](https://github.com/timsixth/T-DataBasesAPI/releases) adapter 

> [!WARNING]
> The documenation is not completed, because this module is in Beta version.<br>
> Please ask for help to developer on [discord](https://discord.com/invite/zKmMy8bK56)

## Adding dependencies

```xml
<repositories>
  <repository>
   <id>jitpack.io</id>
   <url>https://jitpack.io</url>
  </repository>
</repositories>

<dependency>
   <groupId>com.github.timsixth.MinigameAPI</groupId>
   <artifactId>sql-databases-support</artifactId>
   <version>v2.0.0</version>
</dependency>
```

```gradle
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
  
 dependencies {
   implementation 'com.github.timsixth.MinigameAPI:sql-databases-support:v2.0.0'    
}
```

## How to register SQL module?

```java
    //Use SQLLibraryConfiguration instend of LibraryConfiguration
    //Type list with SQLModule
    @Override
    protected LibraryConfiguration configure() {
        return new SQLLibraryConfiguration(this, getConfiguratorsInitializer(),
                () -> Collections.singletonList(new SQLModule())) //you can register modules before others
                .builder()
                .setGameManager(new MyGameManager(this, settings, messages))
                .build();
    }

```

with custom SQL module configuration (Optional)
```java
    @Override
    protected LibraryConfiguration configure() {
        return new SQLLibraryConfiguration(this, getConfiguratorsInitializer(),
                () -> Collections.singletonList(new SQLModule(new MySQLModuleConfigurator().configure()))) //you can register modules before others
                .builder()
                .setGameManager(new MyGameManager(this, settings, messages))
                .build();
    }
```

## How to define SQL Module configurator (Optional)?

```java
public class MySQLModuleConfigurator extends DefaultSQLModuleConfigurator {

    @Override
    public SQLModuleConfiguration configure() {
        return SQLModuleConfiguration.builder()
                .sqlDatabaseAdapter(new T_DataBaseApiIntegration()) //if you want to create your own SQL Database adapter
                .sqlDatabaseMigrator(new T_DatabaseApiMigrator()) //if you want to create your own SQL Database migrator
                .build();
    }
}
```
