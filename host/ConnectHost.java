package host;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;
import java.util.TreeMap;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

public class ConnectHost {
	
	public static void main(String[] args) throws JSchException, IOException {
		
		List <String> hostNames = new ArrayList<String>();
		
		hostNames.add("host1");
		hostNames.add("host2");
		
		executeCommands(hostNames, "Username", "pwd..", "nproc");
	}
	
	/**
	 * Method to execute commands on Unix hosts.
	 * @param host
	 * @param user
	 * @param password
	 * @param command
	 */
	private static void executeCommands(List<String> hostNamesList, String user, String password,String command){
		try {
			Properties props = new Properties();
			props.put("StrictHostKeyChecking", "no");
			JSch jsch = new JSch();
			
			// Iterate through all the hosts one by one.
			for(int i=0; i< hostNamesList.size(); i++){
					Session session = jsch.getSession(user, hostNamesList.get(i), 22);
					session.setPassword(password);
					session.setConfig(props);
					session.connect();
					System.out.println("Connected to: " + hostNamesList.get(i));
					Channel channel = session.openChannel("exec");
					((ChannelExec)channel).setCommand(command);
					
					InputStream in = channel.getInputStream();
					channel.connect();
					byte[] tmp = new byte[1024];
					while (true) {
						while (in.available() > 0) {
							int j = in.read(tmp, 0, 1024);
							if (j < 0)
								break;
							System.out.print(new String(tmp, 0, j));
						}
						if (channel.isClosed()) {
							System.out.println("exit-status: "
									+ channel.getExitStatus());
							break;
						}
						try {
							Thread.sleep(1000);
						} catch (Exception ee) {
							ee.printStackTrace();
						}
					}
					channel.disconnect();
					session.disconnect();
				}
			System.out.println("******************Program execution finished for all hosts*************");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
