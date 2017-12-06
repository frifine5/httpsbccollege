package com.ht;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.UnknownHostException;
import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import org.apache.log4j.Logger;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
public class MinitorSchedulTask {

	public final static String START = "[START]";
	public final static String END = "[END]";
	private static final Logger logger = Logger.getLogger(MinitorSchedulTask.class); 
	
//	@Scheduled(cron = "*/30 * * * * ?")
	@Scheduled(fixedRate=2000)
	public void process() throws UnknownHostException {
		// 获取系统和JVM监控信息
		MonitorInfo info = MonitorInfo.getInstance(); 
		Map<String, Number> data = new HashMap<String, Number>();
		if (info.getTotalJVMMemory() != -1) {
			data.put("totalJVM", info.getTotalJVMMemory());
		}
		if (info.getFreeJVMMemory() != -1) {
			data.put("usedJVM", info.getFreeJVMMemory());
		}
		if (info.getMaxJVMMemory() != -1) {
			data.put("maxJVM", info.getMaxJVMMemory());
		}
		if (info.getFreeJVMMemory() != -1) {
			data.put("freeJVM", info.getFreeJVMMemory());
		}
		if (info.getTotalPhysicalMemory() != -1) {
			data.put("totalPhy", info.getTotalPhysicalMemory());
		}
		if (info.getUsedPhysicalMemory() != -1) {
			data.put("usedPhy", info.getUsedPhysicalMemory());
		}
		if (info.getFreePhysicalMemory() != -1) {
			data.put("freePhy", info.getFreePhysicalMemory());
		}
		if (info.getCpuRatio() != -1) {
			data.put("sysCpu", info.getCpuRatio());
		}
		if (info.getProcessCpuRatio() != -1) {
			data.put("procCpu", info.getProcessCpuRatio());
		}
		if (info.getTotalThread() != -1) {
			data.put("totalThread", info.getTotalThread());
		}

		// *****************-_-!******************

		StringBuilder sb = new StringBuilder(START);
//		sb.append("IP={").append(InetAddress.getLocalHost().getHostAddress());
		sb.append("IP={").append( getIP() );
		sb.append("},osName={").append(info.getOsName()).append("}");
		
		Iterator<Entry<String, Number>> it = data.entrySet().iterator();
		while (it.hasNext()) {
			Entry<String, Number> entry = it.next();
			sb.append(",").append(entry.getKey()).append("={").append( entry.getValue() ) .append("}");
		}
		sb.append(END);
//		System.out.println( sb.toString());
		logger.debug(sb.toString());
	}

	
	protected String getIP(){
		String ip = "";
		String cmds = "ifconfig";
		try{
			Process exec = Runtime.getRuntime().exec(cmds);
			BufferedReader brf = new BufferedReader(new InputStreamReader( exec.getInputStream(), Charset.defaultCharset()));
			String line = null;
			while( (line= brf.readLine())!=null){
				if(line.trim().toLowerCase().startsWith("inet addr") 
						&& line.indexOf("Bcast")>0 ){
					ip =  line.substring(line.indexOf(":")+1, line.indexOf("B")).trim() ;
					break;
				}
			}
			brf.close();
		}catch(Exception e){
			ip = "192.168.0.1";
		}
		return ip;
	}
	
}
