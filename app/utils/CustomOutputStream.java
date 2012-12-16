package utils;

import java.io.IOException;
import java.io.OutputStream;

public class CustomOutputStream extends OutputStream {

	private StringBuilder string = new StringBuilder();

	@Override
	public void write(int b) throws IOException {
		this.string.append((char) b+":toto:");
	}

	@Override
	public void flush() throws IOException {
		super.flush();
		string.setLength(0);
	}

	public String toString(){
        return this.string.toString();
    }
}
