package cse3461asn1;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.FileSystems;
import java.nio.file.Path;

//the base class for all databases, opens and closes the files
public abstract class Database {
	protected FileWriter fileForWriter;
	protected Reader fileForReader;
	protected Path filePath;
	private Boolean open = false;
	protected BufferedReader reader;
	protected BufferedWriter writer;
	
	public Database(String file) throws IOException {
		this.filePath = FileSystems.getDefault().getPath("db", file);
	}
	
	//open up the db file for reading and writing
	protected void open(Boolean truncate) throws IOException {
		if(!this.open) { //make sure the files aren't already open
			//if we are writing to the db then we truncate the file and rebuild the data
			if(truncate)
				this.fileForWriter = new FileWriter(this.filePath.toString());
			else
				this.fileForWriter = new FileWriter(this.filePath.toString(), true);
			this.fileForReader = new FileReader(this.filePath.toString());
			
			this.reader = new BufferedReader(this.fileForReader);
			
			this.writer = new BufferedWriter(this.fileForWriter);
			
			this.open = true;
			
			//set the reset mark for the reader to the byte size of the file + 1
			File info = this.filePath.toFile();
			this.reader.mark((int) info.length() + 1);
		}
	}
	
	//flush the writer and close the db file, and destroy the reader and writer
	public void close() throws IOException {
		if(this.open) {
			this.reader.close();
			this.writer.flush();
			this.writer.close();
			this.open = false;
			this.reader = null;
			this.writer = null;
		}
	}
}
