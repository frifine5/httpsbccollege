package com.ht;

import com.sun.management.OperatingSystemMXBean;
import java.lang.management.ManagementFactory;

/**
 * 系统信息获取：
 * <li>调用Runtime获取jvm的系统信息;
 * <li>调用OperatingSystemMXBean部分os系统信息；
 * 
 * @author 王成宇 2017.05.15
 * @modify 王成宇 2017.05.17 新增系统信息部分
 *
 */
@SuppressWarnings("restriction")
public class MonitorInfo {
	/** JVM可使用内存. */
	private long totalJVMMemory;

	/** JVM剩余内存. */
	private long freeJVMMemory;

	/** JVM最大可使用内存. */
	private long maxJVMMemory;

	/** 操作系统. */
	private String osName;

	/** 总的物理内存. */
	private long totalPhysicalMemory;

	/** 剩余的物理内存. */
	private long freePhysicalMemory;

	/** 已使用的物理内存. */
	private long usedPhysicalMemory;

	/** 线程总数. */
	private int totalThread;

	OperatingSystemMXBean osmxb = (OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

	public long getTotalJVMMemory() {
		totalJVMMemory = Runtime.getRuntime().totalMemory();
		return totalJVMMemory;
	}

	public long getFreeJVMMemory() {
		freeJVMMemory = Runtime.getRuntime().freeMemory();
		return freeJVMMemory;
	}

	public long getMaxJVMMemory() {
		maxJVMMemory = Runtime.getRuntime().maxMemory();
		return maxJVMMemory;
	}

	public String getOsName() {
		osName = System.getProperty("os.name");
		return osName;
	}

	public long getTotalPhysicalMemory() {
		totalPhysicalMemory = osmxb.getTotalPhysicalMemorySize();
		return totalPhysicalMemory;
	}

	public long getFreePhysicalMemory() {
		freePhysicalMemory = osmxb.getFreePhysicalMemorySize();
		return freePhysicalMemory;
	}

	public long getUsedPhysicalMemory() {
		usedPhysicalMemory = (osmxb.getTotalPhysicalMemorySize() - osmxb.getFreePhysicalMemorySize());
		return usedPhysicalMemory;
	}

	public int getTotalThread() {
		return totalThread;
	}

	/**
	 * jvm 总内存
	 * 
	 * @return
	 */
	public Number getTotalMemory() {
		long totalMemory = Runtime.getRuntime().totalMemory();
		return totalMemory;
	}

	/**
	 * jvm 最大设置内存
	 * 
	 * @return
	 */
	public Number getMaxMemory() {
		long maxMemory = Runtime.getRuntime().maxMemory();
		return maxMemory;
	}

	public double getCpuRatio() {
		return osmxb.getSystemCpuLoad();
	}

	/**
	 * jvm 当前活动线程数
	 * 
	 * @return
	 */
	public Number getActiveThread() {
		// 获得线程总数
		ThreadGroup parentThread;
		for (parentThread = Thread.currentThread().getThreadGroup(); parentThread
				.getParent() != null; parentThread = parentThread.getParent())
			;
		int totalThread = parentThread.activeCount();
		return totalThread;
	}

	/**
	 * jvm 空闲内存
	 * 
	 * @return
	 */
	public Number getFreeMemory() {
		long freeMemory = Runtime.getRuntime().freeMemory();
		return freeMemory;
	}

	/**
	 * jvm 瞬时使用内存
	 * 
	 * @return
	 */
	public Number getUsingMemory() {
		Long usingMemory = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
		return usingMemory;
	}

	public double getProcessCpuRatio() {
		return osmxb.getProcessCpuLoad();
	}

	// singleton: lazy loading by inner class // {
	public static MonitorInfo getInstance() {
		return SingletonHolder.instance;
	}

	private MonitorInfo() {
	}

	private static class SingletonHolder {
		private final static MonitorInfo instance = new MonitorInfo();
	}
	// }
	
}
