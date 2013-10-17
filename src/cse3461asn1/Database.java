package cse3461asn1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public abstract class Database {
	private Path filename;
	private Charset charset = Charset.forName("US-ASCII");
	private Boolean open = false;
	protected BufferedReader reader;
	protected BufferedWriter writer;
	
	public Database(String file) throws IOException {
		this.filename = FileSystems.getDefault().getPath("db", file);
		this.reader = Files.newBufferedReader(this.filename, this.charset);
		this.writer = Files.newBufferedWriter(this.filename, this.charset, StandardOpenOption.WRITE);
		this.open = true;
	}
	
	public void reopen() throws IOException {
		if(!this.open) {
			this.reader = Files.newBufferedReader(this.filename, this.charset);
			this.writer = Files.newBufferedWriter(this.filename, this.charset, StandardOpenOption.WRITE);
			this.open = true;
		}	
	}
	
	public void close() throws IOException {
		if(this.open) {
			this.reader.close();
			this.writer.close();
			this.open = false;
			this.reader = null;
			this.writer = null;
		}
	}
}
