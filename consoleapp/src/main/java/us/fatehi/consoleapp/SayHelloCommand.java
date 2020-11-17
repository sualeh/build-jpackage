package us.fatehi.consoleapp;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.ParentCommand;

@Command(
    name = "say-hello",
    mixinStandardHelpOptions = true,
    version = "1.0",
    description = {"Hello world command"},
    subcommands = {CommandLine.HelpCommand.class})
class SayHelloCommand implements Runnable {
  @Option(
      names = {"-n", "--name"},
      description = {"Your name for the greeting"})
  private String name;

  @ParentCommand CliCommands parent;

  @Override
  public void run() {
    if (name == null) {
      parent.println("Hello there!");
    } else {
      parent.printf("Hello, %s!%n", name);
    }
  }
}
