package gcon.genomespace;


import java.net.URL;

import javax.swing.WindowConstants;

import org.genomespace.client.ConfigurationUrls;
import org.genomespace.client.ui.GSLoginDialog;

import com.github.heuermh.gcon.GenomeConnectorClient;
import com.github.heuermh.gcon.GenomeConnectorFile;

public class GenomeSpaceClient implements GenomeConnectorClient{

	
	public GenomeSpaceClient(){
		// Do magic for GS here
		ConfigurationUrls.init("prod");

		GSLoginDialog loginDialog = new GSLoginDialog();
	    loginDialog.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);

	}
	
	@Override
	public GenomeConnectorFile root() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public GenomeConnectorFile file(URL url) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void put() {
		// TODO Auto-generated method stub
		
	}

}
