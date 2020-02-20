# BrainfuckGenerator

Give a string, get a Brainfuck program that prints it.

Still better than serialization in Java, probably.

## Compiling and running

Compilation:

```bash
sbt clean compile
```

Running:

```bash
echo "Hello world!" | sbt run
```

```
[info] Loading project definition from /home/Workspace/BrainfuckGenerator/project
[info] Loading settings for project brainfuckgenerator from build.sbt ...
[info] Set current project to BrainfuckGenerator (in build file:/home/kondziu/BrainfuckGenerator/)
[info] running Main 
++++++++++[>+++>+++++++>++++++++++>+++++++++++<<<<-]>>++.>+.>--..+++.<<<++.>>>++++++++.--------.+++.------.<-.
[success] Total time: 0 s, completed Feb 20, 2020, 2:51:56 PM
```
