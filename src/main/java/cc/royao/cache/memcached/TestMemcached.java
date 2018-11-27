/*
 *  TestMemcached.java
 *
 *  Author: liudong
 *  http://www.youcity.com
 *  Created @: 2007-4-3
 */
package cc.royao.cache.memcached;


/**
 * 启动memcached服务器端
 * /usr/local/memcached/bin/memcached -d -m 50 -p 11211 -u root
 * @author jkliu
 */
public class TestMemcached {

	public static void main(String[] args) {
		String[] servers = { "192.168.1.182:11211"};
		SockIOPool pool = SockIOPool.getInstance();
		pool.setServers( servers );
		pool.setFailover( true );
		pool.setInitConn( 100 );
		pool.setMinConn( 100 );
		pool.setMaxConn( 250 );
		pool.setMaintSleep( 30 );
		pool.setNagle( false );
		pool.setSocketTO( 3000 );
		pool.setAliveCheck( true );
		pool.initialize();

		MemCachedClient memCachedClient = new MemCachedClient();
		//String token = (String)memCachedClient.get("Thread-1_1");
		memCachedClient.set("1","123");
		String token = (String)memCachedClient.get("1");
		System.out.println(token);
		// turn off most memcached client logging:
		/*
		Logger.getLogger( MemCachedClient.class.getName() ).setLevel( Logger.LEVEL_WARN );

		for ( int i = 0; i < 10; i++ ) {
			new MyThread(memCachedClient).start();
		}*/
		//System.out.println("TIME: %d\n"+(System.currentTimeMillis()-ct));
	}

	public static class MyThread extends Thread {
		MemCachedClient client;
		public MyThread(MemCachedClient mc){
			this.client = mc;
		}
		public void run(){
			long ct = System.currentTimeMillis();

			for ( int i = 0; i < 10; i++ ) {
				String key = getName() + "_" + i;
				boolean success = client.set( key, "Hello!" );
				String result = (String)client.get(key);
				System.out.printf("%s: set( %d ): %s\n", getName(), i, success);
				System.out.printf("%s: get( %d ): %s\n", getName(), i, result);
			}
			System.out.printf("%s: TIME: %d \n", getName(),(int)(System.currentTimeMillis()-ct));
		}
	}
}
