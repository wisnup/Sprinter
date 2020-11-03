# Sprinter
Simple Sprint Tracker

# How to run
1. Grab prebuilt jar from https://github.com/wisnup/Sprinter/releases/
2. Download the sample config here https://github.com/wisnup/Sprinter/blob/master/src/main/resources/application.yaml and put it into same directory as the jar
3. Make sure to update the configuration file
4. Open command prompt or terminal and run `java -jar sprinter-x.y.z`
5. Wait until you see this log in screen
```
 Netty started on port(s): 8013
```
6. Open browser and launch this url `http://localhost:[your_port_in_config]`

# How contribution counted
- Story, bug and chore are counted when they are closed within the sprint
- Story is counted to sprint when its title contains [SP=<story point>]
- PR Review is counted when they are merged within the sprint
- Bug is counted when it has Bug label. Accepted label can be configured
- Chore is counted when it has Chore label. Accepted label can be configured
- Use wisely, each API call some Github GraphQL queries so it might hit the rate limit pretty quickly :/
