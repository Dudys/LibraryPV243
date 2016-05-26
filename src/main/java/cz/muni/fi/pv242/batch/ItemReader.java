package cz.muni.fi.pv242.batch;

import javax.batch.api.chunk.AbstractItemReader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Serializable;
import java.util.logging.Level;
import java.util.logging.Logger;


public class ItemReader extends AbstractItemReader {

	private BufferedReader reader;

	@Override
	public void open(Serializable checkpoint) throws Exception {
		reader = new BufferedReader(
				new InputStreamReader(
						this
								.getClass()
								.getClassLoader()
								.getResourceAsStream("/META-INF/mydata.csv")
				)
		);
	}

	@Override public Object readItem() throws Exception {
		try {
			return reader.readLine();
		} catch (IOException ex) {
			Logger.getLogger(ItemReader.class.getName()).log(Level.SEVERE, null, ex);
		}
		return null;
	}
}
