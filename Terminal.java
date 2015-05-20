package pl.labno.bernard;
import org.junit.Rule;
import org.junit.Test;
import org.junit.Assert;
import org.junit.rules.ExpectedException;
import static org.mockito.Mockito.*;

public class TerminalTest {

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void sendLine_parameterNull_throwException(){
        expectedException.expect(IllegalArgumentException.class);
        expectedException.expectMessage("Line param must not be null");

        //Given
        Connection conconnect = mock(Connection.class);
        Terminal terminal = new Terminal(conconnect);
        terminal.getErrorMessage();

        //When

        //Then
        terminal.sendLine(null);
    }



    @Test
    public void sendLine_isNotConnected_throwException(){
        expectedException.expect(IllegalStateException.class);

        //Given
        Connection conconnect = mock(Connection.class);
        Terminal terminal = new Terminal(conconnect);

        when(conconnect.isConnected()).thenReturn(true);
        when(terminal.sendLine(".")).thenThrow(IllegalStateException.class);

        //When
        terminal.sendLine(".");

        //Then
        verify(conconnect, times(1)).isConnected();
        String errorMessage = terminal.getErrorMessage();
        Assert.assertEquals("This command is unknown",  errorMessage);
        expectedException.expectMessage(errorMessage);

    }


    @Test
    public void sendLine_unknownCommand_throwException(){
        expectedException.expect(IllegalStateException.class);

        //Given
        Connection conconnect = mock(Connection.class);
        Terminal terminal = new Terminal(conconnect);
        when(conconnect.isConnected()).thenReturn(false);

        //When
        terminal.sendLine(".");

        //Then
        String errorMessage = terminal.getErrorMessage();
        Assert.assertEquals("Terminal is not connected", errorMessage);
        expectedException.expectMessage("Not connected");

    }

    @Test
    public void sendLine_isConnectedSendLineThrowError_throwException(){
        expectedException.expect(IllegalStateException.class);

        //Given
        Connection connect = mock(Connection.class);
        Terminal terminal = new Terminal(conconnect);
        when(conconnect.isConnected()).thenReturn(true);
        when(conconnect.sendLine(".")).thenThrow(UnknownCommandException.class);

        //When
        terminal.sendLine(".");

        //Then
        verify(conconnect, times(1)).sendLine(".");
        verify(conconnect, times(1)).isConnected();
        String errorMessage = terminal.getErrorMessage();
        Assert.assertEquals("This command is unknown",  errorMessage);
        expectedException.expectMessage(errorMessage);
    }

    @Test
    public void sendLine_parameterNotNull_returnsSendLinesParam() {
        //Given
        Connection conconnect = mock(Connection.class);
        Terminal terminal = new Terminal(conconnect);
        when(conconnect.sendLine(".")).thenReturn(".");
        when(conconnect.isConnected()).thenReturn(true);
        terminal.getErrorMessage();

        //Wen

        //Then
        terminal.sendLine(".");
        verify(conconnect, times(1)).sendLine(".");

    }
}
