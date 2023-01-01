package us.fatehi.consoleapp;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.jline.console.SystemRegistry;
import org.jline.console.impl.Builtins;
import org.jline.console.impl.SystemRegistryImpl;
import org.jline.reader.EndOfFileException;
import org.jline.reader.LineReader;
import org.jline.reader.LineReaderBuilder;
import org.jline.reader.MaskingCallback;
import org.jline.reader.Parser;
import org.jline.reader.UserInterruptException;
import org.jline.reader.impl.DefaultParser;
import org.jline.terminal.Terminal;
import org.jline.terminal.TerminalBuilder;

import picocli.CommandLine;
import picocli.shell.jline3.PicocliCommands;

/**
 * From Example.java that demonstrates how to build an interactive shell with JLine3 and picocli.
 */
public class ConsoleApp {

  public static void main(final String[] args) throws Throwable {

    if (args != null && args.length > 0) {
      System.out.printf("Hello, %s!", args[0]);
      System.exit(1);
    }

    // --------------
    // REPL

    // set up JLine built-in commands
    final Builtins builtins = new Builtins(ConsoleApp::workDir, null, null);
    // set up picocli commands
    final CliCommands commands = new CliCommands();
    final CommandLine cmd = new CommandLine(commands);
    final PicocliCommands picocliCommands = new PicocliCommands(cmd);

    final Parser parser = new DefaultParser();
    try (final Terminal terminal = TerminalBuilder.builder().build()) {
      final SystemRegistry systemRegistry =
          new SystemRegistryImpl(parser, terminal, ConsoleApp::workDir, null);
      systemRegistry.setCommandRegistries(builtins, picocliCommands);

      final LineReader reader =
          LineReaderBuilder.builder()
              .terminal(terminal)
              .completer(systemRegistry.completer())
              .parser(parser)
              .variable(LineReader.LIST_MAX, 50) // max tab completion candidates
              .build();
      builtins.setLineReader(reader);
      commands.setLineReader(reader);

      final String prompt = "prompt> ";
      final String rightPrompt = null;

      // start the shell and process input until the user quits with Ctrl-D
      String line;
      while (true) {
        try {
          systemRegistry.cleanUp();
          line = reader.readLine(prompt, rightPrompt, (MaskingCallback) null, null);
          systemRegistry.execute(line);
        } catch (final UserInterruptException e) {
          // Ignore
        } catch (final EndOfFileException e) {
          return;
        } catch (final Exception e) {
          systemRegistry.trace(e);
        }
      }
    }
  }

  private static Path workDir() {
    return Paths.get(System.getProperty("user.dir"));
  }
}
