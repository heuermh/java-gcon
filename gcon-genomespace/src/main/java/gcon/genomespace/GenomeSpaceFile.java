package gcon.genomespace;

import java.io.InputStream;
import java.net.URL;
import java.nio.file.Path;

import com.github.heuermh.gcon.GenomeConnectorFile;
import com.github.heuermh.gcon.GenomeConnectorFileMetadata;

public class GenomeSpaceFile implements GenomeConnectorFile{

	@Override
	public InputStream stream() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public URL url() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void download(Path path) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Iterable<GenomeConnectorFile> list() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenomeConnectorFileMetadata meta() {
		// TODO Auto-generated method stub
		return null;
	}

}
