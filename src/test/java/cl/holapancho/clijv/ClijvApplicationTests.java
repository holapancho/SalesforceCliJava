package cl.holapancho.clijv;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import cl.holapancho.clijv.command.MyCommand;
import picocli.CommandLine;

import static org.springframework.boot.test.context.SpringBootTest.WebEnvironment.NONE;
import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = NONE, classes = ClijvApplication.class)
public class ClijvApplicationTests {

    @Autowired
    CommandLine.IFactory factory;

    @Autowired
    MyCommand myCommand;

	@Test
    public void testParsingCommandLineArgs() {
        CommandLine.ParseResult parseResult = new CommandLine(myCommand, factory)
                .parseArgs("-x", "abc");
        assertEquals("abc", myCommand.x);
    }
}
