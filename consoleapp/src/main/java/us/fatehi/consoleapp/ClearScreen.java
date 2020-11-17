package us.fatehi.consoleapp;

import java.io.IOException;
import java.util.concurrent.Callable;

import picocli.CommandLine.Command;
import picocli.CommandLine.ParentCommand;

/**
 * Clear screen command. (From Example.java that demonstrates how to build an interactive shell with
 * JLine3 and picocli.)
 */
@Command(
    name = "cls",
    aliases = "clear",
    mixinStandardHelpOptions = true,
    description = "Clears the screen")
class ClearScreen implements Callable<Void> {

  @ParentCommand CliCommands parent;

  @Override
  public Void call() throws IOException {
    parent.clearScreen();
    return null;
  }
}
