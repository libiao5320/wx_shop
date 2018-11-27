package cc.royao.Synchro;

import java.nio.charset.Charset;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import cc.royao.cache.memcached.MemCachedClient;
import cc.royao.cache.memcached.SockIOPool;
import cc.royao.http.HttpAsyncClientUtil;
import cc.royao.http.HttpHeader;
import cc.royao.http.HttpProcessException;
import cc.royao.http.HttpAsyncClientUtil.IHandler;



public class SynchroLogin {
	private static final Logger logger = Logger.getLogger(SynchroLogin.class);
	private final String url = "http://192.168.1.177/api.php/User/myinfoByPhone";
	private final String[] servers = { "192.168.1.177:11211"}; // Memecached 服务器地址
	
	public void SessionWrite(final String phone,final String openid,String token) throws HttpProcessException {
		IHandler handler = new IHandler() {
			@Override
			public Object failed(Exception e) {
				logger.error("连接出错",e);
				return null;
			}

			@Override
			public Object completed(String respBody) {
				logger.debug("成功返回信息");
				
				JSONObject result = JSONObject.parseObject(respBody);
				if(result.get("status") != null && Integer.valueOf(result.get("status").toString()) == 1){
					JSONArray jsonArray = (JSONArray) result.get("data");
					System.out.println(jsonArray.get(0));
					
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
					memCachedClient.set("token",jsonArray.get(0).toString());
					String token = (String)memCachedClient.get("token");
					System.out.println(token);
				}else{
					logger.error("json 解析出错");
					logger.info(respBody);
				}
				
				return null;
			}

			@Override
			public Object cancelled() {
				logger.info("连接取消");
				return null;
			}
		};
	    HttpHeader.custom().userAgent("Mozilla/5.0 (Windows NT 6.1; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/32.0.1700.107 Safari/537.36");
	    Map<String, Object> parasMap = new HashMap<String, Object>();
	    parasMap.put("phone", phone);
	    parasMap.put("openid", openid);
	    HttpAsyncClientUtil.send(url, parasMap, HttpHeader.custom().build(), Charset.defaultCharset().name() ,handler );
	}
	

}
