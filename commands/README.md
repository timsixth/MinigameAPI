# Commands Module

> [!CAUTION]
> Module must be shaded to your JAR file

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
   <artifactId>commands</artifactId>
   <version>v2.0.0-rc1</version>
</dependency>
```

```gradle
allprojects {
	repositories {
		maven { url 'https://jitpack.io' }
	}
}
  
 dependencies {
   implementation 'com.github.timsixth.MinigameAPI:commands:v2.0.0-rc1'    
}
```

## How to register Commands Module?

```java
//Add new instance of Commands Module to registerModules method in configure method
    @Override
    protected LibraryConfiguration configure() {
        commandsModule = new CommandsModule(this);
        //or use your own command configurator
        commandsModule = new CommandsModule(this, new MyCommandConfigurator());
        return new LibraryConfiguration(this, getConfiguratorsInitializer())
                .builder()
                .setGameManager(new MyGameManager(this, settings, messages))
                .registerModules(commandsModule) //new way how to register command module
                .build();
    }
```

## How to define command configurator?

```java
public class MyCommandConfigurator extends DefaultCommandConfigurator {

    @Override
    public CommandConfiguration configure() {
        return CommandConfiguration.builder()
                .doNotHavePermissionMessage("No permission")
                .onlyPlayersMessage("Only players can use this command")
                .build();
    }
}
```

## How to use Commands Module ?

### Parent commands
Every parent command must extend ParentCommand.<br>
Parent command has three constructors.<br>
1. First constructor <br>
This constructor requires all parameters:<br>

- permission (first param)
- hasArguments (second param)
- onlyPlayers (third param)
- usePermission (fourth param)

```java
 public MyCommand() {
    super("minigame.admin", true, true, true);
 }
```
2. Second constructor<br>
This constructor requires four parameters:<br>
- permission (first param)<br>
- onlyPlayers (second param)<br>
- usePermission (third param)<br>
- hasArguments (in this constructor this field is set to false)
```java
 public MyCommand() {
    super("minigame.admin", true, true);
 }
```
3. Third constructor <br>
This constructor doesn't have parameters,everything is set to default value like false, empty string.<br>

The parent command class has three methods to implement:<br>
- excecuteCommand - executes command<br>
- getName - gets command name, this is so important for addons system<br>
- getTabCompleter - gets tab completer for command (Optional)<br>
The whole implementation of the command class.
```java
public class MyCommand extends ParentCommand {

    public MyCommand (CommandConfiguration commandConfiguration) {
        super("command.admin", true, true, true, commandConfiguration);
        
        addSubCommand(new MySubCommand());
    }

    @Override
    protected boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        player.sendMessage("test");

        return false;
    }

    @Override
    public String getName() {
        return "mycommand";
    }

}
```

### SubCommands
Every sub-command must be registered in the parent command class.<br>
Every sub-command must implement SubCommand interface.<br>
The sub-command has two methods:<br>
- executeCommand - executes subcommand<br>
- getName - gets sub-command name<br>
The whole implementation of the sub-command class.
```java
public class MySubCommand implements SubCommand {

    @Override
    public boolean executeCommand(CommandSender sender, String[] args) {
        Player player = (Player) sender;

        player.sendMessage("my sub command");
        
        return false;
    }

    @Override
    public String getName() {
        return "mysubcommand";
    }
}
```

### TabCompleter 
Tab completer in parent commands is optional.<br>
To add a tab completer override method getTabCompleter in the parent command class
```java
@Override
public BaseTabCompleter getTabCompleter() {
    return new MyCommandTabCompleter(this);
}
```
How to create a tab completer class?<br>
Every sub-command is automatically added to the tab completer, you can define other conditions
```java
public class MyCommandTabCompleter extends BaseTabCompleter {

    public MyCommandTabCompleter(ParentCommand parentCommand) {
        super(parentCommand);

        this.addConditions((sender, args) -> {
            List<String> completions = new ArrayList<>();

            if (args.length == 2) {
                completions.addAll(Arrays.asList("test1", "test2"));
            }
             
            return completions;
        });
    }
}
```

### Command registration
The CommandRgistration has two methods to register commands.<br>
Every command must be register in main class.

Create instance of command registration with one param, this is your main class 
```java
CommandRegistration commandRegistration = commandsModule.getCommandRegistration();
```
Registering command without tabcompleter
```java
commandRegistration.registerCommand(new MyCommand());
```
Registering command with tabcompleter
```java
commandRegistration.registerCommandWithTabCompleter(new MyCommand());
```
