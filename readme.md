HexLib
======
I had a bunch of random classes lying around for private projects that I never got to use, so I decided to slowly migrate
them to a public project. Currently, the only really usable part of this is the poly-version compat module.

##WARNING
---------
Very little of this project actually works yet. If you feel like asking something about it, don't hesitate to pop into
`#_` on `irc.spi.gt` ([webchat](https://irc.spi.gt/iris/?channels=_).

###Building
-----------
I'm just a tiny bit proud of the build system that I'm using for this. Unfortunately, I have been unable to test the system
on a Windows environment. As far as I can estimate, the build should proceed as normal but it won't automatically correct
any errors it creates in your IntelliJ project files. To clarify, the maven project uses templating to filter a specific
source file at runtime. This means that it has to be located in a separate source folder than the rest of the code. However,
some IDEs (only tested with IntelliJ IDEA) recognize both this new directory and maven's generated sources directory as a sources
root. Because if this, I have created a shell script that will be run during the compile phase of building that removes
this directory from IntelliJ's source root list.