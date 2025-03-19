# NoBypassX 🛑
NoBypassX is an anti-swear plugin, where you can customize all the words and commands that are being filtered. 🧹

## Commands ⌨️
- `/nobypass` | `/nobypassx`

## Usage 🔨
The plugin generates a config file where you can input your blocked words. The plugin only checks if a chat message or a command contains a word that is blocked in the config.

## Example/default config 📝
```yml
# Response by message
### Placeholders for messages:
# - %player% - the name of the player that send the message/command
# - %adress% - the adress of the player that send the message/command
# - %displayname% - the displayname of the player that send the message/command (Set by EssentialsX or the most Nickname plugins)
# - %level% - the level of the player
# - %ping% - the ping of the player
messageEnable: false
message: "Please don't use inappropriate word"
command-message: "Please don't use inappropriate word in commands"

# Response by command

## Placeholders for command:
# - %player% - the name of the player that send the message/command

commandEnable: false
command: "warn %player% inappropriate word"

# Word list
words:
  - word
  - word
  - word
  - word
```
The placeholders are values of the player (idk why u need adress, but its there)

## Permissions 📌
**there a no permissions.**

## Help / Issues 🚨
If you have any troubles with the plugin, feel free to open an issue

## License & repo 📃
This repo is a fork, continue and improvement of [NoBypass](https://github.com/Infinity470/NoBypass) which was archived by the creator.
