![](https://javacord.org/img/javacord3_banner.png)

# Javacord [![Latest version](https://shields.javacord.org/github/release/Javacord/Javacord.svg?label=Version&colorB=brightgreen&style=flat-square)](https://github.com/Javacord/Javacord/releases/latest) [![Latest JavaDocs](https://shields.javacord.org/badge/JavaDoc-Latest-yellow.svg?style=flat-square)](https://docs.javacord.org/api/v/latest/) [![Javacord Wiki](https://shields.javacord.org/badge/Wiki-Home-red.svg?style=flat-square)](https://javacord.org/wiki/) [![Javacord Discord server](https://shields.javacord.org/discord/151037561152733184.svg?colorB=%237289DA&label=Discord&style=flat-square)](https://discord.gg/0qJ2jjyneLEgG7y3)

An easy to use multi-threaded library for creating Discord bots in Java.

Javacord is a modern library that focuses on similicity and speed 🚀. By reducing itself to standard Java classes and features like `Optional`s and `CompletableFuture`s, it is extemely easy to use for every Java developers as it does not requires you to learn any new frameworks or complex abstractions. It has rich [documentation](#-documentation) and an [awesome community on Discord](#-support) that loves to help with any specific problems and questions.

## 🎉 Basic usage

The following example logs the bot in and replies to every "!ping" message with "Pong!". 

<img align="right" src="https://i.imgur.com/q8rsAhL.gif" width="20.5%">

```java
public class MyFirstBot {

    public static void main(String[] args) {
        // Insert your bot's token here
        String token = "your token";

        DiscordApi api = new DiscordApiBuilder().setToken(token).login().join();

        // Add a listener which answers with "Pong!" if someone writes "!ping"
        api.addMessageCreateListener(event -> {
            if (event.getMessageContent().equalsIgnoreCase("!ping")) {
                event.getChannel().sendMessage("Pong!");
            }
        });

        // Print the invite url of your bot
        System.out.println("Invite me: " + api.createBotInvite());
    }

}
```

More sophisticated examples can be found at the [end of the README](#-more-examples). 
You can also check out the [Example Bot](https://github.com/Javacord/Example-Bot) for a fully functional bot.

## 📦 Download / Installation

The recommended way to get Javacord is to use a build manager, like Gradle or Maven.  
If you are not familiar with build managers, you can follow this [Setup Guide](#-ide-setup) 
or download Javacord directly from [GitHub](https://github.com/Javacord/Javacord/releases/latest).

### Javacord Dependency

#### Gradle
```groovy
repositories { mavenCentral() }
dependencies { implementation 'org.javacord:javacord:3.0.5' }
```

#### Maven
```xml
<dependency>
    <groupId>org.javacord</groupId>
    <artifactId>javacord</artifactId>
    <version>3.0.5</version>
    <type>pom</type>
</dependency>
```

### Optional Logger Dependency

Any Log4j-2-compatible logging framework can be used to provide a more sophisticated logging experience
with being able to configure log format, log targets (console, file, database, Discord direct message, ...),
log levels per class, and much more.

For example, Log4j Core in Gradle
```groovy
dependencies { runtimeOnly 'org.apache.logging.log4j:log4j-core:2.11.0' }
```
Take a look at the [Logger Configuration](https://javacord.org/wiki/basic-tutorials/logger-configuration/) wiki article for further information.

## 🔧 IDE Setup

If you have never used Gradle or Maven before, you should take a look at one of the setup tutorials:
* **[IntelliJ & Gradle](https://javacord.org/wiki/getting-started/intellij-gradle/)** _(recommended)_
* **[IntelliJ & Maven](https://javacord.org/wiki/getting-started/intellij-maven/)**
* **[Eclipse & Maven](https://javacord.org/wiki/getting-started/eclipse-maven/)**

## 🤝 Support

Javacord's Discord community is an excellent resource if you have questions about the library.  
* **[The Javacord Server](https://discord.gg/0qJ2jjyneLEgG7y3)**

## 📒 Documentation

* The [Javacord Wiki](https://javacord.org/wiki/) is a great place to get started 
* Additional documentation can be found in the [JavaDoc](https://docs.javacord.org/api/v/latest/)

## 💡 How to create a bot user and get its token 

* **[Creating a Bot User Account](https://javacord.org/wiki/essential-knowledge/creating-a-bot-account/)**

## 📋 Version numbers

The version number has a 3-digit format: `major.minor.trivial`
* `major`: Increased extremely rarely to mark a major release (usually a rewrite affecting very huge parts of the library).
* `minor`: Any backwards incompatible change to the api. 
* `trivial`: A backwards compatible change to the **api**. This is usually an important bugfix (or a bunch of smaller ones)
 or a backwards compatible feature addition.
 
## 🔨 Deprecation policy

A method or class that is marked as deprecated can be removed with the next minor release (but it will usually stay for
several minor releases). A minor release might remove a class or method without having it deprecated, but we will do our
best to deprecate it before removing it. We are unable to guarantee this though, because we might have to remove / replace
something due to changes made by Discord, which we are unable to control. Usually you can expect a deprecated method or
class to stay for at least 6 months before it finally gets removed, but this is not guaranteed.

## 🙌 More examples 

### Play a song from YouTube 🎵

The following example shows how to play a song from YouTube. As soon as a user types "!music" in the chat, the bot joins the current voice channel of this user and plays [the song](https://youtu.be/NvS351QKFV4) from YouTube. This requires the [Lavaplayer-Wrapper](https://github.com/Bastian/Lavaplayer-Wrapper) library as an additional dependency.

```java
api.addMessageCreateListener(event -> {
    if (!event.getMessageContent().equalsIgnoreCase("!music") {
       return;
    }

    if (!event.isServerMessage()) { // Ignore direct messages
        return;
    }
  
    if (event.getMessageAuthor().isUser()) { // Ignore message authors that are no users (e.g., webhooks)
        return;
    }
  
    User author = event.getMessageAuthor().asUser().orElseThrow(AssertionError::new);
    ServerTextChannel textChannel = event.getServerTextChannel().orElseThrow(AssertionError::new);
    ServerVoiceChannel voiceChannel = author.getConnectedVoiceChannel(channel.getServer()).orElse(null);

    // Make sure that the user (that has typed the command) is in a voice channel
    if (voiceChannel == null) {
        event.getChannel().sendMessage("Please join a voice channel first!");
        return;
    }

    // Check if the bot is already in a voice channel on the server
    if (textChannel.getServer().getAudioConnection().isPresent()) {
        event.getChannel().sendMessage("Sorry, I'm already in a voice channel!");
        return;
    }

    // Connect to the voice channel and play the song
    voiceChannel.connect()
      .thenAcceptAsync(connection -> {
          connection.set(YouTubeAudioSource.of(api, "https://youtu.be/NvS351QKFV4").join());
          // Leave the voice channel when the song finished
          connection.addAudioSourceFinishedListener(e -> connection.close());
      })
      .exceptionally(throwable -> {
          // Something went wrong
          textChannel.sendMessage("Failed to play song!");
          throwable.printStrackTrace();
          return null;
      });
  });
}
```

<img align="right" src="https://i.imgur.com/AP1cjDf.png" width="34%">

### Using the MessageBuilder 🛠

The following example uses the built-in `MessageBuilder`. It is very useful to construct complex messages with images, code-blocks, embeds, or attachments.

```java
new MessageBuilder()
  .append("Look at these ")
  .append("awesome", MessageDecoration.BOLD, MessageDecoration.UNDERLINE)
  .append(" animal pictures! 😃")
  .appendCode("java", "System.out.println(\"Sweet!\");")
  .addAttachment(new File("C:/Users/JohnDoe/Pictures/kitten.jpg"))
  .addAttachment(new File("C:/Users/JohnDoe/Pictures/puppy.jpg"))
  .setEmbed(new EmbedBuilder()
        .setTitle("WOW")
        .setDescription("Really cool pictures!")
        .setColor(Color.ORANGE))
  .send(channel);
```

### Listener in their own class 🗃

All the previous examples used inline listeners for similicity. For better readability it is also possible to have listeners in their own class:

```java
public class MyListener implements MessageCreateListener {

    @Override
    public void onMessageCreate(MessageCreateEvent event) {
        Message message = event.getMessage();
        if (message.getContent().equalsIgnoreCase("!ping")) {
            event.getChannel().sendMessage("Pong!");
        }
    }

}
```
```java
api.addListener(new MyListener());
```

For commands, you can even use one of the many existing command frameworks like
* [Command Framework](https://github.com/Vampire/command-framework) 
* [Sdcf4j](https://github.com/Bastian/sdcf4j)

or even write your own!

### Attach listeners to objects 📌


You can even attach listeners to objects. Let's say you have a very sensible bot. As soon as someone reacts with a 👎 within the first 30 minutes of message creation, it deletes its own message:

<img align="right" src="https://i.imgur.com/DTMIIqA.gif" width="36%"> 

```java
api.addMessageCreateListener(event -> {
    if (event.getMessageContent().equalsIgnoreCase("!ping")) {
        event.getChannel().sendMessage("Pong!").thenAccept(message -> {
            // Attach a listener directly to the message
            message.addReactionAddListener(reactionEvent -> {
                if (reactionEvent.getEmoji().equalsEmoji("👎")) {
                    reactionEvent.deleteMessage();
                }
            }).removeAfter(30, TimeUnit.MINUTES);
        });
    }
}
```

## 📃 License

Javacord is distributed under the [Apache license version 2.0](./LICENSE).
