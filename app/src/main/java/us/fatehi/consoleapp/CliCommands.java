package us.fatehi.consoleapp;

import java.io.PrintWriter;

import org.jline.reader.LineReader;
import org.jline.reader.impl.LineReaderImpl;

import picocli.CommandLine;
import picocli.CommandLine.Command;

/**
 * Top-level command that just prints help. (From Example.java that demonstrates how to build an
 * interactive shell with JLine3 and picocli.)
 */
@Command(
    name = "",
    description = {
      "Example interactive shell with completion and autosuggestions. "
          + "Hit @|magenta <TAB>|@ to see available commands.",
      "Hit @|magenta ALT-S|@ to toggle tailtips.",
      ""
    },
    footer = {"", "Press Ctl-D to exit."},
    subcommands = {SayHelloCommand.class, ClearScreen.class, CommandLine.HelpCommand.class})
class CliCommands implements Runnable {

  private LineReaderImpl reader;
  private PrintWriter out;

  public void setLineReader(LineReader reader) {
    this.reader = (LineReaderImpl) reader;
    out = reader.getTerminal().writer();
  }

  @Override
  public void run() {
    out.println(new CommandLine(this).getUsageMessage());
  }

  public void println(final String string) {
    out.println(string);
  }

  public void printf(final String format, final String name) {
    out.printf(format, name);
  }

  public void clearScreen() {
    reader.clearScreen();
  }
}
